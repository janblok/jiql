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
import org.jiql.util.SQLParser;
import org.jiql.jdbc.ResultSet;

public abstract class ResultMetaObj  implements Serializable
{
	org.jiql.jdbc.ResultSet result = null;


public ResultMetaObj(org.jiql.jdbc.ResultSet r){
	result = r;

}

public org.jiql.jdbc.ResultSet 	getResultSet()throws SQLException{
	return result;
}

public String 	getTableName(int column)throws SQLException{
	return result.getSQLParser().getAction();
}
	public String 	getCatalogName(int column)throws SQLException{
		return getTableName(column);
	}

public int 	getColumnDisplaySize(int column)throws SQLException{
	return getColumnName(column).length();
}
public String 	getColumnLabel(int column)throws SQLException{
return getColumnName(column);
}


public abstract String 	getColumnName(int column)throws SQLException;

public abstract int getColumnType(int column)throws SQLException;

public abstract String 	getColumnTypeName(int column)throws SQLException;

public abstract int getColumnCount()throws SQLException;

}


