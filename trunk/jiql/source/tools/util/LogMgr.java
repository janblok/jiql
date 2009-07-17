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

package tools.util;
import tools.util.StringUtil;
import java.util.Date;

//javax.servlet.http.HttpServletRequest
public class LogMgr
{

public static  long acs = System.currentTimeMillis();
public static boolean acsdebug = false;

	public  static void log(String l){
	if (l != null && (l.startsWith("#") || l.startsWith("^")))
		l = "-" + l;

		System.out.println(l);
	}

	public  static void red(String l){
		debug("<font face=verdana,sans-serif size=1 color=#FF0000>" + StringUtil.webEncode(l) + "</font>");
	}

	public  static void color(String l,String col){
		debug("<font face=verdana,sans-serif size=1 color=#" + col + ">" + StringUtil.webEncode(l) + "</font>");
	}


	public  static void logt(String l){
		System.out.println(new java.util.Date().toString() + ": " + l);
	}


	public  static void err(String l){
	if (l != null && (l.startsWith("#") || l.startsWith("^")))
		l = "-" + l;

		System.err.println(new Date() + "[ERROR]: " + l);
		
	}

	public  static void warn(String l){
	if (l != null && (l.startsWith("#") || l.startsWith("^")))
		l = "-" + l;

		System.err.println(new Date() + "[WARNING]: " + l);
		
	}
	public  static void out(String l){
		System.out.println(l);
	}

	public  static void debug(String l){
		if (l != null && (l.startsWith("#") || l.startsWith("^")))
			l = "-" + l;
		if(debug)
			System.out.println(new Date() + " Debug: " + l);
	}

	public static boolean debug = true;

	public  static void debugger(String l){
		if (debug)debug(l);
	}

	public  static void ac(String l){
		if (acsdebug)debug("ACS: " + l);
	}


	public  static void security(String l){
		if (l != null && (l.startsWith("#") || l.startsWith("^")))
			l = "-" + l;
		red("appcloem:SECURITY: " + l);
	}


	public  static void debug(Throwable e){
		if(debug)
			e.printStackTrace();
		if (e.toString().toLowerCase().indexOf("too many open files") > -1)
		{
			//elseadmin.main.ServerMgr.turnOffAll2();
			System.out.println("There is too many open files");
			//("restart");
		}

	}


}
