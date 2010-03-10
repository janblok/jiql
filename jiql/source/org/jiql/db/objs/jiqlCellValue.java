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

package org.jiql.db.objs;
import java.util.*;
import java.sql.Types;
import tools.util.NumberUtil;
import java.text.DateFormat;
//import java.text.SimpleDateFormat;
import org.jiql.util.jiqlException;
import java.sql.SQLException;
import org.jiql.util.SQLParser;
import org.jiql.jdbc.jiqlConnection;
import java.text.SimpleDateFormat;
import com.google.appengine.api.datastore.Blob;
import org.jiql.jdbc.jiqlBlob;
public class jiqlCellValue implements Comparable
{
SQLParser sqp = null;
//jiqlConnection connection = null;
Object co = null;
//String rid = null;
int otype = 0;
//String coln = null;
static String[] dformats = new String[]{"EEE MMM dd HH:mm:ss z yyyy","yyyy/MM/dd","yyyy-MM-dd"};

public jiqlCellValue(Object v,int type,SQLParser sqp)throws SQLException{
//new jiqlCellValue(get(cn),ci.getColumnType())	rid = i;
	otype = type;
	this.sqp = sqp;
co = getObj(v,type,sqp);
/*if (v == null)return ;
	if ( = type )
		co = new String(v.toString());
	else if (  == type)
		co = new  (v.toString());
	else co = v;*/	
	
}

/*public void setColumnName(String c){
	coln = c;
}*/
public static Object getObj(Object v,int type,SQLParser sqp)throws SQLException{
if (v == null)return null;
if (v instanceof com.google.appengine.api.datastore.Text)
v = new String(((com.google.appengine.api.datastore.Text)v).getValue());
	if (Types.VARCHAR == type )
		return new String(v.toString());
	else if (Types.INTEGER == type || Types.BIGINT == type)
		return new Long(v.toString());
	else if (Types.BLOB == type && (v instanceof com.google.appengine.api.datastore.Blob))
		return new jiqlBlob(((com.google.appengine.api.datastore.Blob)v).getBytes());
	else if (Types.FLOAT == type)
		return new Float(v.toString());
	else if (Types.BOOLEAN == type)
		return new Boolean(v.toString());
	else if (Types.DATE == type){
		if(v instanceof Date)
			return new java.sql.Date(((Date) v).getTime());
		else if (!NumberUtil.isNumeric(v.toString()))
		{
			//String dfmt = "yyyy-MM-dd HH:mm:ss";
			//0000-00-00 00:00:00 yyyy-MM-dd HH:mm:ss
		DateFormat df = sqp.getDateFormat();
				//new SimpleDateFormat(dfmt);
				//DateFormat.getInstance() ;
			try{
		//	org.jiql.util.JGUtil.log("  " +  );
			//		DateFormat df =  .getDateFormat();
	
			return new java.sql.Date(df.parse(v.toString()).getTime());
			}catch (Throwable e){
			 //org.jiql.util.JGUtil.log(e);
			 for (int ct = 0;ct < dformats.length;ct++)
			 	try{
			 					df = new SimpleDateFormat(dformats[ct]);
			 		return new java.sql.Date(df.parse(v.toString()).getTime());			

			 	}catch (Exception e2){
			 	}
			 throw jiqlException.get("invalid_date_format",new StringBuffer(v.toString()).append(" Invalid DateTime Format. Should be like ").append(sqp.getDatePattern()).toString());
	
			}
		}	
		return new java.sql.Date(Long.valueOf(v.toString()).longValue());
	}
	return v;	
	
}

/*public String getRowId(){
	return rid;
}*/


public int compareTo(Object o) 
{

if (co == null && o == null)return 0;
	if (co == null || co.toString() == null)return -1;
	if (o == null || o.toString() == null)return 1;


	//if (co == null || o == null || o.toString() == null || co.toString() == null)return 1;
	try{
	
	o = getObj(o,otype,sqp);
	}catch (Exception e){
		tools.util.LogMgr.err(o + " jiqlCellValue.compareTo " + e);
		
	}
	int r =  ((Comparable)co).compareTo(o);

	//(r + ":" + co + " COMORE " + o);
return r;
//return -1;
}

public String toString(){
	if (co == null)
		return null;
	return co.toString();
}


}


