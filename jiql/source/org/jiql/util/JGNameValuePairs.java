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
import tools.util.*;
import java.util.Hashtable;
public class JGNameValuePairs extends NameValuePairs
{
	private long kid = 0;
	private String kidn = null;
	private String tn = null;
	String enc = null;
	
		public  JGNameValuePairs ()
	{
		super();
		
	}
		public  JGNameValuePairs (Hashtable fields)
	{
		super(fields);
		
	}
	
		public void setEncoding(String ki){
		enc = ki;
	}
	
	/*public Object get(Object k){
		Object v = super.get(k);

			if (v != null){
				if (v instanceof String){
					try{
						if (k.equals("name") || k.equals("id"))
						{
						 
					System.out.println(k + " WR ENCODE yes  '????' RRR " + v);
					v = new String(v.toString().getBytes(), enc);
					System.out.println(k + " WR ENCODED RRR " + v);
					return v;
						}
					}catch (java.io.UnsupportedEncodingException ue){
					//throw JGException.get("UnsupportedEncodingException","Error writing to table : " + ue.toString());

					} 
				}
			
			}

	
		return v;
	}*/
	
	public void setKeyId(long ki){
		kid = ki;
	}
	public long getKeyId(){
		return kid;
	}

	public void setTableName(String ki){
		tn = ki;
	}
	public String getTableName(){
		return tn;
	}


	public void setKeyName(String ki){
		kidn = ki;
	}
	public String getKeyName(){
		if (kidn != null)
		return kidn;
		if (kid > 0)
			return String.valueOf(kid);
		return null;
	}

}