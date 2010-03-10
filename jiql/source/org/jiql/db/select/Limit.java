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

public class Limit  implements Serializable
{

	int begin = 0;
	int range = 0;
	
	
	public int getBegin(){
		return begin;
	}

public int getRange(){
		return range;
	}
	
	public StringBuffer parse(StringBuffer tok)throws SQLException{
String tokstr = "limit ";
int i3 = tok.toString().toLowerCase().indexOf(tokstr);
	//( n + " addAutoIncrement2  " + i3 + ":" + tok);

			if (i3 > 0){
			//	if (autoincrement.contains(n))return tok;
	        	String l = tok.substring(i3,tok.length());
	        	l = l.trim();
	        	l = l.substring(5,l.length());
	        	l = l.trim();
	        	
	        	tok.delete(i3,tok.length());
	        	i3 = l.indexOf(",");
	        	if (i3 > 0){
	        		begin = Integer.parseInt(l.substring(0,i3).trim());
	        		range = Integer.parseInt(l.substring(i3+1,l.length()).trim());
	        		
	        	}
	        	else
	        		range = Integer.parseInt(l);

			
					return tok;


			}else
				return tok;


	}



}


