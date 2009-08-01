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
//SharedMethods
public class StringUtil
{


    static public Hashtable parseQueryString(String s) {

    String valArray[] = null;
    
    if (s == null) {
        throw new IllegalArgumentException("Cannot Parse Null Query String");
    }
    Hashtable ht = new Hashtable();
    StringBuffer sb = new StringBuffer();
    StringTokenizer st = new StringTokenizer(s, "&");
    while (st.hasMoreTokens()) {
        String pair = (String)st.nextToken();
        int pos = pair.indexOf('=');
        if (pos == -1) {
    	// XXX
    	// should give more detail about the illegal argument
    	throw new IllegalArgumentException("Query String missing = token");
        }
        String key = parseName(pair.substring(0, pos), sb);
        String val = parseName(pair.substring(pos+1, pair.length()), sb);
        if (ht.containsKey(key)) {
    	String oldVals[] = (String []) ht.get(key);
    	valArray = new String[oldVals.length + 1];
    	if(oldVals.length > 1500){
    		tools.util.LogMgr.red(key + " Excess Parameter Value ");	
    		throw new IllegalArgumentException(key + " Excess Parameter Value ");	
		}
    	for (int i = 0; i < oldVals.length; i++) 
    	    valArray[i] = oldVals[i];
    	valArray[oldVals.length] = val;
        } else {
    	valArray = new String[1];
    	valArray[0] = val;
        }
        ht.put(key, valArray);
        if(ht.size() > 5000){
    		tools.util.LogMgr.red(key + " Excess Parameters");	
        	throw new IllegalArgumentException(" Excess Parameters ");	
	}
    }
    return ht;
    }


	public static StringBuffer trimQuotes(StringBuffer selsb){
		selsb = SharedMethods.replaceSubstringBuffer(selsb,"\"","");
		selsb = SharedMethods.replaceSubstringBuffer(selsb,"'","");
		selsb = SharedMethods.replaceSubstringBuffer(selsb,"`","");
		return selsb;
		
	}

    static private String parseName(String s, StringBuffer sb) {
    sb.setLength(0);
    for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i); 
        switch (c) {
        case '+':
    	sb.append(' ');
    	break;
        case '%':
    	try {
    	    sb.append((char) Integer.parseInt(s.substring(i+1, i+3), 
    					      16));
    	    i += 2;
    	} catch (NumberFormatException e) {
    	    // XXX
    	    // need to be more specific about illegal arg
    	    throw new IllegalArgumentException("Invalid Encoded String");
    	} catch (StringIndexOutOfBoundsException e) {
    	    String rest  = s.substring(i);
    	    sb.append(rest);
    	    if (rest.length()==2)
    		i++;
    	}
    	
    	break;
        default:
    	sb.append(c);
    	break;
        }
    }
    return sb.toString();
    }


		    public static String toLink(String wit)
    {
    	 wit = replaceSubstring(wit,"%","THIS_IS_A_PERCENT");
	     wit = replaceSubstring(wit," ","%20");
	     wit = replaceSubstring(wit,"#","%23");
    	 wit = replaceSubstring(wit,"&","%26");
    	 wit = replaceSubstring(wit,"=","%3D");
    	 wit = replaceSubstring(wit,"\r","%0D");
    	 wit = replaceSubstring(wit,"\n","%0A");
    	 wit = replaceSubstring(wit,"/","%2F");
    	  wit = replaceSubstring(wit,"?","%3f");
     wit = replaceSubstring(wit,";","%3B");
    	  wit = replaceSubstring(wit,"\"","%22");
    	 wit = replaceSubstring(wit,"$","%24");
    	 wit = replaceSubstring(wit,"!","%21");
    	 wit = replaceSubstring(wit,"THIS_IS_A_PERCENT","%25");
    	 
    	 
    	 
       	return wit;
    }
    /*
     *Dollar ("$")
 Ampersand ("&")
 Plus ("+")
 Comma (",")
 Forward slash/Virgule ("/")
 Colon (":")
 Semi-colon (";")
 Equals ("=")
 Question mark ("?")
 'At' symbol ("@")
 24
26
2B
2C
2F
3A
3B
3D
3F
40 36
38
43
44
47
58
59
61
63
64 
    */

	public static String removeNL(String src){
		src = replaceSubstring(src,"\r\n",")(");
		src = replaceSubstring(src,"\r",")");
		src = replaceSubstring(src,"\n","(");
		return src;
	}
	
	public static int indexOfText(String src,String bl){
		//src = removeNL(src);
		//bl = removeNL(bl);
		//bl = bl.trim();
		if (src.indexOf("\r\n") > -1 && (bl.indexOf("\r\n") < 0 && bl.indexOf("\n") > -1))
		{
			bl = replaceSubstring(bl,"\n","NG_SL_N$!");
			bl = replaceSubstring(bl,"NG_SL_N$!","\r\n");
			//("indexText SUB NL");	
	
		}
		//(bl + "::::::::::::" + src);
		return src.indexOf(bl);
	}
	
	public static String replaceText(String src,String bl,String wi){
		if (bl.equals(wi))return src;
		//bl = bl.trim();
		if (src.indexOf("\r\n") > -1 && (bl.indexOf("\r\n") < 0 && bl.indexOf("\n") > -1))
		{
			bl = replaceSubstring(bl,"\n","NG_SL_N$!");
			bl = replaceSubstring(bl,"NG_SL_N$!","\r\n");
			//("replaceText SUB NL");	
		}
		if (src.indexOf("\r\n") > -1 && (wi.indexOf("\r\n") < 0 && wi.indexOf("\n") > -1))
		{
			wi = replaceSubstring(wi,"\n","NG_SL_N$!");
			wi = replaceSubstring(wi,"NG_SL_N$!","\r\n");
			//("replaceText SUB WII NL");	
		}
		
		int i =  indexOfText(src,bl);
		//(i + " replaceText SUB NL 2");	

		if (i > -1){
			
			
			
			String src1 = src.substring(0,i);
			String src2 = src.substring(i + bl.length(),src.length());
			src = src1 + wi + src2;	
		}
		return src;
	}
	
	public static String env(String s){
	
		if (s.startsWith("$")){
		s = s.substring(1,s.length());
		s = "\\$" + s;
		}	
		return s;
	}
	
	
					public static String strip(String s){
				if (!StringUtil.isRealString(s))
					return s;
				String an = "abcdefghijk lmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()~_+-/.\\=?,;:[]{}|`<>";	
				char[] c = s.toCharArray();
				StringBuffer sub = new StringBuffer();
				for (int ct = 0; ct < c.length;ct++)
					if (an.indexOf(c[ct]) > -1)
						sub.append(c[ct]);
				//	else //(s + ":" + c[ct] + ":STRIPPED FROM STRING");
				return sub.toString();	
	}
	
	
    public static String fromLink(String wit)
{
 wit = replaceSubstring(wit,"&#58;",":");
 wit = replaceSubstring(wit,"&#92;","\\");
 wit = replaceSubstring(wit,"%3C","<");
 wit = replaceSubstring(wit,"%3E",">");
return wit;
}
	
    public static String webEncode(String wit)
{
 //wit = replaceSubstring(wit,"&#58;",":");
 //wit = replaceSubstring(wit,"&#92;","\\");
 wit = replaceSubstring(wit,"<","&#60");
 wit = replaceSubstring(wit,">","&#62");
 wit = replaceSubstring(wit,"\"","&#34");
return wit;
}
	

	public static boolean isBetween(String src,String targ,String left,String right){
		int i1 = src.indexOf(targ);
		if (i1 < 0)
			return false;
		String s1 = src.substring(0,i1);
		int i2 = s1.lastIndexOf(left);
		if (i2 < 0)
			return false;
		String s2 = src.substring(i1 + targ.length());
		int i3 = s2.indexOf(right);
		if (i3 > -1)
			return true;
		return false;	
	}
	public static boolean isRealString(String s){
				if (s != null)s = s.trim();
				if (s == null || s.equals("null") || s.length() < 1)
					return false;
				return true;	
	}
	
	public static String getTrimmedValue(StringBuffer t)
{
	return getTrimmedValue(t.toString());
}
	
 	 		public static String getTrimmedValue(String t)
 	{
 		if (t != null)
 		{
 			t = t.trim();
 			if (t.startsWith("'") || t.startsWith("\"") || t.startsWith("`"))
 				t = t.substring(1,t.length());
 			if (t.endsWith("'") || t.endsWith("\"")  || t.endsWith("`"))
 				t = t.substring(0,t.length() - 1);
 			return t;
 		}
 		return null;
 	}
 	
 	public static int charCount(String m,String c){
 		int cter = 0;
 		for (int ct = 0;ct < 10000;ct++)
 		 {
 		 
 		 	if (m.indexOf(c) < 0)break;	
 		 	cter+=1;
 		 	m = replaceFirstSubstring(m,c,"");
 		 }
 		 return cter;
 	}
 	
 	    public static String replaceSubstring(String mn,String pat,int wit)
    {
    	return replaceSubstring(mn,pat,String.valueOf(wit));
	}
 	
 	    public static String replaceSubstring(String mn,String pat,String wit)
    {
        try
        {
            String wit2;
            if(wit == null)
                wit = "";
            if(pat == null)
                return mn;
            if(mn == null)
                return "";
			int cter = 0;
			if (pat.equals(wit))
			{
				tools.util.LogMgr.red("StringUtil.replaceSubstring.equals " + pat);	
				return mn;
			}
			if (wit.indexOf(pat) > -1)
			{
				tools.util.LogMgr.red(pat + " StringUtil.replaceSubstring.startsWith " + wit);	
				return mn;
			}
            for(int indx = mn.indexOf(pat);indx > -1 ; indx = mn.indexOf(pat))
            {
              wit2 = mn.substring(0,indx) + wit;
              mn = wit2 + mn.substring(indx + pat.length(),mn.length());
			  //("CTER " + cter++);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return mn;
    }

    public static String replaceFirstSubstring(String mn,String pat,String wit)
{
    try
    {
        String wit2;
        if(wit == null)
            wit = "";
        if(pat == null)
            return mn;
        if(mn == null)
            return "";

        int indx = mn.indexOf(pat);
    		if (indx < 0)
    			return mn;
        wit2 = mn.substring(0,indx) + wit;
        mn = wit2 + mn.substring(indx + pat.length(),mn.length());
    }
    catch (Exception e)
    {
        e.printStackTrace();
    }
    return mn;
}

   public static StringBuffer replaceLastSubstringBuffer(StringBuffer mn,String pat,String ret)
    {
        return new StringBuffer(replaceLastSubstring(mn.toString(),pat,ret));

    }
    public static String replaceLastSubstring(String mn,String pat,String wit)
{
    try
    {
        String wit2;
        if(wit == null)
            wit = "";
        if(pat == null)
            return mn;
        if(mn == null)
            return "";

        int indx = mn.lastIndexOf(pat);
    		if (indx < 0)
    			return mn;
        wit2 = mn.substring(0,indx) + wit;
        mn = wit2 + mn.substring(indx + pat.length(),mn.length());
    }
    catch (Exception e)
    {
        e.printStackTrace();
    }
    return mn;
}


static int mll = 70;

	public static String setLF(String str) throws Exception{
	return setLF(str,mll);
}

	public static String setLF(String str,int ml) throws Exception{
			String body = "";
			
			LineNumberReader lin = new LineNumberReader(new StringReader(str));
			String tok = null;
				while ((tok = lin.readLine()) != null)
				{
					//if (tok.length() == 0 && body == null)
				//		body = "";
				//	else if (body != null)
					{
						if (tok.length() > ml)
						{
							//("TOK: " + tok);
							String tok2 = "";
							while ( tok.length() > ml)
							{
								tok2 = tok2 + tok.substring(0,ml) + "\r\n";
								tok = tok.substring(ml,tok.length());
							}
							
							tok = tok2 + tok;

						}
						body = body + tok + "\r\n";	
					}
					
						
		
				}
				if (body == null)
					body = "";
					
				return body;
			
			}

			public static TempData setLFeed(InputStream str) throws Exception{
			return setLFeed(str,mll);
		}

			public static TempData setLFeed(InputStream str,int ml) throws Exception{
					TempData body = TempData.create(str.available());
					
					LineNumberReader lin = new LineNumberReader(new InputStreamReader(str));
					String tok = null;
						while ((tok = lin.readLine()) != null)
						{
							//if (tok.length() == 0 && body == null)
						//		body = "";
						//	else if (body != null)
							{
								if (tok.length() > ml)
								{
									//("TOK: " + tok);
									String tok2 = "";
									while ( tok.length() > ml)
									{
										tok2 = tok2 + tok.substring(0,ml) + "\r\n";
										tok = tok.substring(ml,tok.length());
									}
									
									tok = tok2 + tok;
		
								}
								body.add(new String(javax.mail.internet.MimeUtility.decodeText(tok + "\r\n")).getBytes());	
							}
							
								
				
						}
						if (body == null)
							body = TempData.create(0);
							
						return body;
					
					}



		public	static void pad(StringBuffer b,int ct,char c )
	
		{
			for (int ct2 = 0;ct2 < ct;ct2++)
				b.append(c);
		}

	
}