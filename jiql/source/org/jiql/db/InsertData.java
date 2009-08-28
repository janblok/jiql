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
import org.jiql.jdbc.jiqlPreparedStatement;

public class InsertData  implements Serializable
{
StringBuffer left = new StringBuffer("jiqlInsert ");
	StringBuffer sb = null;
	Connection connection = null;
	String table = null;
	public InsertData(SQLParser sqp,String tok){
//		this.sqp = sqp;
		sb = new StringBuffer( tok.trim());
	}
	public void execute(Connection connection,Hashtable h)throws SQLException{
	execute(connection,null,h);
	}

	public void execute(Connection connection,List<String> l,Hashtable h)throws SQLException{
//INSERT INTO `wp_term_relationships` (`object_id`, `term_taxonomy_id`, `term_order`) VALUES
//(1, 2, 0),
//(2, 2, 0),
//int i = sb.toString().toLowerCase().indexOf("into");
//("ID1 " + sb);
sb.delete(0,6);
int i = sb.indexOf(")");
if (i < 0)throw new SQLException ("Missing CLOSING BRACKET term in insert statement " + sb);

while (true){
int i2 = sb.toString().toLowerCase().indexOf("values");
//INSERT INTO `schema_info` VALUES (7);
//INTO `schema_info` valjiqlues (7)
if (i2 < 0)break;
//throw new SQLException ("Missing VALUES term in insert statement " + sb);
if (i2 < i){
	sb.replace(i2,i2 + 6,"valjiqlues");
	continue;
}
left.append(sb.substring(0,i2 + 6)).append(" ");
sb.delete(0,i2 + 6);
break;
}
while (true){
int i2 = left.indexOf("valjiqlues");
if (i2 > 0){
	sb.replace(i2,i2 + 10,"values");
	continue;
}
break;
}
//(left +" ID2 " + sb);

this.connection = connection;
load(sb,l,h);
	}


		public  void load (StringBuffer f)throws SQLException
	{
		load(f,null,new Hashtable());
	}
		public  void load (StringBuffer f,List<String> l,Hashtable h)throws SQLException
	{
			boolean exec = false;

		try{
		boolean tf = true;
		boolean dr = false;
		String fs = f.toString().trim();
		if (!fs.endsWith(","))fs = fs + ",";
		ByteArrayInputStream bin = new ByteArrayInputStream(fs.getBytes());
		InputStreamReader fin = new InputStreamReader(bin);
		try{
		String templ = null;
		LineNumberReader lr = new LineNumberReader(fin);
		String tc = "";
		int isc = -1;
		StringBuffer fields = null;
		int olength = 0;
		while ((templ = lr.readLine()) != null){
		//(left +" ID4 " + templ);

		if (!StringUtil.isRealString(templ) || templ.trim().startsWith("--") || templ.trim().startsWith("#"))continue;
		templ = tc + templ;


		if (!StringUtil.getTrimmedValue(templ).endsWith(",")){

			tc = templ;
			continue;
		}
			templ = templ.trim();
			templ = templ.substring(0,templ.length() - 1);
					fields = new StringBuffer(left).append(templ);


		/*if (fields == null){
			//insert into wp_users (user_login) values ('gabo');
			fields = new StringBuffer(left).append(templ);
		}
		else{
		if (left.length() != fields.length())
			fields.delete(left.length() ,fields.length());
		fields.append(templ);*/

		//( .getTable() + ":LD.load 3 :" + fields);
		if (l != null)
			l.add(fields.toString());
		else{
		
		org.jiql.jdbc.Statement stm = (org.jiql.jdbc.Statement)connection.createStatement();
		stm.setDirectValues(h);
		stm.execute(fields.toString());
		}
//(left +" ID3 " + fields);

	//	}
		//}catch (Exception e){
		//		//( .getTable() + ":LD.load 4 :" + e.toString());
		//		//(e);


	//	}
	exec = true;
		}
		}finally{
			fin.close();
		}
		}catch (IOException e){
			tools.util.LogMgr.err("InsertData.parseScript " + e.toString());
			e.printStackTrace();
		}
		if (!exec)throw new SQLException("Incomplete SQL Insert Statement");
	}






}


