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

package org.jiql.db.cmds;
import java.util.*;
import java.sql.*;
import java.io.*;
import org.jiql.util.*;
import tools.util.EZArrayList;
import org.jiql.db.*;
import org.jiql.db.objs.*;


public class VerifyTable extends DBCommand
{
public Object execute(SQLParser sqp)throws SQLException{
			//Hashtable ch = cgw.getConstraints(,sqp.getTable());
			jiqlTableInfo jti = sqp.getJiqlTableInfo();
			//jiqlDBMgr.get(sqp.getProperties()).getTableInfo(sqp.getTable(),true);
if (jti == null )
{
	/*int i = sqp.getTable().indexOf(" ");
	if (i > 0)
	{
		String t = sqp.getTable();
		String a = t.substring(i + 1,t.length());
		t = t.substring(0,i);
		sqp.setTable(t);
		sqp.addAlias(t,a);
		jti = sqp.getJiqlTableInfo();
	}
	else*/{
	
	String t = (String)sqp.getAliases().get(sqp.getTable());
	if (t != null)
	{
		sqp.setTable(t);
		jti = sqp.getJiqlTableInfo();
	}
	}

	if (jti == null )
	throw JGException.get("table_does_not_exists",sqp.getTable() + " Table does NOT Exists " + sqp);
}
		//TableInfo ti = Gateway.get(sqp.getProperties()).readTableInfo(sqp.getTable());
					TableInfo ti = sqp.getTableInfo();
			if (ti == null)
				ti = Gateway.get(sqp.getProperties()).readTableInfo(sqp.getTable());

		//(sqp.getTable() + " VerifyTable " + ti);

			Vector v = sqp.getIncludeAllList();
		//(sqp.getTable() + " VerifyTable 1 " + v);
			for (int ct = 0;ct < v.size();ct ++)
			{
			Criteria c = (Criteria)v.elementAt(ct);
		//(sqp.getTable() + " VerifyTable 2 " + c);

			if (ti.getColumnInfo(sqp.getRealColName(c.getName())) == null && !( sqp.getAction().equals("select") && sqp.getSelectParser().isCompareValues(c)))
			throw jiqlException.get("unknown_column",sqp.getTable() + " Included Unknown column " + c.getName() + ":" + ti + ":" + sqp);
			}

			v = sqp.getEitherOrAllList();
			for (int ct = 0;ct < v.size();ct ++)
			{
			Criteria c = (Criteria)v.elementAt(ct);
			if (ti.getColumnInfo(sqp.getRealColName(c.getName())) == null)
			throw jiqlException.get("unknown_column","Additional Criteria Unknown column " + c.getName());
			}

			v = sqp.getSelectList();
			for (int ct = 0;ct < v.size();ct ++)
			{
			String sn = v.elementAt(ct).toString();
			if (sn.indexOf("*") > -1)continue;
			if (ti.getColumnInfo(sqp.getRealColName(sn)) == null  && !sqp.isCount())
			throw jiqlException.get("unknown_column",sqp.getTable() + " Selected Unknown column " + sn + ":" + sqp.getRealColName(sn) + ":" + sqp.getTAlias(sqp.getTable()) + ":" + ti);
			}

			return null;
}



}


