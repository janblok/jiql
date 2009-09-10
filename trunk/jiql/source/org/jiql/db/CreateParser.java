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

public class CreateParser  implements Serializable
{
	UnSigned unsigned = new UnSigned();
	AutoIncrement autoincrement = null;
	NextVal nextval = new NextVal();
	CreateKeyParam keys = new CreateKeyParam();
	CreateUniqueKeyParam ukeys = new CreateUniqueKeyParam();
	
		SQLParser sqp = null;
	public CreateParser(SQLParser sqp){
		this.sqp = sqp;
		autoincrement = new AutoIncrement(sqp);

	}
	boolean notexists = false;
	public void setIfNotExists(boolean tf){
	notexists = tf;
	}

	public boolean ifNotExists(){
	return notexists;
	}
	
	public AutoIncrement getAutoIncrement(){
		return autoincrement;
	}
		
	public UnSigned getUnSigned(){
		return unsigned;
	}
	public StringBuffer parse(String n,StringBuffer tok)throws SQLException{
		tok = getUnSigned().parse(n,tok);
		tok = getAutoIncrement().parse(n,tok);
		tok = nextval.parse(n,tok);
		return tok;

	}

	public boolean parseParams(String toks,Enumeration en)throws SQLException{
		StringBuffer tok = new StringBuffer(toks);
		boolean tf = false;
		if (keys.parse(tok))
			tf = true;
		else if (ukeys.parse(tok,sqp,en))
			tf = true;

		return tf;

	}


}


