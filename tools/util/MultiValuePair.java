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
 import java.util.*;
 import java.io.*;
 
/**
 * JSPParser.
 *
 *
 * Author: Gabriel Wong
 **/
 public class MultiValuePair extends Hashtable implements Externalizable

 {
 
 public boolean noVector = false;
 private Vector m_sort = new Vector();
 
 public MultiValuePair(){
 	super();
 }
 
 
 public static Hashtable toHashtable (MultiValuePair pairs)
 {
 		Hashtable h = new Hashtable(pairs.size());
 		Object key = null;
   	for (Enumeration e = pairs.keys(); e.hasMoreElements(); )
     {
  				key = e.nextElement();
     		h.put(key,pairs.get(key));

     }
 	return h;
 }
 
 public MultiValuePair(Map m){
 	this(new Hashtable(m));
 }
 
 public MultiValuePair(Hashtable m){
 	super();
	Enumeration en = m.keys();
	Object o = null;
	Object k = null;
	while (en.hasMoreElements())
	{
		k = en.nextElement();
		o = m.get(k);
		
		if (o instanceof Vector)
			setValues(k,(Vector)o);
		else if (o instanceof String[])
		{

			setValues(k,(Vector)(new EZArrayList((String[])o)));
		}
		else put(k,o);
			
	}
	
	
 }
 
 
 
 public MultiValuePair(boolean nv){
 	this();
 	noVector = nv;
 }
 public boolean caseSensitive = true;

			public Enumeration keys()
	{
		return m_sort.elements();
		
	}
 
 public void clear(){
 	m_sort.clear();
	super.clear();
 }
 
 
 		public Object put(Object k,Object o){
			Object o1 = null;//super.get(k);
			if (! caseSensitive)
				o1 = super.get(k.toString().toLowerCase());
			else
			 	o1 = super.get(k);	
			Vector v = new Vector();
			if ( o1 != null)
				v = (Vector)o1;
			if (noVector && (o instanceof Vector))
				v = (Vector)o;
			else	
				v.addElement(o);
			
			if (m_sort == null)
				m_sort = new Vector();
			if(!m_sort.contains(k))
				m_sort.add(k);
			if (! caseSensitive)
				return super.put(k.toString().toLowerCase(),v);
			return super.put(k,v);	
		}
		
		public void replace(Object k,Object o){
			remove(k);
			put(k,o);
		}
		
		public Object get(Object k){
			Vector v = getValues(k);
			if (v != null && v.size() > 0)
				return v.elementAt(0);
			return null;	
		}
		
		
		public Vector getValues(Object k){
		if (!caseSensitive)
			return (Vector)super.get(k.toString().toLowerCase());
		
			return (Vector)super.get(k);
		}
		
		
		public Vector setValues(Object k,Vector v){
		if (m_sort == null)
			m_sort = new Vector();
		if(!m_sort.contains(k))
			m_sort.add(k);
		
			return (Vector)super.put(k,v);
		}
	
		public int indexOf(Object k){
			if (m_sort == null)
				m_sort = new Vector();
			return m_sort.indexOf(k);
		
		}
	
	
	
		public Vector setValues(Object k,Vector v,int index){
		if (m_sort == null)
			m_sort = new Vector();
		if(!m_sort.contains(k))
			m_sort.insertElementAt(k,index);
		
			return (Vector)super.put(k,v);
		}
	
	
		public Vector removeValues (Object name)
		{
			m_sort.remove(name);
			Object rem = null;
			if (!caseSensitive)
				rem =  super.remove(name.toString().toLowerCase());
			else rem = super.remove(name);
			return (Vector)rem;
		}
		
		public Object remove (Object name)
		{
			m_sort.remove(name);
			Object rem = null;
			if (!caseSensitive)
				rem =  super.remove(name.toString().toLowerCase());
			else rem = super.remove(name);
			//("VVVVVVVVVVVVV 1: " + rem);
			
			if (rem != null && (rem instanceof Vector))
			{
				Vector v = (Vector)rem;
				//("VVVVVVVVVVVVV: " + v.size());
				if (v.size() > 0)return v.elementAt(0);
				else return null;
			}
			
			return rem;
		}
		
		
		
		
		//private void writeObject
		public void writeExternal(java.io.ObjectOutput out)
     throws IOException{
     	out.writeBoolean(noVector);
		out.writeObject(m_sort);
	 	out.writeInt(size());
		Enumeration ks = super.keys();
		Object k = null;
		Object v = null;
		while (ks.hasMoreElements())
		{
			k = ks.nextElement();
			v = super.get(k);
			out.writeObject(k);
			out.writeObject(v);
			//(k + " MULTI WRITE :: > " + v);	
			
		}
     }
 //private void readObject
 public void readExternal(java.io.ObjectInput in)
     throws IOException,ClassNotFoundException{
	 noVector = in.readBoolean();
	 m_sort = (Vector)in.readObject();
	 int size = in.readInt();
	 Object k = null;
	 Object v = null;
	 for (int ct = 0;ct < size;ct++)
	 {
	 	k = in.readObject();
	 	v = in.readObject();
		super.put(k,v);
		//(k + " MULTI READ " + v);
	 }
	 }
		
		
		
		
		
		
 }
 
 