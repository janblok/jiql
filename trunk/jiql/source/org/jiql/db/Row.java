/*
 Copyright (c) 2007-2009 WebAppShowCase DBA Appcloem (http://www.appcloem.com). All rights reserved.
Apache Software License 2.0
Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

  1. Redistributions of source code must retain the above copyright notice,
     this list of conditions and the following disclaimer.

  2. Redistributions in binary form must reproduce the above copyright
     notice, this list of conditions and the following disclaimer in
     the documentation and/or other materials provided with the distribution.

  3. The names of the authors may not be used to endorse or promote products
     derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL WebAppShowCase
OR ANY CONTRIBUTORS TO THIS SOFTWARE BE LIABLE FOR ANY DIRECT, INDIRECT,
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package org.jiql.db;
import java.util.*;
import java.sql.*;
import java.io.*;
import tools.util.NameValuePairs;
import org.jiql.db.objs.*;
import org.jiql.util.*;

public class Row extends NameValuePairs implements Comparable, Serializable
{
String rid = null;
	private String tn = null;
	private String reid = null;

	public void setTableName(String ki){
		tn = ki;
	}
	public String getTableName(){
		return tn;
	}

	public void setRealID(String ki){
		reid = ki;
	}
	public String getRealID(){
		return reid;
	}
	
public Row(){
	super();
}
public Row(JGNameValuePairs h){
	super(h);
	setRowId(h.getKeyName() + "_" + h.getTableName());
	setTableName(h.getTableName());
	setRealID(h.getKeyName());
}
public void setRowId(String id){
	rid = id;
}

public String getRowId(){
	return rid ;
}
SQLParser sqp = null;
public void setSQLParser(SQLParser s){
	 sqp = s;
}


public SQLParser getSQLParser(){
	return sqp;
}


Union union = null;
public void setUnion(Union s){
	 union = s;
}


public Union getUnion(){
	return union;
}

public int compareTo(Object o) 
{

if (sqp == null)return -1;
	
	if (o == null)
		return 1;
	Row ro = (Row)o;
	
			Vector sl = sqp.getSortList();
String cn = null;
ColumnInfo ci = null;
TableInfo ti = sqp.getTableInfo();
int r = 0;
Comparable c1 = null;
Comparable c2 = null;
	for (int ct = 0;ct < sl.size();ct++)
	{
			cn = (String)sl.elementAt(ct);
			cn = sqp.getRealColName(cn);
			ci = ti.getColumnInfo(cn);

			
			c1 = (Comparable)get(cn);
			c2 = (Comparable)ro.get(cn);
			//(r + ":" + cn + ":" + c2 + ":" + ci + ":SIFDOR "  + c1);
			
			if (c1 == null)
				r = -1;
			else if (c2 == null)
				r = 1;
			else
			r = ((Comparable)get(cn)).compareTo(((Comparable)ro.get(cn)));
			
			
			if (r != 0)break;

	}
	
//return r;
return r;
}

public Object getObject(String cn){
	return super.get(cn);
}

public Object get(String cn){
			if (sqp == null)
				try{
				
				return jiqlCellValue.getObj(super.get(cn),Types.VARCHAR,sqp);
		}catch (Exception e){
				tools.util.LogMgr.err(cn + " ERROR Row.get null sqp 1 " + e.toString() + ":" + sqp);
				e.printStackTrace(System.out);
				JGUtil.log(e);

		}
			cn = sqp.getRealColName(cn);
TableInfo ti = sqp.getTableInfo();
		ColumnInfo	ci = ti.getColumnInfo(cn);
		if (ci == null && union != null)
		{try{
		
			
			String t = union.findTable(cn);
			if (t != null)
			{
				SQLParser squ = union.getAnySQLParser(t);
					
				cn = squ.getRealColName(cn);
				ti = squ.getTableInfo();
				//(squ.getTable() + " TI GET INGO " + ti);
				ci = ti.getColumnInfo(cn);
				
				
			}
		}catch (Exception e){
				tools.util.LogMgr.err(cn + " ERROR union Row.get " + e.toString() + ti + ":" + ci);
				e.printStackTrace();

		}
		}
			try{
			//(cn + ":" + this + ":" + ci + ":SIFDOR "  + super.get(cn));
			if (ci == null)
			{
					String t = (String)sqp.getAliases().get(sqp.getTable());
					if (t != null)
					{
						sqp.setTable(t);
				ti = sqp.getTableInfo();
				//(sqp.getTable() + " TI GET INGO 2 " + ti);
				ci = ti.getColumnInfo(cn);

					}
			}
			return jiqlCellValue.getObj(super.get(cn),ci.getColumnType(),sqp);
			}catch (Exception e){
				tools.util.LogMgr.err(cn + " ERROR Row.get 2 " + super.get(cn) + " " + e.toString() + ":" + ci + ":" + union + ":" + sqp);
				//e.printStackTrace();
				//e.printStackTrace(System.out);
				JGUtil.log(e);

			}
return null;
}

public String getString(String cn){
	if (get(cn) == null)return null;
	return super.getString(cn);
	
}

public int getInt(String cn){
	if (get(cn) == null)return 0;
	return super.getInt(cn);
	
}

public long getLong(String cn){
	if (get(cn) == null)return 0;
	return super.getLong(cn);
	
}



}


