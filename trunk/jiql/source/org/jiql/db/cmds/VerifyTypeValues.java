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


public class VerifyTypeValues extends DBCommand
{
public Object execute(SQLParser sqp)throws SQLException{
			//Hashtable ch = cgw.getConstraints(,sqp.getTable());

		//TableInfo ti = Gateway.get(sqp.getProperties()).readTableInfo(sqp.getTable());
			TableInfo ti = sqp.getTableInfo();
			if (ti == null)
				ti = Gateway.get(sqp.getProperties()).readTableInfo(sqp.getTable());


ColumnInfo ci = null;
			Hashtable inv = sqp.getHash();
			Enumeration en = inv.keys();
String cn = null;
int ml = 0;
			while(en.hasMoreElements())
			{

			cn = (String)en.nextElement();
			ci = ti.getColumnInfo(cn);
			//(ci + ": TITIT " + cn + ":" + ti);
			try{
			
			ml = ci.getMaxLength();
			}catch (NullPointerException e){
				throw jiqlException.get("insert_attributes_does_not_match_table",cn + " Insert attributes does not match table " + sqp.getTable() +". Try the 'describe <tablename>' command " + cn);

			}
			if (ml <1)continue;
			//(cn + " VTVB " + ml + ":" + inv.get(cn).toString() + ":" + inv.get(cn).toString().length());

			if (!inv.get(cn).toString().toLowerCase().equals("null") && inv.get(cn).toString().length() > ml )
			throw jiqlException.get("data_too_long_for_column","Data too long for column " + cn);
			if (sqp.isNotQuoted(cn) && ci.getColumnType() == Types.VARCHAR && !inv.get(cn).toString().toLowerCase().equals("null"))
				throw jiqlException.get("unknown_column_or_missing_quotes","Unknown column or missing quotes " + cn);



			}
			return null;
}



}


