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

public class AutoIncrement  implements Serializable
{

	
	//Vector<String> autoincrement = new Vector<String>();
	SQLParser sqp = null;
	public AutoIncrement(SQLParser sqp){
		this.sqp = sqp;
	}


/*	public String parse(String n,String tok){
int i3 = tok.toLowerCase().indexOf("auto_increment ");
		//(tok + " groupby " + i3);
			if (i3 > 0){
				String tok1 = tok.substring(0,i3);
				String tok2 = tok.substring(i3 +  "auto_increment ".length(),tok.length());
				tok2 = StringUtil.getTrimmedValue(tok2);

				if (!autoincrement.contains(n))
					autoincrement.add(n);
				return tok1 + " " + tok2;


			}else
				return tok;
			//return tok;


	}*/



	public StringBuffer parse(String n,StringBuffer tok)throws SQLException{
String tokstr = "auto_increment";
int i3 = tok.toString().toLowerCase().indexOf(tokstr);
	//( n + " addAutoIncrement2  " + i3 + ":" + tok);

			if (i3 > 0){
			//	if (autoincrement.contains(n))return tok;
	        	tok.replace(i3,i3 + tokstr.length()," ");

			
					sqp.getJiqlTableInfo(true).addAutoIncrement(n);
					return tok;


			}else
				return tok;


	}



}


