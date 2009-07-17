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

package org.jiql.db.objs;
import java.util.*;
import tools.util.EZArrayList;
import tools.util.SEXParser;
import tools.util.NameValue;
import org.jiql.util.*;
import java.sql.*;
import java.io.Serializable;
public class jiqlTableInfo implements Serializable
{
private long tid = -1;
EZArrayList autoincrement = new EZArrayList();

public EZArrayList listAutoIncrements(){
	return autoincrement;
}
public void addAutoIncrement(String nn){
	if (nn != null && !autoincrement.contains(nn))
	autoincrement.add(nn);
}

public void setTId(long t){
	tid = t;
}

public long getTId(){
	return tid;
}

public jiqlTableInfo(String t)throws SQLException{
	if (t ==null)return;
	//(" WTEFROM " + t);
	SEXParser sexp = new SEXParser();
	Enumeration en = sexp.parse(t);
	/*try{
	
	en = sexp.parse(t);
	}catch (Exc)*/
	while (en.hasMoreElements())
	{
		NameValue nv = (NameValue)en.nextElement();
	Enumeration eni = null;
	try{
	
	 eni = sexp.parse(nv.value);
	}catch (Exception e){
	
		e.printStackTrace();
		throw jiqlException.get("tableinfo_parse_error",t + " Table Info Parse Error " + nv.value);
	
		
	}
	while (eni.hasMoreElements())
	{
		nv = (NameValue)eni.nextElement();
		//(nv.name + " TI FROM NV " + nv.value);
		if (nv.name.equals("name"))
			setName(nv.value);
		else if (nv.name.equals("prefix_value"))
			prefixValue = nv.value;
		else if (nv.name.equals("prefix"))
			prefix = new Boolean(nv.value).booleanValue();
		else if (nv.name.equals("tableleafs"))
			tableleafs = new Boolean(nv.value).booleanValue();
		else if (nv.name.equals("fieldList"))
			fieldList = new EZArrayList(new StringTokenizer(nv.value,";"));


		else if (nv.name.equals("primarykeys"))
			primarykeys = new EZArrayList(new StringTokenizer(nv.value,";"));
		else if (nv.name.equals("autoincrements"))
			autoincrement = new EZArrayList(new StringTokenizer(nv.value,";"));

		else if (nv.name.equals("notnulls"))
			notnulls = new EZArrayList(new StringTokenizer(nv.value,";"));
		else if (nv.name.equals("constraints"))
		{
			 Enumeration enc = sexp.parse(nv.value);
			while (enc.hasMoreElements())
			{
				nv = (NameValue)enc.nextElement();
				Hashtable ch = sexp.parseToHash(nv.value);
				//(nv.name + " connFROM NV " + ch);
				jiqlConstraint jc = new jiqlConstraint();
				String cn = (String)ch.get("name");
				jc.setName(cn);
				String ty = (String)ch.get("type");
				jc.setType(Integer.parseInt(ty));
				String tar = (String)ch.get("target");
				EZArrayList tarv = new EZArrayList(new StringTokenizer(tar,";"));
				jc.addEnumeration(tarv.iterator());
				Hashtable rh = (Hashtable)ch.get("references");
				if (rh != null)
				{
				jiqlFunction jfr = new jiqlFunction();
				String rn = (String)rh.get("name");
				jfr.setName(rn);
				String rtar = (String)rh.get("target");
				EZArrayList rtarv = new EZArrayList(new StringTokenizer(rtar,";"));
				jfr.addEnumeration(rtarv.iterator());
				jc.setReference(jfr);
					
				}
				addConstraint(jc);
			}

			
		}
		else if (nv.name.equals("defaultvalues"))
		{
			 Enumeration enc = sexp.parse(nv.value);
			while (enc.hasMoreElements())
			{
				nv = (NameValue)enc.nextElement();
				Hashtable ch = sexp.parseToHash(nv.value);
				String cn = (String)ch.get("field");
				Object fv = ch.get("value");
				defaultValues.put(cn,fv);
			}

			
		}
		
		
	}

	}
	
}
String name = null;
Hashtable constraints = new Hashtable();
EZArrayList notnulls = new EZArrayList();
EZArrayList primarykeys = new EZArrayList();
EZArrayList fieldList = new EZArrayList();

Hashtable defaultValues = new Hashtable();
public void addPrimaryKey(String nn){
	if (nn != null && !primarykeys.contains(nn))
	primarykeys.add(nn);
}

public void setPrimaryKeys(EZArrayList nn){
	primarykeys = nn;
}


public void setNotNulls(EZArrayList nn){
	//(getName() + " notnulls 2 " + nn);

	notnulls = nn;
}

public void setFieldList(EZArrayList nn){
	fieldList = nn;
}
public void addFieldList(String f){
	fieldList.add(f);
}

public void setDefaultValues(Hashtable df){
	defaultValues = df;
}

public void addDefaultValues(String f,Object v){
	defaultValues.put(f,v);
}

public Hashtable getDefaultValues(){
	return defaultValues;
}


public EZArrayList getPrimaryKeys(){
	return primarykeys;
}

public EZArrayList getFieldList(){
	return fieldList;
}

public void addNotNull(String nn){
	if (nn != null && !notnulls.contains(nn))
	notnulls.add(nn);
}

public EZArrayList getNotNulls(){
	return notnulls;
}


public void setName(String t){
	name = t;
}

public String getName(){
	return name;
}

public void addConstraint(jiqlConstraint jc){
	constraints.put(jc.name,jc);
}

public Hashtable getConstraints(){
	return constraints;
}

public jiqlConstraint getConstraint(String n){
	return (jiqlConstraint)constraints.get(n);
}

/*
 <tableinfo>
  <name>
  </name>
  <constraints>
   <constraint>
    <name>
    </name>
    <type>
    </type>
    <target>
    </target>
    <references>
     <name>
     </name>
     <target>
     </target>
    </references>
   </constraint>
  </constraints>
 </tableinfo>
 	*/
String prefixValue = "jiql";
boolean prefix = true;
boolean tableleafs = true;
public boolean isPrefix(){
	return prefix;
}
public boolean hasTableLeafs(){
	return tableleafs;
}
public String getPrefixName(){
	if(!prefix)return "";
	return prefixValue;
}

public void setPrefix(boolean tf){
	 prefix = tf;
}
public void setTableLeafs(boolean tf){
	tableleafs = tf;
}
public void setPrefixName(String pn){
	prefixValue = pn;
}

public String toString(){
	StringBuffer xml = new StringBuffer();
	SEXParser sp = new SEXParser();
	sp.addOpenNodeName("tableinfo",xml);
	//.addLineFeed(xml);
	sp.addOpenNodeName("name",xml);
	xml.append(getName());
	sp.addCloseNodeName("name",xml);

	sp.addOpenNodeName("prefix",xml);
	xml.append(prefix);
	sp.addCloseNodeName("prefix",xml);

	sp.addOpenNodeName("tableleafs",xml);
	xml.append(tableleafs);
	sp.addCloseNodeName("tableleafs",xml);

	sp.addOpenNodeName("prefix_value",xml);
	xml.append(prefixValue);
	sp.addCloseNodeName("prefix_value",xml);

	if (primarykeys.size() > 0)
	{
		sp.addOpenNodeName("primarykeys",xml);
		xml.append(primarykeys.toDelimitedString(";"));
		sp.addCloseNodeName("primarykeys",xml);
	}
	if (autoincrement.size() > 0)
	{
		sp.addOpenNodeName("autoincrements",xml);
		xml.append(autoincrement.toDelimitedString(";"));
		sp.addCloseNodeName("autoincrements",xml);
	}	
	
		if (fieldList.size() > 0)
	{
		sp.addOpenNodeName("fieldList",xml);
		xml.append(fieldList.toDelimitedString(";"));
		sp.addCloseNodeName("fieldList",xml);
	}
					//(getName() + " notnulls " + notnulls);

	if (notnulls.size() > 0)
	{
		sp.addOpenNodeName("notnulls",xml);
		xml.append(notnulls.toDelimitedString(";"));
		sp.addCloseNodeName("notnulls",xml);
	}

	
	if (constraints.size() > 0)
	{
	sp.addOpenNodeName("constraints",xml);
	//.addLineFeed(xml);
	Enumeration en = constraints.keys();
	while (en.hasMoreElements())
	{
		String jn = (String)en.nextElement();
		jiqlConstraint jc = (jiqlConstraint)constraints.get(jn);
		sp.addOpenNodeName("constraint",xml);
		//.addLineFeed(xml);
		sp.addOpenNodeName("name",xml);
		xml.append(jn);
		sp.addCloseNodeName("name",xml);
		//.addLineFeed(xml);
		sp.addOpenNodeName("type",xml);
		xml.append(jc.getType());
		sp.addCloseNodeName("type",xml);
		//.addLineFeed(xml);
		sp.addOpenNodeName("target",xml);
		xml.append(jc.toDelimitedString(";"));
		sp.addCloseNodeName("target",xml);
		//.addLineFeed(xml);
		jiqlFunction jref = jc.getReference();
		if (jref != null)
		{
		sp.addOpenNodeName("references",xml);
		//.addLineFeed(xml);
		sp.addOpenNodeName("name",xml);
		xml.append(jref.getName());
		sp.addCloseNodeName("name",xml);
		//.addLineFeed(xml);
		sp.addOpenNodeName("target",xml);
		xml.append(jref.toDelimitedString(";"));
		sp.addCloseNodeName("target",xml);
		//.addLineFeed(xml);			
		sp.addCloseNodeName("references",xml);
		//.addLineFeed(xml);
		}	
		sp.addCloseNodeName("constraint",xml);
		//.addLineFeed(xml);
	}
	sp.addCloseNodeName("constraints",xml);

		
	}
	
	if (defaultValues.size() > 0)
	{
	sp.addOpenNodeName("defaultvalues",xml);
	//.addLineFeed(xml);
	Enumeration en = defaultValues.keys();
	while (en.hasMoreElements())
	{
		String jn = (String)en.nextElement();
		Object jc = defaultValues.get(jn);
		sp.addOpenNodeName("defaultvalue",xml);
		//.addLineFeed(xml);
		sp.addOpenNodeName("field",xml);
		xml.append(jn);
		sp.addCloseNodeName("field",xml);
		//.addLineFeed(xml);
		sp.addOpenNodeName("value",xml);
		xml.append(jc.toString());
		sp.addCloseNodeName("value",xml);
		//.addLineFeed(xml);
	
		sp.addCloseNodeName("defaultvalue",xml);
		//.addLineFeed(xml);
	}
	sp.addCloseNodeName("defaultvalues",xml);

		
	}
	
	
	sp.addCloseNodeName("tableinfo",xml);
	return xml.toString();
}

}


