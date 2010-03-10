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



import java.util.Map;
import java.sql.*;
import java.util.Properties;
import org.jiql.util.*;
import org.jiql.JIQLGDataUtil;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.io.IOException;
import com.google.appengine.api.datastore.Transaction;



public class jiqlConnection implements Connection{

Properties properties = null;

int isolation = 0;
int foundrows = 0;
int nfoundrows = 0;
int identity = 0;
public jiqlConnection(){
}
public jiqlConnection(Properties p){
	properties = p;
	JIQLGDataUtil.removeThread();
/*	if (isRemote()){
		dpat = properties.getProperty("date.format");

	}*/
}
DateFormat df = null;
String dpat = null;


public void setFoundRows(int f){
	nfoundrows = f;
	foundrows = nfoundrows;
}

public int getFoundRows(){
	return foundrows;
}


public void setIdentity(int f){
//("jc.setIdentity " + f);
	if (f > 0)
	identity = f;
}

public int getIdentity(){
	return identity;
}

/*
public void writeExternal(java.io.ObjectOutput out)
                   throws IOException
                   {
                   }

public void readExternal(java.io.ObjectInput in)
                  throws IOException,
                         ClassNotFoundException{
                         }
*/






	public DateFormat getDateFormat(){
				if (df != null)return df;
				/*	String dfmt = properties.getProperty("date.format");
					if (dfmt == null)
					dfmt = "yyyy-MM-dd HH:mm:ss";*/
			df = new SimpleDateFormat(getDatePattern());
		return df;
	}
		public String getDatePattern(){
					if (dpat != null)return dpat;
					dpat = properties.getProperty("date.format");
					if (dpat == null)
					dpat = "yyyy-MM-dd HH:mm:ss";
	//("JC P " + properties );
		return dpat;
	}
	
public boolean isRemote(){
	String u = properties.getProperty("url");
	return (u.startsWith("http"));
}
public Properties getProperties(){
	return properties;
}

public	boolean 	isWrapperFor(Class<?> iface)throws SQLException{
	return false;
}
      //    Returns true if this either implements the interface argument or is directly or indirectly a wrapper for an object that does.
public <T> T unwrap(Class<T> iface)throws SQLException
{
	jcLog( " : unwrap " );
	return null;
}

public void 	clearWarnings()throws SQLException{
}
//          Clears all warnings reported for this Connection object.
boolean close = false;
public void 	close()throws SQLException{
close = true;
}

public void 	close(boolean tf)throws SQLException{
close = tf;
}


//          Releases this Connection object's database and JDBC resources immediately instead of waiting for them to be automatically released.
public void 	commit()throws SQLException{
	if (trans != null)
	trans.commit();
  	//("trans.commit " );
	trans = null;
}
//Makes all changes made since the previous commit/rollback permanent and releases any database locks currently held by this Connection object.
public Array 	createArrayOf(String typeName, Object[] elements)throws SQLException{
                    	jcLog( " : ss 26 " );

        throw JGException.get("not_supported","Not Supported");

}
//Factory method for creating Array objects.
public Blob 	createBlob()throws SQLException{
                   	jcLog( " : ss 27 " );

        throw JGException.get("not_supported","Not Supported");

}
//Constructs an object that implements the Blob interface.
public Clob 	createClob()throws SQLException{
                    	jcLog( " : ss 28 " );

        throw JGException.get("not_supported","Not Supported");

}
//Constructs an object that implements the Clob interface.
public NClob 	createNClob()throws SQLException{
                  	jcLog( " : ss 29 " );

        throw JGException.get("not_supported","Not Supported");

}
//Constructs an object that implements the NClob interface.
public SQLXML 	createSQLXML()throws SQLException{
                  	jcLog( " : ss 30 " );

        throw JGException.get("not_supported","Not Supported");

}
//Constructs an object that implements the SQLXML interface.
public java.sql.Statement 	createStatement()throws SQLException{
       // throw JGException.get("not_supported","Not Supported");
       return new org.jiql.jdbc.Statement(this);

}
//Creates a Statement object for sending SQL statements to the database.
public java.sql.Statement 	createStatement(int resultSetType, int resultSetConcurrency)throws SQLException{
                  	jcLog( resultSetType + " : createStatement " + ResultSet.TYPE_FORWARD_ONLY + ":" + ResultSet.TYPE_SCROLL_INSENSITIVE + ":" + ResultSet.TYPE_SCROLL_SENSITIVE );
                  	jcLog( resultSetConcurrency + " : createStatementb " + ResultSet.CONCUR_READ_ONLY  + ":" + ResultSet.CONCUR_UPDATABLE  );

        //throw new SQLFeatureNotSupportedException(JGException.get("not_supported","Not Supported").toString());
		return createStatement();
}
//Creates a Statement object that will generate ResultSet objects with the given type and concurrency.
public java.sql.Statement 	createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)throws SQLException{
                   	jcLog( " : ss 32 " );

        throw new SQLFeatureNotSupportedException(JGException.get("not_supported","Not Supported").toString());

}
//Creates a Statement object that will generate ResultSet objects with the given type, concurrency, and holdability.
public Struct 	createStruct(String typeName, Object[] attributes)throws SQLException{
                    	jcLog( " : ss 33 " );

        throw JGException.get("not_supported","Not Supported");

}
//Factory method for creating Struct objects.
public boolean 	getAutoCommit()throws SQLException{
        //throw JGException.get("not_supported"," ");
        return autoCommit;

}
// Retrieves the current auto-commit mode for this Connection object.
public String 	getCatalog()throws SQLException{
  
      String ver = getProperties().getProperty("Catalog");

   jcLog("jclaw 1 " + ver);
   			

        return ver;
        //throw JGException.get("not_supported"," ");

}
// Retrieves this Connection object's current catalog name.
public Properties 	getClientInfo()throws SQLException{
        jcLog("jclaw 2");

        throw JGException.get("not_supported","Not Supported");

}
//Returns a list containing the name and current value of each client info property supported by the driver.
public String 	getClientInfo(String name)throws SQLException{
     jcLog("jclaw 3");

        throw JGException.get("not_supported","Not Supported");

}
//Returns the value of the client info property specified by name.
public int 	getHoldability()throws SQLException{
return ResultSet.CLOSE_CURSORS_AT_COMMIT;
}

void jcLog(String t){
	//tools.util.LogMgr.debug("jcLog:" + t);
}
//Retrieves the current holdability of ResultSet objects created using this Connection object.
public DatabaseMetaData 	getMetaData()throws SQLException{
	jcLog("getMetaData");

return new jiqlDatabaseMetaData(this);
}
//Retrieves a DatabaseMetaData object that contains metadata about the database to which this Connection object represents a connection.
public int 	getTransactionIsolation()throws SQLException{
return  isolation;
}
// Retrieves this Connection object's current transaction isolation level.
public Map<String,Class<?>> 	getTypeMap()throws SQLException{
             	jcLog( " : ss 10 " );

        throw JGException.get("not_supported","Not Supported");

}
//Retrieves the Map object associated with this Connection object.
public SQLWarning 	getWarnings()throws SQLException{
             	jcLog( " : ss 11 " );

             	return null;

}
//Retrieves the first warning reported by calls on this Connection object.
public boolean 	isClosed()throws SQLException{
return close;
}

public void setClosed(boolean tf){
	close = tf;
}

//Retrieves whether this Connection object has been closed.
public boolean 	isReadOnly()throws SQLException{
return false;
}
//Retrieves whether this Connection object is in read-only mode.
public boolean 	isValid(int timeout)throws SQLException{
   jcLog("jclaw 4");

return (!close);
}
//Returns true if the connection has not been closed and is still valid.
public String 	nativeSQL(String sql)throws SQLException{
     jcLog("jclaw 5");

        throw JGException.get("not_supported","Not Supported");

}
//Converts the given SQL statement into the system's native SQL grammar.
public CallableStatement 	prepareCall(String sql)throws SQLException{
              	jcLog( " : ss 13 " );

        throw JGException.get("not_supported","Not Supported");

}
//Creates a CallableStatement object for calling database stored procedures.
public CallableStatement 	prepareCall(String sql, int resultSetType, int resultSetConcurrency)throws SQLException{
                 	jcLog( " : ss 14 " );

        throw JGException.get("not_supported","Not Supported");

}
//          Creates a CallableStatement object that will generate ResultSet objects with the given type and concurrency.
public CallableStatement 	prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability)throws SQLException{
                 	jcLog( " : ss 15 " );

        throw JGException.get("not_supported","Not Supported");

}
//          Creates a CallableStatement object that will generate ResultSet objects with the given type and concurrency.
public PreparedStatement 	prepareStatement(String sql)throws SQLException{
                 	jcLog( " : prepareStatement "  + sql);

     return new org.jiql.jdbc.jiqlPreparedStatement(this,sql);


}
//          Creates a PreparedStatement object for sending parameterized SQL statements to the database.
public PreparedStatement 	prepareStatement(String sql, int autoGeneratedKeys)throws SQLException{
                 	jcLog( " : ss 16 " );

        throw JGException.get("not_supported","Not Supported");

}
//          Creates a default PreparedStatement object that has the capability to retrieve auto-generated keys.
public PreparedStatement 	prepareStatement(String sql, int[] columnIndexes)throws SQLException{
              	jcLog( " : ss 17 " );

        throw JGException.get("not_supported","Not Supported");

}
//          Creates a default PreparedStatement object capable of returning the auto-generated keys designated by the given array.
public PreparedStatement 	prepareStatement(String sql, int resultSetType, int resultSetConcurrency)throws SQLException{
             	jcLog( " : ss 18 " );

        throw JGException.get("not_supported","Not Supported");

}
//          Creates a PreparedStatement object that will generate ResultSet objects with the given type and concurrency.
public PreparedStatement 	prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability)throws SQLException{
               	jcLog( " : ss 19 " );

        throw JGException.get("not_supported","Not Supported");

}
//          Creates a PreparedStatement object that will generate ResultSet objects with the given type, concurrency, and holdability.
public PreparedStatement 	prepareStatement(String sql, String[] columnNames)throws SQLException{
                	jcLog( " : ss 20 " );

        throw JGException.get("not_supported","Not Supported");

}
//          Creates a default PreparedStatement object capable of returning the auto-generated keys designated by the given array.
public void 	releaseSavepoint(Savepoint savepoint)throws SQLException{
                	jcLog( " : ss 21 " );

       throw JGException.get("not_supported","Not Supported");

}
//          Removes the specified Savepoint and subsequent Savepoint objects from the current transaction.
public void 	rollback()throws SQLException{
        //throw JGException.get("not_supported","Not Supported");
		if (trans != null && !trans.isActive())
		trans.rollback();
trans = null;

}
//          Undoes all changes made in the current transaction and releases any database locks currently held by this Connection object.
public void 	rollback(Savepoint savepoint)throws SQLException{
                   	jcLog( " : ss 22 " );

        throw JGException.get("not_supported","Not Supported");

}
//          Undoes all changes made after the given Savepoint object was set.
Transaction trans = null;
boolean autoCommit = true;
/*public Transaction 	begin()throws SQLException{
if(getAutoCommit())return null;
	trans = JIQLGDataUtil.getTransaction();
  	org.jiql.util. ("trans.begin " + trans);
	return trans;
}*/

public void setTransaction(Transaction t)throws SQLException{
	 trans = t;
	   	//("trans.begin " );

}
public void 	setAutoCommit(boolean autoCommit)throws SQLException{
if (!autoCommit && !isRemote() && (getProperties().getProperty("enable.transactions") != null && getProperties().getProperty("enable.transactions").equals("true")))
	this.autoCommit = false;
else
	this.autoCommit = true;

}
//          Sets this connection's auto-commit mode to the given state.
String scatalog = null;
public void 	setCatalog(String catalog)throws SQLException{
   jcLog("jclaw 7");

scatalog = catalog;
}
//          Sets the given catalog name in order to select a subspace of this Connection object's database in which to work.
Properties cproprties = new Properties();
public void 	setClientInfo(Properties props)throws SQLClientInfoException{
   jcLog("jclaw 8");

cproprties = props;
}
//          Sets the value of the connection's client info properties.
public void 	setClientInfo(String name, String value)throws SQLClientInfoException{
   jcLog("jclaw 9");

cproprties.put(name,value);
}
//          Sets the value of the client info property specified by name to the value specified by value.
int holdability = 0;
public void 	setHoldability(int holdability)throws SQLException{
this.holdability = holdability;
}
//          Changes the default holdability of ResultSet objects created using this Connection object to the given holdability.
public void 	setReadOnly(boolean readOnly)throws SQLException{
}
//          Puts this connection in read-only mode as a hint to the driver to enable database optimizations.
public Savepoint 	setSavepoint()throws SQLException{
                     	jcLog( " : ss 23 " );

       throw JGException.get("not_supported","Not Supported");

}
//          Creates an unnamed savepoint in the current transaction and returns the new Savepoint object that represents it.
public Savepoint 	setSavepoint(String name)throws SQLException{
                   	jcLog( " : ss 24 " );

        throw JGException.get("not_supported","Not Supported");

}
//          Creates a savepoint with the given name in the current transaction and returns the new Savepoint object that represents it.
public void 	setTransactionIsolation(int level)throws SQLException{
 isolation = level;
}
//          Attempts to change the transaction isolation level for this Connection object to the one given.
public void 	setTypeMap(Map<String,Class<?>> map)throws SQLException{
                     	jcLog( " : ss 25 " );

        throw JGException.get("not_supported","Not Supported");

}
//          Installs the given TypeMap object as the type map for this Connection object.

}
