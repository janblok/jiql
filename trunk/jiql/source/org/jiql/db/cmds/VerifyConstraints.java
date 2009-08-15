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


public class VerifyConstraints extends DBCommand
{
public Object execute(SQLParser sqp)throws SQLException{
			//Hashtable ch = cgw.getConstraints(sqp.getTable());
			jiqlTableInfo ti = sqp.getJiqlTableInfo();
			//jiqlDBMgr.get(sqp.getProperties()).getTableInfo(sqp.getTable(),true);
if (ti == null )return null;
Hashtable ch = ti.getConstraints();
//("vc1 " + ch);
			if (ch == null || ch.size() < 1)return null;
			
			Gateway cgw = Gateway.get(sqp.getProperties());

						
			jiqlConstraint pk = null;
			Enumeration en = ch.elements();
			while(en.hasMoreElements())
			{
			
			pk = (jiqlConstraint)en.nextElement();
	//(pk.getType() + " vc2 " + pk);		
			//cgw.getPrimaryKeys(,sqp.getTable());
			if (pk == null || pk.size() < 1)continue;
			if (jiqlConstraint.UNIQUE == pk.getType())
			{
			
			Vector incl = new Vector();
			String n = null;
			Object v = null;
			Hashtable inv = sqp.getHash();
			for (int ct = 0;ct < pk.size();ct++)
			{
				 n = pk.elementAt(ct).toString();
				 v = inv.get(n);
		//(n + " vc3 " + v);		

				 if (v != null)
				 	incl.add(new Criteria(n,v.toString(),"=",sqp));
			}
			if (incl == null || incl.size() < 1)continue;

			Hashtable h =  cgw.readTableValue(sqp.getTable(),null,null,incl,sqp);
	//(h+ " vdddddddddddc3 " + incl);
	
			if (h != null && h.size() > 0)
			throw jiqlException.get("duplicate_entry_for_unique_key","Duplicate entry for unique key " + pk);
			}
			else if (jiqlConstraint.FOREIGNKEY == pk.getType())
			{
			
			Vector incl = new Vector();
			String n = null;
			Object v = null;
			Hashtable inv = sqp.getHash();
			jiqlFunction ref = pk.getReference();
			//for (int ct = 0;ct < pk.size();ct++)
				//(inv + ":" + pk + ":" + ref+ ":"+ " **VerifyConstraints AAAAAA " + incl + ":" + ref.getName() + ":" + v + ":" + sqp );
//{realm_passphrase=tigres, realm_username=ruser2}:[realm_username]:[realm_user]: **VerifyConstraints AAAAAA []:realm_userrole:null:org.jiql.util.SQLParser@d83365 
			for (int ct = 0;ct < pk.size();ct++)
			//for (int ct = 0;ct < ref.size();ct++)
			{
			n = pk.elementAt(ct).toString();

				 //n = ref.elementAt(ct).toString();
				 v = inv.get(n);
				 //(n + ":jiqlFunction:" + v + ":" + ref.elementAt(ct));
				 if (v != null){
 				 	n = ref.elementAt(ct).toString();
				 	incl.add(new Criteria(n,v.toString(),"=",sqp));
				 }
			}
			if (incl == null || incl.size() < 1)continue;

					SQLParser sqp2 = new SQLParser(ref.getName(),sqp.getConnection());
					TableInfo ti2 = Gateway.get(sqp.getProperties()).readTableInfo(ref.getName());
					sqp2.setTableInfo(ti2);

			Hashtable h =  cgw.readTableValue(ref.getName(),null,null,incl,sqp2);
				//(pk + ":" + ref+ ":"+ h + " **Verify straints " + incl + ":" + ref.getName() + ":" + v + ":" + sqp + ":" + h);
//[realm_username]:[realm_user]:{} **Verify straints [SQLCriteria:realm_user=ruser2]:realm_userrole:ruser2:org.jiql.util.SQLParser@a02839:{} 
			if (h == null || h.size() < 1)
 				throw jiqlException.get("foreign_key_constraint_fails","Cannot add or update a child row: a foreign key constraint fails " + v);
			}
			
			
			}
			return null;
}



}


