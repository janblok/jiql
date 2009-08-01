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

import java.io.IOException;
import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.Driver;
import java.sql.Connection;
import java.util.Properties;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.io.OutputStream;
import java.io.ObjectOutputStream;
import java.io.File;
import tools.util.StringUtil;
import tools.util.NameValuePairs;
import org.jiql.util.JGException;

public class JiqlServlet extends HttpServlet {
  String theUser = null;
  String thePassword = null;
     public void init(ServletConfig config)
    throws ServletException
  {
  	try{

		String ps = config.getServletContext().getRealPath("/WEB-INF/jiql.properties");
		if (!new File(ps).exists())
				{
			return;
		}
 		NameValuePairs p = new NameValuePairs(ps);
 		theUser = (String)p.get("user");
		thePassword = (String)p.get("password");

  	}catch (Exception e){
  		tools.util.LogMgr.err("JiqlServlet.init " + e.toString());
  	}
  	super.init(config);
  }
   
      public void doPost(HttpServletRequest req, HttpServletResponse resp)
              throws IOException,ServletException {
              	doGet(req,resp);
              }
  
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
              throws IOException,ServletException {
		String user = req.getParameter("user");
		String password = req.getParameter("password");
		if (!StringUtil.isRealString(user) || !StringUtil.isRealString(password) )
		{
			resp.sendError(403,"Invalid User or Password");
			return;
		}

		if (!StringUtil.isRealString(theUser) || !StringUtil.isRealString(thePassword) )
		{
			resp.sendError(403,"Invalid User OR Password");
			return;
		}
			//throw new ServletException("Invalid User or Password");
			
		
		/*String ps = getServletContext().getRealPath("/WEB-INF/jiql.properties");
		if (!new File(ps).exists())
				{
			resp.sendError(403,"Missing /WEB-INF/jiql.properties file with user and password. Please refer to jiql Documentation");
			return;
		}*/
			//throw new ServletException("Missing /WEB-INF/jiql.properties file with user and password. Please refer to jiql Documentation");
	
			
			 Connection Conn = null;
	 Hashtable h = new Hashtable();	
	 	 h.put("remote","true");	 
 try{
 	//	NameValuePairs p = new NameValuePairs(ps);
			if (!user.equals(theUser) || !password.equals(thePassword) )
			{
			resp.sendError(403,"Invalid User OR Invalid Password");
			return;
		}
			//throw new ServletException("Invalid User OR Password");

			String sql = req.getParameter("query");
NameValuePairs nvp = new NameValuePairs();
String tok = req.getParameter("date.format");
if (tok != null)
nvp.put("date.format",tok);
 Conn = get(nvp);
org.jiql.jdbc.Statement Stmt = (org.jiql.jdbc.Statement)Conn.createStatement();
Stmt.execute(sql);
org.jiql.jdbc.ResultSet res = (org.jiql.jdbc.ResultSet)Stmt.getResultSet();
 

 if (res != null)
 {
 
 if (res.getResults() != null)
 h.put("results",res.getResults());
  if (res.getSQLParser() != null)
 h.put("sqlparser",res.getSQLParser());
 }
 else
 	 h.put("sqlparser",Stmt.getSQLParser());

 //h.put("remote","true");
            resp.setContentType("binary/object");
            OutputStream fos = resp.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(h);
            //resp.getWriter(). ("result" + h);


}catch (Exception ex){
org.jiql.util.JGUtil.olog(ex);
ex.printStackTrace();
tools.util.LogMgr.err("JIQLServlet " + ex.toString());
JGException je = null;
if (ex instanceof JGException)
je = (JGException)ex;
else
je = new JGException(ex.toString());

 h.put("error",je);


            resp.setContentType("binary/object");
            OutputStream fos = resp.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(h);


//throw new ServletException(ex.toString());
}finally{
if (Conn != null)
try{

Conn.close();
}catch (Exception ex){
ex.printStackTrace();
}
}

			
			


    }
    
    
    
       static Driver driver = null;
  	static String url = "jdbc:jiql://local";
    static Properties props = new Properties();

    static {
    	
    String password = "";
    String user = "";
     
    props.put("user",user);
    props.put("password",password);
   try{
   
    Class clazz = Class.forName("org.jiql.jdbc.Driver");
    driver = (Driver) clazz.newInstance();
   }catch (Exception e){
   	e.printStackTrace();
   }
    }

    public static Connection get(NameValuePairs nvp) {
    	try{
    	nvp.merge(props);
    	
        return driver.connect(url,nvp.toProperties());
    	}catch (java.sql.SQLException e){
      	e.printStackTrace();

    	}
    	return null;
    }
    
    
    
    
    
    
}

