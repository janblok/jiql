/*
 Copyright (c) 2007-2009 WebAppShowCase DBA Appcloem (http://www.appcloem.com). All rights reserved.
Author: Gabriel Wong
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
import tools.util.*;
import java.io.*;
import java.util.*;

/**
 * This class is used for common methods.
 **/
public class TempFiles
{
	public static NameValuePairs locked = new NameValuePairs();
	static String tmpDir = null;
	static int maxTries = 10;
	static int toDelete = 10;
	static int triesToDelete = 0;
	static long maxAge = 60*60*1000L;
	static{
		//tmpDir = EZJProperties.privateDir + "temp" + File.separator;
		try{
		//FileUtil.deleteAll(tmpDir);
		}catch (Throwable e){
			tools.util.LogMgr.err("TempFile init Error " + e.toString());
		}
		new File(tmpDir).mkdirs();
	}






	public synchronized static void check(){
	try{
	//triesToDelete++;
	//if (triesToDelete == toDelete)
	{
		//triesToDelete = 0;
		File f = new File(tmpDir);
		String[] fl = f.list();
		if (fl != null)
		{
			if (fl.length > 0)
				System.out.println("Open TempFiles: " + fl.length);
			for (int ct = 0;ct < fl.length;ct++)
			{
				File ff = new File(tmpDir + fl[ct]);
				if (System.currentTimeMillis() - ff.lastModified() > maxAge)
					try{
						if (!locked.getBoolean(fl[ct]))	
						{
						System.out.println(ff.length() + ":" + new Date(ff.lastModified()) + ":" + new Date() + " DELETED OLD TEMP FILE " + ff.delete());
						ff.delete();
						}
						else
						System.out.println(new Date(ff.lastModified()) + ":" + new Date() + " LOCKED OLD TEMP FILE " + ff.delete());
						
					}catch (Throwable e){
						System.out.println("Error deleting Old temp file " + e.toString());
					}
			}
		}
	
	}
	}catch (Throwable e){
		System.out.println("TempFiles.check ERROR: " + e.toString());
	} 
	}



static Object dc = new Object();

  static void doCheck()throws IOException{
	synchronized(dc){
	triesToDelete++;
	if (triesToDelete == toDelete)
	{
		triesToDelete = 0;
		File f = new File(tmpDir);
		String[] fl = f.list();
		if (fl != null)
			for (int ct = 0;ct < fl.length;ct++)
			{
				File ff = new File(tmpDir + fl[ct]);
				if (System.currentTimeMillis() - ff.lastModified() > maxAge)
					try{
						if (!locked.getBoolean(fl[ct]))
						{
						System.out.println(ff.length() + ":" + new Date(ff.lastModified()) + ":" + new Date() + " doCheck.DELETED OLD TEMP FILE " + ff.delete());
						ff.delete();
						}else
							System.out.println(new Date(ff.lastModified()) + ":" + new Date() + " doCheck.LOCKED OLD TEMP FILE " + ff.delete());
					}catch (Throwable e){
						System.out.println("Error deleting Old temp file " + e.toString());
					}
			}

	}
}
}


static Object crea = new Object();
public static File create()throws IOException{
	synchronized(crea){
	int ct = 0;
	
	while (ct++ < maxTries)
	{
		doCheck();
		File f = new File(tmpDir + System.currentTimeMillis() + Thread.currentThread().hashCode() + ct++);
		if ( f.createNewFile())return f;
		tools.util.LogMgr.debug("TempFiles Trying to create again ");
	}
	return null;
}
}







}
