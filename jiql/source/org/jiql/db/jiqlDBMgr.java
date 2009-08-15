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
import org.jiql.db.cmds.*;
import org.jiql.db.objs.*;
import org.jiql.util.*;
public class jiqlDBMgr
{
	static Hashtable commands = new Hashtable();
	//static jiqlDBMgr jDBMgr = null;
	static{
		commands.put("dropTable",new DropTableCommand());
		commands.put("addPrimaryKeys",new AddPrimaryKeysCommand());
		commands.put("verifyPrimaryKeys",new VerifyPrimaryKeysCommand());
		commands.put("addConstraint",new AddConstraint());
		commands.put("verifyConstraints",new VerifyConstraints());
		commands.put("VerifyNotNulls",new VerifyNotNulls());
		commands.put("VerifyTable",new VerifyTable());
		commands.put("VerifyDefaultValues",new VerifyDefaultValues());
		commands.put("VerifyTypeValues",new VerifyTypeValues());
		commands.put("SortResult",new SortResult());
		

	}
	
	public static  jiqlDBMgr get(Properties p){
		return new jiqlDBMgr(p);
		/*if (jDBMgr == null)
			jDBMgr = new jiqlDBMgr(p);
		return jDBMgr;*/
	}
	Properties properties = null;
	public Properties getProperties(){
		return properties;
	}	
	public jiqlDBMgr(Properties p){
		properties = p;
		/*commands.put("dropTable",new DropTableCommand());
		commands.put("addPrimaryKeys",new AddPrimaryKeysCommand());
		commands.put("verifyPrimaryKeys",new VerifyPrimaryKeysCommand());
		commands.put(" ",new  ());
		commands.put("verifyConstraints",new VerifyConstraints());
		commands.put("VerifyNotNulls",new VerifyNotNulls());
		commands.put("VerifyTable",new VerifyTable());
		commands.put("VerifyDefaultValues",new VerifyDefaultValues());
		commands.put("VerifyTypeValues",new VerifyTypeValues());
		commands.put("SortResult",new SortResult());
		*/
		
		
		
		
	}
public DBCommand getCommand(String c)throws SQLException{

	return (DBCommand)commands.get(c);
}

Hashtable tableinfos = new  Hashtable();
public void saveTableInfo(String t)throws SQLException{
jiqlTableInfo ti = getTableInfo(t);
saveTableInfo(t,ti);
}

public void saveTableInfo(String t,jiqlTableInfo ti)throws SQLException{
synchronized (ti)
{
	String tis = ti.toString();
	//("TIS: " + tis);
	Gateway.get(getProperties()).writeTableProp(t,tis,ti);
}
}

public jiqlTableInfo getTableInfo(String t)throws SQLException{
return getTableInfo(t,false);
}

public jiqlTableInfo getTableInfo(String t,boolean create)throws SQLException{
		if (t == null)return null;
		jiqlTableInfo uti = (jiqlTableInfo)tableinfos.get(t);
		if (uti != null)return uti;
			JGNameValuePairs o = Gateway.get(getProperties()).readTableProp(t);

		 Object tis = null;
		 	if (o != null)
		 		tis = o.get("tableprop");
	//(tis + " getTableInfo1 " + o + ":" + t);
		 if (tis == null && !create)return null;

	if (tis instanceof com.google.appengine.api.datastore.Text)
	tis = new String(((com.google.appengine.api.datastore.Text)tis).getValue());
		if (tis == null)
			tis = new String("");
	
		 uti = new jiqlTableInfo(tis.toString());
		 uti.setName(t);
		 if (o != null)
		 	uti.setTId(o.getKeyId());
		 tableinfos.put(t,uti);
		return uti;
}




public void removeTableInfo(String t)throws SQLException{
	tableinfos.remove(t);
	//Gateway.get(getProperties()).removeTableProp(t);


}




}


