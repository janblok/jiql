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
import org.jiql.util.*;
import tools.util.EZArrayList;
import org.jiql.db.objs.*;
public class VerifyPrimaryKeysCommand extends DBCommand
{
public Object execute(SQLParser sqp)throws SQLException{
			Gateway cgw = Gateway.get(sqp.getProperties());
			//Vector pk = cgw.getPrimaryKeys(,sqp.getTable());
			jiqlTableInfo ti = sqp.getJiqlTableInfo();
			//jiqlDBMgr.get(sqp.getProperties()).getTableInfo(sqp.getTable(),true);
			Vector pk = ti.getPrimaryKeys();


			if (pk == null || pk.size() < 1)return null;
			Vector incl = new Vector();
			String n = null;
			Object v = null;
			Hashtable inv = sqp.getHash();
			
			//(pk + ": + PPKK ");
			
			for (int ct = 0;ct < pk.size();ct++)
			{
				 n = pk.elementAt(ct).toString();
				 v = inv.get(n);
				 if (v != null)
				 	incl.add(new Criteria(n,v.toString(),"=",sqp));
			}
			if (incl == null || incl.size() < 1)return null;

			Hashtable h =  cgw.readTableValue(sqp.getTable(),incl,null,null,false,sqp);
			//(incl + ": + PPffKK " + h + ":" + cgw.readTableValue(,sqp.getTable()));
		//sqp.getIncludeAllList(),sqp.getSelectList(),sqp.getEitherOrAllList(),sqp.isDistinct());

			//(incl + ":"  + h + ":" + pk + ":VerifyPrimaryKeysCommand:" + sqp.getHash());
			if (h != null && h.size() > 0)
			throw jiqlException.get("duplicate_entry_for_primary_key",sqp.getTable() + " Duplicate entry for primary key " + pk + ":" + incl + ":" + h);

			return null;
}



}


