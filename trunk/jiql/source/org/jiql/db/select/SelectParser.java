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
import tools.util.NumberUtil;

public class SelectParser  implements Serializable
{
	Limit limit = new Limit();
	CalcFoundRows cfr = new CalcFoundRows();
		SQLParser sqp = null;
		SQLFunctionParser sfp = new SQLFunctionParser();
	public SelectParser(SQLParser sqp){
		this.sqp = sqp;

	}

	public boolean isCompareValues(Criteria c){
		boolean b = (NumberUtil.isNumeric(c.getName()) && NumberUtil.isNumeric(c.getValueString()));
		if (b){
			
			sqp.getIncludeAllList().remove(c);
		}
		return b;
	}


	public Limit getLimit(){
		return limit;
	}
	public CalcFoundRows getCalcFoundRows(){
		return cfr;
	}

	public StringBuffer parse(StringBuffer tok)throws SQLException{
		tok = sqp.getUnion().parse(tok);
		tok = getLimit().parse(tok);
		tok = getCalcFoundRows().parse(tok);
		return tok;

	}

	public SQLFunctionParser getSQLFunctionParser(){
		return sfp;
	}
	public StringBuffer parseFunctions(StringBuffer tok,String alias)throws SQLException{
		tok = getSQLFunctionParser().parse(tok,alias);
		return tok;

	}
	
		public boolean parseNoFrom(String tok)throws SQLException{
		return getSelectValue().parse(tok,sqp);

	}
SelectValue sv = null;
	public SelectValue getSelectValue(){
		if (sv == null)
			sv = new SelectValue();
		return sv;
	}

}


