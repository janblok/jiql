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
import tools.util.SharedMethods;
import org.jiql.db.objs.*;
import org.jiql.db.*;


public class CreateUniqueKeyParam  implements Serializable
{

//	Vector<String> keys = new Vector<String>();


StringBuffer cache = new StringBuffer();


	public boolean parseUF(StringBuffer tok,SQLParser sqp,Enumeration en)throws SQLException{
String tokstr = "unique(";
//tok.insert(0,cache);
boolean i3 = tok.toString().trim().toLowerCase().startsWith(tokstr);
if (!i3)
{
tokstr = "unique (";
i3 = tok.toString().trim().toLowerCase().startsWith(tokstr);	
}
			if (i3){

				int i = tok.indexOf(")");
				while(i < 0){
					tok.append(en.nextElement().toString());
					i = tok.indexOf(")");
				}
				//(i + " UN 0 " + tok);
				
				String ustr = tok.substring(tokstr.length(),i);
				Vector v = SharedMethods.toVector(ustr,",");
				jiqlTableInfo ti = sqp.getJiqlTableInfo();
				//("UN 1 " + v);
				for (int ct = 0;ct < v.size();ct++)
				{
					ustr = v.elementAt(ct).toString().trim();
					jiqlConstraint jc = new jiqlConstraint();
					jc.add(ustr);
					jc.setName(ustr);
					jc.setType(jiqlConstraint.UNIQUE);
					ti.addConstraint(jc);
				//("UN 2 " + ustr);

				}
				tok.replace(0,tok.length(),"");
				return true;
				


			}
			return false;


	}



	public boolean parse(StringBuffer tok,SQLParser sqp,Enumeration en)throws SQLException{
		if (parseUF(tok,sqp,en))
			return true;
String tokstr = "unique key ";
tok.insert(0,cache);
boolean i3 = tok.toString().trim().toLowerCase().startsWith(tokstr);
			if (i3){
				if (tok.indexOf("(") > 0 && !(tok.indexOf(")") > 0))
				{
					cache.append(tok);
					return true;
				}
				//if (keys.contains(n))return tok;
					//("$$ IS KEY " + tok);

	        	tok.replace(0,tok.length(),"");
					if (cache.length() > 0)
					cache.delete(0,cache.length());
					//keys.add(n);
					return true;


			}
			return false;


	}



}


