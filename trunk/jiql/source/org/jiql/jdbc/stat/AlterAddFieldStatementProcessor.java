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
import org.jiql.db.objs.jiqlTableInfo;
import org.jiql.db.jiqlDBMgr;
import org.jiql.util.CacheMgr;


public class AlterAddFieldStatementProcessor extends StatementProcessor implements Serializable
{


 public org.jiql.jdbc.ResultSet process(SQLParser sqp)throws SQLException{


 		//Object o = sqp.getSelectParser().getSelectValue().getValue();
  		//int  i = sqp.getSelectParser().getSelectValue().getType();


			jiqlTableInfo ti = sqp.getJiqlTableInfo();
		//	ti.setPrefix(sqp.hasPrefix());
		//	ti.setPrefixName(sqp.getPrefixValue());
		//	ti.setTableLeafs(sqp.hasTableleafs());


			Hashtable hash = sqp.getHash();
			//Enumeration en = hash.keys();
			Hashtable dv = sqp.getDefaultValues();
			Enumeration dven = dv.keys();
			//(dv + " createTable " + hash);
			//{yesno=share titbit, countf=22} createTable {name=varchar(18), yesno=varchar(5), countf=int} 
			
			String n = null;
			String v = null;
			int ml = 0;
					while(dven.hasMoreElements())
			{
				n = dven.nextElement().toString();
				v = (String)hash.get(n);
			if(v == null)
				throw JGException.get("no_column_for_default_value","No Column for default value " + n);
			if (v.startsWith("varchar(")){
		v = v.substring("varchar(".length(),v.length()-1);
		ml =  Integer.parseInt(v);
		v = (String)dv.get(n);
		if (v.length() > ml)
				throw JGException.get("default_value_cannot_exceed_max_column_length",v + " Default value cannot exceed max column length " + n);

			}
			
				
			}
					Gateway gappe = Gateway.get(sqp.getProperties());
	
			gappe.writeTableInfo(sqp.getTable(),hash);


			
			//jiqlDBMgr.get(sqp.getProperties()).getCommand("addPrimaryKeys").execute(sqp);
			Enumeration en = sqp.getPrimaryKeys().elements();
			while (en.hasMoreElements())
				ti.addPrimaryKey(en.nextElement().toString());
	
			Hashtable df = sqp.getDefaultValues();
				 en = df.keys();
			//	String n = null;
			while (en.hasMoreElements()){
				n = en.nextElement().toString();
				ti.addDefaultValues(n,df.get(n));
			}
			
			en = sqp.getNotNulls().elements();
			while (en.hasMoreElements())
				ti.addNotNull(en.nextElement().toString());
			
				
			//ti.setDefaultValues(sqp.getDefaultValues());
			//ti.setNotNulls(sqp.getNotNulls());
			jiqlDBMgr.get(sqp.getProperties()).saveTableInfo(sqp.getTable(),ti);
//if (!sqp.isRemote())
CacheMgr.removeMetaCache(sqp.getConnection().getProperties().getProperty("baseUrl"),sqp.getTable());
//else
sqp.setAttribute("removeMetaCache",sqp.getTable());


 		return null;
 }



}


