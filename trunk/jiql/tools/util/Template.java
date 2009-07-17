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
 * This class is used to store, and process Data.
 **/
public class Template  implements Serializable
{
    private StringBuffer m_template = new StringBuffer("");
    private StringBuffer m_template2 = new StringBuffer("");
    long maxF = 1000000;
		private String m_file = null;

			public Template (URL attr)
	throws Exception
	{
		this(attr.getFile());
	}

	public  Template (File file,boolean cr)
	throws Exception
	{
		if (cr)
		file.createNewFile();
		if ( file.length() > maxF)tools.util.LogMgr.red(file.length() + " Large Size Template " + file);
			String templ = new String(SharedMethods.getBytesFromFile(file));
	    m_template = new StringBuffer(templ);
	    m_template2 = new StringBuffer(templ);
			m_file = file.toString();
	}

    /**
     * Constructs a new Template with the specified template.
     **/
    public  Template (String file,boolean cr)
    throws Exception
    {
    if (cr)
    new File(file).createNewFile();
			if ( file.length() > maxF)tools.util.LogMgr.red(file.length() + " Large Size Template " + file);

    		String templ = new String(SharedMethods.getBytesFromFile(file));
        m_template = new StringBuffer(templ);
        m_template2 = new StringBuffer(templ);
    		m_file = file;
    }


	public  Template (File file)
	throws Exception
	{
					if ( file.length() > maxF)tools.util.LogMgr.red(file.length() + " Large Size Template " + file);

			String templ = new String(SharedMethods.getBytesFromFile(file));
	    m_template = new StringBuffer(templ);
	    m_template2 = new StringBuffer(templ);
			m_file = file.toString();
	}

    /**
     * Constructs a new Template with the specified template.
     **/
    public  Template (String file)
    throws Exception
    {
    				if ( new File(file).length() > maxF)tools.util.LogMgr.red(new File(file).length() + " Large Size Template " + file);

    		String templ = new String(SharedMethods.getBytesFromFile(file));
        m_template = new StringBuffer(templ);
        m_template2 = new StringBuffer(templ);
    		m_file = file;
    }
	
	    /**
     * Constructs a new Template with the specified template.
     **/
    public  Template ()
    {
        m_template = new StringBuffer();
        m_template2 = new StringBuffer();
    }


	  /**
     * Constructs a new Template with the specified template.
     * @param templ the Template
     **/
    public  Template (StringBuffer templ)
    {
        m_template = new StringBuffer(templ.toString());
        m_template2 = new StringBuffer(templ.toString());
    }


	  /**
     * Constructs a new Template with the specified template.
     * @param templ the Template
     **/
    public  Template (Template templ)
    {
        m_template = new StringBuffer(templ.toString());
        m_template2 = new StringBuffer(templ.toString());
    }


	
	
    /**
     * Sets this Template with the specified template.
     *
     * @param templ the template
     **/
    public  void setTemplate (String templ)
    {
        m_template = new StringBuffer(templ);
        m_template2 = new StringBuffer(templ);
    }

	    public  void setTemplate (StringBuffer templ)
    {
        setTemplate(templ.toString());
    }
    
    /**
     * Sets the Tag for the specified tag.
     *
     * @param tag the tag template to be replaced
     * @param tagvalue the value of the tag
     **/
    public boolean setTag(String tag,String tagvalue)
    {
		if (tag.equals(tagvalue)){
			tools.util.LogMgr.debug(" CANNOT setTag Template - IDENTICAL " + tag);	
			return false;
		}
		if (m_template.toString().indexOf(tag) > -1)
		{
        	m_template = SharedMethods.replaceSubstringBuffer(m_template,tag,tagvalue);
			return true;
		}
		else
			return false;
	}
	
	 public void setText(String tag,String tagvalue)
    {
	   	m_template = new StringBuffer( StringUtil.replaceText(m_template.toString(),tag,tagvalue));

	}
	
	
	public int indexOf(String tag)
	{
		return m_template.indexOf(tag);
	}
		public int icIndexOf(String tag)
	{
		return m_template.toString().toLowerCase().indexOf(tag.toLowerCase());
	}
	
	
		public int lastIndexOf(String tag)
	{
		return m_template.lastIndexOf(tag);
	}
	
	
	public int indexOfText(String tag)
	{
		return StringUtil.indexOfText(m_template.toString(),tag);
	}
	
	public boolean contains(String tag)
	{
		return (indexOf(tag) > -1);
	}	
	
	  /**
     * Sets the Tag for the specified tag.
     *
     * @param tag the tag template to be replaced
     * @param tagvalue the value of the tag
     **/
    public void setTag(String tag,Template tagvalue)
    {
			setTag(tag,tagvalue.toString());
    }

    public void setLastTag(String tag,Template tagvalue)
    {
			setLastTag(tag,tagvalue.toString());
    }

	    public void setFirstTag(String tag,String tagvalue)
    {
        m_template = SharedMethods.replaceFirstSubstringBuffer(m_template,tag,tagvalue);
    }

	    public void setLastTag(String tag,String tagvalue)
    {
        m_template = StringUtil.replaceLastSubstringBuffer(m_template,tag,tagvalue);
    }
	
    /**
     * Sets the Tag for the specified tag.
     *
     * @param tag the tag template to be replaced
     * @param tagvalue the value of the tag
     **/
    public void setTag(String pat,int wit)
    {
        String ret = "";
        ret = ret + wit;
        setTag(pat,ret);
    }
	
    public void setTag(String pat,long wit)
    {
        String ret = "";
        ret = ret + wit;
        setTag(pat,ret);
    }
	

    /**
     * Prints out the data to a printstream and clears the present
     * value of the data back to the default template.  This is ideal if
     * you are wrting rows of data so you can use the same object instead
     * of creating a new Template object
     *
     * @param outp the OutputStream
     **/
    public synchronized void store(OutputStream  outp)
    	throws Exception
	{
		
            outp.write(m_template.toString().getBytes());
            clear();
    }
    
    

	
	    public void serialize(PrintWriter  outp)
    	throws Exception
	{
            outp.print(m_template.toString());
			outp.flush();
            //clear();
    }
	
		    public void store(String  file)
    	throws Exception
	{
		store(new File(file));
	}
	
	public void store(File  file)
throws Exception
{
	FileUtil.writeTextToFile(file,m_template.toString());
/*FileOutputStream str = null;
try{
str = new FileOutputStream(file);
PrintWriter outp = new PrintWriter(str,true);
serialize(outp);
str.close();
}finally{
	if (str != null)
		str.close();
}*/
}
	
	
	public void store()
  throws Exception
	{
		store(m_file);
	}

	



    /**
     * Clears the present value of the data back to the default template.
     * This is ideal if you are wrting rows of data so you can use the same
     * object instead of creating a new Template object
     *
     **/
    public void clear()
    {
        m_template = new StringBuffer(m_template2.toString());

    }
    
        public void reset()
    {
        m_template = new StringBuffer();

    }

    /**
     * Appends the value of the specified Template to the end of
     * this Template.
     *
     * @param hout the Template
     **/
    public Template append(Template hout)
    {
        if(hout != null)
            m_template = m_template.append(hout.toString());
    		return this;
    }

    /**
     * Prepends the value of the specified Template to the begining of
     * this Template.
     *
     * @param hout the Template
     **/
    public Template prepend(Template hout)
    {
        if(hout != null)
            m_template = new StringBuffer(hout.toString() + m_template.toString()) ;
        		return this;

    }



    /**
     * Appends the value of the specified Template String to the end of
     * this Template.
     *
     * @param templ the template String
     **/
    public Template append(String templ)
    {
        if(templ != null)
            m_template.append(templ);
    	    		return this;

    }

	
	    public Template append(StringBuffer templ)
    {
			return append(templ.toString());
    }
    	
    /**
     * Prepends the value of the specified template String to the beginning of
     * this Template.
     *
     * @param templ the template String
     **/
    public Template prepend(String templ)
    {
        if(templ != null)
            m_template = new StringBuffer(templ + m_template.toString());
        		return this;

    }



    /**
     * Returns the present value of the template as a String.
     * @return the String value
     **/
    public String toString()
    {
        return m_template.toString();
    }
	

	
	  /**
     * Returns the present value of the template as a StringBuffer.
     * @return the StringBuffer value
     **/
    public StringBuffer getValue()
    {
        return m_template;
    }
	
	    public void setValue(String val)
    {
        m_template = new StringBuffer(val);
    }

	    public void appendln(String val)
    {
        append(val +System.getProperty("line.separator"));
    }
    
    	    public void prependln(String val)
    {
        prepend(val +System.getProperty("line.separator"));
    }

	
	  /**
     * Returns the length of the present value of the template.
     * @return the length
     **/
    public int length()
    {
        return m_template.length();
    }



}