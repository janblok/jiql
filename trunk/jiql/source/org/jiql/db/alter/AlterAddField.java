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

package org.jiql.db.alter;
import java.util.*;
import java.sql.*;
import java.io.*;
import org.jiql.util.JGNameValuePairs;
import tools.util.EZArrayList;
import tools.util.NumberUtil;
import org.jiql.util.Criteria;
import org.jiql.util.SQLParser;
import org.jiql.util.Gateway;
import org.jiql.db.objs.jiqlCellValue;
import tools.util.NameValuePairs;
import org.jiql.util.JGException;
import org.jiql.util.JGUtil;
import org.jiql.db.jdbc.stat.AlterAddFieldStatementProcessor;
import tools.util.StringUtil;
import org.jiql.db.objs.jiqlTableInfo;

public class AlterAddField  implements Serializable
{
static AlterAddFieldStatementProcessor spro = new AlterAddFieldStatementProcessor();


public boolean parse(String n,SQLParser sqp)throws SQLException
{
		System.out.println("ALTER1 " + n);
		n = StringUtil.replaceSubstring(n,"\"","");
		n = StringUtil.replaceSubstring(n,"'","");
		n = StringUtil.replaceSubstring(n,"`","");
			n = n.trim();
		int 	i = n.indexOf(" ");
			if (i < 0)
				i = n.indexOf("\t");
			if (i < 0)
				i = n.indexOf("	");
			
			String va = n.substring(i + 1,n.length());
			va = va.trim();
			n =n.substring(0,i);
			n = n.trim();

			jiqlTableInfo ti = sqp.getJiqlTableInfo();
			if (ti.getFieldList().contains(n))
				throw new SQLException(n + " FIELD ALREADY EXISTS ON TABLE " + sqp.getTable());
			String tok = null;
			i = va.indexOf(" ");

			/*if (n.toLowerCase().equals("primary") && va.toLowerCase().startsWith("key"))
			{
				va = va.substring("key".length(),va.length());
				va = va.trim();
				va = va.substring( 1,va.length());
				va = va.trim();
				while (!va.endsWith(")")){
					ct = ct + 1;
					va = va + "," + v.elementAt(ct).toString();
				}
				va = va.trim();

				va = va.substring( 0,va.length() -1);
				va = va.trim();
				primaryKeys = new EZArrayList(new StringTokenizer(va,","),true);
			}
			
			else*/ if (i > 0)
			{
				tok = va.substring(i,va.length());
				va = va.substring(0,i);
				tok = tok.trim();
				va = va.trim();
				if (va.endsWith("("))
				{
					i = tok.indexOf(")");
					va = va + tok.substring(0,i + 1);
					tok = tok.substring(i + 1,tok.length());
					tok = tok.trim();
					va = va.trim();
				}
				else if (tok.startsWith("("))
				{
					i = tok.indexOf(")");
					va = va + tok.substring(0,i + 1);
					tok = tok.substring(i + 1,tok.length());
					tok = tok.trim();
					va = va.trim();
				}

				String tok2 = tok.toLowerCase();
				i = tok2.indexOf("not ");
				if (i > -1 && tok2.indexOf(" null") > i)
				{
					if (!sqp.getNotNulls().contains(n)){

						sqp.getNotNulls().add(n);
					}
				}
				i = tok2.indexOf("primary ");
					if (i > -1 && tok2.indexOf(" key") > i)
				{
					n = StringUtil.trimQuotes(new StringBuffer(n)).toString();
					if (!sqp.getPrimaryKeys().contains(n))
						sqp.getPrimaryKeys().add(n);
				}
				i = tok2.indexOf("default ");
					if (i > -1 )
				{
					tok = tok.substring(i + "default ".length(),tok.length());
					tok = tok.trim();
					
					if (tok.startsWith("'"))
					{
					tok = tok.substring(1,tok.length());
					i = tok.indexOf("'");
					tok2 = tok.substring(0,i);
					tok = tok.substring(i + 1,tok.length());
					tok = tok.trim();
					tok2 = sqp.decode(tok2);
					sqp.getDefaultValues().put(n,tok2);
						
					}
					else{
					
					i = tok.indexOf(" ");
					if (i > 0)
						tok = tok.substring(0,i);
					tok = tok.trim();
					tok = StringUtil.getTrimmedValue(tok);
					tok = sqp.decode(tok);
					sqp.getDefaultValues().put(n,tok);
					}
					
				}
						if (!JGUtil.validFieldType(va,sqp))
							throw JGException.get("invalid_field_type",n + " --> " + va + " Invalid Field Type on Table " + sqp.getTable());

				//	throw new SQLException (n + " --> " + va + " Invalid Field Type on Table " + sqp.getTable());

				sqp.getHash().put(n,sqp.convert(va,n));
				ti.addFieldList(n);
			}	
			else{
				if (!JGUtil.validFieldType(va,sqp))
					throw JGException.get("invalid_field_type",n + " --> " + va + " Invalid Field Type on Table " + sqp.getTable());

					//throw new SQLException (n + " --> " + va + " Invalid Field Type on Table " + sqp.getTable());
		
			sqp.getHash().put(n,sqp.convert(va,n));
			ti.addFieldList(n);
			}
	sqp.setAction("AlterAddField");
	sqp.setSpecial(true);
	sqp.setStatementProcessor(spro);
	return true;
}




}


