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


import java.io.*;
import java.sql.*;
import java.util.*;
import javax.sql.*;
import tools.util.*;
import org.jiql.util.JGException;


public class jiqlDataSource implements DataSource,Serializable {


    // ----------------------------------------------------- Instance Constants

    static final long serialVersionUID = 0x7b9a162fff8dbf10L;
PrintWriter logWriter = null;
    private static final String SQLEXCEPTION_GETCONNECTION =
     "getConnection(String username, String password)  Method not supported. Use getConnection() instead.";



    // ----------------------------------------------------- Instance Variables


    /**
     * Has this data source been closed?
     */
    protected transient boolean closed = false;


    /**
     * The list of Connections (wrapped in our associated wrapper class) that
     * have been created but are not currently in use.
     */


    /**
     * The JDBC driver that we use as a connection factory.
     */
    protected Driver driver = null;


    /**
     * The login timeout for this data source.
     */
    protected static int loginTimeout = 60;



    /**
     * The connection properties for use in establishing connections.
     */
    private  Properties properties = new Properties();//new Properties();
    private  Properties myprops = null;//new Properties();

    // ------------------------------------------------------------- Properties
	static boolean create = false;
	protected static boolean debug = false;



LinkedList cons = new LinkedList();
int useCount = 0;
int activeCount = 0;

public static jiqlDataSource get(Properties p){
	return new jiqlDataSource(p);
}
public static jiqlDataSource get(){
	Properties p = new Properties();
	return get(p);
}

public jiqlDataSource(){
	addProperty("url","jdbc:jiql://local");
	addProperty("user","");
	addProperty("password","");
}
	public jiqlDataSource(Properties p){
		this();
		myprops = p;
		if (create)return;
		create = true;
		System.out.println("[jiql DataSource created.]");
		setProperties(p);

	}

public	boolean 	isWrapperFor(Class<?> iface)throws SQLException{
	return false;
}
      //    Returns true if this either implements the interface argument or is directly or indirectly a wrapper for an object that does.
public <T> T unwrap(Class<T> iface)throws SQLException
{
	return null;
}
    /**
     * Add a generic property to the list of connection properties to be used.
     *
     * @param name Name of the generic property
     * @param value Corresponding generic property value
     */
    public void addProperty(String name, String value) {
        properties.put(name, value);
    }

	public void setProperties(Properties p){
	if (url != null)
	{
		if (debug)
		{
			System.out.println(url + ":" + user + ":" + password + " jiqlDataSource URL ALREADY Set. " + properties + ":" + connections() + ":" +  getActiveCount());
			Thread.dumpStack();
		}
		return;
	}
		Enumeration en = p.keys();
		String k = null;
		while (en.hasMoreElements())
		{
			k = en.nextElement().toString();
			properties.put(k,p.getProperty(k));
			
		}
		//if (p.size() > 0)
		//properties = p;
		//else
			p = properties;
	String tok = (String)p.get("database.driverClass");
	//if (tok == null)
	//	tok
	if (tok != null)
		setDriverClass(tok);
	 tok = (String)p.get("database.description");
	if (tok != null)
			setDescription(tok);
	tok = (String)p.get("database.maxCount");
	if (tok != null)
		setMaxCount(Integer.parseInt(tok));
	tok = (String)p.get("database.minCount");
	if (tok != null)
		setMinCount(Integer.parseInt(tok));

	tok = (String)p.get("database.password");
	if (tok != null)
		setPassword(tok);
	tok = (String)p.get("database.url");
	if (tok != null)
		setUrl(tok);

	tok = (String)p.get("database.user");
	if (tok != null)
		setUser(tok);
	if (url == null)System.out.println("jiqlDataSource url NOT Set.");
	if (user == null)System.out.println("jiqlDataSource user NOT Set.");
	if (password == null)System.out.println("jiqlDataSource password NOT Set.");
	}

    /**
     * The number of connections that have been created by this data source.
     */

    public int getActiveCount() {

		return activeCount;
    }


    /**
     * The default auto-commit state for newly created connections.
     */
    protected boolean autoCommit = true;

    public boolean getAutoCommit() {
        return (this.autoCommit);
    }

    public void setAutoCommit(boolean autoCommit) {
        this.autoCommit = autoCommit;
    }


    /**
     * The description of this data source.
     */
    protected  String description = null;

    public String getDescription() {
        return (this.description);
    }

    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * The Java class name of the JDBC driver to use.
     */
    protected String driverClass = null;

    public String getDriverClass() {
        return (this.driverClass);
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

	public boolean acceptsURL(String url)
                   throws SQLException
	   {
	   	open();
	   	return driver.acceptsURL(url);
	   }

	public Connection connect(String url,
                          Properties info)
                   throws SQLException{
		return getConnection();
	}

	public DriverPropertyInfo[] getPropertyInfo(String url,
                                            Properties info)
                                     throws SQLException
	 {
	 open();

	 	return driver.getPropertyInfo(url,info);
	 }


	public int getMajorVersion(){
	try{
open();
}catch (Throwable e){}

		return driver.getMajorVersion();
	}

	public int getMinorVersion(){
		try{
	open();
	}catch (Throwable e){}

		return driver.getMinorVersion();
	}

	public boolean jdbcCompliant(){
	try{
open();
}catch (Throwable e){}

		return driver.jdbcCompliant();
	}

    /**
     * The maximum number of connections to be created.
     */
    protected  int maxCount = 200;
    protected  int maxRC = 50;
    public int getMaxCount() {
        return (this.maxCount);
    }

    public void setMaxCount(int maxCount) {
		//("************* Set Max: " + maxCount);
        this.maxCount = maxCount;
    }


    /**
     * The minimum number of connections to be created.
     */
    protected transient int minCount = 1;

    public int getMinCount() {
        return (this.minCount);
    }

    public void setMinCount(int minCount) {
        this.minCount = minCount;
    }


    /**
     * The database password for use in establishing a connection.
     */
    protected  String password = null;

    public String getPassword() {
        return (this.password);
    }

    public void setPassword(String password) {
    if (this.password != null)
    {
    if (debug)
		System.out.println("jiqlDataSource password ALREADY Set.");
    	if (properties.get("password") == null)
    		addProperty("password", this.password);

    	return;
    }

        this.password = password;
        addProperty("password", this.password);
    }


    /**
     * The default read-only state for newly created connections.
     */
    protected transient boolean readOnly = false;

    public boolean getReadOnly() {
        return (this.readOnly);
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }


    /**
     * The JDBC URL for the database connection to be opened.
     */
    protected  String url = null;
    protected  String failoverURL = null;

    public String getUrl() {
        return (this.url);
    }

public void setURL(String url) {
	}
    public void setUrl(String url) {
		if (this.url != null)
		{
		if (debug)
		{
			System.out.println(url + ":" + user + ":" + password + " jiqlDataSource url ALREADY Set. " + properties + ":" + connections() +  ":" + activeCount);
			Thread.dumpStack();
		}
			return;
		}
        this.url = url;


    }


    /**
     * The number of connections created by this data source that are
     * currently in use.
     */

    public int getUseCount() {
        return useCount;
        

    }


	protected void useCountMinus()
	{
		useCount = useCount - 1;
	}

	protected void useCountAdd()
	{
		useCount = useCount + 1;
	}


	protected void activeCountMinus()
	{
		activeCount = activeCount -1;

	}

	protected void activeCountAdd()
	{
				activeCount = activeCount +-1;

	}



    /**
     * The database username for use in establishing a connection.
     */
    protected  String user = null;

    public String getUser() {
        return (this.user);
    }

    public void setUser(String user) {
    if (this.user != null)
    {
    if (debug)
		System.out.println("jiqlDataSource user ALREADY Set.");
		if (properties.get("user") == null)
			addProperty("user", this.user);
    	return;
    }

        this.user = user;
        addProperty("user", this.user);
    }

    public void setUserName(String user) {
        setUser(user);
    }



    // ----------------------------------------------------- DataSource Methods


    /**
     * Attempt to establish a database connection.
     *
     * @exception SQLException if a database access error occurs
     */
	 static int cfc = 0;

	 public void checkForClosedConnections(){
	 	if (connectionsSize() < 1)return;
	 	int ct = 0;
		int cb = connectionsSize();
		 while (ct < connectionsSize())
		 {
		 	if (cb < 0 || ct >= connectionsSize())return;
			cb--;
			 jiqlConnection connection = (jiqlConnection) cons.get(ct);
			 connection.setClosed(false);
			 try{
			 if (connection.isClosed())
			 {
			 if (debug)
				System.out.println(new java.util.Date() + "jiqlDataSource DISCARDING Closed Pool Connection " + connectionsSize() + ":" + getUseCount() + ":" +getActiveCount() + ":" + ct + ":" + cb + ":" + creac);
			 	activeCountMinus();
				if (getActiveCount() < 0)
				synchronized (cons){
					activeCountAdd();
				}
				if (getUseCount() < 0)
				synchronized (cons){
					useCount = 0;
				}
				cons.remove(ct);
			 }else ct++;
			 }catch (SQLException e){
			 	System.out.println(ct + " jiqlDataSource.checkForClosedConnections ERROR " + e.toString());
			 }
		 }
	 }

    public Connection getConnection() throws SQLException {
        //int seconds = 0;
	try{
        if (closed)
            throw new JGException("getConnection:  Data source is closed");
		int wc = 0;
		cfc++;
		if (cfc == 10)
		{
		cfc = 0;
		checkForClosedConnections();
		}
        while (true) {

            synchronized (cons)
			{

                if (!cons.isEmpty())
				{
                    useCountAdd();



					jiqlConnection connection = (jiqlConnection) cons.removeFirst();
                    connection.setClosed(false);

					if (!connection.isClosed())
                    	return connection;
					else
					{
					if (debug)
						System.out.println(new java.util.Date() + " Discarding Closed Pool Connection " + connectionsSize() + ":" + getUseCount() + ":" +getActiveCount() );
						activeCountMinus();
						checkForClosedConnections();
						//createConnection();// 737:-717:20
					}

                }
            }
            if ( getActiveCount() < maxCount && creac < maxRC) {
                Connection conn = createConnection();
                useCountAdd();
                return (conn);
            }
			else if (getActiveCount() >= maxCount)System.out.println("Excess jiqlDataSource Connection Objects: " + getActiveCount());
			else if (creac >= maxRC)System.out.println("Excess jiqlDataSource Real Connections: " + creac);

            try {
				if (wc > loginTimeout)
					throw new JGException("DB Login timeout OR too many open DB connections (" + getActiveCount() + ":" + creac + ":" + connectionsSize() + "). Please make sure your calling the close method in the Connection object.");

                Thread.sleep(1000);
                wc++;
            } catch (InterruptedException e) {
                ;
            }

        }

        // We have timed out awaiting an available connection
        //throw new JGException
           // ("getConnection: Timeout awaiting connection");
		}catch (Throwable e){
		org.jiql.util.JGUtil.olog(e);
		e.printStackTrace();
		throw new JGException
		    (e.toString());
		}
    }

    private LinkedList connections(){
    	return cons;
    }


		private int connectionsSize(){
			return cons.size();
		}

    /**
     * Attempt to establish a database connection.  <b>WARNING</b> - The
     * specified username and password are ignored by this implementation.
     *
     * @param username Database username for this connection
     * @param password Database password for this connection
     *
     * @exception SQLException if a database access error occurs
     */



    public Connection getConnection(String username, String password)
        throws SQLException {

        return getConnection();
		//throw new JGException(SQLEXCEPTION_GETCONNECTION); // Not implemented

    }


    /**
     * Return the login timeout for this data source.
     *
     * @exception SQLException if a database access error occurs
     */
    public int getLoginTimeout() throws SQLException {

        return (loginTimeout);

    }



    /**
     * Return the log writer for this data source.
     *
     * @exception SQLException if a database access error occurs
     */
    public PrintWriter getLogWriter() throws SQLException {

        return new PrintWriter(System.out);

    }


    /**
     * Set the login timeout for this data source.
     *
     * @param loginTimeout The new login timeout
     *
     * @exception SQLException if a database access error occurs
     */
    public void setLoginTimeout(int loginTimeout) throws SQLException {

        loginTimeout = loginTimeout;

    }


    /**
     * Set the log writer for this data source.
     *
     * @param logWriter The new log writer
     *
     * @exception SQLException if a database access error occurs
     */
    public void setLogWriter(PrintWriter logWriter) throws SQLException {

        this.logWriter = logWriter;

    }


    // --------------------------------------------------------- Public Methods


    /**
     * Close all connections that have been created by this data source.
     *
     * @exception SQLException if a database access error occurs
     */


     public void cleanup(){
     try
     {
     	close();

     }catch (Throwable e){
	 	//e.printStackTrace();
     	System.out.println("Error Closing jiqlDataSource: " + e.toString());
     }
	 }
    public void close() throws SQLException {

        try{
		if (closed)
			throw new JGException("close:  Data Source already closed");

		if (cons == null)
		{
			closed  = true;
			return;
		}


	if (cons.size() > 1)
        System.out.println("Shutting down all active jiql Datasource connections " + connectionsSize() + ":" + getUseCount() + ":" +getActiveCount() + ":" + creac);

	if (cons.size() != getActiveCount())
        System.out.println("Some Connections not properly closed \r\n[Active Connection Count: " + getActiveCount() + "; Connection Cache Count: " + cons.size()  +  "]");

        while (cons.size() > 0) {
			if (!cons.isEmpty()) {
                    jiqlConnection conn = (jiqlConnection) cons.removeFirst();
            conn.close(true);
			}
			activeCountMinus();
        }

        // Mark this data source as having been closed and release our driver
        closed = true;
		}catch (NullPointerException e){
			System.out.println("ALL DATASOURCE SOCKETS ALREADY CLOSED");
		}
        //driver = null;

    }


    public void close(int sid) throws SQLException {

        if (closed)
    		throw new JGException("close:  Data Source already closed");
		synchronized (cons){

        for (int ct = 0;ct < cons.size();ct++) {
            jiqlConnection conn = (jiqlConnection) cons.get(ct);
			if (conn.hashCode() == sid)
			{
	            conn.close(true);
	            activeCountMinus();
				cons.remove(ct);
				break;
			}
        }
		}
    }



    /**
     * Open the initial connections that are appropriate for this data source.
     *
     * @exception SQLException if a database access error occurs
     */
    public void open() throws SQLException {

        // Have we already been opened?
        //(" OPEN ");

        if (driver != null)
            return;

            //setProperties(myprops);
			//properties.put("autoReconnect","true");
          //  if (user == null)
          //  	user = "";

		   // if (password == null)
		//	password = "";

			if (url == null)
			{
				//System.out.println("*********** [No URL for DataSource found. Please refer to the DataSource topic in the Help Section.]");
				//throw new JGException("No URL for DataSource found. Please refer to the DataSource topic in the Help Section");
				url = "jdbc:jiql://local";
			}


		if (driverClass == null)
		{

			driverClass = "org.jiql.jdbc.Driver";
		}
        // Instantiate our database driver
        try {
            Class clazz = Class.forName(driverClass);
            driver = (Driver) clazz.newInstance();
        } catch (Throwable t) {
            throw new JGException("createConnection: " + t);
        }


        closed = false;

    }


    /**
     * Return a string representation of this component.
     */
    public String toString() {

        StringBuffer sb = new StringBuffer("jiqlDataSource[");
        sb.append("activeCount=");
        sb.append(getActiveCount());
        sb.append(", autoCommit=");
        sb.append(autoCommit);
        sb.append(", closed=");
        sb.append(closed);
        if (description != null) {
            sb.append(", description=");
            sb.append(description);
        }
        sb.append(", driverClass=");
        sb.append(driverClass);
        sb.append(", loginTimeout=");
        sb.append(loginTimeout);
        sb.append(", maxCount=");
        sb.append(maxCount);
        sb.append(", minCount=");
        sb.append(minCount);
        sb.append(", password=");
        sb.append(password);
        sb.append(", readOnly=");
        sb.append(readOnly);
        sb.append(", url=");
        sb.append(url);
        sb.append(", useCount=");
        sb.append(useCount);
        sb.append(", user=");
        sb.append(user);
        sb.append("]");
        return (sb.toString());

    }


    // ------------------------------------------------------ Protected Methods


    /**
     * Create, configure, and return a new JDBC Connection that has been
     * wrapped in our corresponding wrapper.
     *
     * @exception SQLException if a database access error occurs
     */
    protected synchronized Connection createConnection() throws SQLException {
		open();
        activeCountAdd();
        return createTheConnection();

    }

	protected static int creac = 0;
    protected synchronized Connection createTheConnection() throws SQLException {
		try{
		//if (properties == null)
			//setProperties(myprops);
        Connection co = driver.connect(url, properties);
		creac++;
		if (co == null)
			throw new JGException(creac + " Error Creating DB Connection for jiqlDataSource " + url + ":" + driver + ":" + properties);

		return co;
		}catch (NullPointerException nu){
		 throw new JGException("NULL Values while creating DB Connection. Please refer to the DataSource topic in the Help Section " + url + ":" + driver + ":" + properties);
		}
		catch (SQLException se){
			System.out.println(se.toString() + " jiqlDataSource.creatTheConnection ERROR " + toString() + ":" + properties);
			throw se;
		}

    }

  
  


    // -------------------------------------------------------- Package Methods


    /**
     * Return this connection to the available connection pool.
     *
     * @param conn The connection being returned
     */
    void returnConnection(jiqlConnection conn) {

        synchronized (cons) {
            cons.addLast(conn);
            useCountMinus();
        }

    }

    void releaseConnection(jiqlConnection conn,boolean tf) {
				useCountMinus();
        if  (tf) {
            activeCountMinus();

        }

    }



}




