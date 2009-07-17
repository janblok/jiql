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
 * This class is used as the base SerializableObject object.
 **/

public class EZArrayList extends Vector implements Serializable
{

    private static final long serialVersionUID = 4505319459048894843L;//-111111111112222222L;

	private transient Object 	m_file = null;
	private boolean m_check = false;
	private long m_lastmodified = -1;
	boolean newline = false;

	public EZArrayList()
	{
		super();
	}

	public EZArrayList(Object[] lst)
	{
		super();
		for (int i = 0;i < lst.length;i++)
			add(lst[i]);

			}

			public void addArray(Object[] lst)
			{
				for (int i = 0;i < lst.length;i++)
					add(lst[i]);

			}

			public EZArrayList(StringTokenizer lst)
			{
				this(lst,false);
			}
			public EZArrayList(StringTokenizer lst,boolean trm)
			{
				super();
				while (lst.hasMoreTokens())
				{
					String a = lst.nextToken().trim();
					if (trm)
						a = StringUtil.getTrimmedValue(a);
					if (a.length() > 0)
					add(a);

				}
					}

			public EZArrayList(Enumeration lst)
			{
				super();
				while (lst.hasMoreElements())
				{
					Object a = lst.nextElement();
					add(a);

				}
			}

			boolean unique = false;
			public void setUnique(boolean tf){
				unique = tf;
			}
			public void addEnumeration(Iterator lst)
			{
				while (lst.hasNext())
				{
					Object a = lst.next();
					add(a);

				}
			}
	
				public void addEnumeration(List lst)
			{
				addEnumeration(lst.iterator());
			}		


			public static void addEnumeration(Enumeration lst,EZArrayList ez)
			{
					addEnumeration(lst,ez,true);
			}

			public static void addEnumeration(Enumeration lst,EZArrayList ez,boolean alldp)
			{
				while (lst.hasMoreElements())
				{
					Object a = lst.nextElement();
					//("addEnumeration " + a);
					if (!(!alldp && ez.contains(a)))
					ez.add(a);

				}
			}


			public Vector sortedList(){
				String[] b = toStringArray();
				Arrays.sort(b,String.CASE_INSENSITIVE_ORDER);
				Vector v = new Vector();
				for (int ct = 0;ct < b.length;ct++)
					v.add(b[ct]);
				return v;
			}

			public Object[] toArray()
			{
				Object[] lst = new Object[size()];
				for (int i = 0;i < lst.length;i++)
					lst[i] = elementAt(i);
				return lst;
					}

		public String[] toStringArray()
		{
			String[] lst = new String[size()];
			for (int i = 0;i < lst.length;i++)
				lst[i] = (String)elementAt(i);
			return lst;
		}


		public String toDelimitedString()
		{
			return toDelimitedString(";");
		}

		public String toDelimitedString(String d)
		{
			StringBuffer lst = new StringBuffer();
			for (int i = 0;i < size();i++)
				lst.append((String)elementAt(i)).append(d);
			String lsts = lst.toString();
			if (lsts.endsWith(d))
				lsts = lsts.substring(0,lsts.lastIndexOf(d));
			return lsts.toString();
		}



	private void check(){

		if (m_check){
		if (m_file != null){
		//synchronized (m_file)
		{
		if (m_file instanceof String){
			try
			{
			File f = new File((String)m_file);
			long m = f.lastModified();
			if (m != m_lastmodified){
				init();
				m_lastmodified = m;
			}
			}
			catch (Throwable e){
				e.printStackTrace();
			}
		}
		}
		}

		}

	}

		public void checkForChange(){
		m_check = true;
	}


	public  EZArrayList(String filename)
		throws Exception
	{
		this(filename,false);
	}

	public  EZArrayList(String filename,boolean tf)
		throws Exception
	{
		this(false,filename,tf);
		}


	public  EZArrayList(boolean nl,String filename,boolean tf)
		throws Exception
	{
		if (tf)new File(filename).createNewFile();
		newline = nl;
		m_file = filename;
		init();
	}
	public  EZArrayList(File filename)
		throws Exception
	{
		this(filename.toString(),false);
	}
	public  EZArrayList(boolean nl,File filename)
		throws Exception
	{
		this(nl,filename.toString(),false);
	}


		private void  init()
		throws Exception
	{
		FileInputStream fi = null;
		try{
		if (m_file != null){
		if (m_file instanceof String){
			File f = new File((String)m_file);
		if (!f.exists())
		{
			f.createNewFile();
			FileOutputStream fo = null;
			try{
			fo = new FileOutputStream(f);
			}finally{

			fo.close();
			}
		}
		fi = new FileInputStream(f);
		clear();
		read(fi);
		}
		}
		}finally{
			if (fi != null)
				fi.close();
		}
	}

	public EZArrayList(InputStream in){
		read(in);
	}

			    public void read (InputStream in)
    {
        	read(new InputStreamReader(in));
        
        /*int i = -1;
    		LineNumberReader lr = null;

        try
        {
        	lr = new LineNumberReader(new InputStreamReader(in));
           String s = null;
           while ((s = lr.readLine()) != null)
	        {
		        if(s != null && (s.length() > 0 || newline))
    				  add(s);
	        }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        		return ;
        }*/
    }


			    public void read (InputStreamReader in)
    {
        int i = -1;
    		LineNumberReader lr = null;

        try
        {
        	lr = new LineNumberReader(in);
           String s = null;
           while ((s = lr.readLine()) != null)
	        {
		        if(s != null && (s.length() > 0 || newline))
    				  add(s);
	        }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        		return ;
        }
    }


public void store()
	throws Exception
	{
		if (m_file instanceof String)
			store((String)m_file);
	}

Boolean lock = new Boolean(false);

		public  void store(String fil)
			throws Exception
    {
    	synchronized(lock){
    		FileOutputStream out = new FileOutputStream(new File(fil));
				store (out);
    		out.close();
    	}
    }

	boolean strip = false;
	public void setStrip(boolean tf){
	strip = tf;	
	}
    		public  void store(OutputStream out)
			throws Exception
    {
    		synchronized(lock){
    		PrintWriter wr = new PrintWriter(out,true);
    		 for (Enumeration en = elements(); en.hasMoreElements() ;)
	      {
					String desc = (String)en.nextElement();
					if (strip)
					 desc = StringUtil.strip(desc);
	      	wr.println(desc);
	      }
	     }

    }



	public EZArrayList(int s)
	{
		super(s);
	}
	public boolean add(Object obj)
		{
			if (unique && contains(obj))return false;
			addElement(obj);
			return true;
		}

	public Object get(int indx)
		{
			check();
			return elementAt(indx);
		}

	public boolean remove(Object obj)
		{
			//check();
			removeElement(obj);
			return true;
		}

		public void removeAll(Object obj)
			{
				//check();
				while (contains(obj)){
					remove(obj);
				}
			}

		public NameValue getNV(int i){
			String tj = (String)elementAt(i);
			int ind = tj.lastIndexOf("=");
			String com = tj.substring(0,ind);
			String v = tj.substring(ind + 1,tj.length());
			return new NameValue (com,v);
		}

		public NameValue getNV(int i,boolean tf){
			String tj = (String)elementAt(i);
			int ind = tj.lastIndexOf("=");
			if (!tf)
				ind = tj.indexOf("=");
			String com = tj.substring(0,ind);
			String v = tj.substring(ind + 1,tj.length());
			return new NameValue (com,v);
		}


		public void putNV(String n,String v){
			add(n + "=" + v);
		}

		public void putNV(NameValue nv){
			putNV(nv.name , nv.value);
		}

		public static Vector toVector (EZArrayList pairs)
		{
				Vector h = new Vector(pairs.size());
				Object key = null;
				//	if (pairs == null)
				//		return h;
		  	for (Enumeration e = pairs.elements(); e.hasMoreElements(); )
		    {
		 				key = e.nextElement();
		    		h.add(key);

		    }
			return h;
		}

	public  boolean containsIgnoreCase(String s){
		String n = null;
		for (int ct = 0; ct < size();ct++){
			n = (String)elementAt(ct);
			if (s.equalsIgnoreCase(n))return true;
		}
		return false;
	}
}