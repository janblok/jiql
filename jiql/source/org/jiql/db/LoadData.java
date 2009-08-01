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

public class LoadData  implements Serializable
{
	SQLParser sqp = null;
	StringBuffer sb = null;
	Connection connection = null;
	String table = null;
	public LoadData(SQLParser sqp,String tok){
		this.sqp = sqp;
		sb = new StringBuffer( tok.trim());
	}
	public void execute(Connection connection)throws SQLException{
	execute(connection,null);
	}

	public void execute(Connection connection,List<String> l)throws SQLException{
//LOAD DATA INTO TABLE jokes INTEXT 'bla bla';
sb.delete(0,21);
int i = sb.indexOf(" ");
table = sb.substring(0,i);
if (sqp != null)
sqp.setTable(table);
sb.delete(0,i+8);

//( .getTable() + ":LD.execute 3 :" + sb);
this.connection = connection;
load(sb,l);
	}


		public  void load (StringBuffer f)throws SQLException
	{
		load(f,null);
	}
		public  void load (StringBuffer f,List<String> l)throws SQLException
	{
		try{
		boolean tf = true;
		boolean dr = false;
		ByteArrayInputStream bin = new ByteArrayInputStream(f.toString().getBytes());
		InputStreamReader fin = new InputStreamReader(bin);
		try{
		String templ = null;
		LineNumberReader lr = new LineNumberReader(fin);
		String tc = "";
		int isc = -1;
		StringBuffer fields = null;
		int olength = 0;
		while ((templ = lr.readLine()) != null){
		if (!StringUtil.isRealString(templ) || templ.trim().startsWith("--") || templ.trim().startsWith("#"))continue;
		//templ = tc + templ;



	//	templ = StringUtil.replaceLastSubstring(templ,";","");
	//	templ = StringUtil.replaceSubstring(templ,"NG_TM_SC",";");
		//try{
		//tc = "";

		if (fields == null){
			//insert into wp_users (user_login) values ('gabo');
			fields = new StringBuffer("jiqlInsert into ").append(table).append(" (").append(templ).append(") values (");
			olength = fields.length();
		}
		else{
		if (olength != fields.length())	
			fields.delete(olength ,fields.length());
		fields.append(templ).append(")");
		
		//( .getTable() + ":LD.load 3 :" + fields);
		if (l != null)
			l.add(fields.toString());
		else
		connection.createStatement().execute(fields.toString());
		
		}
		//}catch (Exception e){
		//		//( .getTable() + ":LD.load 4 :" + e.toString());
		//		//(e);


	//	}
		}
		}finally{
			fin.close();
		}
		}catch (IOException e){
			tools.util.LogMgr.err("LoadData.parseScript " + e.toString());
			e.printStackTrace();
		}
	}






}


