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
package tools.io;




import java.io.*;
import java.util.*;
import tools.util.*;



public class TempDataOutputStream extends OutputStream
{
	public static boolean debug = false;
	TempData m_buffer = new SmallTempData();
	String tempFile = null;
	String tempID = null;
	int ml = 90000000;
	public TempDataOutputStream(String tid){
		tempID = tid;
	}

	public TempDataOutputStream(){

	}

	public TempDataOutputStream(int l){
		this();
		ml = l;
	}


		public TempDataOutputStream(int l,boolean tf)
	throws 	java.io.IOException
	{
		this(l);
		if (tf)
		{
				tempFile = getFile();
				m_buffer = new LargeTempData(tempFile,ml);
		}

	}


	public  int length()
	{
		return m_buffer.size();
	}

	public TempData getTempData(){
		return m_buffer;
	}
	public  byte[] getBytes()throws java.io.IOException
	{
		return m_buffer.getBytes();
	}


	public  void close()
		throws java.io.IOException
  {
  			//if (debug)
			//System.out.println(m_buffer + " CLOSE " + hashCode());

			if (m_buffer != null)
			m_buffer.close();
  }

  public  void reset()
{
  		try{
		if (m_buffer != null)
		m_buffer.delete();
		}catch ( java.io.IOException e){
			LogMgr.err("TempDataOutStream.reset " + e.toString());
		}finally{
		m_buffer = null;
		tempFile = null;

		}
}

	public boolean flush = false;
	public  void flush()
	throws 	java.io.IOException
	{

		//if (debug)
		//System.out.println("FLUSH " + hashCode());
		if (flush && m_buffer != null)
		{
			m_buffer.flush();
			//checkBuffer();
		}
  }
 static int diskCache = 100000; 
	private void checkBuffer(int l)
	throws 	java.io.IOException
	{
		if (m_buffer == null)
		m_buffer = new SmallTempData();
		if (tempFile == null)
		if (l + m_buffer.size() >= diskCache)
		{
			synchronized (this){
				TempData tp = m_buffer;
				tempFile = getFile();
				m_buffer = new LargeTempData(tempFile,ml);
				m_buffer.add(tp.get(),tp.size());
				tp.delete();
			}

		}

	}


	 String getFile()throws IOException{
	 	if (tempID == null)
			return TempFiles.create().toString();



		StringBuffer outid = new StringBuffer(System.getProperty("context.dir")).append( "private" ).append( File.separator).append("temp").append(File.separator);
		if (!new File (outid.toString()).exists())
			new File (outid.toString()).mkdirs();
		outid.append("javaoutput_").append(tempID);
		return outid.toString();
	}


	  	public  void write(byte[] x)
	throws 	java.io.IOException
  {

  		checkBuffer(x.length);
		m_buffer.add(x);

  }


  	public  void write(byte[] buf, int off, int len)
	throws 	java.io.IOException
  {
  	checkBuffer(len);
		m_buffer.add( buf,off,len);



  }

	public  void write(int x)
			throws 	java.io.IOException

  {
  		checkBuffer(1);
		m_buffer.add( x);


  }




}