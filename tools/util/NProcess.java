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

 import java.io.*;
 import java.util.*;
 import java.net.*;

import tools.util.*;


public  class NProcess extends Process
{
	static long totalProcess = 0;
	static long running = 0;
	public long pause = 1000;
	public int maxWait = 2;
	static long successfulExit = 0;
	static long exitAttempts = 0;
	public int exV = -1;
	public boolean exited = false;
	public boolean trace = true;
	public StringBuffer printTrace = new StringBuffer();
	public boolean noPre = false;

	Process process = null;
	String msg = null;

	String cstring = null;
	public void setCommandString(String cs){
		cstring = cs;	
	}


	public NProcess (Process p){
		process = p;
		totalProcess++;
		running++;
	}

	public NProcess (Process p,long s,int w){
		this(p);
		maxWait = w;
		pause = s;

	}

	String id = null;
	public String getId(){
		if (id == null)id = String.valueOf(hashCode());
		return id;
	}

	public InputStream getInputStream(){
	try{
		InputStream error = process.getErrorStream();
		int av = 0;
	while ((av = error.available()) > 0)
	{
		byte[] b = new byte[av];
		error.read(b,0,av);
		String es = new String(b);
		if (!noPre)
			es = StringUtil.replaceSubstring(es,"#","-");
		if (es != null)
			printTrace.append(es);
		if (trace)
			LogMgr.debug(process + " Nprocess Test Err " + es);
	}
	//error.close();
	}catch (Throwable eq){
		if (eq.toString().indexOf("Bad file descriptor") < 0){
		
		LogMgr.debug(process + " Nprocess Test err Error: " + eq.toString());
		///eq.printStackTrace();
		}
	}

		return process.getInputStream();
	}

	public OutputStream getOutputStream(){
		return process.getOutputStream();
	}

	public InputStream getErrorStream(){
		return process.getErrorStream();
	}
	public String getStreamsString(){
	return getStreamsString(1);
}
	String buf = null;

		public String getPresentStreamsString(int trs){
			if (buf == null)
			getStreamsString(trs);
			return buf;
		}


				public String getPresentStreamsString(){
			return getPresentStreamsString(1);
		}
	public String getStreamsString(int trs){

	int av = 0;
	InputStream in = getInputStream();
	InputStream error = getErrorStream();
	//StringBuffer str = new StringBuffer();
	for (int ct = 0;ct < trs;ct++)
	{
	try{
	if (ct > 0)
		Thread.sleep(1000);

	while ((av = in.available()) > 0)
	{
		byte[] b = new byte[av];

			in.read(b,0,av);
			String es = new String(b);
			if (!noPre)
				es = StringUtil.replaceSubstring(es,"#","-");
			if(trace)
			LogMgr.debug(process + " Nprocess In " + es);
			if (es != null){
				printTrace.append(es);
				buf = es;
			}

	}
	//in.close();
	}catch (Throwable eq){
		if (eq.toString().indexOf("Stream closed") < 0)
		LogMgr.debug(process + " Nprocess in Error: " + eq.toString());
	}
	try{
	while ((av = error.available()) > 0)
	{
		byte[] b = new byte[av];
		error.read(b,0,av);
		String es = new String(b);
		if (!noPre)
			es = StringUtil.replaceSubstring(es,"#","-");
		if (trace)
		LogMgr.debug(process +  " Nprocess Err " + es);
		if (es != null){
			printTrace.append(es);
			buf = es;
		}

	}
	//error.close();
	}catch (Throwable eq){
		if (eq.toString().indexOf("Bad file descriptor")  < 0)
		LogMgr.debug(process + " Nprocess err Error: " + eq.toString());
	}
	}
		if (printTrace.length() > 0){
		if (trace)
		LogMgr.debug("NPROCESSINSTREAM: " + printTrace);
		if (printTrace.toString().indexOf("Too many open files in system") > -1)
		{
			//LogMgr.debug(elseadmin.main.ServerMgr.fifo.size() + " List of contexts running: " + elseadmin.main.ServerMgr.fifo);
		}

		}
		return printTrace.toString();


	}


	public void destroy(String ms){
		msg = ms ;
		//LogMgr.debug("SET NP Message: " + ms);
		destroy();
}

	public void destroy(){
	try{
	if (pause > 0)
	Thread.currentThread().sleep(pause);
	}catch (Throwable es){
		LogMgr.debug("Nprocess.sleep Error 1: " + es.toString());
	}

	exitAttempts++;

	getStreamsString();
	for (int ct = 0;ct < maxWait; ct++)
	{
		if (msg != null && (ct + 1) >=maxWait)
		try{
			LogMgr.debug("Sending Final Exit Msg: " + msg);
			PrintStream pr = new PrintStream(getOutputStream(),true);
			pr.println(msg);
			pr.flush();
			if (pause > 0)
				Thread.currentThread().sleep(pause*3);

		}
		catch (Throwable e){
			LogMgr.debug(msg + " NP Destroy Message Err: " + e.toString());
		}

		try{
			getStreamsString();
			 exV = exitValue();
			successfulExit++;
			break;

		}catch (Throwable e){
			if (trace)
				LogMgr.debug(ct + " Nprocess.exitValue Error: " + e.toString());
		}
		try{
		if (pause > 0)
		Thread.currentThread().sleep(pause);
		}catch (Throwable es){
			LogMgr.debug("Nprocess.sleep Error: " + es.toString());
		}
	}

		try{
			process.destroy();
			if (exV < 0)
			{
				LogMgr.debug("Process Not Exited Properly: " + cstring);
				
			}


		}catch (Throwable es){
			LogMgr.debug("Nprocess.destroy Error: " + es.toString());
		}

		exited = true;
	}


	protected void finalize()
	             throws Throwable

	{
		running--;
	}


	public int exitValue() throws IllegalThreadStateException
	{
		return process.exitValue();
	}

public  int waitFor()
                     throws InterruptedException{
					 return process.waitFor();
					 }

}


