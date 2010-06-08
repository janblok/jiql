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

package org.jiql.jdbc;
import java.sql.*;
import java.util.Properties;
import java.util.Map;
import java.util.Calendar;
import javax.sql.DataSource;
import java.util.Vector;
import java.util.Hashtable;
import org.jiql.util.*;
import java.util.Enumeration;
import org.jiql.db.*;
import tools.util.EZArrayList;
import tools.util.NameValue;
import tools.util.NameValuePairs;
import org.jiql.db.jdbc.stat.StatementProcessor;

import org.jiql.db.objs.*;
public class Statement implements java.sql.Statement
{
	/*String password
	protected String getPassword(){
		return password;
	}*/
	jiqlConnection connection = null;
	Hashtable directValues = new Hashtable();
	
	
	
	static Vector<String> locals = new Vector<String>();
	//static Hashtable<String,StatementProcessor> sprocessors = new Hashtable<String,StatementProcessor>();

	static{
		locals.add("getTypeInfo");
		locals.add("SelectValue");
		locals.add("sqldump");

		//locals.add("getTypeInfo;");
		//sprocessors.put("",new SelectValueStatementProcessor());

	}
	protected void setDirectValue(String n,Object v){
		directValues.put(n,v);
		//(n  + " SET 2 " + v + ":" + directValues);

	}
		public void setDirectValues(Hashtable h){
		directValues = h;
		//(" setDirectValues " + directValues);

	}
	
	public	boolean 	isWrapperFor(Class<?> iface)throws SQLException{
	return false;
}
      //    Returns true if this either implements the interface argument or is directly or indirectly a wrapper for an object that does.
public <T> T unwrap(Class<T> iface)throws SQLException
{
	return null;
}
	
	public Statement(jiqlConnection c){
	connection = c;
	}

	void sdebug(String t){
		//tools.util.LogMgr.debug("statment " + t);
	}
	
	SQLParser dsqp = null;
	public SQLParser getSQLParser(){
		return dsqp;
	}
	public boolean execute(String sql)
	throws SQLException
	{
	resultset = null;
	sdebug("execute1: " + sql);
	if (connection.isRemote() && ! (locals.contains(sql) || sql.toLowerCase().trim().startsWith("load ") || sql.toLowerCase().trim().startsWith("insert "))){
		Hashtable hres = org.jiql.JiqlClient.execute(connection,sql,directValues);
		//JGException je = (JGException)hres.get("error");
		//if (je != null)throw je;
		Object je = hres.get("error");
		if (je != null){
		System.err.println(je.toString());
			throw new SQLException(je.toString());
		}
		SQLParser sqp = (SQLParser)hres.get("sqlparser");
		dsqp = sqp;
		Vector vres = (Vector)hres.get("results");
					sqp.setConnection(connection);

		if (vres != null)
		{
				//sqp.setConnection(connection);

			resultset = new org.jiql.jdbc.ResultSet(vres,sqp);
		}
		if (!locals.contains(sqp.getAction()))
		return true;
	}

	dsqp = SQLParser.get(sql,connection);
	SQLParser sqp = dsqp;
	if (sqp.getAction() != null)
	{
		Gateway gappe = Gateway.get(connection.getProperties());
		//("ST1 " + sqp.getAction());
    	Union union = sqp.getUnion();
		sqp.mergeAliases(union.getAliases());
//( " select sqp + :" + sqp);
		StatementProcessor sp = sqp.getStatementProcessor();
		if (sp != null){
			resultset = sp.process(sqp);
			return true;
		}else if (sqp.getAction().equals("createIndex"))
		{
		}
		else if (sqp.getAction().equals("setOperation"))
		{
		}
		else if (sqp.getAction().equals("loadTable"))
		{
			sqp.getLoadTable().execute(connection);
		}
		else if (sqp.getAction().equals("sqlInsert"))
		{
			//(directValues + ":DIREODFDF");
			sqp.getInsertIntoTable().execute(connection,directValues);
		}
		else if (sqp.getAction().equals("showTables"))
		{
			showTables(sqp);
		}
		else if (sqp.getAction().equals("describeTable"))
		{
			describeTable(sqp);
		}
		else if (sqp.getAction().equals("getColumns"))
		{
			getColumns(sqp);
		}
		else if (sqp.getAction().equals("getPrimaryKeys"))
		{
			getPrimaryKeys(sqp);
		}
		else if (sqp.getAction().equals("getExportedKeys"))
		{
			getExportedKeys(sqp);
		}		
		else if (sqp.getAction().equals("getIndex"))
		{
			getIndex(sqp);
		}
		else if (sqp.getAction().equals("getImportedKeys"))
		{
			getImportedKeys(sqp);
		}
		else if (sqp.getAction().equals("getIdentity"))
		{
			getIdentity(sqp);
		}
		else if (sqp.getAction().equals("getTypeInfo"))
		{
			getTypeInfo(sqp);
		}
		else if (sqp.getAction().equals("getFoundRows"))
		{
			getFoundRows(sqp);
		}
		else if (sqp.getAction().equals("jiqldescribeTable"))
		{
			jiqldescribeTable(sqp);
		}
		else if (sqp.getAction().equals("createTable"))
		{

			if(Gateway.get(connection.getProperties()).readTableInfo(sqp.getTable()).size() > 0)
			{
				if (sqp.getCreateParser().ifNotExists())
					return true;
				throw JGException.get("table_exists",sqp.getTable() + " Table Exists");
			}
		//			jiqlTableInfo ti = jiqlDBMgr.get(sqp.getProperties()).getTableInfo(sqp.getTable(),true);
			jiqlTableInfo ti = sqp.getJiqlTableInfo(true);
			ti.setPrefix(sqp.hasPrefix());
			ti.setPrefixName(sqp.getPrefixValue());
			ti.setTableLeafs(sqp.hasTableleafs());
			//jiqlDBMgr.get(sqp.getProperties()).getTableInfo(sqp.getTable(),true);

			Hashtable hash = sqp.getHash();
			//Enumeration en = hash.keys();
			Hashtable dv = sqp.getDefaultValues();
			Enumeration dven = dv.keys();
			//(dv + " createTable " + hash);
			//{yesno=share titbit, countf=22} createTable {name=varchar(18), yesno=varchar(5), countf=int} 
			
			String n = null;
			String v = null;
			int ml = 0;
			int ty = 0;
			String dVal = null;
					while(dven.hasMoreElements())
			{
				n = dven.nextElement().toString();
				v = (String)hash.get(n);
			if(v == null)
				throw JGException.get("no_column_for_default_value","No Column for default value " + n);
			ty = ColumnInfo.getTypeFromName(v);
			dVal = (String)dv.get(n);
			if (!dVal.equalsIgnoreCase("null"))
			jiqlCellValue.getObj(dVal,ty,sqp);
			if (v.startsWith("varchar(")){
		v = v.substring("varchar(".length(),v.length()-1);
		ml =  Integer.parseInt(v);
		//v = (String)dv.get(n);
		if (!dVal.equalsIgnoreCase("null"))
		if (dVal.length() > ml)
				throw JGException.get("default_value_cannot_exceed_max_column_length",v + " Default value cannot exceed max column length " + n);

			}
				
			}
			
			gappe.writeTableInfo(sqp.getTable(),hash);


			
			jiqlDBMgr.get(sqp.getProperties()).getCommand("addPrimaryKeys").execute(sqp);
			ti.setDefaultValues(sqp.getDefaultValues());
			ti.setNotNulls(sqp.getNotNulls());
			jiqlDBMgr.get(sqp.getProperties()).saveTableInfo(sqp.getTable(),ti);

		}
		else if (sqp.getAction().equals("dropTable"))
		{

			jiqlDBMgr.get(sqp.getProperties()).getCommand("dropTable").execute(sqp);

		}
    	else if (sqp.getAction().equals("writeTableRow")){
		    				jiqlDBMgr.get(sqp.getProperties()).getCommand("VerifyTable").execute(sqp);

			Hashtable wt = sqp.getHash();
			//(directValues + " directValues 1 " + wt);
			if (directValues.size() > 0){
			Enumeration en = wt.keys();
			String k = null;
			String v = null;
			Object dv = null;
		
			while (en.hasMoreElements()){
			k = en.nextElement().toString();
			v = wt.get(k).toString();
			dv = directValues.get(v);
			//(k + " directValues 2 " + v + ":" + dv);

			if (dv != null)
				wt.put(k,dv);
			}
			}
				directValues.clear();

			jiqlDBMgr.get(sqp.getProperties()).getCommand("verifyPrimaryKeys").execute(sqp);
			jiqlDBMgr.get(sqp.getProperties()).getCommand("verifyConstraints").execute(sqp);
			jiqlDBMgr.get(sqp.getProperties()).getCommand("VerifyDefaultValues").execute(sqp);
			jiqlDBMgr.get(sqp.getProperties()).getCommand("VerifyNotNulls").execute(sqp);
			jiqlDBMgr.get(sqp.getProperties()).getCommand("VerifyTypeValues").execute(sqp);



			Gateway.get(connection.getProperties()).writeTableRow(sqp.getTable(),wt,sqp);
					connection.setIdentity(sqp.getInsertParser().getAutoIncrementValue());

						//(sqp.getTable() + "   1 " + sqp.getHash());

			
    	}
		else if (sqp.getAction().equals("select"))
		{

			select(sqp);

		}
		else if (sqp.getAction().equals("addConstraint"))
		{

			jiqlDBMgr.get(sqp.getProperties()).getCommand("addConstraint").execute(sqp);

		}
		else if (sqp.getAction().equals("update"))
		{
		Hashtable h = Gateway.get(connection.getProperties()).readTableValue(sqp.getTable(),sqp.getIncludeAllList(),sqp.getSelectList(),sqp.getEitherOrAllList(),sqp.isDistinct(),sqp);
if (h == null) h = new Hashtable();
		//Vector r = new EZArrayList(h.elements());
		//(sqp.getUpdateList() +" VR tabffff234 " + h);
		
		Enumeration en = h.keys();
		String rid = null;
		Vector ul = sqp.getUpdateList();//null;
		NameValue nv = null;
		Row ur = null;
		updatedRowsCount = 0;
		while (en.hasMoreElements())
		{
			rid = en.nextElement().toString();
			ur = (Row)h.get(rid);
			//ul = sqp.getUpdateList();
			for (int ct = 0; ct < ul.size();ct++)
			{
			nv = (NameValue)ul.elementAt(ct);
		
			//gappe.updateTableValue(, sqp.getTable(),nv.name,nv.value.toString(),rid);
			ur.put(nv.name,sqp.convert(nv.value,nv.name));
			}
			gappe.updateTableValue( sqp.getTable(),ur,sqp);
			updatedRowsCount ++;
		}

		}
		else if (sqp.getAction().equals("delete"))
		{
    		TableInfo tp = Gateway.get(connection.getProperties()).readTableInfo(sqp.getTable());
    							if (tp == null || tp.size() < 1)
					 throw JGException.get("table_not_exist","Table does not exists! " + sqp.getTable());
    		
    		//SQLCriteria:realm_username=ruser2]:[] 
    		Vector vil = sqp.getIncludeAllList() ;
    		Criteria cr = null;
    		for (int ctvil = 0; ctvil < vil.size(); ctvil++)
    		{
    			cr = (Criteria)vil.elementAt(ctvil);
    			if (tp.getColumnInfo(cr.getName()) == null)
					 throw JGException.get("column_not_exist","Column does not exists! " + cr.getName());

    		} 
    		
    		Hashtable h = Gateway.get(connection.getProperties()).readTableValue(sqp.getTable(),sqp.getIncludeAllList(),sqp.getSelectList(),sqp.getEitherOrAllList(),sqp.isDistinct(),sqp);
		if (h == null) return true;
		Vector r = new EZArrayList(h.elements());
		
		Enumeration en = h.keys();
		String rid = null;
		Vector ul = null;
		NameValue nv = null;
		Row row = null;
		updatedRowsCount = 0;
		while (en.hasMoreElements())
		{
			rid = en.nextElement().toString();
			row = (Row)h.get(rid);
			gappe.deleteTableValue( sqp.getTable(),rid);
			updatedRowsCount ++;

		}

		}
		//else //throw JGException.get("statement_not_recognized","Statement NOT recognized! " + sql);
			//gappe.write(sqp.getAction(),sqp.getHash());
	}		else throw JGException.get("statement_not_recognized",sqp.getAction() + " Statement NOT recognized! " + sql);


	return true;
	}
org.jiql.jdbc.ResultSet resultset = null;
    public ResultSet executeQuery(String sql) throws SQLException {
    	//try{
    	
		sdebug("execute: " + sql);
resultset = null;

	if (connection.isRemote() && !locals.contains(sql)){
		Hashtable hres = org.jiql.JiqlClient.execute(connection,sql,directValues);
		Object je = hres.get("error");
		if (je != null){
			System.err.println(je.toString());
			throw new SQLException(je.toString());
		}
		SQLParser sqp = (SQLParser)hres.get("sqlparser");
		dsqp = sqp;

		Vector vres = (Vector)hres.get("results");
		sqp.setConnection(connection);

		if (vres != null)
		{
			//sqp.setConnection(connection);
			resultset = new org.jiql.jdbc.ResultSet(vres,sqp);
		}
		if (!locals.contains(sqp.getAction()))
		return resultset;
	}

	dsqp = SQLParser.get(sql,connection);
	SQLParser sqp = dsqp;
	if (sqp.getAction() != null)
	{
		Gateway gw = Gateway.get(connection.getProperties());
		//("sel 1 " + sqp.getAction());
		
		StatementProcessor sp = sqp.getStatementProcessor();
		if (sp != null){
			resultset = sp.process(sqp);
			return resultset;
		} 
		else if (sqp.getAction().equals("select"))
		{
			select(sqp);

	
		}
		else if (sqp.getAction().equals("showTables"))
		{
			showTables(sqp);
		}
		else if (sqp.getAction().equals("describeTable"))
		{
			describeTable(sqp);
		}
		else if (sqp.getAction().equals("getColumns"))
		{
			getColumns(sqp);
		}
		else if (sqp.getAction().equals("getPrimaryKeys"))
		{
			getPrimaryKeys(sqp);
		}
				else if (sqp.getAction().equals("getIndex"))
		{
			getIndex(sqp);
		}
		else if (sqp.getAction().equals("getExportedKeys"))
		{
			getExportedKeys(sqp);
		}	
		else if (sqp.getAction().equals("getTypeInfo"))
		{
			getTypeInfo(sqp);
		}
		else if (sqp.getAction().equals("getIdentity"))
		{
			getIdentity(sqp);
		}				
		else if (sqp.getAction().equals("getFoundRows"))
		{
			getFoundRows(sqp);
		}
		//getFoundRows
		else if (sqp.getAction().equals("getImportedKeys"))
		{
			getImportedKeys(sqp);
		}
		else if (sqp.getAction().equals("jiqldescribeTable"))
		{
			jiqldescribeTable(sqp);
		}
	}
    /*	}catch (Throwable e){
    		sdebug(" TEMP EQ ERROR " + e.toString());
    		JGUtil.olog(e);
    		throw new SQLException(e.toString());
    	}*/
	return resultset;
    }
               protected void jiqldescribeTable(SQLParser sqp )throws SQLException{
    	Vector r = Gateway.get(connection.getProperties()).jiqlDescribeTable(sqp);

		sqp.setResults(r);

		resultset = new org.jiql.jdbc.ResultSet(sqp.getResults(),sqp);
		
    } 
            protected void describeTable(SQLParser sqp )throws SQLException{
    	Vector r = Gateway.get(connection.getProperties()).describeTable(sqp);

		sqp.setResults(r);

		resultset = new org.jiql.jdbc.ResultSet(sqp.getResults(),sqp);
		
    }
    
    	              protected void getPrimaryKeys(SQLParser sqp )throws SQLException{
    	Vector r = Gateway.get(connection.getProperties()).getPrimaryKeys(sqp);

		sqp.setResults(r);

		resultset = new org.jiql.jdbc.ResultSet(sqp.getResults(),sqp);
		
    }
  
          	              protected void getIndex(SQLParser sqp )throws SQLException{
    	Vector r = Gateway.get(connection.getProperties()).getIndex(sqp);

		sqp.setResults(r);

		resultset = new org.jiql.jdbc.ResultSet(sqp.getResults(),sqp);
		
    }
    
        	              protected void getExportedKeys(SQLParser sqp )throws SQLException{
    	Vector r = Gateway.get(connection.getProperties()).getExportedKeys(sqp);

		sqp.setResults(r);

		resultset = new org.jiql.jdbc.ResultSet(sqp.getResults(),sqp);
		
    }
      protected void getImportedKeys(SQLParser sqp )throws SQLException{
    	Vector r = Gateway.get(connection.getProperties()).getImportedKeys(sqp);

		sqp.setResults(r);

		resultset = new org.jiql.jdbc.ResultSet(sqp.getResults(),sqp);
		
    }
    
    	         protected void getFoundRows(SQLParser sqp )throws SQLException{
    	Vector r = Gateway.get(connection.getProperties()).getFoundRows(sqp);

		sqp.setResults(r);

		resultset = new org.jiql.jdbc.ResultSet(sqp.getResults(),sqp);
		
    }
          protected void getIdentity(SQLParser sqp )throws SQLException{
    	Vector r = Gateway.get(connection.getProperties()).getIdentity(sqp);

		sqp.setResults(r);

		resultset = new org.jiql.jdbc.ResultSet(sqp.getResults(),sqp);
		
    }
    
              protected void getTypeInfo(SQLParser sqp )throws SQLException{
    	Vector r = Gateway.get(connection.getProperties()).getTypeInfo(sqp);

		sqp.setResults(r);

		resultset = new org.jiql.jdbc.ResultSet(sqp.getResults(),sqp);
		
    }
    	
               protected void getColumns(SQLParser sqp )throws SQLException{
    	Vector r = Gateway.get(connection.getProperties()).getColumns(sqp);

		sqp.setResults(r);

		resultset = new org.jiql.jdbc.ResultSet(sqp.getResults(),sqp);
		
    } 
        protected void showTables(SQLParser sqp )throws SQLException{
    	Vector r = Gateway.get(connection.getProperties()).showTables();

		sqp.setResults(r);

		resultset = new org.jiql.jdbc.ResultSet(sqp.getResults(),sqp);
		
    }
    
    
    protected void select(SQLParser sqp )throws SQLException{
    				//Union union = sqp.getUnion();
					//sqp.mergeAliases(union.getAliases());
resultset = null;
    				jiqlDBMgr.get(sqp.getProperties()).getCommand("VerifyTable").execute(sqp);

		Hashtable h = null;
	//	try{
		
		h = Gateway.get(connection.getProperties()).readTableValue(sqp.getTable(),sqp.getIncludeAllList(),sqp.getSelectList(),sqp.getEitherOrAllList(),sqp.isDistinct(),sqp);
		if (h == null) h = new Hashtable();
		/*}catch (Throwable e){
			throw new SQLException(e.toString() + " : "+ sqp.getStatement());
		}*/
		sqp.setResultsTable(h);
		Vector r = null;
		
		if (sqp.isCount())
		{
			org.jiql.db.Row nvp = new org.jiql.db.Row();
			nvp.setSQLParser(sqp);
			//nvp.put("count(*)",h.size());
			if (h.get("jiql_row_count") == null)
			nvp.put("count(*)",h.size());
			else
				nvp.put("count(*)",h.get("jiql_row_count"));
			r = new EZArrayList();
			r.add(nvp);
		}
		else
		r = new EZArrayList(h.elements());
		//union. (r);

		sqp.setResults(r);
		jiqlDBMgr.get(sqp.getProperties()).getCommand("SortResult").execute(sqp);

		resultset = new org.jiql.jdbc.ResultSet(sqp.getResults(),sqp);
		
		// ("UNION " + union);
    }
    

    public boolean execute(String sql,
                           int autoGeneratedKeys) throws SQLException {
    	sdebug(autoGeneratedKeys + " : ss 1 " + sql);

        throw JGException.get("not_supported","Not Supported");
    }

    public boolean execute(String sql,
                           String[] columnNames) throws SQLException {
    	sdebug(columnNames + " : ss 2 " + sql);

        throw JGException.get("not_supported","Not Supported");
    }


    public boolean execute(String sql,
                           int[] columnIndexes) throws SQLException {
    	sdebug(columnIndexes + " : ss 3 " + sql);

        throw JGException.get("not_supported","Not Supported");
    }
    
  Vector batch = new Vector();
    
public    void 	addBatch(String sql) throws SQLException {
    	sdebug( " : ssb 3 " + sql);

batch.add(sql);
}        
//Adds the given SQL command to the current list of commmands for this Statement object.
public void 	cancel() throws SQLException {
    	sdebug( " : ss 4 ");

        throw JGException.get("not_supported","Not Supported");

}        
//Cancels this Statement object if both the DBMS and driver support aborting an SQL statement.
public void 	clearBatch() throws SQLException {
    	sdebug( " : ssb 4 " );

batch.clear();
}        
//Empties this Statement object's current list of SQL commands.
public void 	clearWarnings() throws SQLException {
    	sdebug( " : ssb 5 " );

}        
//Clears all the warnings reported on this Statement object.
boolean close = false;
public void 	close() throws SQLException {
	close = true;
}        
//Releases this Statement object's database and JDBC resources immediately instead of waiting for this to happen when it is automatically closed.

public int[] 	executeBatch() throws SQLException {
    	sdebug( " : ss executeBatch ");

	while (batch.size() > 0){
		String sql = batch.elementAt(0).toString();
		batch.removeElementAt(0);
		execute(sql);
	}
	return new int[0];
}        
//Submits a batch of commands to the database for execution and if all commands execute successfully, returns an array of update counts.
int updatedRowsCount = -1;
public int 	executeUpdate(String sql) throws SQLException {
    	sdebug( " : ss executeUpdate ");

    updatedRowsCount = -1;    	
    execute(sql);
    return updatedRowsCount == -1 ? 0 : updatedRowsCount;
}        
//Executes the given SQL statement, which may be an INSERT, UPDATE, or DELETE statement or an SQL statement that returns nothing, such as an SQL DDL statement.

int agkey = -1;
public int 	executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
    	sdebug( " : ss executeUpdate 2 " + autoGeneratedKeys);
agkey = autoGeneratedKeys;
//try{

execute(sql);
/*    	sdebug( " : ss executeUpdate 2b********** " + dsqp);
}catch (Exception e){
    	sdebug( " : ss executeUpdate 2 errrr " + e.toString());

	JGUtil.olog(e);
throw new SQLException (e.toString());
}*/
	if (dsqp != null && dsqp.getAction() != null && dsqp.getAction().equals("sqlInsert"))
		return 1;

	return 0;
}        
//Executes the given SQL statement and signals the driver with the given flag about whether the auto-generated keys produced by this Statement object should be made available for retrieval.
public int 	executeUpdate(String sql, int[] columnIndexes) throws SQLException {
     	sdebug(columnIndexes + " : ss 5 " + sql);

        throw JGException.get("not_supported","Not Supported");

}        
//Executes the given SQL statement and signals the driver that the auto-generated keys indicated in the given array should be made available for retrieval.
public int 	executeUpdate(String sql, String[] columnNames) throws SQLException {
     	sdebug(columnNames + " : ss 6 " + sql);

        throw JGException.get("not_supported","Not Supported");

}        
//Executes the given SQL statement and signals the driver that the auto-generated keys indicated in the given array should be made available for retrieval.
public Connection 	getConnection() throws SQLException {
return connection;
}        
//Retrieves the Connection object that produced this Statement object.
public int 	getFetchDirection() throws SQLException {
     	sdebug( " : ss 7 " );

        throw JGException.get("not_supported","Not Supported");

}        
//Retrieves the direction for fetching rows from database tables that is the default for result sets generated from this Statement object.
public int 	getFetchSize() throws SQLException {
     	sdebug( " : ss 8 " );

        throw JGException.get("not_supported","Not Supported");

}        
//Retrieves the number of result set rows that is the default fetch size for ResultSet objects generated from this Statement object.
public ResultSet 	getGeneratedKeys() throws SQLException {
       	sdebug( " : ss 9 " );

    	SQLParser sqpgk = new SQLParser(connection.getProperties());
    	sqpgk.setSpecial(true);
		sqpgk.setAction("getGeneratedKeys");
    	
    	Vector r = new Vector();
		//(agkey + ":" + Statement.RETURN_GENERATED_KEYS + "GKs**** "  + connection.getIdentity());
		if (agkey == Statement.RETURN_GENERATED_KEYS && connection.getIdentity() > 0){
			Row ro  = new Row();
			ro.put("GENERATED_KEY",connection.getIdentity());
			r.add(ro);			
		}

		sqpgk.setResults(r);

		resultset = new org.jiql.jdbc.ResultSet(sqpgk.getResults(),sqpgk);
		return resultset;
}        
//Retrieves any auto-generated keys created as a result of executing this Statement object.
public int 	getMaxFieldSize() throws SQLException {
    	sdebug( " : ssb 6 " );

return 30000;
}        
//Retrieves the maximum number of bytes that can be returned for character and binary column values in a ResultSet object produced by this Statement object.
public int 	getMaxRows() throws SQLException {
    	sdebug( " : ssb 7 " );

return 30000;
}        
//Retrieves the maximum number of rows that a ResultSet object produced by this Statement object can contain.
/*public boolean 	getMoreResults() throws SQLException {
        throw JGException.get("not_supported"," ");

}        
//Moves to this Statement object's next result, returns true if it is a ResultSet object, and implicitly closes any current ResultSet object(s) obtained with the method getResultSet.
public boolean 	getMoreResults(int current) throws SQLException {
        throw JGException.get("not_supported"," ");

}    */  



   /*
    * Moves to this Statement object's next result, returns true if it is a
    * ResultSet object, and implicitly closes any current ResultSet object(s)
    * obtained with the method getResultSet.
    */
   public boolean getMoreResults() throws SQLException {
     	sdebug( " : ssb 8 " );

       return getMoreResults( Statement.CLOSE_CURRENT_RESULT );
   }

   /*
    * Moves to this Statement object's next result, deals with any current
    * ResultSet object(s) according to the instructions specified by the given
    * flag, and returns true if the next result is a ResultSet object.
    */
   public boolean getMoreResults( int current ) throws SQLException {
          	sdebug( " : ss 10 " );

       switch ( current ) {
           case Statement.CLOSE_CURRENT_RESULT :
               if ( resultset != null ) {
                   resultset.close();
                   resultset = null;
               }
               return false;

           case Statement.CLOSE_ALL_RESULTS :
           case Statement.KEEP_CURRENT_RESULT :
               throw new SQLFeatureNotSupportedException( "multiple open result sets not supported" );

           default :
               throw new SQLException( "invalid parameter" );
       }
   }


  
//Moves to this Statement object's next result, deals with any current ResultSet object(s) according to the instructions specified by the given flag, and returns true if the next result is a ResultSet object.
public int 	getQueryTimeout() throws SQLException {
    	sdebug( " : ssb 10 " );

return 30;
}        
//Retrieves the number of seconds the driver will wait for a Statement object to execute.
public ResultSet 	getResultSet() throws SQLException {
	return resultset;
}        
//Retrieves the current result as a ResultSet object.
public int 	getResultSetConcurrency() throws SQLException {
sdebug( " : ssb 110 " );
	return -1;
}        
//Retrieves the result set concurrency for ResultSet objects generated by this Statement object.
public int 	getResultSetHoldability() throws SQLException {
sdebug( " : ssb 120 " );
	return -1;
}        
// Retrieves the result set holdability for ResultSet objects generated by this Statement object.
public int 	getResultSetType() throws SQLException {
sdebug( " : ssb 13 " );
	return ResultSet.TYPE_FORWARD_ONLY;
}        
//Retrieves the result set type for ResultSet objects generated by this Statement object.
public int 	getUpdateCount() throws SQLException {
sdebug( " : ssb 14 " );
return -1;
}        
//Retrieves the current result as an update count; if the result is a ResultSet object or there are no more results, -1 is returned.
public SQLWarning 	getWarnings() throws SQLException {
sdebug( " : ssb 15 " );
return null;
}        
//Retrieves the first warning reported by calls on this Statement object.
public boolean 	isClosed() throws SQLException {
sdebug( " : ssb 16 " );
	return close;
}        
//Retrieves whether this Statement object has been closed.
public boolean 	isPoolable() throws SQLException {
sdebug( " : ssb 17 " );
	return false;
}        
String cname = null;
//Returns a value indicating whether the Statement is poolable or not.
public void 	setCursorName(String name) throws SQLException {
sdebug( " : ssb 18 " );
cname = name;
}        
//Sets the SQL cursor name to the given String, which will be used by subsequent Statement object execute methods.
public void 	setEscapeProcessing(boolean enable) throws SQLException {
sdebug( " : setEscapeProcessing "  + enable);
}        
//Sets escape processing on or off.
public void 	setFetchDirection(int direction) throws SQLException {
sdebug( " : ssb 20 " );
}        
//Gives the driver a hint as to the direction in which rows will be processed in ResultSet objects created using this Statement object.
public void 	setFetchSize(int rows) throws SQLException {
sdebug( " : ssb 21 " );
}        
//Gives the JDBC driver a hint as to the number of rows that should be fetched from the database when more rows are needed for ResultSet objects genrated by this Statement.
public void 	setMaxFieldSize(int max) throws SQLException {
sdebug( " : ssb 22 " );
}        
//Sets the limit for the maximum number of bytes that can be returned for character and binary column values in a ResultSet object produced by this Statement object.
public void 	setMaxRows(int max) throws SQLException {
sdebug( " : ssb 23 "  + max);
}        
//Sets the limit for the maximum number of rows that any ResultSet object generated by this Statement object can contain to the given number.
public void 	setPoolable(boolean poolable) throws SQLException {
sdebug( " : ssb 24 " );
}        
//Requests that a Statement be pooled or not pooled.
public void 	setQueryTimeout(int seconds) throws SQLException {
sdebug( " : ssb 25 " );
}        
//Sets the number of seconds the driver will wait for a Statement object to execute to the given number of seconds.
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}


