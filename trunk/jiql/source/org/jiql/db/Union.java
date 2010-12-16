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

public class Union implements Serializable
{
//	Vector tables = new Vector();
	Hashtable aliases = new Hashtable();
   //Hashtable<String, Vector> selects  = new Hashtable<String, Vector>();	
   //Hashtable<String, Hashtable>    = new Hashtable<String, Hashtable>();	
   Vector<Criteria> jincludealllist  = new Vector<Criteria>();	
   	Vector<Criteria> jeitheroralllist  = new Vector<Criteria>();
  // 	   Hashtable<String, Vector> includealllist  = new Hashtable<String, Vector>();	
  // 	   Hashtable<String, Vector> eitheroralllist  = new Hashtable<String, Vector>();	

SQLParser sqp = null;
   Hashtable<String, SQLParser> sqps  = new Hashtable<String, SQLParser>();	

public Union(SQLParser s){
	sqp = s;
}

	public StringBuffer parse(StringBuffer tok)throws SQLException{
		int i = tok.indexOf("INNER JOIN");
		if (i < 0)
			i = tok.indexOf("inner join");
		if (i > 0){
			tok.replace(i, i + 10,",");
			i = tok.indexOf(" WHERE ");
			if (i < 0)
			i = tok.indexOf(" where ");
			if (i > 0)
				tok.replace(i,i + 7 ," and ");


			i = tok.indexOf(" ON ");
			if (i < 0)
			i = tok.indexOf(" on ");
			if (i > 0)
				tok.replace(i,i + 4 ," where ");

		}
		//(" U.parse " + tok);
		return tok;

	}


	public Vector joinTable(String rn,Vector h,int typ,Object v,NameValuePairs ref)throws SQLException{
		
//					if (incl.size() < 1)
//				return h;
	    	//Enumeration en = h.keys();
	    	Vector sh = new Vector();
	    //	String id = null;
	    	Row row = null;
	    	//int typ = 0;
			//Criteria cr = null;
		    jiqlCellValue c1 = null;
	    	jiqlCellValue c2 = null;
	    	Object nvo = null;
	    	//boolean add = false;

	    	for (int ct = 0;ct < h.size();ct++)
	    	{
	    		//id = en.nextElement().toString();
	    		row = (Row)h.elementAt(ct);

						//try{
						
	    				nvo = row.get(rn);
						//}catch (Exception e)
						//{
						//	continue;
						//}

	    				if (nvo == null)continue;
	    				c1 = new jiqlCellValue(nvo,typ,sqp);
	    				c2 = new jiqlCellValue(v,typ,sqp);

	    				//(nvo + " CMPR 5 " + v + ":" +typ + ":" + rn + ":" + h + ":" + sqp);
//ruser2 CMPR 5 ruser3:12:user.realm_username:[{realm_passphrase=tigres, realm_username=ruser2}, {realm_passphrase=tigres, realm_username=ruser1}]:jiql.SQLParser [ORIGINAL SQL:select role.realm_rolename from realm_user1 user INNER JOIN realm_userrole1 role ON role.realm_username=user.realm_username; ] table:realm_user1;aliases:{INNER=realm_user1, user=realm_user1, role=realm_userrole1};values:{};selects:[];selectAS:{};selectAS2:{};includealllist:[];eitheroralllist:[];getOriginalSelectList:[role.realm_rolename];groupby:org.jiql.db.GroupBy@856447;union:jiql.Union tables:{realm_userrole1=jiql.SQLParser [ORIGINAL SQL:null ] table:realm_userrole1;aliases:{realm_userrole1=role, role=realm_userrole1};values:{};selects:[];selectAS:{};selectAS2:{};includealllist:[];eitheroralllist:[];getOriginalSelectList:[];groupby:org.jiql.db.GroupBy@17a7de4;union:null, role=jiql.SQLParser [ORIGINAL SQL:null ] table:realm_userrole1;aliases:{role=realm_userrole1};values:{};selects:[role.realm_rolename];selectAS:{};selectAS2:{};includealllist:[];eitheroralllist:[];getOriginalSelectList:[];groupby:org.jiql.db.GroupBy@b5ac2c;union:null};aliases:{role=realm_userrole1};selects:;selectAS:;jincludealllist:[SQLCriteria:role.realm_username=user.realm_username];jeitheroralllist:[];includealllist:;eitheroralllist:
//ruser1 CMPR 5 ruser3:12:user.realm_username:[{realm_passphrase=tigres, realm_username=ruser2}, {realm_passphrase=tigres, realm_username=ruser1}]:jiql.SQLParser [ORIGINAL SQL:select role.realm_rolename from realm_user1 user INNER JOIN realm_userrole1 role ON role.realm_username=user.realm_username; ] table:realm_user1;aliases:{INNER=realm_user1, user=realm_user1, role=realm_userrole1};values:{};selects:[];selectAS:{};selectAS2:{};includealllist:[];eitheroralllist:[];getOriginalSelectList:[role.realm_rolename];groupby:org.jiql.db.GroupBy@856447;union:jiql.Union tables:{realm_userrole1=jiql.SQLParser [ORIGINAL SQL:null ] table:realm_userrole1;aliases:{realm_userrole1=role, role=realm_userrole1};values:{};selects:[];selectAS:{};selectAS2:{};includealllist:[];eitheroralllist:[];getOriginalSelectList:[];groupby:org.jiql.db.GroupBy@17a7de4;union:null, role=jiql.SQLParser [ORIGINAL SQL:null ] table:realm_userrole1;aliases:{role=realm_userrole1};values:{};selects:[role.realm_rolename];selectAS:{};selectAS2:{};includealllist:[];eitheroralllist:[];getOriginalSelectList:[];groupby:org.jiql.db.GroupBy@b5ac2c;union:null};aliases:{role=realm_userrole1};selects:;selectAS:;jincludealllist:[SQLCriteria:role.realm_username=user.realm_username];jeitheroralllist:[];includealllist:;eitheroralllist:
//ruser2 CMPR 5 ruser1:12:user.realm_username:[{realm_passphrase=tigres, realm_username=ruser2}, {realm_passphrase=tigres, realm_username=ruser1}]:jiql.SQLParser [ORIGINAL SQL:select role.realm_rolename from realm_user1 user INNER JOIN realm_userrole1 role ON role.realm_username=user.realm_username; ] table:realm_user1;aliases:{INNER=realm_user1, user=realm_user1, role=realm_userrole1};values:{};selects:[];selectAS:{};selectAS2:{};includealllist:[];eitheroralllist:[];getOriginalSelectList:[role.realm_rolename];groupby:org.jiql.db.GroupBy@856447;union:jiql.Union tables:{realm_userrole1=jiql.SQLParser [ORIGINAL SQL:null ] table:realm_userrole1;aliases:{realm_userrole1=role, role=realm_userrole1};values:{};selects:[];selectAS:{};selectAS2:{};includealllist:[];eitheroralllist:[];getOriginalSelectList:[];groupby:org.jiql.db.GroupBy@17a7de4;union:null, role=jiql.SQLParser [ORIGINAL SQL:null ] table:realm_userrole1;aliases:{role=realm_userrole1};values:{};selects:[role.realm_rolename];selectAS:{};selectAS2:{};includealllist:[];eitheroralllist:[];getOriginalSelectList:[];groupby:org.jiql.db.GroupBy@b5ac2c;union:null};aliases:{role=realm_userrole1};selects:;selectAS:;jincludealllist:[SQLCriteria:role.realm_username=user.realm_username];jeitheroralllist:[];includealllist:;eitheroralllist:

//ruser1 CMPR 5 ruser1:12:user.realm_username:[{realm_passphrase=tigres, realm_username=ruser2}, {realm_passphrase=tigres, realm_username=ruser1}]:jiql.SQLParser [ORIGINAL SQL:select role.realm_rolename from realm_user1 user INNER JOIN realm_userrole1 role ON role.realm_username=user.realm_username; ] table:realm_user1;aliases:{INNER=realm_user1, user=realm_user1, role=realm_userrole1};values:{};selects:[];selectAS:{};selectAS2:{};includealllist:[];eitheroralllist:[];getOriginalSelectList:[role.realm_rolename];groupby:org.jiql.db.GroupBy@856447;union:jiql.Union tables:{realm_userrole1=jiql.SQLParser [ORIGINAL SQL:null ] table:realm_userrole1;aliases:{realm_userrole1=role, role=realm_userrole1};values:{};selects:[];selectAS:{};selectAS2:{};includealllist:[];eitheroralllist:[];getOriginalSelectList:[];groupby:org.jiql.db.GroupBy@17a7de4;union:null, role=jiql.SQLParser [ORIGINAL SQL:null ] table:realm_userrole1;aliases:{role=realm_userrole1};values:{};selects:[role.realm_rolename];selectAS:{};selectAS2:{};includealllist:[];eitheroralllist:[];getOriginalSelectList:[];groupby:org.jiql.db.GroupBy@b5ac2c;union:null};aliases:{role=realm_userrole1};selects:;selectAS:;jincludealllist:[SQLCriteria:role.realm_username=user.realm_username];jeitheroralllist:[];includealllist:;eitheroralllist:
//ruser1 CMPR 5 ruser1:12:user.realm_username:[{realm_passphrase=tigres, realm_username=ruser2, realm_rolename=role2}, {realm_passphrase=tigres, realm_username=ruser1, realm_rolename=role1}]:jiql.SQLParser [ORIGINAL SQL:select role.realm_rolename from realm_user1 user INNER JOIN realm_userrole1 role ON role.realm_username=user.realm_username; ] table:realm_user1;aliases:{INNER=realm_user1, user=realm_user1, role=realm_userrole1};values:{};selects:[];selectAS:{};selectAS2:{};includealllist:[];eitheroralllist:[];getOriginalSelectList:[role.realm_rolename];groupby:org.jiql.db.GroupBy@856447;union:jiql.Union tables:{realm_userrole1=jiql.SQLParser [ORIGINAL SQL:null ] table:realm_userrole1;aliases:{realm_userrole1=role, role=realm_userrole1};values:{};selects:[];selectAS:{};selectAS2:{};includealllist:[];eitheroralllist:[];getOriginalSelectList:[];groupby:org.jiql.db.GroupBy@17a7de4;union:null, role=jiql.SQLParser [ORIGINAL SQL:null ] table:realm_userrole1;aliases:{role=realm_userrole1};values:{};selects:[role.realm_rolename];selectAS:{};selectAS2:{};includealllist:[];eitheroralllist:[];getOriginalSelectList:[];groupby:org.jiql.db.GroupBy@b5ac2c;union:null};aliases:{role=realm_userrole1};selects:;selectAS:;jincludealllist:[SQLCriteria:role.realm_username=user.realm_username];jeitheroralllist:[];includealllist:;eitheroralllist:

//ruser2 CMPR 5 ruser2:12:user.realm_username:[{realm_passphrase=tigres, realm_username=ruser2}, {realm_passphrase=tigres, realm_username=ruser1, realm_rolename=role1}]:jiql.SQLParser [ORIGINAL SQL:select role.realm_rolename from realm_user1 user INNER JOIN realm_userrole1 role ON role.realm_username=user.realm_username; ] table:realm_user1;aliases:{INNER=realm_user1, user=realm_user1, role=realm_userrole1};values:{};selects:[];selectAS:{};selectAS2:{};includealllist:[];eitheroralllist:[];getOriginalSelectList:[role.realm_rolename];groupby:org.jiql.db.GroupBy@856447;union:jiql.Union tables:{realm_userrole1=jiql.SQLParser [ORIGINAL SQL:null ] table:realm_userrole1;aliases:{realm_userrole1=role, role=realm_userrole1};values:{};selects:[];selectAS:{};selectAS2:{};includealllist:[];eitheroralllist:[];getOriginalSelectList:[];groupby:org.jiql.db.GroupBy@17a7de4;union:null, role=jiql.SQLParser [ORIGINAL SQL:null ] table:realm_userrole1;aliases:{role=realm_userrole1};values:{};selects:[role.realm_rolename];selectAS:{};selectAS2:{};includealllist:[];eitheroralllist:[];getOriginalSelectList:[];groupby:org.jiql.db.GroupBy@b5ac2c;union:null};aliases:{role=realm_userrole1};selects:;selectAS:;jincludealllist:[SQLCriteria:role.realm_username=user.realm_username];jeitheroralllist:[];includealllist:;eitheroralllist:
//ruser1 CMPR 5 ruser2:12:user.realm_username:[{realm_passphrase=tigres, realm_username=ruser2, realm_rolename=role2}, {realm_passphrase=tigres, realm_username=ruser1, realm_rolename=role1}]:jiql.SQLParser [ORIGINAL SQL:select role.realm_rolename from realm_user1 user INNER JOIN realm_userrole1 role ON role.realm_username=user.realm_username; ] table:realm_user1;aliases:{INNER=realm_user1, user=realm_user1, role=realm_userrole1};values:{};selects:[];selectAS:{};selectAS2:{};includealllist:[];eitheroralllist:[];getOriginalSelectList:[role.realm_rolename];groupby:org.jiql.db.GroupBy@856447;union:jiql.Union tables:{realm_userrole1=jiql.SQLParser [ORIGINAL SQL:null ] table:realm_userrole1;aliases:{realm_userrole1=role, role=realm_userrole1};values:{};selects:[];selectAS:{};selectAS2:{};includealllist:[];eitheroralllist:[];getOriginalSelectList:[];groupby:org.jiql.db.GroupBy@17a7de4;union:null, role=jiql.SQLParser [ORIGINAL SQL:null ] table:realm_userrole1;aliases:{role=realm_userrole1};values:{};selects:[role.realm_rolename];selectAS:{};selectAS2:{};includealllist:[];eitheroralllist:[];getOriginalSelectList:[];groupby:org.jiql.db.GroupBy@b5ac2c;union:null};aliases:{role=realm_userrole1};selects:;selectAS:;jincludealllist:[SQLCriteria:role.realm_username=user.realm_username];jeitheroralllist:[];includealllist:;eitheroralllist:
//ruser2 CMPR 5 ruser1:12:user.realm_username:[{realm_passphrase=tigres, realm_username=ruser2, realm_rolename=role2}, {realm_passphrase=tigres, realm_username=ruser1, realm_rolename=role1}]:jiql.SQLParser [ORIGINAL SQL:select role.realm_rolename from realm_user1 user INNER JOIN realm_userrole1 role ON role.realm_username=user.realm_username; ] table:realm_user1;aliases:{INNER=realm_user1, user=realm_user1, role=realm_userrole1};values:{};selects:[];selectAS:{};selectAS2:{};includealllist:[];eitheroralllist:[];getOriginalSelectList:[role.realm_rolename];groupby:org.jiql.db.GroupBy@856447;union:jiql.Union tables:{realm_userrole1=jiql.SQLParser [ORIGINAL SQL:null ] table:realm_userrole1;aliases:{realm_userrole1=role, role=realm_userrole1};values:{};selects:[];selectAS:{};selectAS2:{};includealllist:[];eitheroralllist:[];getOriginalSelectList:[];groupby:org.jiql.db.GroupBy@17a7de4;union:null, role=jiql.SQLParser [ORIGINAL SQL:null ] table:realm_userrole1;aliases:{role=realm_userrole1};values:{};selects:[role.realm_rolename];selectAS:{};selectAS2:{};includealllist:[];eitheroralllist:[];getOriginalSelectList:[];groupby:org.jiql.db.GroupBy@b5ac2c;union:null};aliases:{role=realm_userrole1};selects:;selectAS:;jincludealllist:[SQLCriteria:role.realm_username=user.realm_username];jeitheroralllist:[];includealllist:;eitheroralllist:



	    			    if (c1.compareTo(c2) == 0){
	    			    	row.merge(ref);
		    				sh.add(row);
		    				row.setUnion(this);
	    			    }

						
							    				
	    		//	}
	    		//	else
	   			//		throw JGException.get("operator_not_supported_for_joins",cr.getCompareOperator() + " Operator not supported for joins");



	    			
	    		//}
	    		//if (add)
	    		//sh.put(id,row);
	    		
	    		
	    	}
	    	//if (sh.size() < 1)return null;
	    	//("UNION HASH " + sh);
	    	return sh;
		
	}

	public static Hashtable joinTable(String rn,Hashtable h,int typ,Object v)throws SQLException{
		
//					if (incl.size() < 1)
//				return h;
	    	Enumeration en = h.keys();
	    	Hashtable sh = new Hashtable();
	    	String id = null;
	    	NameValuePairs row = null;
	    	//int typ = 0;
			//Criteria cr = null;
		    jiqlCellValue c1 = null;
	    	jiqlCellValue c2 = null;
	    	Object nvo = null;
	    	boolean add = false;

	    	while (en.hasMoreElements())
	    	{
	    		id = en.nextElement().toString();
	    		row = (NameValuePairs)h.get(id);
	    		//for (int ct = 0;ct < incl.size();ct++){
	    			//add = false;
	    			//cr = (Criteria)incl.elementAt(ct);
	    		//	if (cr.getCompareOperator().equals("="))
	    		//	{
	    				nvo = row.get(rn);
	    				//(nvo + " CMPR 1 " + id + ":" + h + ":" + rn + ":" + v);

	    				if (nvo == null)continue;
	    		//typ = ti.get (cr.getName()).getColumnType();
	    				c1 = new jiqlCellValue(nvo,typ,null);
	    				c2 = new jiqlCellValue(v,typ,null);
	    			    if (c1.compareTo(c2) == 0)
		    				sh.put(id,row);

						
							    				
	    		//	}
	    		//	else
	   			//		throw JGException.get("operator_not_supported_for_joins",cr.getCompareOperator() + " Operator not supported for joins");



	    			
	    		//}
	    		//if (add)
	    		//sh.put(id,row);
	    		
	    		
	    	}
	    	if (sh.size() < 1)return null;
	    	return sh;
		
	}


		public String findTable(String t,String a,String l)throws SQLException{
		
	if (l.startsWith(t + "."))
		return t;
	if (a != null && l.startsWith(a + "."))
		return t;


	String n = null;
	String v = null;
		Vector tables = new EZArrayList(sqps.keys());
		for (int ct = 0; ct < tables.size();ct++)
		{
			v = (String)tables.elementAt(ct);
		if (l.startsWith(v + "."))
			return v;


		}
	Enumeration en = aliases.keys();

		while (en.hasMoreElements())
		{
			n = (String)en.nextElement();
			v = (String)aliases.get(n);
		if (l.startsWith(n + "."))
			return v;
		if (l.startsWith(v + "."))
			return v;


		}

		
	
	en = sqps.keys();
	Enumeration en2 = null;
	Hashtable h = null;
	SQLParser sq = null;
	while (en.hasMoreElements()){
		t = (String)en.nextElement();
	if (l.startsWith(t + "."))
		return t;
		sq = add(t);
		h = sq.getSelectAS();
		en2 = h.keys();
		while (en2.hasMoreElements())
		{
			n = (String)en2.nextElement();
			if (n.equals(l))
			return t;

			v = (String)h.get(n);
			if (v.equals(l))
			return t;
		}
		
	}
	if (sqp.getTableInfo().getColumnInfo(sqp.getRealColName(l)) != null)
		return sqp.getTable();
return null;		
	}

		public String findTable(String l)throws SQLException{
		

	String n = null;
	String v = null;
		Vector tables = new EZArrayList(sqps.keys());
		for (int ct = 0; ct < tables.size();ct++)
		{
			v = (String)tables.elementAt(ct);
		if (l.startsWith(v + "."))
			return v;


		}
	Enumeration en = aliases.keys();

		while (en.hasMoreElements())
		{
			n = (String)en.nextElement();
			v = (String)aliases.get(n);
		if (l.startsWith(n + "."))
			return v;
		if (l.startsWith(v + "."))
			return v;


		}

		
	
	en = sqps.keys();
	Enumeration en2 = null;
	Hashtable h = null;
	SQLParser sq = null;
	String t = null;
	while (en.hasMoreElements()){
		t = (String)en.nextElement();
	if (l.startsWith(t + "."))
		return t;
		sq = getSQLParser(t);
		h = sq.getSelectAS();
		en2 = h.keys();
		while (en2.hasMoreElements())
		{
			n = (String)en2.nextElement();
			if (n.equals(l))
			return t;

			v = (String)h.get(n);
			if (v.equals(l))
			return t;
		}
	if (sq.getTableInfo().getColumnInfo(sq.getRealColName(l)) != null)
		return sq.getTable();
		
	}
	TableInfo ti = sqp.getTableInfo();
	if (ti != null)
	if (ti.getColumnInfo(sqp.getRealColName(l)) != null)
		return sqp.getTable();
return null;		
	}
public Hashtable getAliases(){
	return aliases;
}
public boolean isJoin(){
		if (sqps == null || sqps.size() < 1)
		return false;
		return true;
}
public Vector join(Vector r)throws SQLException{
	//sqp.mergeAliases(aliases);
	merge();

	
	if (sqps == null || sqps.size() < 1)
		return r;
	Enumeration en = sqps.elements();
	SQLParser s = null;
   //Hashtable<String, Hashtable> results  = new Hashtable<String, Hashtable>();	
	Hashtable h = null;
	Vector sl = new Vector();
	sl.add("*");
	while (en.hasMoreElements()){
		s = (SQLParser)en.nextElement();
		s.mergeAliases(aliases);
		 jiqlDBMgr.get(s.getProperties()).getCommand("VerifyTable").execute(s);
		h = Gateway.get(s.getProperties()).readTableValue(s.getTable(),s.getIncludeAllList(),sl,s.getEitherOrAllList(),s.isDistinct(),s);
		//(h + " JR " + s.getTable());
		if (h != null){
			if (h.size() < 1)
			{
	String t = (String)s.getAliases().get(s.getTable());
	if (t != null)
	{
		s.setTable(t);
		h = Gateway.get(s.getProperties()).readTableValue(s.getTable(),s.getIncludeAllList(),sl,s.getEitherOrAllList(),s.isDistinct(),s);
		//(h + " *******JR2************ " + s.getTable());

	}
	//else
				//(h + " *******NO JR2************ " + s);

			}
			s.setResultsTable(h);
		}
	}
	String rn = null;
	Criteria c = null;
	String n = null;
	String tt = null;
	Hashtable<String, String> rns  = new Hashtable<String, String>();	
	//Vector vm = new EZArrayList(sqps.keys());
	//vm.add(sqp.getTable());
	//(jincludealllist + " ****jincludealllist**** " + r + ":" + h);
	for (int ct = 0;ct < jincludealllist.size();ct++)
	{
		c = (Criteria)jincludealllist.elementAt(ct);
		if (!c.getCompareOperator().equals("="))
	   			throw JGException.get("operator_not_supported_for_joins",c.getCompareOperator() + " Operator not supported for joins");
		n = c.getName();
		tt = findTable(n);
	//(sqp.getTable() + ":" + n + " ****jincludealllist 2 **** " + tt);
//realm_user1:realm_username ****jincludealllist 2 **** realm_userrole1
//realm_user1:role.realm_username ****jincludealllist 2 **** role

		if (tt == null)
			 throw JGException.get("table_not_found_for_join_criteria",n + " Table not found for join criteria");
		if (!sqp.getTable().equals(tt) && sqps.get(tt) == null)
			 throw JGException.get("invalid_table_for_join_criteria",n + " Invalid Table for join criteria " + tt);
		if (sqp.getTable().equals(tt)){
		
			rn = n;
			//vm.remove(tt);
			//continue;
		}
		else{
		rns.put(tt,n);
		//vm.remove(tt);
			if (sqp.getTableInfo().getColumnInfo(sqp.getRealColName(n)) == null)
	{
			String t = (String)sqp.getAliases().get(sqp.getTable());
	if (t != null)
	{
		sqp.setTable(t);
	
	//("DID YO WOK RELAN " + t + ":" + n + ":"  + sqp.getRealColName(n) + ":" + sqp.getTableInfo() + ":" + sqp.getTableInfo().getColumnInfo(sqp.getRealColName(n)) + ":" + sqp);
}
	}
		
		
				if (sqp.getTableInfo().getColumnInfo(sqp.getRealColName(n)) != null)
		{
			rn = n;
			//vm.remove(sqp.getTable());
		}
			//continue;

		}

		n = c.getValueString();
		tt = findTable(n);

		if (tt == null)
			 throw JGException.get("table_not_found_for_join_criteria",n + " Table not found for join criteria");
		if (!sqp.getTable().equals(tt) && sqps.get(tt) == null)
			 throw JGException.get("invalid_table_for_join_criteria",n + " Invalid Table for join criteria " + tt);
		//(sqp.getTable() + ":" + tt + " THE RELAN NAME " + rn + ":" + n);
//realm_user1:role THE RELAN NAME realm_username:role.realm_username
//realm_user1:realm_user1 THE RELAN NAME role.realm_username:user.realm_username

		if (sqp.getTable().equals(tt)){
		
			//vm.remove(tt);
			rn = n;
			//continue;
		}
		else{
		//vm.remove(tt);
		rns.put(tt,n);
				if (sqp.getTableInfo().getColumnInfo(sqp.getRealColName(n)) != null)
		{
			rn = n;
			//vm.remove(sqp.getTable());
		}

		}

	}
	if (rn == null)
	{
 en = rns.keys();

while (en.hasMoreElements()){
	tt = en.nextElement().toString();
	n = rns.get(tt);
		if (sqp.getTableInfo().getColumnInfo(sqp.getRealColName(n)) != null)
		{
			rn = n;
			//vm.remove(sqp.getTable());
			break;
		}

}		
	}
	
if (rn == null)
 	 throw JGException.get("missing_main_join_dependency",sqp.getTable() + " Missing main join dependency ");

	
int type = sqp.getTableInfo().getColumnInfo(sqp.getRealColName(rn)).getColumnType();	
if (rns.size() > sqps.size())
 	 throw JGException.get("missing_join_dependencies",n + " Missing join dependencies " + rns + ":" + rns.size() + "!=" + sqps.size());
//else
 //	 //(n + " FOUND dependencies " + rns + ":" + rns.size() + "==" + sqps.size());

 en = rns.keys();

while (en.hasMoreElements()){
	tt = en.nextElement().toString();
	n = rns.get(tt);
	s = (SQLParser)sqps.get(tt);
	//("DID YO WOK " + tt + ":" + n + ":"  + s.getRealColName(n) + ":" + s.getTableInfo() + ":" + s.getTableInfo().getColumnInfo(s.getRealColName(n)) + ":" + s);
	if (s.getTableInfo().getColumnInfo(s.getRealColName(n)) == null)
	{
			String t = (String)s.getAliases().get(s.getTable());
	if (t != null)
	{
		s.setTable(t);
	}
	//("DID YO WOK @ FORCE " + tt + ":" + n + ":"  + s.getRealColName(n) + ":" + s.getTableInfo() + ":" + s.getTableInfo().getColumnInfo(s.getRealColName(n)) + ":" + s);

	}
	if (s.getTableInfo().getColumnInfo(s.getRealColName(n)).getColumnType() != type)
		throw JGException.get("incompatible_join_criteria",n + " Incompatible join criteria " + tt);
}

	//Hashtable  (String rn,Hashtable h,int typ,Object v)
	Vector vr = new Vector();
   	NameValuePairs row = null;
	h = null;


	String n2 = null;
	String tt2 = null;
	Vector joined = new Vector();
	Vector j1 = null;
	Vector j2 = null;
		EZArrayList nvr = new EZArrayList();
		Vector vt = null;
		Object val = null;
		NameValuePairs ref = null;

	
	
		for (int ct = 0;ct < jincludealllist.size();ct++)
	{
		c = (Criteria)jincludealllist.elementAt(ct);
		n = c.getName();
		tt = findTable(n);
		//("************TT1 " + tt);
		if (sqp.getTableInfo().getColumnInfo(sqp.getRealColName(n)) != null)
			tt = sqp.getTable();
		//("************TT2 " + tt);

		n2 = c.getValueString();
		tt2 = findTable(n2);
		if (sqp.getTableInfo().getColumnInfo(sqp.getRealColName(n2)) != null && !tt.equals(sqp.getTable()))
			tt2 = sqp.getTable();
	//role.realm_username Cannot join the same table realm_user1:realm_user1 
		if (tt.equals(tt2))
			tt = findTable(n);
		//("************TT3 " + tt);

		if (tt.equals(tt2))
		 throw JGException.get("cannot_join_same_table",n + " Cannot join the same table " + tt + ":" + tt2);
		
		if (joined.contains(tt) && joined.contains(tt2))
		 throw JGException.get("at_least_1_table_must_not_be_joined",n + " Trying to Join 2 tables already joined. At least 1 table must not be joined " + tt + ":" + tt2);
		if (joined.contains(tt))
			j1 = vr;
		else{
			if (tt.equals(sqp.getTable()))
				j1 = r;
			else
				j1 = new EZArrayList(getSQLParser(tt).getResultsTable().elements());
		}


		if (joined.contains(tt2))
		{
			j2 = vr;
		//("j2 1 " + j2);

		}
		else{
			if (tt2.equals(sqp.getTable())){
			
				j2 = r;
					//("j2 2 " + j2);

			}
			else{
			
				j2 = new EZArrayList(getSQLParser(tt2).getResultsTable().elements());
					//("j2 3 " + j2);

			}
		}
		nvr = new EZArrayList();
		vt = null;
		val = null;
		ref = null;
			for (int ct1 = 0; ct1 < j1.size(); ct1++){
			ref = (NameValuePairs)j1.elementAt(ct1);
			val = ref.get(n);
			if (val == null)continue;
			vt = joinTable(n2,j2,type,val,ref);
			if (vt.size() > 0)
				nvr.addEnumeration(vt.elements(),nvr);

			//for (int ct2 = 0; ct2 < j2.size(); ct2++){
			
				//Hashtable  (String rn,Hashtable h,int typ,Object v)

			
			//}

			}
			vr = nvr;
			if (vr.size() < 1)
				break;
	}
	
	
		
	return vr;
}

SQLParser getAnySQLParser(String t)throws SQLException{
	if (t.equals(sqp.getTable()))
		return sqp;
	return getSQLParser(t);
}



public String getRealColName(String n){
			String rn = null;
				Enumeration<SQLParser> ens = listSQLParsers();
				//(rn + " *** WOKED BEFORE 1 " + n);

			while (ens.hasMoreElements())
			{
				rn = ens.nextElement().getRealColName(n);
				//(rn + " *** WOKED BEFORE " + n);
				if (rn != null)
					return rn;
			}
			return null;
}
public SQLParser getSQLParser(String t)throws SQLException{
SQLParser s = sqps.get(t);
if (s != null){
	if (sqp != null && sqp.getConnection().isRemote())
		s.setConnection(sqp.getConnection());
	s.setTable(t);
}	
return s;
}

public Enumeration<SQLParser> listSQLParsers(){
return sqps.elements();
}

 SQLParser add(String t)throws SQLException{


SQLParser s = sqps.get(t);
if (s == null){
	if (sqp != null && sqp.getTable() != null && sqp.getTable().equals(t))
		return null;
	s = new SQLParser(sqp.getProperties());
	s.setTable(t);
	sqps.put(t,s);
}
return s;	
}

public void merge()throws SQLException{
	if (sqps.size() < 1)return;
	SQLParser s = (SQLParser)sqps.remove(sqp.getTable());
	if (s != null){
	
		sqp.mergeAliases(s.getAliases());
		Vector v2 = s.getOriginalSelectList();
		sqp.setOriginalSelectList(v2);
		
	
	}
	//(sqp.getOriginalSelectList() + " SELECTSSS U 2b " + sqp.getSelectList());

	String al = (String) sqp.getAliases().get(sqp.getTable());
	if (al != null){
	s = (SQLParser)sqps.remove(al);
	if (s != null)
		sqp.mergeAliases(s.getAliases());		
	}
	if (sqps.size() > 0)
	{

		Hashtable h = sqp.getAliases();
		Enumeration en1 = h.keys();

		while (en1.hasMoreElements()){
		String n = en1.nextElement().toString();
		String nv = (String)h.get(n);
		if (nv.equals(sqp.getTable()))
		{

			s = (SQLParser)sqps.remove(n);
			if (s != null)
			{
Hashtable v =	s.getSelectAS();
	Hashtable v2 =	sqp.getSelectAS();
	Enumeration en = v.keys();
	String k = null;
	while (en.hasMoreElements()){
	
		k = en.nextElement().toString();

		v2.put(k,v.get(k));
	}
	
		Vector vv =	s.getSelectList();
	Vector vv2 =	sqp.getSelectList();
	en = vv.elements();
	String itm = null;
	while (en.hasMoreElements()){
		itm = en.nextElement().toString();
	//(itm + " SELECTSSS U 2c " + sqp.getSelectList() + ":" + vv2.contains(itm) + s.getTable());

				if (itm.endsWith(".*") )
				{
					vv2.remove(itm);

					Vector fl = sqp.getJiqlTableInfo().getFieldList();
					String an = itm.substring(0,itm.length() - 1);
					for (int fct = 0;fct < fl.size();fct++){
						itm = an + fl.elementAt(fct).toString();
					addToTheSelectList(itm);
					//(!addToTheSelectList(itm))
		if (!vv2.contains(itm)){
		vv2.add(itm);
		}
				}
				}

	else if(!addToTheSelectList(itm))
		if (!vv2.contains(itm)){
		vv2.add(itm);
		}
	}
	sqp.updateSelects();				
	
	
	
				Vector v2b = s.getOriginalSelectList();
		sqp.setOriginalSelectList(v2b);

	
	
	
			}
		}
		}
	
		Vector vv2a =	sqp.getSelectList();
	String itm = null;
	Vector vv2 = (Vector)vv2a.clone();
	for (int ct = 0; ct < vv2.size();ct++){
		itm = vv2.elementAt(ct).toString();


				if (itm.endsWith(".*") )
				{
					vv2a.remove(itm);

				///{
					String an = itm.substring(0,itm.length() - 1);
					String tn = findTable(itm.substring(0,itm.length() - 2));
					if (tn == null)
						tn = (String)aliases.get(itm.substring(0,itm.length() - 2));
			//(itm.substring(0,itm.length() - 2) + " SELECTSSS U 24u2 " + tn + ":" + aliases + ":" + vv2a + ":" + itm);
					if (tn == null)continue;
					Vector fl = jiqlDBMgr.get(sqp.getProperties()).getTableInfo(tn).getFieldList();



					for (int fct = 0;fct < fl.size();fct++){
						itm = an + fl.elementAt(fct).toString();
					addToTheSelectList(itm);
					//(!addToTheSelectList(itm))
		if (!vv2a.contains(itm)){
		//vv2a.add(itm);
		}
				}
				}

		
		
	}
	
	
		
	}
	/*if (s!= null){
	Hashtable v =	s. ();
	Hashtable v2 =	sqp. ();
	Enumeration en = v.keys();
	String k = null;
	while (en.hasMoreElements()){
	
		k = en.nextElement().toString();
		org.jiql.util. (sqp.getTable() + " MERGE o " + k + ":" + v.get(k));

		v2.put(k,v.get(k));
	}
	
		Vector vv =	s.getSelectList();
	Vector vv2 =	sqp.getSelectList();
	en = vv.elements();
	String itm = null;
	while (en.hasMoreElements()){
		itm = en.nextElement().toString();
	if(! (itm))
		if (!vv2.contains(itm)){
		org.jiql.util. (sqp.getTable() + " MERGE 1 " + itm);
		vv2.add(itm);
		}
	}
	sqp.updateSelects();
	
	
	}*/
}
void selectASPut(String t,String c,String a)throws SQLException{

				SQLParser sq = add(t);
				Hashtable sl = sq.getSelectAS();
								//(a + " SELECTSSS U " + c);

					sl.put(a,c);

				//sl.put(c,a);

}
	
	
		public boolean addToEitherOrList(String t,String a,String sal,String sar,Criteria cr)throws SQLException{
		String l = cr.getName();
		String r = cr.getValueString();
			String left = findTable(t,a,sal,l);
			String right = findTable(t,a,sar,r);

		
		if (isJoinFilter(left,right)){
			jeitheroralllist.add(cr);
			return true;
		}
		String tab = isFilter(left,right,cr);
		if (tab != null){
			SQLParser sq = add(tab);
			Vector il = sq.getEitherOrAllList();
			cr.setSQLParser(sq);
			il.add(cr);
			return true;
		}

		return false;
	}
	
	
		public boolean addToIncludeList(String t,String a,String sal,String sar,Criteria cr)throws SQLException{
		String l = cr.getName();
		String r = cr.getValueString();
	//(t + " UNION ADD TO INCLUDE LIST " + a + ":" + sal  + ":" + sar + ":" + l + ":" + r);
			String left = findTable(t,a,sal,l);
			String right = findTable(t,a,sar,r);

		
		if (isJoinFilter( left, right)){
			jincludealllist.add(cr);
			return true;
		}
		String tab = isFilter(left,right,cr);
		if (tab != null){
			SQLParser sq = add(tab);
			Vector il = sq.getIncludeAllList();
			cr.setSQLParser(sq);
			il.add(cr);
			return true;
		}
		return false;
	}


	public String isFilter(String left,String right,Criteria cr){

	
	if (left == null && (right != null&& !sqp.getTable().equals(right))){
		/*Vector v = (Vector)l.get(right);
		if (v == null)
		{
			v   = new Vector<Criteria>();
			l.put(right,v);
		}
		v. (cr);*/
		return right;
	}
	
		if ((left != null && !sqp.getTable().equals(left)) && right == null){
		/*Vector v = (Vector)l.get(left);
		if (v == null)
		{
			v   = new Vector<Criteria>();
			l.put(left,v);
		}
		v.add(cr);*/
		return left;
	}
	//if (left != null && right == null)return true;
return null;		
	}
	


	public boolean isJoinFilter(String left,String right){
// :[table2]; :{t2=table2}; :{table2=[t2.price]}; :{table2={t2.price=the_price}} 
//select t1.name,t2.price   the_price,countf from testablet   t1,table2   t2 where the_price=t1.price

	//( t + ":" + a +   ":" + sal  +":" + sar + ":" + l + " log   " + r + ":" + left + ":" + right);
//testablet:t1:null:null:name log   the_price:null:null 
//testablet:t1:null:null:id log   t2.id:null:table2

	if (left != null && right != null && !left.equals(right))
		return true;
	//if (left == null && right != null)return true;
	//if (left != null && right == null)return true;
return false;		
	}
	
	
	/*
	 *
	 *
	 *
	 		public boolean addToEitherOrList(String t,String a,String sal,String sar,Criteria cr){
		if ( (t,a,sal,sar,cr.getName(),cr.getValueString())){
			jeitheroralllist.add(cr);
			return true;
		}
		return false;
	}
	
	 
	 public boolean addToIncludeList(String t,String a,String sal,String sar,Criteria cr){
		if ( (t,a,sal,sar,cr.getName(),cr.getValueString())){
			j .add(cr);
			return true;
		}
		return false;
	}

	public boolean  (String t,String a,String sal,String sar,String l,String r){
// :[table2]; :{t2=table2}; :{table2=[t2.price]}; :{table2={t2.price=the_price}} 
//select t1.name,t2.price   the_price,countf from testablet   t1,table2   t2 where the_price=t1.price
	String left = findTable(t,a,sal,l);
	String right = findTable(t,a,sar,r);

	org.jiql.util.( t + ":" + a +   ":" + sal  +":" + sar + ":" + l + " log   " + r + ":" + left + ":" + right);
//testablet:t1:null:null:name log   the_price:null:null 
//testablet:t1:null:null:id log   t2.id:null:table2

	if (left != null && right != null && !left.equals(right))
		return true;
	//if (left == null && right != null)return true;
	//if (left != null && right == null)return true;
return false;		
	}*/
	
		public String findTable(String t,String a,String sa,String l)throws SQLException{
// :[table2]; :{t2=table2}; :{table2=[t2.price]}; :{table2={t2.price=the_price}} 
//select t1.name,t2.price   the_price,countf from testablet   t1,table2   t2 where the_price=t1.price
	merge();
	if (sa != null && sa.equals(l))
		return t;
		
	if (l.startsWith(t + "."))
		return t;
	if (a != null && l.startsWith(a + "."))
		return t;


	Enumeration en = aliases.keys();
	String n = null;
	String v = null;
		Vector tables = new EZArrayList(sqps.keys());
		for (int ct = 0; ct < tables.size();ct++)
		{
			v = (String)tables.elementAt(ct);
		if (l.startsWith(v + "."))
			return v;


		}

		while (en.hasMoreElements())
		{
			n = (String)en.nextElement();
			v = (String)aliases.get(n);
		if (l.startsWith(n + "."))
			return v;
		if (l.startsWith(v + "."))
			return v;


		}

		
	
	en = sqps.keys();
	Enumeration en2 = null;
	Hashtable h = null;
	SQLParser sq = null;
	while (en.hasMoreElements()){
		t = (String)en.nextElement();
	if (l.startsWith(t + "."))
		return t;
		sq = add(t);
		h = sq.getSelectAS();
		en2 = h.keys();
		while (en2.hasMoreElements())
		{
			n = (String)en2.nextElement();
			if (n.equals(l))
			return t;

			v = (String)h.get(n);
			if (v.equals(l))
			return t;
		}
		
	}
	if (sqp.getTableInfo().getColumnInfo(sqp.getRealColName(l)) != null)
		return sqp.getTable();
return null;		
	}


	public boolean addToTheSelectList(String itm)throws SQLException{
			int i = itm.indexOf( ".");
			
			if (i > 0){
			String t = itm.substring(0,i);
			t = StringUtil.getTrimmedValue(t);
		Vector tables = new EZArrayList(sqps.keys());

			if (!tables.contains(t))
			{
				String t2 = (String)aliases.get(t);
				if (t2 != null)
					t = t2;
			}
			SQLParser s = getSQLParser(t);
			if (s != null){
				Vector sl = s.getSelectList();
				/*if (sl == null){
					sl = new Vector();
					selects.put(t,sl);
				}*/

			//itm = sqp.parseSelectAlias(itm);
			i =  itm.toLowerCase().indexOf(" as ");
			if (i > 0){
			
			String itmn = (StringUtil.getTrimmedValue(itm.substring(0,i)));
			String itmn2 = StringUtil.getTrimmedValue(itm.substring(i + 3,itm.length()));
			selectASPut(t,itmn,itmn2);
			itm = itmn2;
			}
			//else //(" NO AS 1 " + itm);
			//}else
					//(t + " SELECTSSS U 4 " + itm);

				sl.add(itm);
				return true;
			}
			t = (String)aliases.get(t);
			if (t != null){
			s = add(t);
			if (s != null){
				Vector sl = s.getSelectList();
				/*if (sl == null){
					sl = new Vector();
					selects.put(t,sl);
				}*/
			i =  itm.toLowerCase().indexOf(" as ");
			if (i > 0){
			
			String itmn = (StringUtil.getTrimmedValue(itm.substring(0,i)));
						String itmn2 = StringUtil.getTrimmedValue(itm.substring(i + 3,itm.length()));

			selectASPut(t,itmn,itmn2);
			itm = itmn2;
			}			//else //(" NO AS 2 " + itm);

				//(t + " SELECTSSS U 5 " + itm);
			
				sl.add(itm);
				return true;
			}
			}

			}
		return false;
	}
	

	
	public boolean addToSelectList(String itm)throws SQLException{
			merge();
			int i = itm.indexOf( ".");
			
			if (i > 0){
			String t = itm.substring(0,i);
			t = StringUtil.getTrimmedValue(t);
		Vector tables = new EZArrayList(sqps.keys());

			if (!tables.contains(t))
			{
				String t2 = (String)aliases.get(t);
				if (t2 != null)
					t = t2;
			}
			SQLParser s = add(t);
			if (s != null){
				Vector sl = s.getSelectList();
				/*if (sl == null){
					sl = new Vector();
					selects.put(t,sl);
				}*/

			//itm = sqp.parseSelectAlias(itm);
			//itm = s.getSFunctions().parse(itm);

			i =  itm.toLowerCase().indexOf(" as ");
			if (i > 0){
			
			String itmn = (StringUtil.getTrimmedValue(itm.substring(0,i)));
			String itmn2 = StringUtil.getTrimmedValue(itm.substring(i + 3,itm.length()));
			selectASPut(t,itmn,itmn2);
			itm = itmn2;
			}			//else //(" NO AS 3 " + itm);

			
			/* (t + " SELECTSSS U 2 " + itm);
				if (itm.endsWith(".*"))
				{
					String tn = findTable(t);
			 (t + " SELECTSSS U 24u " + tn + ":" + aliases);

					Vector fl = jiqlDBMgr.get(sqp.getProperties()).getTableInfo(tn).getFieldList();
					String an = itm.substring(0,itm.length() - 1);
					for (int fct = 0;fct < fl.size();fct++)
						sl.add(an + fl.elementAt(fct));
				}
				else*/
				sl.add(itm);
				return true;
			}
			t = (String)aliases.get(t);
			if (t != null){
			s = add(t);
			if (s != null){
				Vector sl = s.getSelectList();
				/*if (sl == null){
					sl = new Vector();
					selects.put(t,sl);
				}*/
			i =  itm.toLowerCase().indexOf(" as ");
			if (i > 0){
			
			String itmn = (StringUtil.getTrimmedValue(itm.substring(0,i)));
			String itmn2 = StringUtil.getTrimmedValue(itm.substring(i + 3,itm.length()));
			selectASPut(t,itmn,itmn2);
			itm = itmn2;
			}	//else //(" NO AS 4 " + itm);
				//(t + " SELECTSSS U 3 " + itm);
			
				sl.add(itm);
				return true;
			}
			}

			}
		return false;
	}
	
	public void parseTables(String t)throws SQLException{
			//("UPT: " + t);
			merge();
			EZArrayList uv = new EZArrayList(new StringTokenizer(t,","));
			//(" PARSE U aaaa :" + t + ":");

			String upstr = null;
			for (int ct = 0;ct < uv.size();ct++){
			upstr = uv.elementAt(ct).toString();
			upstr = upstr.trim();
			//(" PARSE U :" + upstr + ":");
			int i = upstr.toLowerCase().indexOf(" as ");
			if (i > 0){
				String alias = upstr.substring(i + " as ".length(),upstr.length());
				upstr = upstr.substring(0,i);
				upstr = upstr.trim();
				aliases.put(alias,upstr);
				upstr = upstr.trim();
				if (sqps.get(upstr) == null)
					add(upstr);
				add(upstr).addAlias(alias,upstr);
				continue;
			}
			else if (upstr.indexOf(" ") > 0){
				
			  i = upstr.indexOf(" ");
			  String ta = upstr.substring(0,i);
			  String a = upstr.substring(i + 1,upstr.length());
				a = a.trim();
				ta = ta.trim();
				if (!SQLParser.getReserved().contains(a)){
				aliases.put(a,ta);
				if (sqps.get(ta) == null)
					add(ta);
				add(ta).addAlias(ta,a);
				//(ta + " FOUND AS 5 " + a);
				continue;
					
				}
			}
				//else //(" NO AS 5 " + upstr);
			
			
				upstr = upstr.trim();
				if (sqps.get(upstr) == null)
					add(upstr);

				
			}
		
	}

	public String toString(){
		return "jiql.Union tables:" + sqps + ";aliases:" + aliases + ";selects:" + ";selectAS:" +  ";jincludealllist:" +  jincludealllist + ";jeitheroralllist:" + jeitheroralllist + ";includealllist:"   + ";eitheroralllist:" ;
	}

}


