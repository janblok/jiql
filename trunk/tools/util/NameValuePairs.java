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
import java.net.URL;


/**
 * This class is used as the base SerializableObject object.
 **/
//NameValuePairs
public class NameValuePairs extends Hashtable implements Serializable
{

    private static final long serialVersionUID = 6952319459048894843L;//-111111111112222222L;
	//private transient Object lock = new Object();
	//private transient boolean isLocked = false;
    private  EZArrayList m_sort = new EZArrayList();
    private boolean m_cs = true;
		private String m_files = null;
		private File m_filef = null;
		private Vector m_descr = new Vector();
		private boolean m_check = false;
		private long m_lastmodified = -1;
		private boolean m_autostore = false;
		String pairToken = "=";
		String delimiter = "\r\n";
		boolean base64values = false;
    /**
     * Constructs a new NameValuePairs.
     *
     * @param type the id
     **/
    
     public void setBase64Values(boolean t){
    	base64values = t;
    }
    public void setPairToken(String t){
    	pairToken = t;
    }
    
       public void setDelimiter(String t){
    	delimiter = t;
    }
    
    public Properties toProperties()
    {
    	Properties p = new Properties();
    	Enumeration en = keys();
    	while (en.hasMoreElements()){
    		Object k = en.nextElement();
    		p.put(k,get(k));	
    	}
    	return p;
    	
    }
			public String getString(String a,String def){
		
			String as = getString(a);
			if (!StringUtil.isRealString(as))as = def;
			
			return def;
		}
    
    protected void setHandler(String o){
     m_files = o;	
    }
        protected void setHandler(File o){
     m_filef = o;	
    }
    
    
    public  NameValuePairs ()
    {
    		super();
    		m_sort = new EZArrayList();
    }

		public NameValuePairs (String attr,String tok)
	throws Exception
	{
		super();
		pairToken = tok;
		load(attr);
		m_files = attr;
	}

		public NameValuePairs (String attr)
	throws Exception
	{
		super();
		load(attr);
		m_files = attr;
	}
	
	public void clear(){
		super.clear();
		if (m_sort != null)
			m_sort.clear();
	}
	public NameValuePairs (URL attr)
	throws Exception
	{
		this(attr.getFile());
	}


	public NameValuePairs (File attr)
	throws Exception
	{
		this(attr.toString());
	}

	public String toDelimitedString(String v,String p){
		Enumeration en = keys();
		EZArrayList b = new EZArrayList();
		Object n = null;
		Object o = null;
		while (en.hasMoreElements())
		{
			n = en.nextElement();
			o = get(n);
			b.add(n + v + o);
		}
		return b.toDelimitedString(p);
	
	}
	
	public Vector getVector(String n){
		String s = getString(n);
		String d = null;
		if (s.indexOf(",") > -1)
			d = ",";
		else
			d = ";"	;
		return getVector(n,d);
	}
	
	public Vector getVector(String n,String d){
		String s = getString(n);
		return SharedMethods.toVector(s,d);
	}
	
	public void fromDelimitedString(String t,String v,String p){
		EZArrayList b = SharedMethods.toVector(t,p);
		Enumeration en = b.elements();

		String n = null;
		String o = null;
		int i = 0;
		while (en.hasMoreElements())
		{
			n = (String)en.nextElement();
			i = n.indexOf(v);
			o = n.substring(i+1,n.length());
			n = n.substring(0,i);
			put(n,o);
		}
	
	}
	
	
	
			public NameValuePairs (String attr,boolean tf)
	throws Exception
	{
		super();
		if (tf)
			loadmkdir(attr);
		else
		load(attr);
		m_files = attr;
	}
	public void autoStore(){
		m_autostore = true;
	}
		private void check(){
		if (m_check){
		if (m_files != null)
		{
		if (m_files instanceof String){	
			try
			{
			File f = new File((String)m_files);
			long m = f.lastModified();
			if (m != m_lastmodified){
				m_check = false;
				//if (size() > 0)
					//clear();
				load((String)m_files);
				m_check = true;
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
	
		public void checkForChange(){
		m_check = true;
	}

	public void checkForChange(boolean c){
	m_check = c;
}


	


	  /**
     * Constructs a new SerializableObject with the specified size.
     *
     * @param type the id
     **/
    public  NameValuePairs (int size)
    {
				super(size);
	  		m_sort = new EZArrayList(size);
    }


	  /**
     * Constructs a new SerializableObject with the specified fields.
     *
     * @param fields the fields
     **/

	public  NameValuePairs (Hashtable fields)
	{
			super();
			 m_sort = new EZArrayList(fields.size());

			set(fields);
	}

		public  NameValuePairs (InputStream in)
	{
			super();
			load(in);
	}
	
	public void setDescription(String descr)
	{
		m_descr = new Vector();	
		descr = descr + delimiter;
		LineNumberReader lr = new LineNumberReader(new StringReader(descr));
    String s = null;
    try
		{
     while ((s = lr.readLine()) != null)
    {
    		if ( s.length() > 0)	
    			m_descr.addElement(s);
    	}
		}
		catch (Exception e)
		{
			e.printStackTrace();	
		}

	}
	
		public String getDescription()
	{
		String descr = null;
    		 for (Enumeration en = m_descr.elements(); en.hasMoreElements() ;)
	      {
	      	if (descr == null)
	        	descr = "";
					descr = descr + (String)en.nextElement() +delimiter;
	      }
	      if (descr != null)
	      	descr = descr.trim();
	      return descr;
	}

	

public void store()
	throws Exception
	{
		if (m_files instanceof String)
			store((String)m_files);
	}

void	loadCustom(String c){

//(delimiter + " RD a " + c);
StringTokenizer t = new StringTokenizer(c,delimiter);
String s = null;
while (t.hasMoreTokens()){

s = t.nextToken();
//("RD " + s);

loadLine(s);
}
	}

boolean readdescr = false;
String desc = null;
void	loadLine(String s){
        int i = -1;
    		String name = "";
    		String value = "";


			        	if (!readdescr && s.startsWith("#"))
	        	{
	        		if (desc == null)
	        			desc = "";
	        		String rd = s.substring(1,s.length());
	        		if (rd != null && rd.length() > 0)	
	        			desc = desc + rd + delimiter;
	        	}
		        else if(s != null && !s.startsWith("#") && !s.startsWith("//") && s.length() > 2)
			    	{
			    		readdescr = true;
		 					i = s.indexOf(pairToken);
						if (i > 0)
						{
	              			name = s.substring(0,i);
	    					value = s.substring(i + pairToken.length());
	    					if (trim){
	    						name = name.trim();
	    						value=value.trim();
	    					}
	    					if (base64values){
	    					
	    						//value=new String(Base64.decode(value.trim().getBytes()));
	    					value=new String(Base64Decoder.fromBase64(value.trim().getBytes()));
	    					}
	    				  	put(name,value);
						}
			    	}
		
	}

		public  void load (InputStream in)
    {
        synchronized(lock){
    		LineNumberReader lr = null;
         desc = null;
         				if (size() > 0)
					clear();


        try
        {
        	lr = new LineNumberReader(new InputStreamReader(in));
           String s = null;
           readdescr = false;
           while ((s = lr.readLine()) != null)
	        {
	        	if (!delimiter.equals("\r\n")){
	        	
	        		loadCustom(s);
	        		
	        	}
	        	else
	        		loadLine(s);


	        }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        		return ;
        }
        if (desc != null)
        	setDescription(desc.trim());
        }
    }
Boolean lock = new Boolean(false);

		public  void store(OutputStream out,boolean sort)
			throws Exception
    {
    		synchronized(lock){
    		String name = "";
    		String val = "";
    		PrintWriter wr = new PrintWriter(out,true);
    		Enumeration enu = sortedKeys();
    		desc = null;
    		 for (Enumeration en = m_descr.elements(); en.hasMoreElements() ;)
	      {
					 desc = (String)en.nextElement();
	      	wr.println((new StringBuffer("#").append(desc)).toString());
	      }

    	//if (! sort)
    	//	enu = sortedKeys();
        for (Enumeration en = enu; en.hasMoreElements() ;)
	      {
					name = (String)en.nextElement();
	      	
        	val = get(name).toString();
			if (base64values)
			{
			 //val=new String(Base64.encode(val.getBytes()));
			val=new String(Base64Encoder.toBase64(val.getBytes()));
			}
			if (!delimiter.equals("\r\n")){
			
	      	wr.print((new StringBuffer(name).append(pairToken).append(val)).append(delimiter).toString());
			//(("WAWAT " + (new StringBuffer(name).append(pairToken).append(val)).append(delimiter).toString()));
			}
	     	else
	     	wr.println((new StringBuffer(name).append(pairToken).append(val)).toString());

	      }
   			if (!delimiter.equals("\r\n"))
				wr.println("");
	     }
    }
	

		public  void store(String fil,boolean sort)
			throws Exception
    {
    		synchronized(lock){
    		FileOutputStream out = new FileOutputStream(new File(fil));
				try{
				store (out,sort);
				}finally{
    		out.close();
			}
		}
    }
	

			public  void store(String fil)
			throws Exception
    {
			synchronized(lock){
			store(fil,true);

			//store(fil + ".tmp",true);
    	//File tf = new File(fil + ".tmp");
    	//new File(fil).delete();
    	//tf.renameTo(new File(fil));
    	//new File(fil + ".tmp").delete();
    }
    }
	
				public  void store(OutputStream out)
			throws Exception
    {
			store(out,true);
    }

			public  void storemkdirs(String fil)
			throws Exception
    {
				synchronized(lock){
				if (! new File(fil).exists())
					new File(fil).mkdirs();
				store(fil);
				}	
    }


	public void setCaseSensitive(boolean tf)
	{
		m_cs = tf;
	}



	  /**
     * Sets the value pair .
     *
     * @param the order number
     **/
    public void setValue (String name,String obj)
    {
        put(name,obj);
    }


		  /**
     * Sets the value pair .
     *
     * @param the order number
     **/
    public void merge (Hashtable pairs)
    {
    		Object key = null;
				if (pairs == null)
					return;
      	for (Enumeration e = pairs.keys(); e.hasMoreElements(); )
        {
     				key = e.nextElement();
        		put(key,pairs.get(key));

        }
    }

    public static Hashtable toHashtable (NameValuePairs pairs)
    {
			Hashtable h = new Hashtable(pairs.size());
    		Object key = null;
    		//	if (pairs == null)
    		//		return h;
      	for (Enumeration e = pairs.keys(); e.hasMoreElements(); )
        {
     				key = e.nextElement();
        		h.put(key,pairs.get(key));

        }
		return h;
    }


			public  void loadmkdir (String fil)
				throws Exception
	{
		 	synchronized(lock){
		 	File fl = new File(fil);
		 //	if (!fl.exists())
		 		fl.createNewFile() ;
		DataInputStream din = new DataInputStream(new FileInputStream(fl));
		try{
		load(din);
		}finally{
		din.close();
		}
}
	}

				public  void load (String fil)
				throws Exception
	{
		synchronized(lock){

		 	File fl = new File(fil);
		 	if (!fl.exists())
		 		return ;

		DataInputStream din = new DataInputStream(new FileInputStream(fl));
		try{
		load(din);
			if (m_files == null)m_files = fil;
		}finally{
		din.close();
		}
}
	}


	

    public Object put (String name,boolean obj)
    {
    		String tf = "false";
    		if(obj)tf = "true";
        return put(name,tf);
    }
    public Object put (String name,long obj)
    {
        return put(name,String.valueOf(obj));
    }
        public Object put (String name,int obj)
    {
        return put(name,String.valueOf(obj));
    }
    
       public Object put (long name,long obj)
    {
        return put(String.valueOf(name),String.valueOf(obj));
    }
        public Object put ( long name,int obj)
    {
        return put(String.valueOf(name),String.valueOf(obj));
    }
    
    
	    public Object put (String name,double obj)
    {
        return put(name,String.valueOf(obj));
    }



	  /**
     * Gets the value from the given name key .
     *
     * @param the order number
     **/
    public String getValue (String name)
    {
        return get(name) == null ? "" : get(name).toString();
    }

	    public boolean getBoolean (String name)
    {
        String tf = getValue(name);
    		return (tf.toLowerCase().equals("true")|| tf.toLowerCase().equals("on")|| tf.equals("1")|| tf.toLowerCase().equals("yes")) ? true : false;
    }
	
	public String getTrimmedValue(String n){
	
		return StringUtil.getTrimmedValue(getString(n));
	
	}
	boolean trim = false;
	public void setTrim(boolean tf){
		trim = tf;
	}

public Date getDate (String name){

  return new Date(getLong(name));
}
		    public long getLong (String name)
    {
        String tf = getValue(name);
        if (tf.endsWith("m"))
        	tf = StringUtil.replaceSubstring(tf,"m","000000");
    		return tf.length() < 1 ? -1 : Long.valueOf(tf).longValue();
    }


public byte[] getBytes(String name)  {
	return getString(name).getBytes();
}

public byte getByte(String name)  {
	return getBytes(name)[0];
}

		    public short getShort (String name)
    {
        String tf = getValue(name);
    		return tf.length() < 1 ? -1 : Short.valueOf(tf).shortValue();
    }

		    public float getFloat (String name)
    {
        String tf = getValue(name);
    		return tf.length() < 1 ? -1 : Float.valueOf(tf).floatValue();
    }
    

		    public double getDouble (String name)
    {
        String tf = getValue(name);
    		return tf.length() < 1 ? -1 : Double.valueOf(tf).doubleValue();
    }


		    public String getString (String name)
    {
        return getValue(name);
    }


	  /**
     * Gets the value from the given name key .
     *
     * @param the order number
     **/
    public int getInt(String name)
    {
        return get(name) == null ? -1 : Integer.parseInt(get(name).toString());
    }
    
     public int getInt(long name)
    {
        return getInt(String.valueOf(name));
    }

		public Object get(String name)
	{
		//
		//check();
		if(!m_cs)
			name = name.toLowerCase();
		return get((Object)name);
	}
	
	public Object getNoLock(String name){
		return super.get(name);
	}
	
	public Object getNoLock(Object name){
		return super.get(name);
	}
	public boolean valueEquals(Hashtable h){
		if (h == null)return false;
		if (size()!= h.size())return false;
		Enumeration e1 = keys();
		while (e1.hasMoreElements()){
			String k = (String)e1.nextElement();
			if (h.get(k) == null)return false;
			if (!h.get(k).equals(get(k)))return false;
		}
		return true;
	}
	boolean nonulls = false;
	public void noNulls(boolean tf){
		nonulls = tf;
		
	}
			public Object get(Object name)
	{
	if(!m_cs && ( name instanceof String))
		name = ((String)name).toLowerCase();
	
			check();
			Object o = super.get(name);
			if (nonulls)
			if (o != null && o.toString().equalsIgnoreCase("null"))
				o = null;
			return o;
	}

	public Collection values()
{
check();
return super.values();
}


			public Enumeration keys()
	{
		check();

		return m_sort.elements();
		
	}
	
	
				public Enumeration elements()
	{

		Enumeration keys = keys();
		Vector v = new Vector();
		while (keys.hasMoreElements())
		 v.add(get(keys.nextElement()));
		
		return v.elements();
				
	}

	public Object get(int i){
		if (i < 0 || size() < 1 + i)return null;
		return get(m_sort.elementAt(i));
	
	}
	
		public String getKey(Object v){
			return getKey(v.toString());
		}
	
	public String getKey(String v){
		Enumeration en = keys();
		while (en.hasMoreElements())
		{
			String k = (String)en.nextElement();
			if (getString(k).equals(v))
			 return k;	
		}	
		return null;
	}
	
	public Object get(){
		return get(0);
	}
	  /**
     * Returns the value pairs.
     * @ return the Hashtable of value pairs
     **/
    public final Hashtable getValuePairs()
    {
        return (Hashtable)this;
    }




		/**
     * Sets the value of the specified name key.
     *
     * @param pairs value pairs
     **/
    public final void set(Hashtable pairs)
    {
    		String key = "";

      	for (Enumeration e = pairs.keys(); e.hasMoreElements(); )
        {
     				key = (String)e.nextElement();
        		put(key,pairs.get(key));

        }
    }

	public Object remove (long name)
	{
		return remove(String.valueOf(name));
	}
	
		public Object remove (int name)
	{
		return remove(String.valueOf(name));
	}
	
			public Object remove (String name)
	{
		return remove((Object)(name));
	}

	public Object remove (Object name)
	{
		m_sort.remove(name);
		Object r = super.remove(name);
			if ((m_check || m_autostore) && r != null)		try{
store();}catch(Throwable e){
				e.printStackTrace();}
				return r;
			//	}finally{
			//	isLocked = false;
				
			//	}
			//	}
	}
	public Object put (String name, Object obj)
	{
			if(!m_cs)
				name = name.toLowerCase();
		return put((Object)name,obj);
	}
	
			public Object put (int name, Object obj)
	{
		return put(String.valueOf(name),obj);
	}
	
				public Object put (int name, boolean obj)
	{
		return put(String.valueOf(name),obj);
	}
	
				public Object put (int name, int obj)
	{
		return put(String.valueOf(name),obj);
	}
	
				public Object put (int name, long obj)
	{
		return put(String.valueOf(name),obj);
	}
	
				public Object put (int name, String obj)
	{
		return put(String.valueOf(name),obj);
	}
	
				public Object put (int name, double obj)
	{
		return put(String.valueOf(name),obj);
	}
	
				public Object put (int name, float obj)
	{
		return put(String.valueOf(name),obj);
	}
	
	
	
	
	
	
	
		public Object put (Object name, Object obj)
	{

		Object r = super.put(name,obj);
		if (m_sort == null)
			m_sort = new EZArrayList();
		m_sort.remove(name);
			m_sort.add(name);
			if (m_check || m_autostore)		try{
store();}catch(Throwable e){
				e.printStackTrace();}
				return r;
			//	}finally{
			//	isLocked = false;
				
		//		}
		//		}
	}


		public Enumeration sortedKeys ()
	{
		return m_sort.elements();
	}
	

	
		/**
     * Converts String with delimeters to NameValuePairs.
     *
     * @param str the String
     * @param pr the name and value delimiter
     * @param tok the pair delimiter
     *
     * @return the NameValuePairs
     **/
     
     	   public static NameValuePairs toNameValuePair(String str)
		{
			return toNameValuePair(str,"=","\r\n");
		}
	   public static NameValuePairs toNameValuePair(String str,String pr,String tok)
    {
    		NameValuePairs vp = null;
       try
        {
        	str = str + tok;
        		StringTokenizer st = new StringTokenizer(str,tok);
		    		vp = new NameValuePairs();
        		int i = 0;
        		String s = "";
        		String name = "";
        		String value = "";
     					while (st.hasMoreElements())
			    	{
			    			s = st.nextToken();
    		    		name = "";
								value = "";
     						i = s.indexOf(pr);
			    			if(i > 0 )
				    	{
	                name = s.substring(0,i);
		    					value = s.substring(i + pr.length());
			    				vp.put(name,value);
				    	}
			    	}

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return vp;
    }

	public Vector getValues(String name){
		Object roles = get(name);
		if (roles == null)
			return null;
		if (roles instanceof Vector)
			return (Vector)roles;
		else
		{
			Vector v = new Vector();
			v.addElement(roles);
			return v;
		}		
	}

}