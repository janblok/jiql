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

/**
 * This class is used for common methods.
 **/
public class NDemon
{
	static int maxTries = 10;
	static Object lock = new Object();
	static String script_dir = "";
	static String script_runner = "";
	public static void setScriptDir(String d){
			script_dir = d;
		}

	public static String getScriptDir(){
			return script_dir;
		}
	public static void setScriptRunner(String d){
			script_runner = d;
			//(script_runner + "*********************88 script_runner");

		}

	public static String getScriptRunner(){
			return script_runner;
			//(script_runner + "*********************88 script_runner");

		}
	
	
static Object queue = new Object();
	static{
		synchronized (lock){
	//new File("/usr/appcloem/admin/appcloemdemonbackgroundcommands/").mkdirs();
	//new File("/usr/appcloem/admin/appcloemdemonbackgroundends/").mkdirs();
	//new File("/usr/appcloem/admin/appcloemdemonends/").mkdirs();
	//new File("/usr/appcloem/admin/data/FailedappcloemCommands").delete();
	//new File("/usr/appcloem/admin/data/AllappcloemCommands").delete();
	//if (Elseproperties.isWindows())
	// new File("/usr/appcloem/admin/appcloemdemonends/").mkdirs();

}
	}


	public static void exec(String script,String user,String home,String stdou)throws Exception{

		//if (np == null){
					if (stdou != null)
		 new File(stdou).delete();

		 String exs = execUser(script,user);
		NProcess  np = NDemon.procExec(exs,home);
		 //}
		if (stdou != null){
		 //new File(stdou).delete();
		 sys(np,stdou);
		 if (new File(stdou).length() < 1)
		 {
		 	String t = np.getStreamsString();
		 	if (t.length() > 0)
		 	 {
		 	 	FileUtil.writeTextToFile(new File(stdou),t);
		 	 	//(stdou + " FOR EXEC WRIT TO GIE " + t);
		 	 }
		 	
		 }
		}
		//Thread.sleep(3000);
		np.destroy();
	}


public static String jExec(String args,String cdir){
	return jExec(args,cdir,25);
}

public static String jExec(String args,String cdir,int ct){
return jExec(args,cdir,ct,null);
}
public static String jExec(String args,String cdir,int ct,Vector v2){
		EZArrayList v = SharedMethods.quotedVector(args," ");
		return winExec(v,cdir,ct,null,v2);
}


public static String jExec(String args){
	return jExec(args,25);
}

public static String jExec(String args,int ct){
		return jExec(args,null,ct);
}

	public static void winExec(String args){
		EZArrayList v = SharedMethods.quotedVector(args," ");
		winExec(v,null,300,null);

	}

	public static void osExec(String args)throws Exception{
		if(System.getProperty("os.name").toLowerCase().indexOf("windows") > -1)
			winExec(args);
		else
			exec(args);

	}


	public static void winExec(String args,String tok){
		EZArrayList v = SharedMethods.quotedVector(args," ");
		winExec(v,null,300,tok);

	}

	public static void osExec(String args,String tok)throws Exception{
		if(System.getProperty("os.name").toLowerCase().indexOf("windows") > -1)
			winExec(args,tok);
		else
			exec(args);

	}



		public static void osExec(String args,boolean tok)throws Exception{
		if(System.getProperty("os.name").toLowerCase().indexOf("windows") > -1)
			winExec(args,tok);
		else
			exec(args);

	}

	/*		public static void osExec(String args,boolean tok,String context)throws Exception{
		if(System.getProperty("os.name").toLowerCase().indexOf("windows") > -1)
			winExec(args,tok);
		else{
					String vpc = appcloem.modules.sitemgr.VPSMgr.getExec(context,args);
		if (vpc != null)
			exec(vpc);
			else
			exec("su - " + context + " -c \"" + args + "\"");
		}

	}


				public static void osExec(String args,String tok,String context)throws Exception{

		ContextProperties cp = ContextProperties.get(context);
		if(System.getProperty("os.name").toLowerCase().indexOf("windows") > -1){
		//winExec(args,tok);
		EZArrayList v = SharedMethods.toVector(args," ");
		winExec(v,null,5000,tok,false,0,cp);

		}
		else{
		String vpc = appcloem.modules.sitemgr.VPSMgr.getExec(context,args);
		if (vpc != null)
			execWait(vpc,5000,cp);
			else
			execWait("su - " + context + " -c \"" + args + "\"",5000,cp);
		}

	}*/



		public static String winExec(String args,boolean tok){
		EZArrayList v = SharedMethods.quotedVector(args," ");
		return winExec(v,null,300,"BLAH",tok,0,null);

	}

	public  static String winExec(EZArrayList args,String cdir,int wa,String tok,int w){
		return winExec(args,cdir,wa,tok,false,w,null);
	}

		public  static String winExec(EZArrayList args,String cdir,int wa,String tok){
		return winExec(args,cdir,wa,tok,false,0,null);
	}
	
			public  static String winExec(EZArrayList args,String cdir,int wa,String tok,Vector v){
		return winExec(args,cdir,wa,tok,false,0,v);
	}
	
	public static String getScriptRunnerString(String s){
		String cm = StringUtil.replaceSubstring(s," ","");
		if (cm.length() > 200)
		 cm = cm.substring(0,200);
		cm = StringUtil.replaceSubstring(cm,"'","");
		cm = StringUtil.replaceSubstring(cm,"\"","");
		cm = StringUtil.replaceSubstring(cm,"\\","");
		cm = StringUtil.replaceSubstring(cm,"/","");
		cm = StringUtil.replaceSubstring(cm,":","");
		cm = StringUtil.replaceSubstring(cm,">","");
		cm = StringUtil.replaceSubstring(cm,"%","");
		cm = StringUtil.replaceSubstring(cm,"..","");
		cm = StringUtil.replaceSubstring(cm,"*","");
		cm = StringUtil.replaceSubstring(cm,"=","");

		return cm;
	} 

public static String getScriptRunner(String s){
		String d = script_dir;
		String cm = StringUtil.replaceSubstring(s," ","");
		if (cm.length() > 200)
		 cm = cm.substring(0,200);

		cm = StringUtil.replaceSubstring(cm,"'","");
		cm = StringUtil.replaceSubstring(cm,"\"","");
		cm = StringUtil.replaceSubstring(cm,"\\","");
		cm = StringUtil.replaceSubstring(cm,"/","");
		cm = StringUtil.replaceSubstring(cm,":","");
		cm = StringUtil.replaceSubstring(cm,">","");
		cm = StringUtil.replaceSubstring(cm,"%","");
		cm = StringUtil.replaceSubstring(cm,"..","");
		cm = StringUtil.replaceSubstring(cm,"*","");
		if (SharedMethods.windows())
		cm = cm + ".bat";
		new File(d + cm).delete();
		FileUtil.writeToFile(s + System.getProperty("line.separator") + "echo \"WS_EXEC_COMPLETE\"",d + cm);
		if (SharedMethods.windows())
		return d + cm;
		return script_runner + " " + d + cm;
	} 

	
public static String execUser(String c,String ac)throws Exception{
	//if (ac == null)return c;
	if (SharedMethods.linux() && ac != null){
	return	getScriptRunner("su - " + ac + " -c '" + c + "'");
	}
	return getScriptRunner(c);
}


	public static String sys (NProcess p,String slog)throws Exception
	{

			String r = "";
			if (p == null)return r;
			synchronized(p){
			if (p.getErrorStream() != null){
			int i = p.getErrorStream().available();
			if (i > 0)
			{
				//(cdir + "ERROR IN JVM STREAM 1 ");
				byte[] b = new byte[i];
				p.getErrorStream().read(b);
				//(cdir + "ERROR IN JVM STREAM 2 " + new String(b));
				try{
					
					if (! new File(slog).exists())
					 Thread.sleep(5000);
					PrintWriter out = new PrintWriter(new FileOutputStream(slog,true));	
					out.print(new String(b));
					r = r + new String(b);
					out.close();

				}catch (Throwable e){
					tools.util.LogMgr.err("JVMClient to system 1 " + e.toString());	
				}
				
			}
			}
			
			if (p.getInputStream() != null){
			int i = p.getInputStream().available();
			if (i > 0)
			{
				byte[] b = new byte[i];
				p.getInputStream().read(b);
				r = r + new String(b);
				
								try{
										/*elseadmin.admin.ContextProperties cp = elseadmin.admin.ContextProperties.get(wac);
					elseadmin.ac.ApplicationComponent apc = elseadmin.ac.ApplicationComponent.get(cp);
					String slog = cp.pdir + "log/system.log";
					if (apc != null && apc.getSysLog(cp) != null)
						slog = apc.getSysLog(cp);*/
						
					PrintWriter out = new PrintWriter(new FileOutputStream(slog,true));	
					//(slog + " SSY OUT " + new String(b).length());
					out.print(new String(b));
					out.close();

				}catch (Throwable e){
					tools.util.LogMgr.err("JVMClient to system 2 " + e.toString());	
					e.printStackTrace();
				}
				
			}
		}
		}	
		return r;
	
	
	}

		public  static NProcess procExec(String args,String cdir)throws Exception{
		return procExec(SharedMethods.quotedVector(args," "),cdir);
	}
		public  static NProcess procExec(EZArrayList args,String cdir)throws Exception{
	NProcess p = null;
	String bu = null;
		//File cf = create();

		File f = null;
		if (cdir != null)
		 f = new File(cdir);
		p = new NProcess(Runtime.getRuntime().exec(args.toStringArray(),null,f),5000,10);
		p.setCommandString(args.toDelimitedString(" "));
		p.trace = false;
		return p;

	}



	static boolean containsToken(Vector v,String c){
		if (v == null)return false;
		if (!StringUtil.isRealString(c))return false;
		for (int ct = 0; ct < v.size();ct++){
			String tok = (String)v.elementAt(ct);
		if(	c.toLowerCase().indexOf(tok.toLowerCase()) > -1)
			return true;
		}
		return false;
	}



		public  static String winExec(EZArrayList args,String cdir,int wa,String tok,boolean tf,int w,Vector toks){
	NProcess p = null;
	String bu = null;
	try{
		//File cf = create();

		File f = null;
		if (cdir != null)
		 f = new File(cdir);
		p = new NProcess(Runtime.getRuntime().exec(args.toStringArray(),null,f),5000,10);
		p.setCommandString(args.toDelimitedString(" "));

		p.trace = false;
	}catch (Throwable e){
		e.printStackTrace();
		LogMgr.debug("winExec Error: " + e.toString());
	}
		//	StatusRefreshPageMgr smr = null;
			String fn = null;
			LongNum  ln = null;
	if (tok == null)tok = "WS_EXEC_COMPLETE";
	for (int ct = 0;ct < wa;ct++)
	{
		try{
			Thread.sleep(1000);
			if (tok != null){
			String c = p.getStreamsString();
			String cf = null;
			if (fn != null)
			{
			cf = FileUtil.getStringFromFile(fn,ln);
			if (c == null)c = "";
			c = c + cf;

			}
			if (StringUtil.isRealString(c)){
				bu = p.getPresentStreamsString();
				if (cf != null){
					if (bu == null)
					 bu = "";
					 bu = bu + cf;
				}


			if (c.toLowerCase().indexOf(tok.toLowerCase()) > -1 || containsToken(toks,c) || tf){
				//LogMgr.debug(c + " osExec BREAK " + tok + ":" + tf);
				break;
			}
		}
		}
			//sys();

		}catch(Throwable e){
		}
	}

	try{
		//p.destroy("exit");
		DestroyNoWait dnw = new DestroyNoWait(p);
		dnw.start();

	for (int ct = 0;ct <w;ct++)
	if (!dnw.fin)
	{
		Thread.currentThread().sleep(1000);
	}
	else
		break;


	}catch(Throwable e){
	LogMgr.debug("winExec Exit Error: " + e.toString());

		e.printStackTrace();
	}

	return bu;

	}









static void logn(String c){
logn(c,"");
}


static void logn(String c,String id){
try{
	synchronized (lock){
		Template t = new Template("/usr/appcloem/admin/data/AllappcloemCommands",true);
		if (t.length() > 10000){

			t.reset();
		}
		t.append(new Date() + "---------------------" + id +System.getProperty("line.separator"));
		t.append(c);
		t.append("END *********************"+System.getProperty("line.separator"));
		t.store();
	}
	}catch (Throwable e){
		LogMgr.err("NDemon.logn " + e.toString());
		}
}


static void log(String c){
	log(c,"");
}

static void log(String c,String id){
try{
	synchronized (lock){
		Template t = new Template("/usr/appcloem/admin/data/FailedappcloemCommands",true);
		if (t.length() > 10000){
			t.reset();
			//("CLEARED LOG " + c);
		}
		t.append(new Date() + "---------------------" + id +System.getProperty("line.separator"));
		t.append(c);
		t.append("END *********************"+System.getProperty("line.separator"));
		t.store();
	}
	}catch (Throwable e){
		LogMgr.err("NDemon.log " + e.toString());
		}
}
public static  void add(String c)throws java.io.IOException{
	exec(c);
}


public static String parsePID(String p,String pall){
		EZArrayList ez = new EZArrayList(new ByteArrayInputStream(pall.getBytes()));
		//("parsePID SIZE " + ez.size());
		String s = null;
		while (ez.size() > 0)
		{
			s = parsePID((String)ez.elementAt(0));
			if (s != null)return s;
			ez.removeElementAt(0);
		}
		return null;
	}

public static String parsePID(String p){
	String nm = p;
	String ls = System.getProperty("line.separator");
	int i = nm.indexOf(ls);
	if (i > -1)nm = nm.substring(0,i).trim();
	if (nm.indexOf("grep") > -1)
	{
		//(nm + " parsePID NO GOOD PARIDE DE " + p);
		return null;
	}
	i = nm.indexOf("root");
	if (i < 0)
	{

		//(nm + " parsePID NO GOOD ROOT " + p);
		return null;
	}

	nm = nm.substring(i + "root".length()).trim();
	String nm2 = "";
					for (int ct = 0;ct < nm.length();ct++)
					{
						//("PERR 3 " + nm);
						if (AlphaNumeric.startsWithNumber(nm))
						{
							nm2 = nm2 + nm.substring(0,1);
							nm = nm.substring(1,nm.length());
						}
						else
							break;

					}

		//(nm2 + " parsePID RESULT " + p);
	return nm2;

}

static String verifyC(String c){
		if (StringUtil.isRealString(c))
		if (c.indexOf(" ") > 0){
			if (c.indexOf(";") > 0)
			{
				//elseadmin.helper.OwnerNotifier.alert("verifyC " + c);

				//c = StringUtil.replaceSubstring(c,";","");

			}
		}
		return c;

	}

	public static  void exec(String c)throws java.io.IOException{
		exec(c,true);
	}
public static  void exec(String c,boolean tf)throws java.io.IOException{
	if (!SharedMethods.linux())return;
	if (tf)
	c = verifyC(c);
	File f = create();
	//(f + " NDEXEC: " + c);
	if (c.indexOf("\r\n") > -1 && !System.getProperty("line.separator").equals("\r\n"))
		c = StringUtil.replaceSubstring(c,"\r\n",System.getProperty("line.separator"));
	c = c +System.getProperty("line.separator");
	c = c + "touch /usr/appcloem/admin/appcloemdemonends/" + f.getName() + "\n";
	logn(c,f.getName());
	addToQueue(f,c);
	try{
		for (int ct = 0;ct < 10;ct++)
		{

			if (new File("/usr/appcloem/admin/appcloemdemonends/" + f.getName()).delete())
				return;
			else
			{
				Thread.sleep(1000);
			}
		}
		log(c,f.getName());
	}catch (InterruptedException e){
	}
}

public static  void execWait(String c)throws Exception{
	execWait(c,120);
}


public static  void execNoWait(String c,int tr)throws Exception{
	new ExecNoWaitEx(c,tr).start();
}

public static  void execWait(String c,int tr)throws Exception{
	if (true)
	throw new Exception("appcloemdemon no longer supported!");
//	execWait(c,tr,null);
//}
//public static  void execWait(String c,int tr,ContextProperties cp)throws Exception{
	if (!SharedMethods.linux())return;
	c = c + "\n";
	File f = createB();
	c = c + "touch /usr/appcloem/admin/appcloemdemonbackgroundends/" + f.getName() + "\n";
	//(c);
	logn(c);

		if (c.indexOf("\r\n") > -1 && !System.getProperty("line.separator").equals("\r\n"))
		c = StringUtil.replaceSubstring(c,"\r\n",System.getProperty("line.separator"));

	//("execWait " + c);
	addToQueue(f,c);


			//	StatusRefreshPageMgr smr = null;
			String fn = null;
			LongNum  ln = null;
	/*if (cp != null)
	try{
		if (cp.attributes.remove("show_sys_status") != null)
		smr = (StatusRefreshPageMgr)cp.attributes.get("StatusRefreshPageMgr");
		fn = (String)cp.attributes.remove("output_file");
		if (fn != null)
		 ln = new LongNum();


	}catch (Throwable e){
		LogMgr.err("execW STATUS CP " + e.toString());
	}*/





			for (int ct = 0;ct < tr;ct++)
		{


			String cf = null;
			if (fn != null)
					try{
			cf = FileUtil.getStringFromFile(fn,ln);
			//if (cf != null && smr != null)

		//smr.setMsg(cf);
	}catch (Throwable e2){
		LogMgr.err("execWaitSMR " + e2.toString());
	}

		if (new File("/usr/appcloem/admin/appcloemdemonbackgroundends/" + f.getName()).delete()){
				//(f + " execWait " + c + ":" + tr + ":" + ct);

				return;

			}
			else
			{
				Thread.sleep(1000);
				//(c +   " EXEC WAIT " + f.getName());
			}
		}
		LogMgr.red(f + " BAD execWait " + c + ":" + tr);

		log(c);
}


public static  String execToTemp(String c)throws Exception{
	return execToTemp(c,120);
}


static void addToQueue(File f,String c){
	synchronized(queue){
	FileUtil.writeTextToFile(f,c);
}
}
public static  String execToTemp(String c,int tr)throws Exception{
	if (!SharedMethods.linux())return null;
	File f = createB();
	c = c + " > /usr/appcloem/admin/appcloemdemonbackgroundends/" + f.getName() +  "_w\n";
	//File f = createB();
	c = c + "touch /usr/appcloem/admin/appcloemdemonbackgroundends/" + f.getName() + "\n";
		if (c.indexOf("\r\n") > -1 && !System.getProperty("line.separator").equals("\r\n"))
		c = StringUtil.replaceSubstring(c,"\r\n",System.getProperty("line.separator"));

	logn(c + "_w");
	addToQueue(f,c);
			for (int ct = 0;ct < tr;ct++)
		{

			if (new File("/usr/appcloem/admin/appcloemdemonbackgroundends/" + f.getName()).delete())
				break;
			else
				Thread.sleep(1000);
		}
		if (!new File("/usr/appcloem/admin/appcloemdemonbackgroundends/" + f.getName() + "_w").exists())LogMgr.red(tr + " NDEMON.execToTemp RESULT FILE NOT FOUND " + c);
		String r = FileUtil.getStringFromFile("/usr/appcloem/admin/appcloemdemonbackgroundends/" + f.getName() + "_w");
		if (!new File("/usr/appcloem/admin/appcloemdemonbackgroundends/" + f.getName() + "_w").delete())
		log(c + "_w");
	return r;
}


	static Object crea = new Object();
public static File create()throws IOException{
	synchronized(crea){
	int ct = 0;

	while (ct++ < maxTries)
	{
		//doCheck();
		File f = new File("/usr/appcloem/admin/appcloemdemoncommands/" + System.currentTimeMillis() + Thread.currentThread().hashCode() + ct++);
		if ( f.createNewFile())return f;
		LogMgr.debug("NDemon Trying to create again " + f);
	}
	return null;
}
}


public static void status(){
	try{
	File f = new File("/usr/appcloem/admin/appcloemdemonbackgroundcommands/");
	String[] fl = f.list();
	//if (fl != null && fl.length > 100)
	//	elseadmin.helper.OwnerNotifier.alert("appcloemdemonbackgroundcommands " + fl.length);
	if (fl != null && fl.length > 5)
	{
		int dc = 0;
		boolean tf = false;
		for (int ct = 0; ct < fl.length;ct++)
			if (System.currentTimeMillis() - new File(f,fl[ct]).lastModified() > 1000*60*5)
			{
				dc++;
				if (!tf){
				//elseadmin.helper.OwnerNotifier.alert(dc + " old background demon command " + fl.length + ":" + fl[ct]);
				tf = true;
			}
				new File(f,fl[ct]).delete();
				//break;
			}


	}
	f = new File("/usr/appcloem/admin/appcloemdemoncommands/");
	fl = f.list();
	//if (fl != null && fl.length > 100)
	//	elseadmin.helper.OwnerNotifier.alert("appcloemdemoncommands " + fl.length);
	if (fl != null && fl.length > 5)
	{
		int dc = 0;
		boolean tf = false;
		for (int ct = 0; ct < fl.length;ct++)
			if (System.currentTimeMillis() - new File(f,fl[ct]).lastModified() > 1000*60*5)
			{
				dc ++;
				if (!tf){
				//elseadmin.helper.OwnerNotifier.alert(dc + " old demon command "  + fl.length + ":" + fl[ct] );
				tf = true;
			}
				new File(f,fl[ct]).delete();
				//break;
			}


	}

	f = new File("/usr/appcloem/admin/appcloemdemonbackgroundends/");
	fl = f.list();
	//if (fl != null && fl.length > 100)
	//	elseadmin.helper.OwnerNotifier.alert("appcloemdemonbackgroundends " + fl.length);
	if (fl != null && fl.length > 10)
	{
		boolean alert = false;
		int dc = 0;
		for (int ct = 0; ct < fl.length;ct++)
			if (System.currentTimeMillis() - new File(f,fl[ct]).lastModified() > 1000*60*15)
			{
				dc ++;
				if (!alert){
					//elseadmin.helper.OwnerNotifier.alert(elseadmin.util.LanguageMgr.get("Problem_executing_system_commands"));
					LogMgr.red(dc + ":" + fl.length + " old background ends " + new Date(new File(f,fl[ct]).lastModified()) + ":" + fl[ct]);
					alert = true;
				}
				else
					LogMgr.red(fl.length + " old background ends " + new Date(new File(f,fl[ct]).lastModified()) + ":" + fl[ct]);

				new File(f,fl[ct]).delete();
				//break;
			}


	}

	//status2();
	}catch (Throwable e){
		LogMgr.err("NDemon.status " + e.toString());
	}

}

	static Object creab = new Object();


public static File createB()throws IOException{
	synchronized(creab){
	int ct = 0;

	while (ct++ < maxTries)
	{
		//doCheck();
		File f = new File("/usr/appcloem/admin/appcloemdemonbackgroundcommands/" + System.currentTimeMillis() + Thread.currentThread().hashCode() + ct++);
		if ( !f.exists() && f.createNewFile())return f;
		try{
		Thread.sleep(2);
		}catch(Throwable e){}
		LogMgr.debug("NDemon Trying to create appcloemdemonbackgroundcommands again ");
	}
	return null;
}

}

	public static Hashtable pids = new Hashtable();
	static String pDir = "./";

	/*public static void start(EZArrayList args,String context)throws Exception{

		String vpc = appcloem.modules.sitemgr.VPSMgr.getExec(context,args.toDelimitedString(" "));
		if (vpc != null)
		 args = SharedMethods.toVector(vpc," ");
		boolean perr = false;
		StringBuffer buf = new StringBuffer();
		StringBuffer cDir = new StringBuffer(pDir).append(context).append(File.separator);
		ContextProperties cp = ContextProperties.get(context);
		elseadmin.ac.ApplicationComponent apc = elseadmin.ac.ApplicationComponent.get(cp);
		boolean isL = cp.group.linux;
		if (!isL && ElseProperties.linux && elseadmin.context.Group.getAttributeBoolean(cp.group,"no_legacy"))
		 isL = true;
		if (ContextJvmMgr.getCustomApp(context) != null)
		{
			//isL = false;
			if( ContextProperties.getAdminProp(cp,"CustomAppServerHomeDir") != null && ContextProperties.getAdminProp(cp,"CustomAppServerHomeDir").length() > 1)
				cDir = new StringBuffer(ContextProperties.getAdminProp(cp,"CustomAppServerHomeDir"));
		}
		new File(cp.pdir + "log/system.log").createNewFile();

		if (isL && vpc == null)
			buf.append("chown " +  context + " " + cp.pdir + "log/system.log"+System.getProperty("line.separator")).append("su - ").append(context).append(" -c ").append("\"");
		buf.append(args.toDelimitedString(" "));

		if (isL && vpc == null)
		buf.append("\"");

		String prci = buf.toString();
		if (apc != null && apc.getSysLog(cp) != null)
			buf.append(" >>" + apc.getSysLog(cp));
		else{
			buf.append(" > ");
			buf.append(cDir.toString()).append("plog");
		}
		buf.append(" < ");
		buf.append(cDir.toString()).append("plog");

		if (apc == null || !apc.noDevNull(cp))
		buf.append(" >/dev/null");
		buf.append(" 2>&1 &");
		if (!ElseProperties.linux)
		{
			String nc = buf.toString();
			nc = StringUtil.replaceSubstring(nc,"C:\\","/usr/");
			nc = StringUtil.replaceSubstring(nc,"c:\\","/usr/");

			nc = StringUtil.replaceSubstring(nc,";",":");
			nc = StringUtil.replaceSubstring(nc,"JDK1.3","java/jdk1.3");
			nc = StringUtil.replaceSubstring(nc,"\\","/");
			nc = StringUtil.replaceSubstring(nc,"JRE","jre");
			nc = StringUtil.replaceSubstring(nc,".exe","");
			buf = new StringBuffer(nc);
		}
		if (!isL)
			buf = new StringBuffer("cd " + cDir.toString() +System.getProperty("line.separator") + buf.toString());
		if (! new File(cDir.toString() + "plog").exists()){
			new File(cDir.toString()+ "plog").createNewFile();
			}
		exec(buf.toString());
		("EXEC " + buf.toString());
		pids.put(context,prci);
	}

	public static String stop(String context)throws Exception{
		String ps = (String)pids.remove(context);
		String p = ps;

		if (p != null)
		{
			p = execToTemp("ps -ef -w e --cols 1000 | grep \"" + StringUtil.replaceSubstring(p,"\"","") + "\"");

			p = parsePID(p);
			if (p == null){
				String pall = execToTemp("ps -ef -w e --cols 1000");

				p = parsePID(ps,pall);
			}
			if (p == null)
			{
				LogMgr.debug(" NO STOP PID FOR "  + context);
				return null;
			}
			int i = p.indexOf(" ");
			if (i > 0)
				p = p.substring(i + 1,p.length());
			try{
			Integer.parseInt(p);
			if (Integer.parseInt(p) == 1)
			LogMgr.red("Cannot kill Process 1");
			else
			 exec("kill " + p);
			}catch (Throwable e){
			elseadmin.helper.OwnerNotifier.alert( " BAD KILL 4 " + p);

			}
			LogMgr.debug(context + ":" + p + " Kill Context: ");

		}

		return p;

	}





	public static void killPIDs(String tt){
		if (!ElseProperties.linux)return;
		try{
			ContextProperties cp = ContextProperties.get(tt);
			if (!cp.group.linux)return;
			//("KILL PIDS " + tt);
			String fi = execToTemp("ps  -u " + tt);
			EZArrayList ez = new EZArrayList();
			ez.read(new ByteArrayInputStream(fi.getBytes()));
			//noKill.addEnumeration(ez.elements(),noKill);
			for (int ct = 0;ct < ez.size();ct++)
			{
			try
			{
				String ln = (String)ez.elementAt(ct);
				int i = ln.indexOf(" ");
				if (i > 0)
				{
					String pi = ln.substring(0,i);
					if (elseadmin.util.AlphaNumeric.startsWithNumber(pi) && (ln.indexOf("java") > -1 || ln.indexOf("mono") > -1))
					{
									if (pi.equals("1"))
			LogMgr.red("Cannot kill Process 1 B");
			else{
						exec("kill " + pi);
						Thread.sleep(3000);
						fi = execToTemp("kill -9 " + pi);
						LogMgr.red(fi + "killPIDS RESPONSE " + pi + ":" + tt);
					}	if (fi != null && fi.indexOf("No such pid") > -1)break;
					}

				}
				}catch (Throwable e){
					LogMgr.err(tt +" killPID " + e.toString());
				}
			}


		}catch (Throwable e){
			e.printStackTrace();
			LogMgr.err("killPIDs " + e.toString());
		}

		try{
			//("killwebpids.sh 1 " + tt);
		execWait("/usr/appcloem/admin/scripts/killwebpids.sh " + tt);//, "/usr/appcloem/admin/scripts/" + tt + "_execpid");
		}catch (Throwable e){
			e.printStackTrace();
			LogMgr.err("killPIDs FINAL " + e.toString());
		}


	}



	private static void status2(){
		try{

		Hashtable mpid = (Hashtable)pids.clone();
		Enumeration pl = mpid.keys();
		String c = null;
		String p = null;
			NameValuePairs dp = new NameValuePairs(ElseProperties.dataDir + "DeadProcess");
			NameValuePairs cdp = new NameValuePairs(ElseProperties.dataDir + "CheckDeadProcess");
		String pall = execToTemp("ps -ef -w e --cols 1000");
		LogMgr.red("Start Telnet Client Status " + mpid.size());
		int cter = 0;
		while (pl.hasMoreElements())
		{
		try{
			cter++;

			c = (String)pl.nextElement();
			String ps = (String)mpid.get(c);
			//(cter + "do Telnet Client Status " + c + ":" + ps);

			p = ps;
			p = execToTemp("ps -ef -w e --cols 1000 | grep \"" + StringUtil.replaceSubstring(p,"\"","") + "\"");

			p = parsePID(p);
			if (p == null)p = parsePID(ps,pall);
			if ((p == null || ! new File("/proc/" + p).exists() ) && pids.get(c) != null)
			{
				LogMgr.red(c + " Dead Process " + p);
			}
			else
			{

				if (cdp.getInt(c) < 3)
				{
					int cdpv = cdp.getInt(c);
					cdpv = cdpv + 1;
					cdp.put(c,cdpv);
					cdp.store();
				}
			else if (dp.remove(c) != null)
				dp.store();
			}

		}catch (Throwable e){
			LogMgr.debug("NDemon.status Error " + e.toString());
		}

		}
			//LogMgr.red("*** END Telnet Client Status " + mpid.size());
		}catch (Throwable e){
		LogMgr.debug("NDemon.status General Error " + e.toString());

		}

	}*/


}



class ExecNoWaitEx extends ThreadEx{
String comm = null;
int wai = 0;
	public ExecNoWaitEx(String c,int w){
		comm = c;
		wai = w;
	}

	protected void exec() throws Exception{
		NDemon.execWait(comm,wai);
	}

}



class DestroyNoWait extends ThreadEx{
NProcess p = null;
	public DestroyNoWait(NProcess pr){
		p = pr;
	}

	protected void exec() throws Exception{
		p.destroy("exit");
	}

}

