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
 import java.util.*;
 import java.io.*;
 
/**
 * XMLParser.
 *
 *
 * Author: Gabriel Wong
 **/
 public class SEXParser 

 {
 
	String ver = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>" +System.getProperty("line.separator");

String dtype = "<!DOCTYPE web-app"+System.getProperty("line.separator") +
    "PUBLIC \"-//Sun Microsystems, Inc.//DTD Web Application 2.2//EN\""+System.getProperty("line.separator") +
    "\"http://java.sun.com/j2ee/dtds/web-app_2_2.dtd\">"+System.getProperty("line.separator");
 
		static String depth = "    ";
		private String body = null; 
		
		public SEXParser (){
		
		}		
 		public SEXParser (String p)
 			throws Exception
 	{
 		body = p;
 	}
 	
 	
 			public SEXParser (boolean p)

 	{
 		trim = p;
 	}
 	
 	boolean trim = false;
 	
 	 	public String getElement(String type){
 	 		String otag = "<" + type + ">";
 	 		String ctag = "</" + type + ">";
 		int i = body.indexOf(otag);
 		//(" getElement 1 " + "<" + type + " id=\"" + id + "\"");
 		if (i > -1){
 		String body2 = body.substring(i + otag.length(),body.length());
 		int i2 = body2.indexOf(ctag);
 		body2 = body2.substring(0,i2);
 		return body2;
 		

 		}
 	
 	return "";	
 	}
 	
 	
 	public String getElement(String id,String type){
 		int i = body.indexOf("<" + type + " id=\"" + id + "\"");
 		//(" getElement 1 " + "<" + type + " id=\"" + id + "\"");
 		if (i > -1){
 		String body2 = body.substring(i,body.length());
 		int i2 = body2.indexOf(">");
 		body2 = body2.substring(i2+1,body2.length());
 		
 		String ctag = "</" + type + ">";
 	 	String otag = "<" + type ;
 	 	
 	 	 		//( i2 + ":" + otag + " getElement 2 " + ctag);

		while (true){
		i2 = body2.indexOf(ctag);
		i = body2.indexOf(otag);
 		//(i2 + " getElement 3 " + i);

		if (i2 < i || i < 0)
		{
			body2 = body2.substring(0,i2);
			body2 = StringUtil.replaceSubstring(body2,"appcloem_CLOSE_TAG",ctag);
			body2 = StringUtil.replaceSubstring(body2,"appcloem_OPEN_TAG",otag);

			return body2;
		}
		else
		{
			body2 = StringUtil.replaceFirstSubstring(body2,ctag,"appcloem_CLOSE_TAG");
			body2 = StringUtil.replaceFirstSubstring(body2,otag,"appcloem_OPEN_TAG");

		}
			
			
		}
 			
 		}
 	
 	return "";	
 	}
 	public void setWebVersion(String v)
 	{
		if (v.equals("2.2"))return;
 		dtype = StringUtil.replaceSubstring(dtype,"2.2",v);
		if (v.equals("2.3"))dtype = StringUtil.replaceSubstring(dtype,"2_2","2_3");
 		if (v.equals("2.4"))dtype = StringUtil.replaceSubstring(dtype,"2_2","2_4");
 		if (v.equals("2.5"))dtype = StringUtil.replaceSubstring(dtype,"2_2","2_5");

 	}
	
 		public String getWebVersion(String bod)
		{
			if (bod.indexOf("DTD Web Application 2.2") > -1)return "2.2";
			if (bod.indexOf("DTD Web Application 2.3") > -1)return "2.3";
			if (bod.indexOf("DTD Web Application 2.4") > -1)return "2.4";
			if (bod.indexOf("DTD Web Application 2.5") > -1)return "2.5";

			return "2.2";
		}
  	
    public Enumeration parse(){
		return parse(body);
	}
	
	public Enumeration parse(File file) throws java.io.IOException{
		byte[] b = FileUtil.getBytesFromFile(file);
		String body1 = new String (b);			
		return parse(body1);
	}
	
	public Hashtable hashList(File file,String id) throws java.io.IOException{
		file.createNewFile();
		byte[] b = FileUtil.getBytesFromFile(file);
		String body1 = new String (b);			
		return hashList(body1,id);
	}
	

	public Hashtable hashList(String l,String id){ 
		Hashtable ret = new NameValuePairs();
		Enumeration en = parse(l);
		while (en.hasMoreElements())
		{
			NameValue vp = (NameValue)en.nextElement();
			//("vp Name " + vp.name);
			Hashtable h = parseToHash(vp.value,true);
			//("vp ID " + h.get(id));
			
			ret.put(h.get(id),h);
		}
		return ret;
			
	}

	

	public Hashtable parseToHash(File file) throws java.io.IOException{
		file.createNewFile();
		byte[] b = FileUtil.getBytesFromFile(file);
		String body1 = new String (b);			
		return parseToHash(body1);
	}
	

    public Enumeration parse(String b)
    {
		Vector lst = new Vector();
		b = takeOutComments(b);
		int dec = 0;
		for (int ct =  b.indexOf("<");ct > -1;ct = b.indexOf("<"))
		{
			dec++;
			b  = b.substring(ct + 1,b.length());
			int openIndex = b.indexOf(">");
			String openTag = b.substring(0,openIndex);
			b  = b.substring(openIndex + 1,b.length());
			int oti = openTag.indexOf(" ");
			//openT2 = "";
			if (oti > -1){
				openT2 = openTag.substring(oti,openTag.length());
				openTag = openTag.substring(0,oti);
			}
			if (! openTag.startsWith("?") && ! openTag.startsWith("!"))
			{
			String closeTag = "</" + openTag + ">";
			openIndex = b.indexOf(closeTag);
			//(b + " OPEN TAG " + closeTag);
			String value = null;
			try{
				value = b.substring(0,openIndex);
			}catch (StringIndexOutOfBoundsException se)
			{
			
				//if (true)
					throw new StringIndexOutOfBoundsException(ct +" SParser Missing Closing Tag Error " + openTag + ":" + closeTag);
			}
			b = b.substring(openIndex + closeTag.length());
			lst.addElement(new NameValue(openTag,value));
			}
			if (dec > 20)
			{
				//("Fatal Parser Error!");
				//break;
			}
			
		}
		return lst.elements();	

    }

    public Hashtable parseToHashtable(File f)
    {
    	try{
    	return parseToHashtable(FileUtil.getStringFromFile(f));
    	}catch (IOException e){
    		tools.util.LogMgr.err(" parseToHashtable " + e.toString());
    		return null;
    			
    	}
    }
	
    public Hashtable parseToHashtable(String b)
    {
    	NameValuePairs lst = new NameValuePairs();
		//if (sorted)
		//	lst.
    	b = takeOutComments(b);
    	int dec = 0;
    	for (int ct =  b.indexOf("<");ct > -1;ct = b.indexOf("<"))
    	{
    		dec++;
    		b  = b.substring(ct + 1,b.length());
    		int openIndex = b.indexOf(">");
    		String openTag = b.substring(0,openIndex);
    		b  = b.substring(openIndex + 1,b.length());
			//openT2 = "";
			int oti = openTag.indexOf(" ");

			if (oti > -1){
				openT2 = openTag.substring(oti,openTag.length());
				openTag = openTag.substring(0,oti);
			}

    		if (! openTag.startsWith("?") && ! openTag.startsWith("!"))
    		{
    		String closeTag = "</" + openTag + ">";
    		openIndex = b.indexOf(closeTag);
    		String value = null;
    		try{
    		value = b.substring(0,openIndex);
    		}catch (Exception e){
    			tools.util.LogMgr.err(openTag + " SEXParser.parseToHashtable " + openIndex + ":" + e.toString());	
    			value = b.substring(0,openIndex);
    		}
    			
    		b = b.substring(openIndex + closeTag.length());
    		lst.put(openTag,new NameValue(openTag,value));
    		}
    		if (dec > 20)
    		{
    			//("Fatal Parser Error!");
    			//break;
    		}
    		
    	}
    	return lst;	

    }
	
String openT2 = "";
public String getOpenT2(){
	return openT2;
}

    public Enumeration parse(String b,boolean notEmpty)
    {
    	Vector lst = new Vector();
		int dec = 0;
    	b = takeOutComments(b);
    	for (int ct =  b.indexOf("<");ct > -1;ct = b.indexOf("<"))
    	{
			dec++;
    		b  = b.substring(ct + 1,b.length());
    		int openIndex = b.indexOf(">");
    		String openTag = b.substring(0,openIndex);
    		b  = b.substring(openIndex + 1,b.length());
    			//openT2 = "";
						int oti = openTag.indexOf(" ");

			if (oti > -1){
				openT2 = openTag.substring(oti,openTag.length());
				openTag = openTag.substring(0,oti);
			}
    		if (! openTag.startsWith("?") && ! openTag.startsWith("!"))
    		{
    		String closeTag = "</" + openTag + ">";
    		openIndex = b.indexOf(closeTag);
    		//(b + " OPEN TAG " + closeTag);
    		String value = b.substring(0,openIndex);
    		
    		b = b.substring(openIndex + closeTag.length());
    		lst.addElement(new NameValue(openTag,value));
			
    		}
    		if (dec > 20)
    		{
    			//("Fatal Error in Parser!");
    			//break;
    		}
			
    	}
		if (notEmpty && lst.size() < 1)
			return null;
    	return lst.elements();	

    }


    public Hashtable parseToHash(String xml,boolean tf){
    	Enumeration en = parse(xml);
    	return parseToHash(en,tf);
    
    }


	public Hashtable parseToHash(String xml){
		Enumeration en = parse(xml);
		return parseToHash(en);
	
	}

	public Hashtable parseToHash(Enumeration en){
	
	return parseToHash(en,false);
}	
	public Hashtable parseToHash(Enumeration en,boolean list){
		 Hashtable h = new NameValuePairs();
		if (list)
			h = new MultiValuePair();
		while (en.hasMoreElements())
		{
			NameValue nv = (NameValue)en.nextElement();
			Enumeration en2 = parse(nv.value,true);
			/*if (h.get(nv.name) != null )
			{
				Object o = h.remove(nv.name);
				Vector v = new Vector();
				if (o != null)
				if (o instanceof Vector)
					v = (Vector)o;
				else
					v.addElement(o);	
				h.put(nv.name,v);
				if (en2 == null)
					v.addElement(decodeXML(nv.value));
				else
					v.addElement(parseToHash(en2));
				
					
			}
			else
			{*/
			if (en2 == null){
				String tv = decodeXML(nv.value);
				if (trim)
				 tv = tv.trim();
				 String tn = nv.name;
				 if (trim)
				  tn = tn.trim();
				h.put(tn,tv);
			}
			else
				h.put(nv.name,parseToHash(en2,list));
			//}	
		}
		return h;

	
	}
	
	
	public static String encodeXML(String wit)
{
	wit = StringUtil.replaceSubstring(wit,"%","THIS_IS_A_PERCENT");
	wit = StringUtil.replaceSubstring(wit,"<","%3C");
	wit = StringUtil.replaceSubstring(wit,">","%3E");
	wit = StringUtil.replaceSubstring(wit,"THIS_IS_A_PERCENT","%25");
	wit = StringUtil.replaceSubstring(wit,"&","THIS_IS_A_PERCENT");
	wit = StringUtil.replaceSubstring(wit,"THIS_IS_A_PERCENT","&amp;");
	return wit;
}

	public static String decodeXML(String wit)
{
	wit = StringUtil.replaceSubstring(wit,"%3C","<");
	wit = StringUtil.replaceSubstring(wit,"%3E",">");
	wit = StringUtil.replaceSubstring(wit,"&#58;",":");
	wit = StringUtil.replaceSubstring(wit,"&#92;","\\");
	wit = StringUtil.replaceSubstring(wit,"&amp;","&");
	return wit;
}

	
    public StringBuffer toXML(Hashtable pairs,StringBuffer xml,boolean tf)
    {
		StringBuffer buf = new StringBuffer();
		if (tf)
		{
			buf.append(ver);
			buf.append(dtype);
		}

		buf.append( toXML(pairs,0,xml));
		return buf;
	}


    public StringBuffer toXML(Hashtable pairs,StringBuffer xml)
    {
		return toXML(pairs,0,xml);
	}
	
	public StringBuffer toXML(Hashtable pairs)
	{
		return toXML(pairs,0,new StringBuffer());
	}
	
	public void writeXML(Hashtable pairs,java.io.OutputStream out)
	 throws java.io.IOException 
	{
		out.write( new String(toXML(pairs,0,new StringBuffer())).getBytes());
	}
	
	public void writeXML(Hashtable pairs,java.io.OutputStream out,boolean tf)
	 throws java.io.IOException 
	{
		out.write( new String(toXML(pairs,new StringBuffer(),tf)).getBytes());
	}
	
	
	public void writeXML(Hashtable pairs,java.io.OutputStream out,String nm)
	 throws java.io.IOException 
	{
		out.write( new String(toXML(pairs,0,new StringBuffer(),nm)).getBytes());
	}

	public void writeXML(Hashtable pairs,java.io.OutputStream out,String nm,boolean tf)
	 throws java.io.IOException 
	{
		out.write( new String(toXML(pairs,new StringBuffer(),nm,tf)).getBytes());
	}

	
	
	public void writeToFile(Hashtable pairs,String f)
	 throws java.io.IOException 
	{
		writeToFile(pairs,new File(f));
	}
	
	public void writeToFile(Hashtable pairs,String f,boolean tf)
	 throws java.io.IOException 
	{
		writeToFile(pairs,new File(f),tf);
	}
	
	
	public void writeToFile(Hashtable pairs,File f)
	 throws java.io.IOException 
	{
		FileOutputStream fout = null;
		try{
		fout = new FileOutputStream(f);
		writeXML(pairs,fout);
		}finally{
			if (fout != null)
				fout.close();
			}	
	}
	
	public void writeToFile(Hashtable pairs,File f,boolean tf)
	 throws java.io.IOException 
	{
		FileOutputStream fout = null;
		try{
		fout = new FileOutputStream(f);
		writeXML(pairs,fout,tf);
		}finally{
			if (fout != null)
				fout.close();
			}	
	}
	


	public void writeToFile(Hashtable pairs,File f,String nm)
	 throws java.io.IOException 
	{
		FileOutputStream fout = null;
		try{
		fout = new FileOutputStream(f);
		writeXML(pairs,fout,nm);
		}finally{
			if (fout != null)
				fout.close();
			}	
	}
	
	
	
	
	
	public void writeToFile(Hashtable pairs,String f,String nm,boolean tf)
	 throws java.io.IOException 
	{
		writeToFile(pairs,new File(f),nm,tf);
	}
	public void writeToFile(Hashtable pairs,File f,String nm,boolean tf)
	 throws java.io.IOException 
	{
		FileOutputStream fout = null;
		try{
		fout = new FileOutputStream(f);
		writeXML(pairs,fout,nm,tf);
		}finally{
			if (fout != null)
				fout.close();
			}	
	}
	
	
	public StringBuffer toXML(Hashtable pairs,int deep,StringBuffer xml,String nm)
	{
		return toXML(pairs.elements(),deep,xml,nm);
	}
	
	public StringBuffer toXML(Hashtable pairs,StringBuffer xml,String nm,boolean tf)
	{
		return toXML(pairs,0,xml,nm,tf);
	}
	
	
	
	public StringBuffer toXML(Enumeration el,int deep,StringBuffer xml,String nm)
	{
		while (el.hasMoreElements())
		{
			addDepth(deep,xml);
			addOpenNodeName(nm,xml);
			addLineFeed(xml);
			toXML((Hashtable)el.nextElement(),deep,xml);
			addCloseNodeName(nm,xml);
			addLineFeed(xml);
			
		}
		return xml;	

	}
	
	
	
	public void writeToFile(Enumeration pairs,File f,String nm)
	 throws java.io.IOException 
	{
		FileOutputStream fout = null;
		try{
		fout = new FileOutputStream(f);
		StringBuffer xml = new StringBuffer();
		xml = toXML(pairs,1,xml,nm);
		fout.write(xml.toString().getBytes());
		}finally{
			if (fout != null)
				fout.close();
			}	
	}
	
	
	public StringBuffer toXML(Hashtable el,int deep,StringBuffer xml,String nm,boolean tf)
{
	return toXML(el,deep,xml,nm,tf,true);
	}	
	
	public StringBuffer toXML(Hashtable el,int deep,StringBuffer xml,String nm,boolean tf,boolean lf)
	{
		Enumeration en = el.keys();
		if (tf)
		addHeaders(xml);
		
		addDepth(deep,xml);
		addOpenNodeName(nm,xml);
		addLineFeed(xml);
		
		while (en.hasMoreElements())
		{
			String name = (String)en.nextElement();
			Object obj = el.get(name);
			if (el instanceof MultiValuePair)
				obj = ((MultiValuePair)el).getValues(name);
			
			if (obj instanceof Vector)
			{
			
				toXML((Vector)obj,xml,name,lf);
			}
			else	
			{
				addDepth(deep,xml);
				addOpenNodeName(name,xml);
				addLineFeed(xml);
				toXML((Hashtable)obj,deep,xml);
				addCloseNodeName(name,xml);
				addLineFeed(xml);
			}
			
		}
		addCloseNodeName(nm,xml);
		addLineFeed(xml);
		
		return xml;	

	}
	
	public StringBuffer toXML(Vector v,StringBuffer xml,String nm)
{
	return toXML(v,xml,nm,true);
}
		
	public StringBuffer toXML(Vector v,StringBuffer xml,String nm,boolean lf)
	{
		Enumeration el = v.elements();
		while (el.hasMoreElements())
		{
			Object obj = (Object)el.nextElement();
			//addDepth(deep,xml);
			addLineFeed(xml);
			
			addOpenNodeName(nm,xml);
			//addLineFeed(xml);
			if (obj instanceof Hashtable)
			{
				addLineFeed(xml);
				toXML((Hashtable)obj,xml);
			}
			else
			{	
				if (lf && !obj.toString().startsWith(System.getProperty("line.separator")))
				addLineFeed(xml);
				addNodeValue(obj.toString(),xml);
			}
			addCloseNodeName(nm,xml);
			addLineFeed(xml);
			
		}
		return xml;	

	}
	
	
	
	
    public StringBuffer toXML(Hashtable pairs,int deep,StringBuffer xml)
    {
    	Enumeration lst = pairs.keys();
    	while (lst.hasMoreElements())
    	{
    		String name = (String)lst.nextElement();
			Vector v = new Vector();
			v.addElement( pairs.get(name));
			if (pairs instanceof MultiValuePair)
				v = ((MultiValuePair)pairs).getValues(name);
			Enumeration vals = v.elements();
			while (vals.hasMoreElements()) 	
			{
				Object value = vals.nextElement();//pairs.get(name);
				addDepth(deep,xml);
				addOpenNodeName(name,xml);
				if (value instanceof Hashtable)
					toXML((Hashtable)value,deep + 1,xml);
				else if (value != null)
				{
	

					addNodeValue(value.toString(),xml);	

				}
				addCloseNodeName(name,xml);
	    		addLineFeed(xml);
			}
    	}
    	return xml;	

    }
	
	

	
	boolean encodeXML = true;
	public void setEncodeXML(boolean tf){
		encodeXML = tf;
	}
    public StringBuffer addNodeValue(String value,StringBuffer xml)
    {
    		if (encodeXML)
			xml.append(encodeXML(value));
			else
			xml.append((value));
    	return xml;	

    }
	
    public StringBuffer addLineFeed(StringBuffer xml)
    {
    		if (xml.length() > 0 && ! xml.toString().endsWith(System.getProperty("line.separator")))
			xml.append(System.getProperty("line.separator"));
    	return xml;	

    }
	
    public StringBuffer addHeaders(StringBuffer xml)
    {
    		xml.append(ver).append(dtype);
			ver = "";
			dtype = "";
    	return xml;	

    }


    public StringBuffer addOpenNodeName(String name,StringBuffer xml)
    {
			xml.append("<").append(name).append(">");
    	return xml;	

    }
	
    public StringBuffer addCloseNodeName(String name,StringBuffer xml)
    {
    		xml.append("</").append(name).append(">");
    	return xml;	

    }
	

    public StringBuffer addDepth(int deep,StringBuffer xml)
    {
    	for (int ct = 0;ct < deep;ct++)
    		xml.append(depth);
    	return xml;	

    }



    public String takeOutComments(String b)
    {
		int dec = 0;
		int bi = 0;
    	for (int ct =  b.indexOf("<!--");ct > -1;ct = b.indexOf("<!--"))
    	{
			dec++;
    		String b2  = b.substring(0,ct);
			b = b.substring(ct + 4,b.length());
			bi = b.indexOf("<!--");
			int openIndex = b.indexOf("-->");

			if (bi > -1 && openIndex > bi){
				b = StringUtil.replaceFirstSubstring(b,"<!--","");
				b = StringUtil.replaceFirstSubstring(b,"-->","");
				b = b2 + "<!--" + b;
				continue;
			}
    		b  = b.substring(openIndex + 3,b.length());
			b = b2 + b;
			//("BODY: " + b);
			if (dec > 200)
			{
				tools.util.LogMgr.err("Parser Error!");
				break;
			}
			
    	}
    	return b;	

    }



    public Vector getNodeValues(String body,String node,String n)throws Exception{
    	try{
			Vector h = new Vector();
    		Vector elv = getNode(body,node);
			Enumeration el = elv.elements();
			while (el.hasMoreElements())
			{
			
				Hashtable hp = (Hashtable)el.nextElement();
				Object v = hp.get(n);
				if (v != null && !h.contains(v))
					h.add(v);
			
			}
			return h;
			
    
    }catch (Throwable e){
    	e.printStackTrace();
    }
    return new Vector();
    }	
	
	
    public Vector removeNodeValues(String body,String node,String n,String itm)throws Exception{
    	try{
    		Vector h = new Vector();
    		Vector elv = getNode(body,node);
    		Enumeration el = elv.elements();
    		while (el.hasMoreElements())
    		{
    		
    			Hashtable hp = (Hashtable)el.nextElement();
    			Object v = hp.get(n);
    			if (v != null && !h.contains(v) && ! ((String)v).equals(itm))
    				h.add(hp);
    		
    		}
    		return h;
    		
    
    }catch (Throwable e){
    	e.printStackTrace();
    }
    return new Vector();
    }	
	
    
        public Enumeration getNodeValueList(String body,String node,String n)throws Exception{
    	try{
			return getNodeValues(body,node,n).elements();
    
    }catch (Throwable e){
    	e.printStackTrace();
    }
    return new Vector().elements();
    }
	
	
	
    public Vector getNode(String body,String node)throws Exception{
    	try{
    Hashtable nvp = parseToHashtable(body);
    
    NameValue mv = null;
    Enumeration en = nvp.keys();
    if (en.hasMoreElements())
    	mv = (NameValue)nvp.get(en.nextElement());
    
    if (mv != null)
    {
    	body = mv.getTrimmedValue();
    	nvp = null;
    	nvp = (MultiValuePair)parseToHash(body,true);
    }
    
    Vector parts = null;
    if (nvp != null)
    parts = ((MultiValuePair)nvp).getValues(node);
    if (parts != null)
    	return parts;
    
    
    }catch (Throwable e){
    	e.printStackTrace();
    }
    return new Vector();
    }	
	


	public  void setNode(String body, String node,Vector v,String wfile){
    	try{
		String wv = 	getWebVersion(body);
	setWebVersion(wv);
    Hashtable nvp = parseToHashtable(body);
    NameValue mv = null;
    Enumeration en = nvp.keys();
 	String outer = "";
    if (en.hasMoreElements())
	{
		outer = (String)en.nextElement();
    	mv = (NameValue)nvp.get(outer);
	}
    if (mv != null)
    {
    	body = mv.getTrimmedValue();
    	nvp = null;
    	nvp = (MultiValuePair)parseToHash(body,true);
    }
    	((MultiValuePair)nvp).setValues(node,v);
    	writeToFile(nvp,wfile,outer,true);
    	
    
    }catch (Throwable e){
    	e.printStackTrace();
    	
    }
    }	

    public  void setNode(String body, String node,Vector v,String wfile,int index){
    	try{

    Hashtable nvp = parseToHashtable(body);
    NameValue mv = null;
    Enumeration en = nvp.keys();
    String outer = "";
    if (en.hasMoreElements())
    {
    	outer = (String)en.nextElement();
    	mv = (NameValue)nvp.get(outer);
    }
    if (mv != null)
    {
    	body = mv.getTrimmedValue();
    	nvp = null;
    	nvp = (MultiValuePair)parseToHash(body,true);
    }
    	((MultiValuePair)nvp).setValues(node,v,index);
    	writeToFile(nvp,wfile,outer,true);
    	
    
    }catch (Throwable e){
    	e.printStackTrace();
    	
    }
    }	





 }