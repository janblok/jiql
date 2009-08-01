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

package org.jiql.util;
import java.io.*;
import java.util.*;
import java.sql.*;
import tools.util.StringUtil;
import tools.util.FileUtil;
//import org.jiql.jdbc.*;

public class TestSuite2 {
static PrintWriter pout = null;
static String typ = "object";
static int errors = 0;
	 public static void main(String args[])throws Exception {

//object bytes type-long type-int type-short
	 	Connection	Conn = null;
	 					int tcomp = 0;
		int ttries = 0;
	long stime = System.currentTimeMillis();
Statement stat = null;





String url = StringUtil.getTrimmedValue(args[0]);
String srcf = StringUtil.getTrimmedValue(args[1]);
String outpu = StringUtil.getTrimmedValue(args[2]);
typ = StringUtil.getTrimmedValue(args[3]);

	Properties props = new Properties();

	Class clazz = Class.forName("org.jiql.jdbc.Driver");
	Driver driver = (Driver) clazz.newInstance();

	Conn = driver.connect(url,props);








	stat = Conn.createStatement();








	ttries = ttries + 1;
try{
	if (new File(outpu).exists())
		new File(outpu).renameTo(new File(outpu + ".bak"));
	new File(outpu).createNewFile();
	pout = new PrintWriter(new FileOutputStream(new File(outpu)));
String f = FileUtil.getStringFromFile(srcf);
parseScript(stat,f);
pout.close();

	if (!new File(outpu+ ".bak").exists())
	{
		System.out.println("*** NO FILE TO COMPARE");
	}
	else
	{
		String f1 = FileUtil.getStringFromFile(outpu);
		String f2 = FileUtil.getStringFromFile(outpu+ ".bak");
		System.out.println("*** IS OUTPUT EQUAL " + (f1.equals(f2)) + ":" + f1.length() + ":" + f2.length());

	}


}catch (Exception e){
	System.out.println("TEST ERROR: " + e.toString());
}
	tcomp = tcomp + 1;


System.out.println("Error Count: " + errors);

	 	}


static void msg(String t,String m){
	System.out.println(t + ":" + m);
	pout.println(t + ":" + m);
}

static void log(String t,boolean tf){
	msg("S",t + "=" + tf);
}

static void err(String t,String tf){
	errors = errors + 1;
	msg("*****E",t + "=" + tf);
}

static void displayResult(ResultSet result)throws SQLException{
	if (result != null) {

    		ResultSetMetaData mres = result.getMetaData();
    		int cc = mres.getColumnCount();

 for (int c = 0;c < cc;c++){

		 System.out.print(mres.getColumnName(c + 1) + "|" );
    }
    System.out.println("");
	pout.println("");
    while (result.next()){
 for (int c = 0;c < cc;c++){

    		 if (typ.startsWith("type"))
    		 {
	int type = mres.getColumnType(c + 1);
		if (Types.VARCHAR == type )
		{
		    System.out.print("varchar: " + result.getString(mres.getColumnName(c + 1)) + ",");
    		 pout.print(result.getString(mres.getColumnName(c + 1)) + ",");
	}	else if (Types.INTEGER == type || Types.BIGINT == type)
		{
		    if (typ.startsWith("type-int")){
		    
		    System.out.print("int: " + result.getInt(mres.getColumnName(c + 1)) + ",");
    		 pout.print(result.getInt(mres.getColumnName(c + 1)) + ",");
		    }
		    else if (typ.startsWith("type-long")){
		    
		    System.out.print("long: " + result.getLong(mres.getColumnName(c + 1)) + ",");
    		 pout.print(result.getLong(mres.getColumnName(c + 1)) + ",");
		    }
		    else {
		    
		    System.out.print("short: " + result.getShort(mres.getColumnName(c + 1)) + ",");
    		 pout.print(result.getShort(mres.getColumnName(c + 1)) + ",");
		    }
	}	else if (Types.FLOAT == type)
		{
		    System.out.print("float: " + result.getFloat(mres.getColumnName(c + 1)) + ",");
    		 pout.print(result.getFloat(mres.getColumnName(c + 1)) + ",");
	}	else if (Types.BOOLEAN == type)
		{
		    System.out.print("boolean: " + result.getBoolean(mres.getColumnName(c + 1)) + ",");
    		 pout.print(result.getBoolean(mres.getColumnName(c + 1)) + ",");
	}	else if (Types.DATE == type)
		{
		    System.out.print("date: " + result.getDate(mres.getColumnName(c + 1)) + ",");
    		 pout.print(result.getDate(mres.getColumnName(c + 1)) + ",");
	} 
	else
	{
		    System.out.print("object: " + result.getObject(mres.getColumnName(c + 1)) + ",");
    		 pout.print(result.getObject(mres.getColumnName(c + 1)) + ",");
	}    		 	
    		 }
    		 else if (typ.equals("object")){
    		 
    		 System.out.print(result.getObject(mres.getColumnName(c + 1)) + ",");
    		 pout.print(result.getObject(mres.getColumnName(c + 1)) + ",");
    		 	}
    		 else if (typ.equals("bytes")){
    		 byte[] b = result.getBytes(mres.getColumnName(c + 1));
    		 String o = null;
    		 if (b != null)
    		 	o = new String(b);
    		 System.out.print(o + ",");
    		 pout.print(o + ",");
    		 	}
    }
    System.out.println("");
    pout.println("");
    }
    }

}


		public static  void parseScript (Statement	sstmt,String f)

	{
		ResultSet result = null;
		try{
		if (!f.trim().endsWith(";"))
		f = f + ";";
		boolean tf = true;
		boolean dr = false;
		ByteArrayInputStream bin = new ByteArrayInputStream(f.getBytes());
		InputStreamReader fin = new InputStreamReader(bin);
		try{
		String templ = null;
		LineNumberReader lr = new LineNumberReader(fin);
		String tc = "";
		int isc = -1;
		while ((templ = lr.readLine()) != null){
		if (!StringUtil.isRealString(templ) || templ.trim().startsWith("--") || templ.trim().startsWith("#"))continue;
		templ = tc + templ;
		if (!StringUtil.getTrimmedValue(templ).endsWith(";")){
			if (StringUtil.getTrimmedValue(templ).endsWith(","))
				templ = templ + "\n";
			tc = templ;
			continue;
		}

		templ = StringUtil.replaceLastSubstring(templ,";","");
		templ = StringUtil.replaceSubstring(templ,"NG_TM_SC",";");
		try{
		tc = "";
		if (dr){
			isc = templ.toLowerCase().indexOf("create table");
			if (isc > -1)
			{
			String dtempl = null;
				try{
						dtempl = templ.substring(isc + "create table".length(),templ.length()).trim();
						isc = dtempl.indexOf("(");
						if (isc < 0)
						 isc = dtempl.indexOf(" ");
						 if (isc > -1){
						dtempl = dtempl.substring(0,isc);
						//scriptL.add("drop table " + dtempl);

		//Statement	sstmt = m_con.createStatement ();
		//sstmt.execute(templ.toString());

						}
					}catch (Exception e){
						err(templ,e.toString());
						e.printStackTrace();
						//if (e.toString().indexOf("ORA-00942") < 0)
						//tools.util.LogMgr.warn(dtempl + " executeScript DROP " + e.toString());
					}

			}
		}
		sstmt.execute(templ.toString());
		//Stmt.execute(sql);
		result = sstmt.getResultSet();
		if (result == null)
			log(templ,false);
		else{
			log(templ,true);
			displayResult(result);
		}
		
		}catch (Exception e){
						err(templ,e.toString());
						e.printStackTrace();

		}
		}
		}finally{
			fin.close();
		}
		}catch (Exception e){
			tools.util.LogMgr.err("parseScript " + e.toString());
			e.printStackTrace();
		}
	}


}
