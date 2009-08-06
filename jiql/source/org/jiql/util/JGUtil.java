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

import tools.util.*;
import java.io.*;
import java.util.*;
import org.jiql.db.Row;
import java.sql.SQLException;



public  class JGUtil
{

static int maxLog = 20;
static Vector<String> validF = new Vector<String>();
static{
	validF.add("BOOLEAN");
	validF.add("BOOL");

}

public static void log(Throwable l){
	ByteArrayOutputStream bout = new ByteArrayOutputStream();
	PrintWriter pint = new PrintWriter(bout);
	l.printStackTrace(pint);
	pint.close();
	log(new String(bout.toByteArray()));
}

public static void olog(Throwable l){
	ByteArrayOutputStream bout = new ByteArrayOutputStream();
	PrintWriter pint = new PrintWriter(bout);
	l.printStackTrace(pint);
	pint.close();
	tools.util.LogMgr.err(new String(bout.toByteArray()));
}
public static void log(String l){
			l = new Date() + " - " + l;
			Hashtable hc = new Hashtable();
			//new com.google.appengine.api.datastore.Text
		hc.put("log", (l));
		try{
		if (org.jiql.JIQLGDataUtil.count("jiqlLog") > maxLog)
			clear();
		org.jiql.JIQLGDataUtil.put("jiqlLog",hc);
		}catch (Exception e){
			tools.util.LogMgr.err(l + " jiqlLog " + e.toString());
			e.printStackTrace();
		}
}

public static void clear(){
		try{
		
		org.jiql.JIQLGDataUtil.delete("jiqlLog");
		}catch (Exception e){
			tools.util.LogMgr.err("jiqlLog.clear " + e.toString());
		}
}

public static boolean validFieldType(String f,SQLParser sqp)throws SQLException{

	f = f.toUpperCase();
	int i = f.indexOf("(");
	if (i > 0)
		f = f.substring(0,i);
	f = f.trim();

	if (validF.contains(f))return true;

	Vector<Row> vr = Gateway.get(sqp.getProperties()).getTypeInfo(sqp);

	Row r = null;
	for (int ct = 0;ct < vr.size();ct++)
	{
		r = vr.elementAt(ct);
		if (r.get("TYPE_NAME").equals(f))return true;
	}
	
	return false;
}

}