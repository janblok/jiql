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


public class ColumnInfo implements Serializable,Cloneable
{
org.jiql.util.JGNameValuePairs nvp = null;

public ColumnInfo(JGNameValuePairs v){
	nvp = v;
}

String dn = null;
public void setDisplayName(String d){
	dn = d;
}
public ColumnInfo copy()throws SQLException{
	try{
	
	return (ColumnInfo)clone();
	}catch (Exception e){
		throw new SQLException("ColumnInfo.copy " + e.toString());
	}
	
}

public String getDisplayName(){
	if (dn != null)return dn;
	return getName();
}

public String getName(){
	return nvp.get("tablefield").toString();
}

public String getTypeName(){
	return nvp.get("tablefieldtype").toString();
}

public int getMaxLength(){
	String t = getTypeName();
	t = t.toLowerCase();
	if (t.startsWith("varchar(")){
		t = t.substring("varchar(".length(),t.length()-1);
		t = t.trim();
		return Integer.parseInt(t);
	}
	if (t.startsWith("tinyint(")){
		t = t.substring("tinyint(".length(),t.length()-1);
		int i = t.indexOf(")");
		if (i > 0)
			t = t.substring(0,i);
		return Integer.parseInt(t);
	}
	if (t.startsWith("bigint(")){
		t = t.substring("bigint(".length(),t.length()-1);
		int i = t.indexOf(")");
		if (i > 0)
			t = t.substring(0,i);
		return Integer.parseInt(t);
	}	
	return 0;
}

public int getColumnSize(){
 int cs = getMaxLength();
 if (cs > 0)return cs;
 	return getName().length();
}


public String 	getStandardTypeName()throws SQLException{
StringBuffer tn =  new StringBuffer(getTypeName());
int i = tn.indexOf("(");
if (i > 0)
tn.delete(i,tn.length());
return tn.toString();

}

public boolean isNumeric()throws SQLException{
	int ct = getColumnType();
	return (ct == Types.INTEGER || ct == Types.BIGINT);
}
/*public static int getTypeFromName(String tn){

}*/
public int 	getColumnType()throws SQLException{
String tn =  getTypeName().toLowerCase();
return getTypeFromName(tn);
}

public static int 	getTypeFromName(String tn)throws SQLException{
//String tn =  getTypeName().toLowerCase();
	if (tn.startsWith("bigint"))
	 return Types.BIGINT;
	else if (tn.startsWith("int"))
		return Types.INTEGER;
	else if (tn.startsWith("var") || tn.indexOf("text") > -1)
		return Types.VARCHAR;
	else if (tn.startsWith("text"))
		return Types.VARCHAR;
	else if (tn.startsWith("bool"))
		return Types.BOOLEAN;
	else if (tn.startsWith("tinyint"))
		return Types.INTEGER;
	else if (tn.startsWith("blob"))
		return Types.BLOB;
	//else if (tn.startsWith("bigint"))
	//	return Types.INTEGER;

	else if (tn.startsWith("float"))
		return Types.FLOAT;
	else if (tn.startsWith("date") || tn.indexOf("timestamp") > -1)
		return Types.DATE;
	else
		return Types.OTHER;

}

public String toString(){
	return "ColumnInfo:" + nvp;
}

}


