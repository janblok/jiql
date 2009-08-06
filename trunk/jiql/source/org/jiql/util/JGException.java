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

package org.jiql.util;
import java.sql.*;
public class JGException extends SQLException implements java.io.Serializable
{
	String message = null;

	public static JGException get(){
		try{

		String p = JGProperties.get().getProperty("JGException");
		if (p != null)
		{
		JGException se = (JGException)Class.forName(p).newInstance();
		return se;
		}
		}catch (Exception e){
			e.printStackTrace();
		}
		return new JGException();
	}

public static JGException get(String id){
	return get(id,id);
}

public static JGException get(String id,String m){
		JGException se =  get();
		se.setJGMessage(m);
		return se;
	}
static boolean debug = true;
	protected void setJGMessage(String m){
		message = m;
		if (debug)
			System.err.println(message);
	}
	public JGException(){
		super();

	}
	public JGException(String m){
		super();
		setJGMessage(m);

	}






	public String toString(){
		return message;
	}





}