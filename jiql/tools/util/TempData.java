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
import java.io.*;
import java.util.*;

/**
 * This class is used for common methods.
 **/
public abstract class TempData implements   Serializable
{
	//public NameValuePairs attributes = new NameValuePairs(0);
	int size = 0;
	public int maxData = 1000000;
	static{
		try{
		}catch (Throwable e){
			tools.util.LogMgr.err("TempData init Error " + e.toString());
		}
	}


	public int size(){
		return size;
	}
	
	static int block = 8;
	
	public void flush()throws IOException{
	}
	
	public int addNoCount(InputStream cin,int l)throws java.io.IOException
	{
		int r = 0;
	int ct = block;
	if (ct > l)ct = l;
	byte[] decd1 = new byte[ct];
	int di = cin.read(decd1);
	if (di > 0)
	r+=di;
	    while (di != -1) {
			tools.util.LogMgr.debugger("deber LOOP 3 : " + hashCode());
	        add(decd1,0,di);
			if (ct + block <= l)
			ct+=block;
			else if (ct  < l)
			{
				decd1 = new byte[l - ct];
				ct+=decd1.length;	
			}
			else
				return r;
			
	        di = cin.read(decd1);
			if (di > 0)
			r+=di;
	    }
		return r;
	
	}
	
	
	public void add(Reader cin,int l)throws java.io.IOException
	{
	int ct = block;
	if (ct > l)ct = l;
	char[] decd1 = new char[ct];
	int di = cin.read(decd1);
	    while (di != -1) {
			tools.util.LogMgr.debugger("deber LOOP 4 : " + hashCode());
	        add(decd1,0,di);
			if (ct + block <= l)
			ct+=block;
			else if (ct  < l)
			{
				decd1 = new char[l - ct];
				ct+=decd1.length;	
			}
			else
				return;
			
	        di = cin.read(decd1);
	    }
	
	}
	
	public void add(InputStream din)throws IOException
	{
		
		add(din,din.available());
	}	
	static int MaxCHUNK = 20480;
	public void add(InputStream din,int len)
	{
		
		        byte[] rec = null;//new byte[0];
				int chunkSize = 0;
				int readCount = 0;
				int cter = 0;
				int readLen = -1;
				
				int loc = 0;
		try
		{
	    if (len > 0)
	    {
	    int rtry = 0;
		for(readCount = 0; readCount < len; )
	    {
	        chunkSize = len - readCount;
	        if (chunkSize > MaxCHUNK)
	            chunkSize = MaxCHUNK;
	        
			if (din.available() < chunkSize) 
			{
				Thread.sleep(1000);
				chunkSize = din.available();
			}
			loc = 0;
			rec = new byte[chunkSize];
			loc = 1;
				readLen = din.read(rec,0,chunkSize);
				loc = 2;
	        if (readLen < 1) {
	            rtry++;
				loc = 3;
				Thread.sleep(1000);
	            if (rtry > 30)
				{
					System.out.println("Read Temp Data Timeout");
					break;
				}
	            continue;
	        }
			else 
			{
			loc = 4;	
				add(rec);
				loc = 5;
				cter++;
	        	rtry = 0;
	        	readCount += readLen;
			}
			loc = 6;
	    }
	    }
		
		}
		catch (Throwable e)
		{
			tools.util.LogMgr.err(readCount + " READ TEMPDATA STREAM ERROR 1 " +  len + ":" + chunkSize + ":" + rec.length + ":" + cter + ":" + loc + ":" + readLen + ":" + size);
			e.printStackTrace();
	}
	}

	
	
	
public static TempData create(File f)throws IOException{
		return 	new LargeTempData(f);
}
	
	
public static TempData create(int l)throws IOException{
	return create(l,50000000);
}


public static TempData create(int l,int ml)throws IOException{
	if (l < 1000000)
	return new SmallTempData();
	else
		return 	new LargeTempData(ml);
}

public static TempData create(byte[] l)throws IOException{
	TempData tp = null;
	if (l.length < 1000000)
	tp =  new SmallTempData();
	else
		tp = 	new LargeTempData();
	tp.add(l);
	return tp;	
}


public static TempData create(InputStream l)throws IOException{
	TempData tp = null;
	if (l.available() < 1000000)
	tp =  new SmallTempData();
	else
		tp = 	new LargeTempData();
	tp.add(l);
	return tp;	
}

public static TempData createSmall(byte[] l)throws IOException{
	TempData tp = new SmallTempData();
	tp.add(l);
	return tp;	
}


	public void add(char[] b)throws IOException
	{
		for (int ct = 0; ct < b.length;ct++)
			add(b[ct]);
	}

	public void add(String b)throws IOException
	{
			add(b.getBytes());
	}

	public void add(char[] b,int ofs,int len)throws IOException
	{
		for (int ct = ofs; ct < ofs + len;ct++)
			add(b[ct]);
	}


	public  byte[] getBytes()throws java.io.IOException
	{
		InputStream inp = get();
		byte[] b = new byte[inp.available()];
		inp.read(b);
		return b;
	}
	
	
	public Reader getReader()throws IOException{
		return new InputStreamReader(get());
	}
	
	public Object getHandle()throws IOException{
		return null;
	}
	
public abstract int available()throws java.io.IOException;
	

public abstract void close()throws IOException;
	public abstract void add(int b)throws IOException;
	public abstract void add(byte[] b)throws IOException;
	public abstract void add(byte[] b,int off,int len)throws IOException;
	public abstract InputStream get()throws IOException;
	public abstract InputStream getCopy()throws IOException;
	public abstract void delete()throws IOException;
	public abstract void copy(OutputStream oc)throws IOException;
	
	
	void check(int l)throws IOException{
		size+=l;
		if (maxData > 0 && size > maxData)throw new IOException(size + " TempData TOO Large! " + maxData);
	}
}


