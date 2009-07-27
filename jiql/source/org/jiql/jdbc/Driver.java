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
import java.util.*;
import java.sql.*;
import java.io.*;
import tools.util.StringUtil;
import tools.util.SEXParser;
public class Driver implements java.sql.Driver
{
	 static
   {
       try
       {

           java.sql.DriverManager.registerDriver(new Driver());
       }
       catch (SQLException e)
       {
           e.printStackTrace();
       }
   }
	
	public Driver()throws SQLException{
	
	//DriverManager.registerDriver(this);
	}

public boolean 	acceptsURL(String url){
//jdbc:jiql:gql:http://gql.appspot.com
return url.startsWith("jdbc:jiql:");
}
          //Retrieves whether the driver thinks that it can open a connection to the given URL.
public Connection 	connect(String url, Properties info){
	//(url + " :connect: " + info);
	//if (info.getProperty("user") == null && info.getProperty("username") != null)
	//	info.put("user",info.getProperty("username"));
url = StringUtil.replaceSubstring(url,"jdbc:jiql:gql:","");
url = StringUtil.replaceSubstring(url,"jdbc:jiql:","");
info.put("url",url);
String baseUrl = url;
int i = url.indexOf("?");
if (i > 0){
String par = url.substring(i + 1,url.length());
baseUrl = url.substring(0,i);
par = SEXParser.decodeXML(par);
Hashtable h = StringUtil.parseQueryString(par);
//("JID1a " + h);

Enumeration en = h.keys();
String n = null;
String[] v = null;
while (en.hasMoreElements())
{
	n = en.nextElement().toString();
	v = (String[])h.get(n);
	info.put(n,v[0]);
	//(n + " JID2 " + v[0]);

}
}
info.put("baseUrl",baseUrl);
return  new jiqlConnection(info);
}
          //Attempts to make a database connection to the given URL.
public int 	getMajorVersion(){
return 0;
}
          //Retrieves the driver's major version number.
public int 	getMinorVersion(){
return 1;
}
          //Gets the driver's minor version number.
public DriverPropertyInfo[] 	getPropertyInfo(String url, Properties info){
return new DriverPropertyInfo[0];
}
          //Gets information about the possible properties for this driver.
public boolean 	jdbcCompliant(){
return false;
}
          //Reports whether this driver is a genuine JDBC CompliantTM driver.


}


