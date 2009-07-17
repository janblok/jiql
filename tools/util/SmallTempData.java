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

public class SmallTempData extends TempData  implements Externalizable{

	transient ByteArrayInputStream in = null;
	transient ByteArrayOutputStream out = new ByteArrayOutputStream();
	
	public SmallTempData(){
	
	}
	public void delete()throws IOException{
		if (out != null)
			out.close();
		if (in != null)
			in.close();
		in = null;
		out = null;	
		size = -1;
	}
	
	public void add(int b)throws IOException
	{
		out.write(b);
		check(1);
	}
	
	public void close()throws IOException{
		if (out != null)
		{
			flush();
			out.close();
		}
	}
	
	public void flush()throws IOException{
		if (out != null)out.flush();
	}
	
	
	public void add(byte[] b)throws IOException
	{
		out.write(b);
		check(b.length);
	}
	
	public void add(byte[] b,int off,int len)throws IOException
	{
		out.write(b,off,len);
		check(len);
	}
	
	
	public void copy(java.io.OutputStream outs)
	throws IOException{
	if (size > 0 && in == null)
	{
		InputStream fin = new ByteArrayInputStream(out.toByteArray());
		StreamUtil.write(fin,outs);
		fin.close();
	}

}
	
public int available()throws java.io.IOException{
	if (in == null)return size;
	return get().available();
}
	
	public InputStream get()throws IOException
	{
		if (in == null) 
		{
			in = new ByteArrayInputStream(out.toByteArray());
			out.close();
			out = null;
		}
		return in;
	}
	
	
	public InputStream getCopy()throws IOException
	{
			if (out == null)throw new IOException("Can't copy Small Stream: Stream not found");
			return new ByteArrayInputStream(out.toByteArray());
	}
	
	
	public void writeExternal(java.io.ObjectOutput outs)
throws IOException{
	//if (size > 0 && in == null)
	{
		byte[] fin = null;
		if (in != null && in.available() > 0)
		{
			fin = new byte[in.available()];
			in.read(fin);
		}
		else fin = 	out.toByteArray();
		outs.writeInt(fin.length);
		if (fin.length > 0)
		{
			//(new Byte(bt).intValue() + " WRE 2 " + (byte)fin.length );
			outs.write(fin);
		}
	}
	
}


public void readExternal(java.io.ObjectInput oin)
	throws IOException,ClassNotFoundException{
	size = oin.readInt();
	out = new ByteArrayOutputStream();

	if (size > 0)
	{
	StreamUtil.readStreamTo(oin,out,size);
	}
	
}
	
	
	

}