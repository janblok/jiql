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

public class GroupBy  implements Serializable
{

	static Vector reserved = new Vector();
	static{
		reserved.add("order by");
	}


	public String parse(String tok){
int i3 = tok.toLowerCase().indexOf("group by ");
		//(tok + " groupby " + i3);
			if (i3 > 0){
				String tok1 = tok.substring(0,i3);
				String tok2 = tok.substring(i3 +  "group by ".length(),tok.length());
				tok2 = StringUtil.getTrimmedValue(tok2);
				String rt = null;
				String rt2 = null;
				int i1 = 0;
				int i2 = 0;
				for (int ct = 0; ct < reserved.size();ct++){
					rt = reserved.elementAt(ct).toString();
					i1 = tok2.toLowerCase().indexOf(rt);
					if (i1 > 0)
					{
						if (i2 == 0)
						{
							rt2 = rt;
							i2 = i1;
						}
						else{
							if (i1 < i2)
							{
	
							rt2 = rt;
							i2 = i1;
						}
						}
					}
				}
				if (i2 > 0)
				{
					tok2 = tok2.substring(i2,tok2.length());
				}
				else tok2 = "";
		//("groupby 2 " + tok1 + " " + tok2);

				return tok1 + " " + tok2;
			
				
			}else
				return tok;
			//return tok;	


	}



}


