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

package org.jiql.util;
import java.sql.SQLException;
import java.io.*;
import java.net.*;
import java.util.*;
import tools.util.EZArrayList;
import tools.util.NameValuePairs;
import tools.util.HashCache;
import tools.util.StringUtil;
import org.jiql.db.*;
import org.jiql.db.objs.*;
import org.jiql.JIQLGDataUtil;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.DatastoreService;


public class GAppEngineGateway extends Gateway
{



			Hashtable TableInfoC = new HashCache();

			public TableInfo readTableInfo(String t)throws SQLException{
				TableInfo v = (TableInfo)TableInfoC.get(t);
				//if (v != null)return v;
				v = new TableInfo();
				v.setTableName(t);

				Vector v2 = JIQLGDataUtil.listWhereEqual("jiqlTableInfo","tablename",t);

				//("v2 " + v2);
				for (int ct = 0;ct < v2.size();ct++)
				{
					JGNameValuePairs v3 = (JGNameValuePairs)v2.elementAt(ct);
							v.add(new ColumnInfo(v3));
				}
				synchronized (TableInfoC){
					TableInfoC.put(t,v);
				}
				return v;

			}



			public void writeTableInfo(String t,Hashtable hash)throws SQLException{
				String f = null;
				String ty = null;;
				long p = -1;
				
				Enumeration en = hash.keys();
				while (en.hasMoreElements())
				{
					f = en.nextElement().toString();
					ty = hash.get(f).toString();
					if (p > -1)
						writeTableInfo(t,f,ty,p);
					else
						p = writeTableInfo(t,f,ty,p);
				}


			}

			protected long writeTableInfo(String t,String f,String ty,long p)throws SQLException{

				Hashtable hash = new Hashtable();
				hash.put("tablename",t);
				hash.put("tablefield",f);
				hash.put("tablefieldtype",ty);
				synchronized (TableInfoC){
					TableInfoC.remove(t);
				}
				return JIQLGDataUtil.put("jiqlTableInfo",hash,p);

			}


		public void removeTableProp(String t,Transaction trans)throws SQLException{
			JIQLGDataUtil.deleteWhereEqual("jiqlTableProp","tablename",t,trans);

		}
		public void removeTableInfo(String t,Transaction trans)throws SQLException{
		JIQLGDataUtil.deleteWhereEqual("jiqlTableInfo","tablename",t,trans);
	synchronized (TableInfoC){
					TableInfoC.remove(t);
				}

		}		
			public JGNameValuePairs readTableProp(String t)throws SQLException{
				try{
				
				JGNameValuePairs o = JIQLGDataUtil.getWhereEqual("jiqlTableProp","tablename",t);
				return o;
				}catch (NullPointerException e){
				
				
				if (e.toString().indexOf("No API environment is registered for this thread") > -1)
					throw JGException.get("remote_config_required","Error connecting to DataStore. You probably need to configure REMOTE connection.");
					
				throw JGException.get("null_pointer","readTableProp NULL : " + e.toString());

				}
			}

		public synchronized int getAutoIncrementInt(SQLParser sqp,String f)throws SQLException{
				int ct = 0;

				try{
					StringBuffer itb = new StringBuffer( "jiqlAutoIncrementInt_").append(sqp.getTable());

						JGNameValuePairs o = JIQLGDataUtil.getWhereEqual(itb.toString(),"tablefield",f);
		
	
				Transaction trans = null;

				try{


				//(f + " getAutoIncrementInt " + o);
				if (o != null)
				{
				ct = o.getInt("incrementvalue") + 1;
				Hashtable h = null;
				while (true){
					h = readTableValueWhereEqual(sqp,sqp.getTable(),f,new Integer(ct));
					if (h == null || h.size() < 1)
						break;
				ct = ct + 1;
		
				}
				Hashtable hash = new Hashtable();
				hash.put("tablefield",f);
				hash.put("incrementvalue",ct);
//trans = JIQLGDataUtil.getTransaction();
					JIQLGDataUtil.update(itb.toString(),hash,o.getKeyId());
//trans.commit();
				}
				else{
					ct = ct + 1;
				Hashtable hash = new Hashtable();
				hash.put("tablefield",f);
				hash.put("incrementvalue",ct);

				JIQLGDataUtil.put(itb.toString(),hash)	;
					
				}

				//trans.commit();
				}catch (Exception e){
					if (trans != null)
					trans.rollback();
					e.printStackTrace();
					///(e);
					throw new SQLException(e.toString());
				}
	
	
				}catch (NullPointerException e){
				
				
				if (e.toString().indexOf("No API environment is registered for this thread") > -1)
					throw JGException.get("remote_config_required","Error connecting to DataStore for getAutoIncrementInt. You probably need to configure REMOTE connection.");
					
				throw JGException.get("null_pointer","getAutoIncrementInt NULL : " + e.toString());

				}

		return ct;	
	}

			public Vector showTables()throws SQLException{
				try{
				
				Vector v = JIQLGDataUtil.list("jiqlTableProp");
				Vector o = new Vector();
						Row nv = null;
						JGNameValuePairs jn = null;
					for (int ct = 0; ct < v.size();ct++)
					{
						jn = (JGNameValuePairs)v.elementAt(ct);
						nv = new Row(jn);
						o.add(nv);
						


					}
	
				return o;
				}catch (NullPointerException e){
				
				
				if (e.toString().indexOf("No API environment is registered for this thread") > -1)
					throw JGException.get("remote_config_required","Error connecting to DataStore. You probably need to configure REMOTE connection.");
					
				throw JGException.get("null_pointer","showTables NULL : " + e.toString());

				}
			}
			
			
			public Vector describeTable(SQLParser sqp)throws SQLException{
				String t = sqp.getTable();

				Vector v = JIQLGDataUtil.listWhereEqual("jiqlTableInfo","tablename",t);

				Vector o = new Vector();
						Row nv = null;
						JGNameValuePairs jn = null;
					jiqlTableInfo jti = jiqlDBMgr.get(sqp.getProperties()).getTableInfo( t);
					for (int ct = 0; ct < v.size();ct++)
					{
						jn = (JGNameValuePairs)v.elementAt(ct);
						nv = new Row();
						nv.put("Field",jn.get("tablefield"));
						nv.put("Type",jn.get("tablefieldtype"));
						if (jti.getPrimaryKeys().contains(jn.get("tablefield")))
							nv.put("Key","PRI");
						else
							nv.put("Key","");
						if (jti.getDefaultValues().get(jn.get("tablefield")) != null)
							nv.put("Default",jti.getDefaultValues().get(jn.get("tablefield")));						
						if (!jti.getNotNulls().contains(jn.get("tablefield")))
							nv.put("Null","YES");
						else
							nv.put("Null","NO");
						nv.put("Extra","");
						o.add(nv);
						


					}
	
				return o;

			}




			public Vector<Row> getPrimaryKeys(SQLParser sqp)throws SQLException{
				String t = sqp.getTable();
				Vector<Row> o = new Vector<Row>();

					jiqlTableInfo jti = jiqlDBMgr.get(sqp.getProperties()).getTableInfo( t);
				if (jti == null)
					//	throw JGException.get("table_not_exists","Table " + t + " doesn't exist");
					return o;
						

				Vector pk = jti.getPrimaryKeys();
				if (pk == null || pk.size() < 1) return o;
				
						String pn = null;
					Row nv = null;
					for (int ct = 0; ct < pk.size();ct++)
					{
						pn = (String)pk.elementAt(ct);
						nv = new Row();

/*
derby
TABLE_CAT  	TABLE_SCHEM  	TABLE_NAME  	COLUMN_NAME  	KEY_SEQ  	PK_NAME
	WS_PORTALU 	WSUSERS 	USERNAME 	1 	SQL090319102920090

mysql
TABLE_CAT  	TABLE_SCHEM  	TABLE_NAME  	COLUMN_NAME  	KEY_SEQ  	PK_NAME
rhkxim2k 	null 	wp_users 	ID 	1 	PRIMARY
*/

						nv.put("TABLE_CAT","");
						nv.put("TABLE_SCHEM","jiql");
						nv.put("TABLE_NAME",t);
						nv.put("COLUMN_NAME",pn);
						nv.put("KEY_SEQ",ct + 1);
						nv.put("PK_NAME","PRIMARY");
						
					o.add(nv);
						


					}
	
				return o;

			}





			public Vector<Row> getImportedKeys(SQLParser sqp)throws SQLException{
				String t = sqp.getTable();

					jiqlTableInfo jti = jiqlDBMgr.get(sqp.getProperties()).getTableInfo( t);

				Vector<Row> o = new Vector<Row>();
				if (jti == null )return o;
				
					Hashtable ch = jti.getConstraints();
						if (ch == null || ch.size() < 1)return o;
				jiqlConstraint pk = null;
			Enumeration en = ch.elements();
			Row nv = null;
			while(en.hasMoreElements())
			{
			
			pk = (jiqlConstraint)en.nextElement();
			if (pk == null || pk.size() < 1)continue;
			if (jiqlConstraint.FOREIGNKEY == pk.getType())
			{

			String n = null;
			String v = null;
			jiqlFunction ref = pk.getReference();
			for (int ct = 0;ct < pk.size();ct++)
			{
			n = pk.elementAt(ct).toString();
		 	v = ref.elementAt(ct).toString();
		nv = new Row();
	nv.put("PKTABLE_CAT","");
	nv.put("PKTABLE_SCHEM","jiql");
	nv.put("PKTABLE_NAME",ref.getName());
	nv.put("PKCOLUMN_NAME",v);
	nv.put("FKTABLE_CAT","");
	nv.put("FKTABLE_SCHEM","jiql");
	nv.put("FKTABLE_NAME",t);
	nv.put("FKCOLUMN_NAME",n);
	nv.put("KEY_SEQ",(ct + 1));
	nv.put("UPDATE_RULE",3);
	nv.put("DELETE_RULE",3);
	nv.put("FK_NAME",pk.getName());
	nv.put("PK_NAME","FOREIGN");
	nv.put("DEFERRABILITY",7);
		//(n + ":" + v + ":" + ct + 1 + ":" + pk.getName() + ":" + ref.getName() + ":GAPIK") ;
			o.add(nv);
			}
		
		
		
		
		
		
				
			}
			}
					return o;
			}

			public Vector<Row> getExportedKeys(SQLParser sqp)throws SQLException{
				String t = sqp.getTable();

					jiqlTableInfo jti = jiqlDBMgr.get(sqp.getProperties()).getTableInfo( t);

				Vector<Row> o = new Vector<Row>();
				if (jti == null || true)return o;
				Vector pk = jti.getPrimaryKeys();
				if (pk == null || pk.size() < 1) return o;
				
						String pn = null;
					Row nv = null;
					for (int ct = 0; ct < pk.size();ct++)
					{
						pn = (String)pk.elementAt(ct);
						nv = new Row();


//getExportedKeys
//PKTABLE_CAT  	PKTABLE_SCHEM  	PKTABLE_NAME  	PKCOLUMN_NAME  	FKTABLE_CAT  	FKTABLE_SCHEM  	FKTABLE_NAME  	FKCOLUMN_NAME  	KEY_SEQ  	UPDATE_RULE  	DELETE_RULE  	FK_NAME  	PK_NAME  	DEFERRABILITY
//	WS_PORTALU 	REALM_USERROLE 	REALM_USER 		WS_PORTALU 	REALM_USER 	REALM_USERNAME 	1 	3 	3 	WS_USERID_FK 	SQL090716111225810 	7



						nv.put("PKTABLE_CAT","");
						nv.put("PKTABLE_SCHEM","jiql");
						nv.put("PKTABLE_NAME",t);
						nv.put("PKCOLUMN_NAME",pn);
						nv.put("KEY_SEQ",ct + 1);
						nv.put("PK_NAME","PRIMARY");
						
					o.add(nv);
						


					}
	
				return o;

			}

			public Vector getColumns(SQLParser sqp)throws SQLException{
				String t = sqp.getTable();

				Vector v = JIQLGDataUtil.listWhereEqual("jiqlTableInfo","tablename",t);
				if (v == null || v.size() < 1)
					return new Vector();
				Vector o = new Vector();
						Row nv = null;
						JGNameValuePairs jn = null;
					jiqlTableInfo jti = jiqlDBMgr.get(sqp.getProperties()).getTableInfo( t);
					TableInfo ti = readTableInfo(t,sqp);
					ColumnInfo ci = null;
					
					Vector<String> fl = jti.getFieldList();
					Vector orv = new Vector();
					String f = null;
							for (int ctf = 0; ctf < fl.size();ctf++)
					{
						f = fl.elementAt(ctf);
											for (int ct = 0; ct < v.size();ct++)
					{
						jn = (JGNameValuePairs)v.elementAt(ct);
						if (jn.get("tablefield").equals(f))
							orv.add(jn);
					}
					}			
					
					for (int ct = 0; ct < orv.size();ct++)
					{
						jn = (JGNameValuePairs)orv.elementAt(ct);
						nv = new Row();
						nv.put("TABLE_CAT","");
						nv.put("TABLE_SCHEM","jiql");
						nv.put("TABLE_NAME",t);
						nv.put("COLUMN_NAME",jn.get("tablefield"));
						ci = ti.getColumnInfo(jn.get("tablefield").toString());
						nv.put("DATA_TYPE",ci.getColumnType());
						nv.put("TYPE_NAME",ci.getStandardTypeName());
						nv.put("COLUMN_SIZE",ci.getColumnSize());
						//nv.put("BUFFER_LENGTH",null);
						//nv.put("DECIMAL_DIGITS",null);
						//nv.put("NUM_PREC_RADIX",null);
						if (jti.getNotNulls().contains(jn.get("tablefield")))
						nv.put("NULLABLE",0);
						else
						nv.put("NULLABLE",1);
						nv.put("REMARKS","");
						if (jti.getDefaultValues().get(jn.get("tablefield")) != null)
							nv.put("COLUMN_DEF",jti.getDefaultValues().get(jn.get("tablefield")));						
						//else
						//	nv.put("COLUMN_DEF",null);
						//nv.put("SQL_DATA_TYPE",null);
						//nv.put("SQL_DATETIME_SUB",null);
						nv.put("CHAR_OCTET_LENGTH",ci.getColumnSize());
						nv.put("ORDINAL_POSITION",ct + 1);	
						if (jti.getNotNulls().contains(jn.get("tablefield")))
						nv.put("IS_NULLABLE","NO");
						else
						nv.put("IS_NULLABLE","YES");												

						o.add(nv);
						


					}
	
				return o;

			}

			public Vector jiqlDescribeTable(SQLParser sqp)throws SQLException{
				String t = sqp.getTable();


				Vector o = new Vector();
						Row nv = null;
						JGNameValuePairs jn = null;
					jiqlTableInfo jti = jiqlDBMgr.get(sqp.getProperties()).getTableInfo( t);
					//for (int ct = 0; ct < v.size();ct++)
					{
						nv = new Row();
						nv.put("prefixName",jti.getPrefixName());
						nv.put("prefix",jti.isPrefix());
						nv.put("tableleafs",jti.hasTableLeafs());
						if (jti.hasTableLeafs())
						nv.put("leafcount",getTotalLeafCount(t,jti.getPrefixName()));
						else
						nv.put("leafcount",1);
						o.add(nv);
						


					}
	
				return o;

			}

			
			public void writeTableProp(String t,String tp,jiqlTableInfo ti)throws SQLException{
			//				JIQLGDataUtil.update(//(t),row,new Long(row.getRowId()).longValue());

				//if (id < )
				//JIQLGDataUtil.deleteWhereEqual("jiqlTableProp","tablename",t);

				Hashtable hash = new Hashtable();
				hash.put("tablename",t);
				hash.put("tableprop",tp);
				if (ti.getTId() > -1)try{
				
					JIQLGDataUtil.update("jiqlTableProp",hash,ti.getTId());
				}catch (Exception e){
					throw new SQLException(e.toString());
				}
				else
					ti.setTId(JIQLGDataUtil.put("jiqlTableProp",hash));

			}


public int getRowCount(String t,SQLParser sqp)throws SQLException{

				int rc = 0;
				int ct = 1;
				String tn = null;
				int r = 0;
				while (true){
				//tn = convertToJiql(t,ct,sqp);
				r = getLeafCount(t,ct,sqp);
				if (r <= 0)
					break;
				rc = rc + r;
				ct = ct + 1;
				}
return rc;

}

public Hashtable readTableValueWhereEqual(SQLParser sqp,String t,String n,Object v)throws SQLException{

					EZArrayList ez = new EZArrayList();
				int ct = 1;
				String tn = null;
				while (true){
				tn = convertToJiql(t,ct,sqp);
				if (getLeafCount(t,ct,sqp) <= 0)
					break;
				ez.addEnumeration(JIQLGDataUtil.listWhereEqual(tn,n,v).elements(),ez);
				ct = ct + 1;
				}
return readTableValue(sqp,t,ez);	
}






public Hashtable readTableValueWhereLessThan(SQLParser sqp,String t,String n,Object v)throws SQLException{

//return  (sqp,t,JIQLGDataUtil.listWhereLessThan( (t),n,v));	
					EZArrayList ez = new EZArrayList();
				int ct = 1;
				String tn = null;

				while (true){
				tn = convertToJiql(t,ct,sqp);
				if (getLeafCount(t,ct,sqp) <= 0)
					break;
				ez.addEnumeration(JIQLGDataUtil.listWhereLessThan(tn,n,v).elements(),ez);
				ct = ct + 1;
				}
return readTableValue(sqp,t,ez);

}

public Hashtable readTableValueWhereGreaterThan(SQLParser sqp,String t,String n,Object v)throws SQLException{

//return  (sqp,t,JIQLGDataUtil.listWhereGreaterThan( (t),n,v));	

	EZArrayList ez = new EZArrayList();
	int ct = 1;
	String tn = null;

	while (true){
	tn = convertToJiql(t,ct,sqp);
	if (getLeafCount(t,ct,sqp) <= 0)
		break;
	ez.addEnumeration(JIQLGDataUtil.listWhereGreaterThan(tn,n,v).elements(),ez);
	ct = ct + 1;
	}
	return readTableValue(sqp,t,ez);

}

public Hashtable readTableValueWhereLessThanOrEqual(SQLParser sqp,String t,String n,Object v)throws SQLException{

//return  (sqp,t,JIQLGDataUtil.listWhereLessThanOrEqual( (t),n,v));	
	EZArrayList ez = new EZArrayList();
	int ct = 1;
	String tn = null;

	while (true){
	tn = convertToJiql(t,ct,sqp);
	if (getLeafCount(t,ct,sqp) <= 0)
		break;
	ez.addEnumeration(JIQLGDataUtil.listWhereLessThanOrEqual(tn,n,v).elements(),ez);
	ct = ct + 1;
	}
	return readTableValue(sqp,t,ez);

}

public Hashtable readTableValueWhereGreaterThanOrEqual(SQLParser sqp,String t,String n,Object v)throws SQLException{

//return  (sqp,t,JIQLGDataUtil.listWhereGreaterThanOrEqual( (t),n,v));	
	EZArrayList ez = new EZArrayList();
	int ct = 1;
	String tn = null;

	while (true){
	tn = convertToJiql(t,ct,sqp);
	if (getLeafCount(t,ct,sqp) <= 0)
		break;
	ez.addEnumeration(JIQLGDataUtil.listWhereGreaterThanOrEqual(tn,n,v).elements(),ez);
	ct = ct + 1;
	}
	return readTableValue(sqp,t,ez);
}

static String leafstem = "leafstem";
		
		
		
		public static String convertToJiql(String t,int li,SQLParser sqp)throws SQLException{
		jiqlTableInfo jti = jiqlDBMgr.get(sqp.getProperties()).getTableInfo( t);
		if (jti != null)
		{
			if(jti.isPrefix())
			return convertToJiql(t,li,jti.getPrefixName());
			else
				return convertToJiql(t,li,"");
		
		}
			
		return convertToJiql(t,li,"jiql");
		}
		
			public static String convertToJiql(String t,int li,String prefix){
				if (prefix.length() > 0)
					prefix = prefix + "_";
				if (li < 2)
				return prefix + t ;
				return prefix + t + "_" + li;
			}
static Hashtable tkeys = new HashCache();
			static Key getParentKey(String t)throws SQLException{
				Key k = (Key)tkeys.get(t);
				if (k != null)return k;
				/*if (JIQLGDataUtil.get(t,leafstem) == null)
				{
					 (t,new Hashtable(),leafstem);
				}*/
				k = KeyFactory.createKey(t,leafstem);
				synchronized (tkeys){
				
				tkeys.put(t,k);
				}
				return k;
			}
	
	

		public int getTotalLeafCount(String t,String prefix)throws java.sql.SQLException{
							int ct = 1;
				while (true){
				if (getLeafCount(t,ct,prefix) <= 0)
					break;
				ct = ct + 1;
				}
				return ct -1;
		}
	
		int getLeafCount(String t,int ct,String prefix)throws SQLException{

				return JIQLGDataUtil.count(convertToJiql(t,ct,prefix));
			}
			
		/*int getLeafCount(String t,int ct)throws SQLException{

				return JIQLGDataUtil.count(convertToJiql(t,ct));
			}*/


		int getLeafCount(String t,int ct,SQLParser sqp)throws SQLException{

				return JIQLGDataUtil.count(convertToJiql(t,ct,sqp));
			}

			
			public void dropTable(String t,SQLParser sqp)throws SQLException{
				int ct = 1;
				String tn = null;
				//JIQLTableIds tid =  getTableIDObj(t);
				while (true){
				tn = convertToJiql(t,ct,sqp);
				if (getLeafCount(t,ct,sqp) <= 0)
					break;
				JIQLGDataUtil.delete(tn);
				ct = ct + 1;
				}
				//JIQLGDataUtil.deleteWhereEqual("jiqlTableProp","tablename",t);
			//	JIQLGDataUtil.deleteWhereEqual(" ","tablename",t);
				jiqlDBMgr.get(getProperties()).removeTableInfo(t);
				removeTableProp(t,null);
				try{
					removeTableInfo(t,null);
				}
				catch (Exception rx){
				
				Transaction trans = JIQLGDataUtil.getTransaction();

				try{
				
				removeTableInfo(t,trans);
				trans.commit();
				}catch (Exception e){
					trans.rollback();
					throw new SQLException(e.toString());
				}			

				}
				StringBuffer itb = new StringBuffer( "jiqlAutoIncrementInt_").append(t);
				JIQLGDataUtil.delete(itb.toString());
				
   		  //  EntityManager em = EMF.get().createEntityManager();

	 //JIQLTableIds tids = em.find(JIQLTableIds.class, t);
	 	//		em.remove(tids);
	 	//		em.close();

			}

String getRealTableName(String t){
	//h.getKeyName() + "_" + h.getTableName()
	int i = t.indexOf("_");
	return t.substring(i + 1,t.length());
}
String getRealID(String t){
	//h.getKeyName() + "_" + h.getTableName()
	int i = t.indexOf("_");
	return t.substring(0,i);
}

			public void deleteTableValue( String t,String rid)throws SQLException{
	
				Key pk = getParentKey(getRealTableName(rid));
				JIQLGDataUtil.deleteById(getRealTableName(rid),new Long(getRealID(rid)).longValue(),pk);
			}



			public void updateTableValue(String t,Row row,SQLParser sqp)throws SQLException{
				

				try{
				//(row.getTableName() + ":" + row.getRealID()+ " updateTableValue " + t);
				Key pk = getParentKey(row.getTableName());
				/*if (!sqp.getConnection().getAutoCommit())
					sqp.getConnection().begin();*/
				//DatastoreService datastore = JIQLGDataUtil.getDatastoreService();
  	 			//Transaction trans = null;
  	 			if (!sqp.getConnection().getAutoCommit()){
  	 			
  	 			JIQLGDataUtil.transUpdate(row.getTableName(),row,new Long(row.getRealID()).longValue(),pk,sqp.getConnection());

  	 			}else
				JIQLGDataUtil.update(row.getTableName(),row,new Long(row.getRealID()).longValue(),pk);
				}catch (Exception e){
					//org.jiql.util. (e);
					e.printStackTrace();
					throw new SQLException(e.toString());
				}
	
	

			}


 static int maxLeafSize=958;




    
    
    static int readTries = 5;
static int writeTries = 15;
static long wtP = 300;
static int maxTS = 5000;

/*	int maxLeafSize (String t,SQLParser sqp)
	{
			jiqlTableInfo jti = jiqlDBMgr.get(sqp.getProperties()).getTableInfo( t);
		if (jti != null && jti.hasTableLeafs())
	
		return maxLeafSize;
	}*/
    	 public void writeTableRow(String t,Hashtable hash,SQLParser sqp)
     throws SQLException

    {
			//String enc = (String)sqp.getProperties().get("encoding");
			/*if (enc != null){
			Enumeration en = hash.keys();
			String k = null;
			Object v = null;
			Object dv = null;
		
			while (en.hasMoreElements()){
			k = en.nextElement().toString();
			v = hash.get(k).toString();

			if (v != null){
				if (v instanceof String){
					try{
					System.out.println(k + " WR ENCODE RR " + v);
					v = new String(v.toString().getBytes(), "UTF-8");
					System.out.println(k + " WR ENCODED RR " + v);
					hash.put(k,v);
					}catch (java.io.UnsupportedEncodingException ue){
					throw JGException.get("UnsupportedEncodingException","Error writing to table : " + ue.toString());

					} 
				}
			
			}
			}
			}*/
			
			
	Throwable ex = null;
for (int ct = 0; ct < writeTries;ct++)
{
	boolean suc = false;
   		long rid = 0;
   		String tn = null;
   		Key pk = null;
try{

   				

							int tct = 1;
				
			//	JIQLTableIds tid =  getTableIDObj(t);

				jiqlTableInfo jti = jiqlDBMgr.get(sqp.getProperties()).getTableInfo( t);
		if (jti != null && !jti.hasTableLeafs())
				tn = convertToJiql(t,tct,sqp);
				else
				while (true){
				tn = convertToJiql(t,tct,sqp);

				if (getLeafCount(t,tct,sqp) >= maxLeafSize)
				{
					tct = tct + 1;
					continue;
				}
				break;
				}
				 pk = getParentKey(tn);

//JIQLTableIds tids = getTableIDObj(t);

//Entity  tx = null;
//EntityManager em = null;
try
{
		//(tn + " GAEput  " + hash + ":" + pk);
		
		if (!sqp.getConnection().getAutoCommit()){
			try{
			
			//(" WRUIT TAB ROW ? " + hash);
						DatastoreService datastore = JIQLGDataUtil.getDatastoreService();
  	 			Transaction trans = datastore.beginTransaction(); 
  	 			sqp.getConnection().setTransaction(trans);
  	 			rid = JIQLGDataUtil.put(tn,hash,pk,datastore,trans);
			}finally{
				return ;
			}
  	 			}
		
		
		rid = JIQLGDataUtil.put(tn,hash,pk);

      suc = true;
      return;
    } finally {
    
    
    }
}catch (Exception e){
	ex = e;
	//if ((ct) == (writeTries -3))
	JGUtil.log(rid + ":" +ct + "WRITE TABLE TRYING " + t + ":" + e.toString());
}

            if (!suc && rid > 0){
  	JGUtil.log(tn + ":" + ct + "deleteById TABLE TRYING 1 " + rid + ":" + pk);
        
        JIQLGDataUtil.deleteById(tn,rid,pk);
  	JGUtil.log(tn + ":" + ct + "deleteById TABLE TRYING 2 " + rid + ":" + pk);

        }
try{

Thread.currentThread().sleep(wtP);
}catch (Exception et){
	tools.util.LogMgr.err("Write Try Sleep error " + et.toString());
}
}
	if (ex instanceof SQLException)
		throw (SQLException)ex;
	throw JGException.get("error_writing_to_table","Error writing to table : " + ex.toString());


    }


			public Hashtable readTableValue(SQLParser sqp,String t)throws SQLException{
					//Vector v = JIQLGDataUtil.list( (t));
					EZArrayList v = new EZArrayList();
				int ct = 1;
				String tn = null;
			    //EntityManager em = EMF.get().createEntityManager();

				//JIQLTableIds tid =  getTableIDObj(t);

				while (true){
				tn = convertToJiql(t,ct,sqp);
				//(String.valueOf(tid. (ct)) + "   " + tn + ":" + ct);
				if (getLeafCount(t,ct,sqp) <= 0)
					break;
				v.addEnumeration(JIQLGDataUtil.list(tn).elements(),v);
				ct = ct + 1;
				}
				//em.close();
					
					
					
				return readTableValue(sqp,t,v);
			}


			public Hashtable readTableValue(SQLParser sqp,String t,Vector v)throws SQLException{
					
				//	if (readTableProp(t) == null)
				//	 throw JGException.get("table_not_exist","Table does not exists! " + t);
					Hashtable h = new Hashtable();
					JGNameValuePairs jn = null;
					Row nv = null;
					for (int ct = 0; ct < v.size();ct++)
					{
						jn = (JGNameValuePairs)v.elementAt(ct);
					//	String enc = (String)sqp.getProperties().get("encoding");
					//	if (enc != null)
					//		jn.setEncoding(enc);
						if (jn.getKeyName().equals(leafstem))
							continue;
						nv = new Row(jn);
						nv.setSQLParser(sqp);
						//(jn + " ******   1 ***** " + nv);
						//(jn.getKeyName() + " ******   2 ***** " + nv.getRowId());
						
						
						h.put(nv.getRowId(),nv);
						


					}

					return h;
			}



public int countTableValueWhereEqual(SQLParser sqp,String t,String n,Object v)throws SQLException{

					int ez = 0;
				int ct = 1;
				String tn = null;
				while (true){
				tn = convertToJiql(t,ct,sqp);
				if (getLeafCount(t,ct,sqp) <= 0)
					break;
				ez = ez + (JIQLGDataUtil.countWhereEqual(tn,n,v));
				ct = ct + 1;
				}
return ez;	
}






public int countTableValueWhereLessThan(SQLParser sqp,String t,String n,Object v)throws SQLException{

//return  (sqp,t,JIQLGDataUtil.countWhereLessThan( (t),n,v));	
					int ez = 0;
				int ct = 1;
				String tn = null;

				while (true){
				tn = convertToJiql(t,ct,sqp);
				if (getLeafCount(t,ct,sqp) <= 0)
					break;
				ez = ez + (JIQLGDataUtil.countWhereLessThan(tn,n,v));
				ct = ct + 1;
				}
return ez;

}

public int countTableValueWhereGreaterThan(SQLParser sqp,String t,String n,Object v)throws SQLException{

//return  (sqp,t,JIQLGDataUtil.countWhereGreaterThan( (t),n,v));	

	int ez = 0;
	int ct = 1;
	String tn = null;

	while (true){
	tn = convertToJiql(t,ct,sqp);
	if (getLeafCount(t,ct,sqp) <= 0)
		break;
	ez = ez + (JIQLGDataUtil.countWhereGreaterThan(tn,n,v));
	ct = ct + 1;
	}
	return ez;

}

public int countTableValueWhereLessThanOrEqual(SQLParser sqp,String t,String n,Object v)throws SQLException{

//return  (sqp,t,JIQLGDataUtil.countWhereLessThanOrEqual( (t),n,v));	
	int ez = 0;
	int ct = 1;
	String tn = null;

	while (true){
	tn = convertToJiql(t,ct,sqp);
	if (getLeafCount(t,ct,sqp) <= 0)
		break;
	ez = ez + (JIQLGDataUtil.countWhereLessThanOrEqual(tn,n,v));
	ct = ct + 1;
	}
	return ez;

}

public int countTableValueWhereGreaterThanOrEqual(SQLParser sqp,String t,String n,Object v)throws SQLException{

//return  (sqp,t,JIQLGDataUtil.countWhereGreaterThanOrEqual( (t),n,v));	
	int ez = 0;
	int ct = 1;
	String tn = null;

	while (true){
	tn = convertToJiql(t,ct,sqp);
	if (getLeafCount(t,ct,sqp) <= 0)
		break;
	ez = ez + (JIQLGDataUtil.countWhereGreaterThanOrEqual(tn,n,v));
	ct = ct + 1;
	}
	return ez;
}



	
	

}