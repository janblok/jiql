/*
 Copyright (c) 2007-2009 WebAppShowCase DBA Appcloem (http://www.appcloem.com). All rights reserved.

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

package org.jiql;

import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Vector;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

import tools.util.EZArrayList;
import tools.util.HashCache;

import org.jiql.util.JGNameValuePairs;
import org.jiql.util.JGException;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.FetchOptions.Builder ;
import org.jiql.jdbc.jiqlConnection;

import org.jiql.util.JGUtil;

import java.sql.SQLException;

public class JIQLGDataUtil
{

   // private static final Logger log = Logger.getLogger(VSCFilter.class.getName());
	public static long put(String entit,Hashtable columns)throws JGException
	{

		 return put(entit,columns,-1);



	}
	
	private static long mtd = 25000;
	private static long mc = 40;
	
	private static HashCache tc = new HashCache();
	
	
		public static int countWhereEqual(String table,String field,Object value)throws SQLException
	{
		return countWhere(table,field,value,Query.FilterOperator.EQUAL);
	}
	
		public static int countWhereGreaterThanOrEqual(String table,String field,Object value)throws SQLException
	{
		return countWhere(table,field,value,Query.FilterOperator.GREATER_THAN_OR_EQUAL);
	}

		public static int countWhereLessThanOrEqual(String table,String field,Object value)throws SQLException
	{
		return countWhere(table,field,value,Query.FilterOperator.LESS_THAN_OR_EQUAL);
	}
		public static int countWhereGreaterThan(String table,String field,Object value)throws SQLException
	{
		return countWhere(table,field,value,Query.FilterOperator.GREATER_THAN);
	}

		public static int countWhereLessThan(String table,String field,Object value)throws SQLException
	{
		return countWhere(table,field,value,Query.FilterOperator.LESS_THAN);
	}
	
	
	
	protected static void checkThread(int c)throws JGException{
	 if (c > mc)
	 	checkThread();
	}
	
		public static void removeThread(){
		String tid = String.valueOf(Thread.currentThread().getId());
		tc.remove(tid);
		}
	
	protected static void checkThread()throws JGException{
/*		String tid = String.valueOf(Thread.currentThread().getId());
		long tct = tc.getLong(tid);
		if (tct < 1){
			tct = System.currentTimeMillis();
			tc.put(tid,tct);
		}
		if (System.currentTimeMillis() - tct > mtd)
					throw JGException.get("thread_timeout"," Timeout! Try to break up the SQL Query. " + System.currentTimeMillis()  + ":" + tct + ":" + mtd);
*/
	}
	 public static Object convert(Object o){
	 	if (o == null)
	 		return o;
	 	if (o instanceof java.sql.Date)
	 		return new java.util.Date(((java.sql.Date)o).getTime());	
	 	return o;
	 }
		public static long put(String entit,Hashtable columns,long p)throws JGException
	{
		checkThread();
		 Entity e = null;
		 if (p > -1)
		 {
		 
	 		Key key = KeyFactory.createKey(entit,p);
		 	e = new Entity(entit,key);
		 }
		else		 	
		 e = new Entity(entit); 
		 Enumeration en = columns.keys();
		 String cn = null;
		 Object o = null;
		 while (en.hasMoreElements()){
		 	 cn = en.nextElement().toString();	
		 	 o = columns.get(cn);
		 	 
		 	 if (o instanceof String)
		 	 {
		 	 		 	 	if (((String)o).length() > maxCL)
		 	 o = new com.google.appengine.api.datastore.Text((String)o);
		 	 
		 	 }
     		e.setProperty(cn,convert(o)); 
		 }
		 DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
     	datastore.put(e); 
     		return e.getKey().getId();



	}
	
	
			public static void put(String entit,Hashtable columns,String p)throws JGException
	{
		checkThread();
		 Entity e = null;
		 //	Key key = KeyFactory.createKey(entit,p);
		 	e = new Entity(entit,p);
		
		 Enumeration en = columns.keys();
		 String cn = null;
		 Object o = null;
		 while (en.hasMoreElements()){
		 	 cn = en.nextElement().toString();	
     		if (o instanceof String)
		 	 {
			 	 	if (((String)o).length() > maxCL)
					o = new com.google.appengine.api.datastore.Text((String)o);
			 }
     		e.setProperty(cn,convert(o)); 
		 }
		 DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
     	datastore.put(e); 

	}
	



		public static long put(String entit,Hashtable columns,Key p,DatastoreService datastore,Transaction trans)throws SQLException
	{
   		//(entit + " put 1 " + columns);

		checkThread();
		 Entity e = null;

		 	
		 e = new Entity(entit,p); 
		 Enumeration en = columns.keys();
		 String cn = null;
		 Object o = null;
		 while (en.hasMoreElements()){
		 	 cn = en.nextElement().toString();	
     		o = columns.get(cn);
     		if (o instanceof String)
		 	 {
			 	 	if (((String)o).length() > maxCL)
					o = new com.google.appengine.api.datastore.Text((String)o);
			 }
     		e.setProperty(cn,convert(o)); 
     		}
	//	 DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
  	//Transaction trans = datastore.beginTransaction()  ;
       	datastore.put(trans,e); 
     		long k = e.getKey().getId();

		return k;

	}










		public static long put(String entit,Hashtable columns,Key p)throws SQLException
	{
   		//(entit + " put 1 " + columns);

		checkThread();
		 Entity e = null;

		 	
		 e = new Entity(entit,p); 
		 Enumeration en = columns.keys();
		 String cn = null;
		 Object o = null;
		 while (en.hasMoreElements()){
		 	 cn = en.nextElement().toString();	
     		o = columns.get(cn);
     		if (o instanceof String)
		 	 {
			 	 	if (((String)o).length() > maxCL)
					o = new com.google.appengine.api.datastore.Text((String)o);
			 }
     		e.setProperty(cn,convert(o)); 
     		}
		 DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    
    	        Throwable ex = null;   
   for (int ct = 0; ct < readTries;ct++)
{
try{ 
     		//(entit + " put 2 " + columns);
//  		    EntityManager em = EMF.get().createEntityManager();
//Entity  tx = em.get ();

	Transaction trans = datastore.beginTransaction()  ;
	boolean suc = false;


try{

//tx.begin();
     	datastore.put(e); 

     		long k = e.getKey().getId();
trans.commit();
suc = true;
return k;
   } finally {
  
    //if (tx.isActive())
    //{
        //tx.rollback();
			if (!suc)
           try{
			
           trans.rollback();
			}catch (Exception e2){
				tools.util.LogMgr.err("JGU.put TABLE.rollback " + e2.toString());
			}

    //}
     // em.close();
   }

	}catch (java.util.ConcurrentModificationException ec){
	ex = ec;
	if ((ct) == (readTries -3))
	System.out.println(ct + "JGU.put TABLE TRYING " + entit  +  ":" + ec.toString());
}
try{

Thread.currentThread().sleep(wtP);
}catch (Exception et){
	tools.util.LogMgr.err("JGU.put Try Sleep error " + et.toString());
}
}
	if (ex instanceof JGException)
		throw (JGException)ex;
	if (ex instanceof SQLException)
		throw (SQLException)ex;

	throw JGException.get(entit + " error_put_table","Error writing to table : " + ex.toString());
	


	}



	public static int countWhere(String table,String field,Object value,Query.FilterOperator qf)throws SQLException
	{
		checkThread();
		EZArrayList ez = new EZArrayList();
		//(" listWhere: " + ":" + qf + ":" + field + ":" + value + ":" + value.getClass().getName());


		Query q = new Query(table);
		q = q.addFilter(field,qf,value);
		 DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
     	 PreparedQuery pq = datastore.prepare(q) ;
		return pq.countEntities();
}
	
		public static int count(String table)throws SQLException
	{
	        Throwable ex = null;   
   for (int ct = 0; ct < readTries;ct++)
{
try{   
		 DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		 Query q = new Query(table); 
     	 PreparedQuery pq = datastore.prepare(q) ;
		return pq.countEntities();

	}catch (com.google.appengine.api.datastore.DatastoreTimeoutException e){
	ex = e;
	if ((ct) == (readTries -3))
	System.out.println(ct + "JGU.count TABLE TRYING " + table  +  ":" + e.toString());
}
try{

Thread.currentThread().sleep(wtP);
}catch (Exception et){
	tools.util.LogMgr.err("JGU.count Try Sleep error " + et.toString());
}
}
	if (ex instanceof JGException)
		throw (JGException)ex;
	if (ex instanceof SQLException)
		throw (SQLException)ex;

	throw JGException.get(table + " error_count_table","Error counting table : " + ex.toString());
	

		
	}	
	static int readTries = 18;
	static long wtP = 350;
		public static Vector<JGNameValuePairs> list(String table)throws JGException
	{
		 DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();


		JGNameValuePairs jn = null;
			 Query q = new Query(table); 
                ArrayList<Key> keys = new ArrayList<Key>(); 
     	 PreparedQuery pq = datastore.prepare(q) ;
		int ezt = pq.countEntities();

		if (ezt > fLimit)
			throw JGException.get("result_too_large"," Result too LARGE. Try narrowing query by adding more filters: " + ezt);

                Iterator it = (pq.asIterator()); 
 
//new ArrayList
				Entity entity = null;
				Vector<JGNameValuePairs> ez = new Vector<JGNameValuePairs>();
				//int ct = 0;
        Throwable ex = null;   
   for (int ct = 0; ct < readTries;ct++)
{
try{        
                while (it.hasNext()) { 
                	//ct = ct + 1;
             		//checkThread(ct);

                	entity = (Entity)it.next();
			jn = new JGNameValuePairs(new Hashtable(entity.getProperties()));
			jn.setKeyName(entity.getKey().getName());
			jn.setKeyId(entity.getKey().getId());
			jn.setTableName(table);
			//(entity.getParent() + ":" + entity.getKey().getName() + " ****** list 2 ***** " + entity.getKey().getId());

			ez.add(jn);

		}
		return ez;
	}catch (com.google.appengine.api.datastore.DatastoreTimeoutException e){
	ex = e;
	if ((ct) == (readTries -3))
	System.out.println(ct + "JGU.list TABLE TRYING " + table  + ":" + ez.size() + ":" + e.toString());
}
try{

Thread.currentThread().sleep(wtP);
}catch (Exception et){
	tools.util.LogMgr.err("JGU.list Try Sleep error " + et.toString());
}
}
	if (ex instanceof JGException)
		throw (JGException)ex;
	throw JGException.get("error_listing_table",table + " Error listing table : " + ex.toString());
	

	}
	
	public static Transaction getTransaction(){
	
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	Transaction trans = datastore.beginTransaction()  ;
	return trans;
	}
	
		public static DatastoreService getDatastoreService(){
	
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		return datastore;
	}
	
	
			public static void delete(String kind)throws JGException
	{
		delete(kind,null);
	}
	static int fLimit = 999;
	static int dLimit = 100;
	
		public static void delete(String kind,Transaction trans)throws JGException
	{
		 DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		FetchOptions fo = FetchOptions.Builder.withLimit(dLimit);
		fo.limit(dLimit);
		while (true){
		
		 Query q = new Query(kind); 
                ArrayList<Key> keys = new ArrayList<Key>(); 


                Iterator it = (datastore.prepare(q).asIterator(fo)); 
 				if (it == null || !it.hasNext())
 					break;
//new ArrayList
				Entity entity = null;
                int ct = 0;
                while (it.hasNext()) { 
                	ct = ct + 1;
                	checkThread(ct);
                	entity = (Entity)it.next();
                        keys.add(entity.getKey()); 
                        /*      if (trans != null)
                	datastore.delete(trans,entity.getKey());
                else
                	datastore.delete(entity.getKey()); */
                
                } 
                if (trans != null)
                	datastore.delete(trans,keys);
                else
                	datastore.delete(keys); 
		}
        } 
		public static void deleteById(String kind,long id,Key pk)throws SQLException
	{
		    	        Throwable ex = null;   
   for (int ct = 0; ct < readTries;ct++)
{
try{ 
  //		    EntityManager em = EMF.get().createEntityManager();
//EntityTransaction tx = em.getTransaction();
		 			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	Transaction trans = datastore.beginTransaction()  ;
boolean suc = false;
try{

//tx.begin();
		
		
		 			checkThread();

		 		Key key = KeyFactory.createKey(pk,kind,id);
                datastore.delete(key); 
trans.commit();
    suc = true;
    	            	return;
  
  
  
   } finally {
  
    //if (tx.isActive())
    //{
        //tx.rollback();
			if (!suc)try{
			
           trans.rollback();
			}catch (Exception e){
				tools.util.LogMgr.err("JGU.delete TABLE.rollback " + e.toString());
			}

    //}
     // em.close();
   }

	}catch (java.util.ConcurrentModificationException ec){
	ex = ec;
	if ((ct) == (readTries -3))
	System.out.println(ct + "JGU.delete TABLE TRYING " + kind  +  ":" + ec.toString());
}
try{

Thread.currentThread().sleep(wtP);
}catch (Exception et){
	tools.util.LogMgr.err("JGU.delete Try Sleep error " + et.toString());
}
}
	if (ex instanceof JGException)
		throw (JGException)ex;
	if (ex instanceof SQLException)
		throw (SQLException)ex;

	throw JGException.get(kind + " error_delete_table","Error Deleting on table : " + ex.toString());
	


  
  
        } 


		public static void deleteById(String kind,long id)throws JGException
	{
		
		 checkThread();
		 DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		 Key key = KeyFactory.createKey(kind,id);
                datastore.delete(key); 
        } 

		public static JGNameValuePairs get(String kind,String id)throws JGException
	{
		
		 checkThread();
		 DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		 Key key = KeyFactory.createKey(kind,id);
		 Entity entity = null;
		 try{
		 
             entity =    datastore.get(key);
		 }catch (com.google.appengine.api.datastore.EntityNotFoundException enf){
		 	return null;
		 }
            if (entity == null)return null;
          	JGNameValuePairs jn = new JGNameValuePairs(new Hashtable(entity.getProperties()));
			jn.setKeyName(entity.getKey().getName());
			jn.setKeyId(entity.getKey().getId());
			jn.setTableName(kind);
      		return jn;
                 
        }

//		 e = new Entity(entit,p); 

	/*	public static void update(String kind,Hashtable columns,long id,Key pk)throws Exception
	{
		 checkThread();
		 DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		 //Entity e = new Entity(kind,id); 
		 		 Key key = KeyFactory.createKey(pk,kind,id);
				Entity e = datastore.get(key);
		 Enumeration en = columns.keys();
		 String cn = null;
		 Object o = null;
		 while (en.hasMoreElements()){
		 	 cn = en.nextElement().toString();	
       		o = columns.get(cn);

     		if (o instanceof String)
		 	 {
			 	if (((String)o).length() > maxCL)
				o = new com.google.appengine.api.datastore.Text((String)o);
			 }
     		e.setProperty(cn,convert(o)); 
     		}
     	datastore.put(e); 

                
        } */

		public static void update(String kind,Hashtable columns,long id,Key pk)throws Exception
	{
		 update(kind,columns,id,pk,getDatastoreService(),null);
	}

		public static void update(String kind,Hashtable columns,long id,Key pk,DatastoreService datastore,Transaction trans)throws Exception
	{
		 checkThread();
		 //DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		//Transaction trans = null;
		//if (!tran)
		//	trans = datastore.beginTransaction()  ;
		 //Entity e = new Entity(kind,id); 
		 		 Key key = KeyFactory.createKey(pk,kind,id);
				Entity e = datastore.get(key);
		 Enumeration en = columns.keys();
		 String cn = null;
		 Object o = null;
		 while (en.hasMoreElements()){
		 	 cn = en.nextElement().toString();	
       		o = columns.get(cn);

     		if (o instanceof String)
		 	 {
			 	if (((String)o).length() > maxCL)
				o = new com.google.appengine.api.datastore.Text((String)o);
			 }
     		e.setProperty(cn,convert(o)); 
     		}
     	if (trans != null)
     	datastore.put(trans,e); 
     		else
		datastore.put(e);
        } 
        	
        	
       		public static void transUpdate(String kind,Hashtable columns,long id,Key pk,jiqlConnection conn)throws Exception
	{


DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
 Transaction trans = datastore.beginTransaction();
 conn.setTransaction(trans);

		 		 Key key = KeyFactory.createKey(pk,kind,id);
				Entity e = datastore.get(key);
		 Enumeration en = columns.keys();
		 String cn = null;
		 Object o = null;
		 while (en.hasMoreElements()){
		 	 cn = en.nextElement().toString();	
       		o = columns.get(cn);

     		if (o instanceof String)
		 	 {
			 	if (((String)o).length() > maxCL)
				o = new com.google.appengine.api.datastore.Text((String)o);
			 }
     		e.setProperty(cn,convert(o)); 
     		}
 
     	datastore.put(trans,e); 

        } 
static int maxCL = 500;
		public static void update(String kind,Hashtable columns,long id)throws Exception
	{
		 checkThread();
		 DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		 //Entity e = new Entity(kind,id); 
		 		 Key key = KeyFactory.createKey(kind,id);
				Entity e = datastore.get(key);
		 Enumeration en = columns.keys();
		 String cn = null;
		 Object o = null;
		 while (en.hasMoreElements()){
		 	 cn = en.nextElement().toString();	
       		o = columns.get(cn);

     		if (o instanceof String)
		 	 {
		 	 	if (((String)o).length() > maxCL)
			 	 o = new com.google.appengine.api.datastore.Text((String)o);
			 }
     		e.setProperty(cn,convert(o)); 
     		}
     	datastore.put(e); 

                
        } 


	public static Vector listWhereEqual(String table,String field,Object value)throws SQLException
	{
		return listWhere(table,field,value,Query.FilterOperator.EQUAL);
	}
	
		public static Vector listWhereGreaterThanOrEqual(String table,String field,Object value)throws SQLException
	{
		return listWhere(table,field,value,Query.FilterOperator.GREATER_THAN_OR_EQUAL);
	}

		public static Vector listWhereLessThanOrEqual(String table,String field,Object value)throws SQLException
	{
		return listWhere(table,field,value,Query.FilterOperator.LESS_THAN_OR_EQUAL);
	}
		public static Vector listWhereGreaterThan(String table,String field,Object value)throws SQLException
	{
		return listWhere(table,field,value,Query.FilterOperator.GREATER_THAN);
	}

		public static Vector listWhereLessThan(String table,String field,Object value)throws SQLException
	{
		return listWhere(table,field,value,Query.FilterOperator.LESS_THAN);
	}
	
		public static JGNameValuePairs getWhereEqual(String table,String field,Object value)throws SQLException
	{
		Vector v = listWhere(table,field,value,Query.FilterOperator.EQUAL);
		if (v.size() > 0)
			return (JGNameValuePairs)v.elementAt(0);
		return null;
	}

		public static JGNameValuePairs getWhereGreaterThan(String table,String field,Object value)throws SQLException
	{
		Vector v = listWhere(table,field,value,Query.FilterOperator.GREATER_THAN);
		if (v.size() > 0)
			return (JGNameValuePairs)v.elementAt(0);
		return null;
	}
		public static JGNameValuePairs getWhereGreaterThanOrEqual(String table,String field,Object value)throws SQLException
	{
		Vector v = listWhere(table,field,value,Query.FilterOperator.GREATER_THAN_OR_EQUAL);
		if (v.size() > 0)
			return (JGNameValuePairs)v.elementAt(0);
		return null;
	}


		public static JGNameValuePairs getWhereLessThan(String table,String field,Object value)throws SQLException
	{
		Vector v = listWhere(table,field,value,Query.FilterOperator.LESS_THAN);
		if (v.size() > 0)
			return (JGNameValuePairs)v.elementAt(0);
		return null;
	}
		public static JGNameValuePairs getWhereLessThanOrEqual(String table,String field,Object value)throws SQLException
	{
		Vector v = listWhere(table,field,value,Query.FilterOperator.LESS_THAN_OR_EQUAL);
		if (v.size() > 0)
			return (JGNameValuePairs)v.elementAt(0);
		return null;
	}

	public static void deleteWhereEqual(String table,String field,Object value)throws JGException
	{
		deleteWhere(table,field,value,Query.FilterOperator.EQUAL,null);
	}
	
		public static void deleteWhereEqual(String table,String field,Object value,Transaction trans)throws JGException
	{
		deleteWhere(table,field,value,Query.FilterOperator.EQUAL,trans);
	}
	
	public static void deleteWhere(String table,String field,Object value,Query.FilterOperator qf)throws JGException
	{
		deleteWhere(table,field,value,qf,null);
	}	
	public static void deleteWhere(String table,String field,Object value,Query.FilterOperator qf,Transaction trans)throws JGException
	{
		checkThread();
		Query q = new Query(table);
		q = q.addFilter(field,qf,value);
		 DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
     	 PreparedQuery pq = datastore.prepare(q) ;
		//EZArrayList ez = new EZArrayList();
		//ez.addEnumeration(pq.asIterator());
		
		Iterator lst = pq.asIterator();
		Entity entity = null;
        ArrayList<Key> keys = new ArrayList<Key>(); 
		int ct = 0;
		while (lst.hasNext())
		{
			ct = ct + 1;
			checkThread(ct);
			entity = (Entity)lst.next();
			keys.add(entity.getKey());
		}
		checkThread();
		if (trans != null)
			datastore.delete(trans,keys); 
		else
         datastore.delete(keys); 
        } 

	public static Vector listWhere(String table,String field,Object value,Query.FilterOperator qf)throws SQLException
	{
		checkThread();
		EZArrayList ez = new EZArrayList();
		//(" listWhere: " + ":" + qf + ":" + field + ":" + value + ":" + value.getClass().getName());


		Query q = new Query(table);
		q = q.addFilter(field,qf,convert(value));
		 DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
     	 PreparedQuery pq = datastore.prepare(q) ;
		int ezt = pq.countEntities();
		if (ezt > fLimit)
			throw JGException.get("result_too_large",table + " Result too LARGE. Try narrowing query by adding more filters: " + ezt + ":" + qf + ":" + field + ":" + value + ":" + value.getClass().getName());

		Iterator lst = pq.asIterator();
 				//if (lst == null || !lst.hasNext())
 				//	break;


		Entity entity = null;
		JGNameValuePairs jn = null;
		int ctl = 0;
			    	        Throwable ex = null;   
   for (int ct = 0; ct < readTries;ct++)
{
try{ 
		
		while (lst.hasNext())
		{
			ctl = ctl + 1;
			checkThread(ctl);
			entity = (Entity)lst.next();
			jn = new JGNameValuePairs(new Hashtable(entity.getProperties()));
			jn.setKeyName(entity.getKey().getName());
			jn.setKeyId(entity.getKey().getId());
			jn.setTableName(table);
			ez.add(jn);

		}
		return ez;
		
		
		
		
	}catch (Exception ec){
	ex = ec;
	if ((ct) == (readTries -3))
	System.out.println(ct + "JGU.select TABLE TRYING " + table  +  ":" + ec.toString());
}
try{

Thread.currentThread().sleep(wtP);
}catch (Exception et){
	tools.util.LogMgr.err("JGU.select Try Sleep error " + et.toString());
}
}
	if (ex instanceof JGException)
		throw (JGException)ex;
	if (ex instanceof SQLException)
		throw (SQLException)ex;

	throw JGException.get(table + " error_selecting_table","Error Selcting on table : " + ex.toString());
	
		
		}

}

