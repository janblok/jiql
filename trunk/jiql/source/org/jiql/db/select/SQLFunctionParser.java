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

package org.jiql.db.select;
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

public class SQLFunctionParser  implements Serializable
{


		static Hashtable<String,FunctionBase> supporedtFunctions = new Hashtable<String,FunctionBase>();
	static{
		supporedtFunctions.put("year",new YearFunction());
		supporedtFunctions.put("month",new MonthFunction());
		supporedtFunctions.put("count",new CountFunction());

	}

	Hashtable<String, String> hasFunctions  = new Hashtable<String, String>();	

	public StringBuffer parse(StringBuffer tok,String alias)throws SQLException{

		//(alias + " SQLFunctionParser 1 " + tok);
		int i = tok.indexOf("(");
		if (i < 1)return tok;
		String f = tok.substring(0,i).toLowerCase();
		//(alias + " SQLFunctionParser 2 " + f);

		if (supporedtFunctions.get(f) == null)
			return tok;
		tok.delete(0,i + 1);
		//(alias + " SQLFunctionParser 3 " + tok);

		i = tok.indexOf(")");
		if (i < 1)return tok;
		tok.delete(i,tok.length());
		//(alias + " SQLFunctionParser 4 " + tok);

		if (alias != null)
		hasFunctions.put(alias,f);
		else
		hasFunctions.put(tok.toString(),f);	

		return tok;


	}

	public FunctionBase getFunction(String t){
		String f = hasFunctions.get(t);
		if (f == null)return null;
		return supporedtFunctions.get(f);
	}
	public boolean hasFunction(String f){
		Enumeration en = hasFunctions.elements();
		while (en.hasMoreElements())
			if (en.nextElement().equals(f))
				return true;
		return false;
		//return (hasFunctions.get(t) != null);
	}

}


