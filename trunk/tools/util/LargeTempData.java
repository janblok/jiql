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

public class LargeTempData extends TempData implements Externalizable{

	FileInputStream in = null;
	FileOutputStream out = null;
	File file = null;
	public LargeTempData()throws IOException{
		this(50000000);
	}
	
	public String toString(){
		return "LargeTempData " + size();
	}
	
	public LargeTempData(int ml)throws IOException{
		file = TempFiles.create();
		out = new FileOutputStream(file);
		maxData = ml;
	}
	
	public Object getHandle()throws IOException{
		flush();
		return file;
	}
	
	
	public void flush()throws IOException{
		if (out != null)out.flush();
	}
	
	/*public LargeTempData(String f)throws IOException{
		this(f,false);
	}*/
	
	public LargeTempData(String f,int ml)throws IOException{
		file = new File(f);
		file.createNewFile();
		out = new FileOutputStream(file);
		maxData = ml;
	}
	
	public LargeTempData(String f)throws IOException{
		this(f,50000000);
	}
	
	
	public LargeTempData(File f,int mx)throws IOException{
		maxData = mx;

		file = f;
		size = (int)f.length();
		if (maxData > 0 && size > maxData)throw new IOException(size + " LargeTempData TOO Large! " + maxData);
		
		in = new LTDFileInputStream(file);
		
	}
	
	
	public LargeTempData(File f)throws IOException{
		this(f,50000000);
	}
	
	
	public void writeExternal(java.io.ObjectOutput outs)
throws IOException{

	//if (size > 0 && in == null)
	FileInputStream fin = null;
	try{
		fin = new FileInputStream(file);
		outs.writeInt((int)file.length());
		StreamUtil.write2(fin,outs);
		fin.close();
	}catch (Throwable e){
		e.printStackTrace();
		throw new IOException(file + " LargeTempData.writeExternal " + file.length() + ":" + e.toString() );
	}
	finally{
		if (fin != null)
			fin.close();
	}
	
}

	public void copy(java.io.OutputStream outs)
throws IOException{
	if (size > 0 && in == null)
	{
		FileInputStream fin = new FileInputStream(file);
		StreamUtil.write(fin,outs);
		fin.close();
	}

}

public int available()throws java.io.IOException{
	if (in == null)return size;
	return get().available();
}

public void readExternal(java.io.ObjectInput oin)
	throws IOException,ClassNotFoundException{
	try{
	size = oin.readInt();
	file = TempFiles.create();
	out = new FileOutputStream(file);
	StreamUtil.readStreamTo2(oin,out,size,"LargeTempData.readExternal");
	}catch (Throwable e){
		//e.printStackTrace();
		System.out.println(" ERROR: LargeTempData.readExternal ");
		
		System.out.println(oin.available() + " ERROR: LargeTempData.readExternal " + size + ":" + file.length());
		throw new IOException(" ERROR: LargeTempData.readExternal " + e.toString());
	}
	
	
}
	
	public void delete()throws IOException{
		if (out != null)
			out.close();
		if (in != null)
			in.close();
		file.delete();	
		out = null;
		in = null;
		size = -1;
		
	}
	
	public void close()throws IOException{
		if (out != null)
		{
			flush();
			out.close();
		}
	}
	
	public void add(byte[] b)throws IOException
	{
		out.write(b);
		check(b.length);
	}
	
	public void add(int b)throws IOException
	{
		out.write(b);
		check(1);
	}
	
	public void add(byte[] b,int off,int len)throws IOException
	{
		out.write(b,off,len);
		check(len);
	}
	
	
	public InputStream get()throws IOException
	{
		if (in == null) 
		{
			out.close();
			out = null;
			
			in = new LTDFileInputStream(file);
		}
		
		return in;
	}
	
	public InputStream getCopy()throws IOException
	{
			return new FileInputStream(file);
	}
	
	

}

