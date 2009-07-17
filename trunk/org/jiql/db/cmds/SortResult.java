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

package org.jiql.db.cmds;
import java.util.*;
import java.sql.*;
import java.io.*;
import org.jiql.util.*;
import tools.util.EZArrayList;
import org.jiql.db.*;
import org.jiql.db.objs.*;


public class SortResult extends DBCommand
{
public Object execute(SQLParser sqp)throws SQLException{
			//Hashtable ch = cgw.getConstraints(,sqp.getTable());
		Vector sl = sqp.getSortList();
		//("ST 1 " + sl);
		if (sl.size() < 1 || sqp.isCount())return null;

		//TableInfo ti = Gateway.get(sqp.getProperties()).readTableInfo(sqp.getTable());
		//sqp.setTableInfo(ti);

ColumnInfo ci = null;
			//Hashtable inv = sqp.getHash();
	//		Enumeration en = inv.keys();
String cn = null;
Hashtable resulto = sqp.getResultsTable();

//("ST 2 " + resulto);

if (resulto == null || resulto.size() < 2)return null; 
Vector resultn = new Vector();

Enumeration en = resulto.keys();

Hashtable newT = new Hashtable();
Hashtable rT = null;
String rid = null;
String sid = null;
Object sido = null;
		/*
		while (en.hasMoreElements())
			{
		rid = en.nextElement().toString();
		rT = (Hashtable)resulto.get(rid);
		sid = "";

			for (int sct = 0;sct < sl.size();sct++)
				
			{

			cn = (String)sl.elementAt(sct);
			cn = sqp.getRealColName(cn);
			sido = rT.get(cn);
			if (sido != null)
				sid = sid + sido + "_";
	
			}
			sid = sid + "_" + rid;
			newT.put(sid,rid);

			}
			String[] sR = new EZArrayList(newT.keys()).toStringArray();
			Arrays.sort(sR);
			if (!sqp.isDescending())
			{
				for (int ct = 0;ct < sR.length;ct++)
				{
					sid = sR[ct];
					rid = newT.get(sid).toString();
					resultn.add(resulto.get(rid));
					
				}
			}
			else
			{
				for (int ct = (sR.length -1);ct >= 0;ct--)
				{
					sid = sR[ct];
					rid = newT.get(sid).toString();
					resultn.add(resulto.get(rid));
					
				}
			}*/




/*		Object[] sR = new Object[resulto.size()];

	int cto = 0;
		while (en.hasMoreElements())
			{
		rid = en.nextElement().toString();
		rT = (Hashtable)resulto.get(rid);


			cn = (String)sl.elementAt(0);
			cn = sqp.getRealColName(cn);
			sido = rT.get(cn);
			 (rid + ":SIFDOR " + sido + ":" + cn + ":" + rT);
			ci = ti.getColumnInfo(cn);
			sR[cto] = new  (sido,ci.getColumnType(),rid);

			cto = cto + 1;

			}
			Arrays.sort(sR);
			if (!sqp.isDescending())
			{
				for (int ct = 0;ct < sR.length;ct++)
				{
					rid = (( )sR[ct]).getRowId();
					resultn.add(resulto.get(rid));
					
				}
			}
			else
			{
				for (int ct = (sR.length -1);ct >= 0;ct--)
				{
						rid = (( )sR[ct]).getRowId();
					resultn.add(resulto.get(rid));
				
				}
			}*/




		Vector reso = sqp.getResults();
		Object[] sR = new EZArrayList(reso.elements()).toArray();
		

/*	int cto = 0;
		while (en.hasMoreElements())
			{
		rid = en.nextElement().toString();
		rT = (Hashtable)resulto.get(rid);


			cn = (String)sl.elementAt(0);
			cn = sqp.getRealColName(cn);
			sido = rT.get(cn);
			 (rid + ":SIFDOR " + sido + ":" + cn + ":" + rT);
			ci = ti.getColumnInfo(cn);
			sR[cto] = new  (sido,ci.getColumnType(),rid);

			cto = cto + 1;

			}*/
			Arrays.sort(sR);
			if (!sqp.isDescending())
			{
				for (int ct = 0;ct < sR.length;ct++)
				{
					resultn.add(sR[ct]);
					
				}
			}
			else
			{
				for (int ct = (sR.length -1);ct >= 0;ct--)
				{
					resultn.add(sR[ct]);

				
				}
			}








			sqp.setResults(resultn,false);
			return null;
}



}


