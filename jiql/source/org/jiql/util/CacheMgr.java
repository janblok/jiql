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
import org.jiql.jdbc.ResultSet;



public  class CacheMgr
{

	static Hashtable<String,Hashtable<String,Hashtable<String,org.jiql.jdbc.ResultSet>>> mcaches = new Hashtable<String,Hashtable<String,Hashtable<String,org.jiql.jdbc.ResultSet>>>();
	

	public static  org.jiql.jdbc.ResultSet getMetaCache(String url,String meta,String table){
		Hashtable<String,Hashtable<String,org.jiql.jdbc.ResultSet>> h1 = mcaches.get(url);
		if (h1 == null)return null;
		
		Hashtable<String,org.jiql.jdbc.ResultSet> h = h1.get(meta);
		if (h == null)return null;
		org.jiql.jdbc.ResultSet r = h.get(table);
		if (r == null)return null;
		r.reset();
		return r;
//		return null;
	}
	
		public static  void removeMetaCache(String url,String table){
		Hashtable<String,Hashtable<String,org.jiql.jdbc.ResultSet>> h1 = mcaches.get(url);
		if (h1 == null)return ;
		
		Enumeration<String> metas = h1.keys();
		while (metas.hasMoreElements()){
		
		Hashtable<String,org.jiql.jdbc.ResultSet> h = h1.get(metas.nextElement());
		if (h != null)
		h.remove(table);
		}


	}

	public static  void setMetaCache(String url,String meta,org.jiql.jdbc.ResultSet r,String mc,String table){
		
		if (mc == null)return;
		if (!mc.equals("true"))return;
		if (r == null)return;

		Hashtable<String,Hashtable<String,org.jiql.jdbc.ResultSet>> h1 = mcaches.get(url);
		if (h1 == null){
			h1 = new Hashtable<String,Hashtable<String,org.jiql.jdbc.ResultSet>>();
			mcaches.put(url,h1);
		}


		Hashtable<String,org.jiql.jdbc.ResultSet> h = h1.get(meta);
		if (h == null)
		{
			h = new Hashtable<String,org.jiql.jdbc.ResultSet>();
			h1.put(meta,h);
		}
		h.put(table,r);
	
	}



}