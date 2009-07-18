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
import java.sql.SQLException;
import java.io.*;
import java.net.*;
import java.util.*;
import tools.util.EZArrayList;
import tools.util.NameValuePairs;
import tools.util.StringUtil;
import tools.util.Crypto;
import org.jiql.db.*;
import org.jiql.db.objs.*;
import org.jiql.jdbc.jiqlConnection;
import org.jiql.util.JGException;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.httpclient.params.HttpMethodParams;
import java.io.ObjectInputStream;

public class JiqlClient
{




 public static Hashtable execute(jiqlConnection jcon,String sql)
     throws SQLException

    {
    try{

			Properties hash = jcon.getProperties();
   			String addr = (String)hash.get("url");

		InputStream responseStream = null;
    HttpClient client = new HttpClient();

    // Create a method instance 2.
    PostMethod method = new PostMethod(addr);

			if (StringUtil.isRealString(hash.getProperty("user")))
             method.addParameter("user", hash.getProperty("user"));

			if (StringUtil.isRealString(hash.getProperty("password")))
             method.addParameter("password", hash.getProperty("password"));
			if (StringUtil.isRealString(hash.getProperty("date.format")))
             method.addParameter("date.format", hash.getProperty("date.format"));

            method.addParameter("query", sql);










    try {
      // Execute the method.
      int statusCode = client.executeMethod(method);

      if (statusCode != HttpStatus.SC_OK) {
        //("Method failed: " + method.getStatusLine());
		throw JGException.get("method_failed","Method failed: " + method.getStatusLine());

      }

      // Read the response body.
       responseStream = method.getResponseBodyAsStream();

		ObjectInputStream ois = new ObjectInputStream(responseStream);
		return (Hashtable)ois.readObject();

       //responseBody getResponseBody();

      // Deal with the response.
      // Use caution: ensure correct character encoding and is not binary data
      //(new String(responseBody));

    } catch (HttpException e) {
      System.err.println("Fatal protocol violation: " + e.getMessage());
      e.printStackTrace();
    	throw JGException.get("fatal_protocol_violation","Fatal protocol violation: " + e.getMessage());


    } catch (IOException e) {
      System.err.println("Fatal transport error: " + e.getMessage());
      e.printStackTrace();
      	throw JGException.get("fatal_transport_error","Fatal transport error: " + e.getMessage());

    } finally {
      // Release the connection.
      method.releaseConnection();
    }





      //  return new ByteArrayInputStream(responseBody);









    }catch (Exception e){
e.printStackTrace();
 	throw new SQLException( e.toString());

    }

//    return null;

    }





}