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

package org.jiql.db.jdbc.stat;
import java.util.*;
import java.sql.*;
import java.io.*;
import org.jiql.util.JGNameValuePairs;
import tools.util.EZArrayList;
import tools.util.StringUtil;
import org.jiql.util.Criteria;
import org.jiql.util.SQLParser;
import org.jiql.util.Gateway;
import org.jiql.db.objs.jiqlCellValue;
import tools.util.NameValuePairs;
import org.jiql.util.JGException;
import org.jiql.util.JGUtil;
import org.jiql.db.Row;

public class SQLDumpStatementProcessor extends StatementProcessor implements Serializable
{

  public boolean isSpecial(){
 	return true;
 }
 public String getName(){
 	return "sqldump";
 }
 public org.jiql.jdbc.ResultSet process(SQLParser sqp)throws SQLException{
 			Vector<Row> r = new Vector<Row>();
 			//sqp.setResults(r);
			//r.add(new Row());
		ResultSet res1 = null; 
		Vector<String> tabl = new Vector<String>();
		int i = sqp.getToken().indexOf(" ");
		if (i > 0)
		{
			String tok = sqp.getToken().substring(i + 1,sqp.getToken().length());
		EZArrayList ez = new EZArrayList(new StringTokenizer(tok,","));
		for (int ct = 0;ct < ez.size();ct++)
			if (StringUtil.isRealString(ez.elementAt(ct).toString()))
			tabl.add(ez.elementAt(ct).toString());

		}
		if (tabl.size() < 1){
		String[] types = {"TABLE"};
		
		res1 = sqp.getConnection().getMetaData().getTables(null, null, "%", types);
		while (res1.next())
			tabl.add(res1.getString(3));
		}

		Statement stat = sqp.getConnection().createStatement();
		Row ro = null;
		Object v2b = null;
		//String sql1 = "INSERT INTO " + tabl.elementAt(ct) + " VALUES (" ;
		StringBuffer entry = null;
		for (int ct = 0;ct < tabl.size();ct++)
		{
			res1 = stat.executeQuery("select * from " + tabl.elementAt(ct));
		String sql1 = "INSERT INTO " + tabl.elementAt(ct) + " VALUES (" ;
		//(" YELLO " + sql1);
		while (res1.next()){
			//Vector v2 = (Vector)v.elementAt(v1);
	//String dv = "";
		entry = new StringBuffer(sql1);
		for (int cv1 = 1;cv1 <= res1.getMetaData().getColumnCount(); cv1++){
			v2b = res1.getObject(cv1);
			if (v2b == null)v2b = "null";
			if (cv1 > 1)entry.append(",");
			if (v2b instanceof Integer || v2b instanceof Long || v2b instanceof Short || v2b.equals("null"))
				entry.append(v2b.toString());
			else
				entry.append("'").append(v2b.toString()).append("'");
		}
		entry.append(");");
		ro = new Row();
		ro.put("INSERT",entry.toString());
		r.add(ro);
		}


		}

		 			Vector r2 = new Vector();
			r2.add(new Row());

		org.jiql.jdbc.ResultSet resultset = new org.jiql.jdbc.ResultSet(r2,sqp);
 		SQLDumpResultMetaObj rmo = new SQLDumpResultMetaObj(resultset);
 		rmo.setRows(r);
 		//Object o = sqp.getSelectParser().getSelectValue().getValue();
  		//int  i = sqp.getSelectParser().getSelectValue().getType();
		//rmo.setValue(o);
		//rmo.setColumnType(i);
 		return resultset;
 }



}


