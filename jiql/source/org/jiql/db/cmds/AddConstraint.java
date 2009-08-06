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
import org.jiql.db.*;
import org.jiql.db.objs.*;

public class AddConstraint extends DBCommand
{
public Object execute(SQLParser sqp)throws SQLException{
			Gateway cgw = Gateway.get(sqp.getProperties());
		if(cgw.readTableInfo(sqp.getTable()).size() <1)
			throw jiqlException.get("table_does_not_exists","Table does NOT Exists");



			//Hashtable h = cgw.getConstraints(,sqp.getTable());
			
			jiqlConstraint jc = sqp.getConstraint();

			Hashtable h =  cgw.readTableValue(sqp.getTable(),sqp);
			if (h != null && h.size() > 0)
			{
			if (jiqlConstraint.UNIQUE == jc.getType())
			{
				String n = null;
				Object v = null;
				Hashtable r = null;
				Vector vl = null;
				for (int ct = 0;ct < jc.size();ct++)
				{
					 n = jc.elementAt(ct).toString();
					 Enumeration en = h.elements();
					 vl = new Vector();
					 while (en.hasMoreElements()){
					 
					 r = (Hashtable)en.nextElement();
					 v = r.get(n);
					 if (v != null){
						if (vl.contains(v))
 						 throw jiqlException.get("duplicate_entry_for_unique_key","Duplicate entry for unique key " + v);
					 
					 	vl.add(v);
					 }
					 }
				}	

			}
			else if (jiqlConstraint.FOREIGNKEY == jc.getType())
			{
		
				jiqlFunction ref = jc.getReference();
				if(cgw.readTableInfo(ref.getName()).size() <1)
				throw JGException.get("parent_table_does_not_exists","Parent Table does NOT Exists");
				Hashtable rh =  cgw.readTableValue(ref.getName(),sqp);

		
				String n = null;
				Object v = null;
				Hashtable r = null;

				Object rv = null;
				Hashtable rr = null;

				Vector vl = null;
				String rn = null;
				boolean refeq = false;
				for (int ct = 0;ct < jc.size();ct++)
				{
					 n = jc.elementAt(ct).toString();
					 rn = ref.elementAt(ct).toString();
					 Enumeration en = h.elements();
					 vl = new Vector();
					 while (en.hasMoreElements()){
					 
					 r = (Hashtable)en.nextElement();
					 v = r.get(n);
					 if (v != null){
			
			
			
			
					Enumeration ren = rh.elements();
					 while (ren.hasMoreElements()){
					 
					 rr = (Hashtable)ren.nextElement();
					 rv = rr.get(rn);
					 if (rv.equals(v))
					 {
					 	refeq = true;
					 	break;
					 }
					 refeq = false;
					 }
			
			
			
			
						
						
						
						if (!refeq)
 						 throw jiqlException.get("foreign_key_constraint_fails","Cannot add or update a child row: a foreign key constraint fails " + v);
					 
					 	//vl.add(v);
					 }
					 }
				}	

			}
			
			}

			//if (h != null && h.get(jc.getName()) != null)
			//	throw jiqlException.get("constraint_exists",jc.getName() + " Constraint already Exists!");
			//cgw.addContstraint(sqp.getTable(),jc);	
			jiqlTableInfo ti = sqp.getJiqlTableInfo();
			//jiqlDBMgr.get(sqp.getProperties()).getTableInfo(sqp.getTable(),true);
			ti.addConstraint(jc);
			
			//(ti + ": ADDCONSRAUBT  " + jc + ":" + sqp.getTable());
			
			jiqlDBMgr.get(sqp.getProperties()).saveTableInfo(sqp.getTable(),ti);
CacheMgr.removeMetaCache(sqp.getConnection().getProperties().getProperty("baseUrl"),sqp.getTable());
sqp.setAttribute("removeMetaCache",sqp.getTable());

			return null;
}



}


