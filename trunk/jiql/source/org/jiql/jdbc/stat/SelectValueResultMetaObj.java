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

public class SelectValueResultMetaObj extends ResultObj implements Serializable
{
	String cn = null;
	int type = Types.BIGINT;
	String typeName = "BIGINT";
	Object val = null;

public SelectValueResultMetaObj(org.jiql.jdbc.ResultSet r){
	super(r);

}
public void setValue(Object c){
	val = c;
	cn = val.toString();
		//(val + " RO.setValue " + cn);

}

/*public void setColumnName(String c){
	cn = c;
}*/

public void setColumnType(int c){
	if (Types.VARCHAR == type){
	
		typeName = "VARCHAR";
			type = c;

	}
}
/*public void setColumnTypeName(String c){
	typeName = c;
}*/

public String 	getColumnName(int column)throws SQLException
{
	//("RO.getColumnName " + cn);
	return cn;
}

public int getColumnType(int column)throws SQLException
{
	return type;
}

public String 	getColumnTypeName(int column)throws SQLException
{
	return typeName;
}

public int getColumnCount()throws SQLException
{
	return 1;
}

public int findColumn(String col)throws SQLException{
return 1;
}

 public Object getValue(String col)throws SQLException{
 		//(val + " RO.getValue " + cn);

 	return val;
 }
  public  int size(){
  	return 1;
  }


}


