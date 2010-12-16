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
import java.sql.SQLException;
import java.io.*;
import java.net.*;
import java.util.*;
import tools.util.EZArrayList;
import tools.util.NameValuePairs;
import tools.util.NameValue;

import tools.util.StringUtil;
import org.jiql.db.*;
import org.jiql.db.objs.*;
public class Gateway
{
	static Gateway gaeg = null;
	public static Gateway get(Properties props){
		if (gaeg == null)
		gaeg = new GAppEngineGateway();
		gaeg.setProperties(props);
		return gaeg;
		}
Properties properties = null;
public Properties getProperties(){
	return properties;
}
public void setProperties(Properties props){
	 properties = props;
}

			public Vector<Row> getIdentity(SQLParser sqp)throws SQLException{
				Vector<Row> o = new Vector<Row>();

						Row nv = new Row();

						nv.put("@@identity",0);

						
					o.add(nv);
						

	
				return o;

			}



    Vector<Row> typeinfo = null;
	EZArrayList	typeinfoCols = null;
	public EZArrayList getTypeinfoCols(){
		return typeinfoCols;
	}

	EZArrayList	typeinfoColTypeNames = null;
	public EZArrayList getTypeinfoColTypeNames(){
		return typeinfoColTypeNames;
	}
    public Vector<Row> getTypeInfo( SQLParser sqp) throws SQLException {
	if (typeinfo == null)
	{
	//	try{
				//("getTypeInfo 2 " + getClass().getResource("typeinfo.properties"));

		InputStream inp = getClass().getResourceAsStream("typeinfo.properties");
		EZArrayList ez1 = new EZArrayList(inp);
		typeinfoCols = new EZArrayList(new StringTokenizer(ez1.elementAt(0).toString(),","));
		ez1.removeElementAt(0);
		typeinfoColTypeNames = new EZArrayList(new StringTokenizer(ez1.elementAt(0).toString(),","));
		ez1.removeElementAt(0);

		EZArrayList rows = null;
				typeinfo = new Vector<Row>();
				//("getTypeInfo 3 " + typeinfo);

						Row nv = null;
		String val = null;
		Object o = null;
		for (int ct = 0;ct < ez1.size();ct++)
		{
			rows = new EZArrayList(new StringTokenizer(ez1.elementAt(ct).toString(),","));
			nv = new Row();
		
				for (int ctc = 0;ctc < typeinfoCols.size();ctc++)
		{
			val = rows.elementAt(ctc).toString();
			if (!val.equalsIgnoreCase("null"))
			{
			o = jiqlCellValue.getObj(val,ColumnInfo.getTypeFromName(typeinfoColTypeNames.elementAt(ctc).toString()),sqp);
			
			//(ColumnInfo.getTypeFromName(typeinfoColTypeNames.elementAt(ctc).toString()) + ": get oo " + typeinfoColTypeNames.elementAt(ctc) + ":" + typeinfoCols.elementAt(ctc) + ":" + o.getClass().getName());
			nv.put(typeinfoCols.elementAt(ctc),o);
			}
		
		}
		
			typeinfo.add(nv);
		
		}
		
	//	}catch (IOException io){
	//		throw new SQLException(io.toString());
	//	}
	}
					//("getTypeInfo 4 " + typeinfo);

        return typeinfo;

    }







			
			public Vector<Row> getFoundRows(SQLParser sqp)throws SQLException{
				Vector<Row> o = new Vector<Row>();
							Row nv = new Row();

						nv.put("FOUND_ROWS()",0);
						o.add(nv);
				return o;

			}

	/*public static Gateway get(){
		if (gaeg == null)
		gaeg = new GAppEngineGateway();
		return gaeg;
		}*/

	/*public void deleteContstraint(Properties props,String t,String cn)throws SQLException{

		}
		public void addContstraint(Properties props,String t,jiqlConstraint c)throws SQLException{

		}*/

		/*		public Hashtable getConstraints(Properties props,String t)throws SQLException{
return new Hashtable();
				}*/	

		/*public void removeTableInfo(String t)throws SQLException{


		}*/

/*public void writeTableInfo(String t,String f,String ty)throws SQLException{
}*/
			public void writeTableInfo(String t,Hashtable hash)throws SQLException{
			}

	public Vector showTables()throws SQLException{
	return new Vector();
	}
	
	
		public int getAutoIncrementInt(SQLParser sqp,String f)throws SQLException{
		return 0;	
	}
		
	public Vector jiqlDescribeTable(SQLParser sqp)throws SQLException{
		return new Vector();	
	}
	public Vector describeTable(SQLParser sqp)throws SQLException{
		return new Vector();
	}
	
				public Vector<Row> getPrimaryKeys(SQLParser sqp)throws SQLException{
				Vector<Row> o = new Vector<Row>();
				return o;
				}
				
						public Vector<Row> getImportedKeys(SQLParser sqp)throws SQLException{
					Vector<Row> o = new Vector<Row>();
				return o;
				}

				public Vector<Row> getIndex(SQLParser sqp)throws SQLException{
					Vector<Row> o = new Vector<Row>();
				return o;
				}
				
				public Vector<Row> getExportedKeys(SQLParser sqp)throws SQLException{
					Vector<Row> o = new Vector<Row>();
				return o;
				}	
	public Vector getColumns(SQLParser sqp)throws SQLException{
		return new Vector();
	}		

			public TableInfo readTableInfo(String t)throws SQLException{
				return null;

			}


			public JGNameValuePairs readTableProp(String t)throws SQLException{
				return null;
			}

			public void writeTableProp(String t,String tp,jiqlTableInfo ti)throws SQLException{
			}


			public TableInfo readTableInfo(String t,SQLParser sqp)throws SQLException{
				TableInfo v = sqp.getTableInfo();
				if (v == null){
				
				v = readTableInfo(t);
				sqp.setTableInfo(v);
				}
				return v;

			}





	
		public void updateTableValue(String t,Row r,SQLParser sqp)throws SQLException{

		}

		public void deleteTableValue(String t,String rid)throws SQLException{

		}
		
	
	
	
		
		
		public void dropTable(String t,SQLParser sqp)throws SQLException{

		}

		 public void writeTableRow(String t,Hashtable hash,SQLParser sqp)
     throws SQLException

    {
  
    }



	public Hashtable readTableValue(String t,Vector incl)throws SQLException{
	return readTableValue(t,incl,null);
	}

public Hashtable readTableValueWhereEqual(SQLParser sqp,String t,String n,Object v)throws SQLException{

return null;	
}

public Hashtable readTableValueWhereLessThan(SQLParser sqp,String t,String n,Object v)throws SQLException{

return null;	
}

public Hashtable readTableValueWhereGreaterThan(SQLParser sqp,String t,String n,Object v)throws SQLException{

return null;	
}

public Hashtable readTableValueWhereLessThanOrEqual(SQLParser sqp,String t,String n,Object v)throws SQLException{

return null;	
}

public Hashtable readTableValueWhereGreaterThanOrEqual(SQLParser sqp,String t,String n,Object v)throws SQLException{

return null;	
}

	public Hashtable readTableValue(String t,Vector incl,SQLParser sqp)throws SQLException{
	    	//Hashtable h = null;
	    	//if (incl == null || incl.size() < 1){
	    	//	return  (sqp,t);
	    	//}
	    	
	    	Hashtable h = null;
	    	if (incl == null || incl.size() < 1){
	    		//if (sqp != null )
	    		//("IS COINT " + sqp.isCount());
	    		if (sqp != null && sqp.isCount()){
	    			h = new Hashtable();
	    			h.put("jiql_row_count",getRowCount(t,sqp));
	    			return h;
	    		}
	    		return readTableValue(sqp,t);
	    	}
	    	
	    	//h =  (sqp,t);
	    	//if (h == null) return null;
	    	//if (incl == null || incl.size() < 1)return h;
	    	
	    	
	    	
	    	NameValuePairs sh = new NameValuePairs();
	    	//Hashtable sh = new Hashtable();
	    	String id = null;
	    	NameValuePairs row = null;
	    	Object nvo = null;
	    	boolean add = false;
	    	ColumnInfo ci = null;
	    	TableInfo ti = null;
	    	if (sqp != null)
	    		ti = sqp.getTableInfo();
	    	if(ti == null)
	    	ti = readTableInfo(t,sqp);
	    	jiqlCellValue c1 = null;
	    	jiqlCellValue c2 = null;
	    	int typ = 0;
	    	
	  
	  
					NameValuePairs h2 = new NameValuePairs();

				    	Criteria cr = (Criteria)incl.elementAt(0);

	    	incl = (Vector)incl.clone();
	    	incl.removeElementAt(0);
					String crn = cr.getRealName();

	    			if (cr.getCompareOperator().equals("="))
	    			{
	    				
						h = readTableValueWhereEqual(sqp,t,crn,cr.getValue());

							    				
	    			}
	    			else if (cr.getCompareOperator().equals(">"))
	    			{
	    				h = readTableValueWhereGreaterThan(sqp,t,crn,cr.getValue());


	    			}
	    			else if (cr.getCompareOperator().equals("<"))
	    			{
	    				h = readTableValueWhereLessThan(sqp,t,crn,cr.getValue());

	    			}

	    			else if (cr.getCompareOperator().equals(">="))
	    			{
	    				h = readTableValueWhereGreaterThanOrEqual(sqp,t,crn,cr.getValue());

	    			}
	    			else if (cr.getCompareOperator().equals("!="))
	    			{
	    				h = readTableValueWhereGreaterThan(sqp,t,crn,cr.getValue());
						if (h != null)
						h2.merge(h);
	    				h = readTableValueWhereLessThan(sqp,t,crn,cr.getValue());
						if (h != null)
						h2.merge(h);
						h = (Hashtable)h2;
	    			}
	    			else if (cr.getCompareOperator().equals("<="))
	    			{
	    				h = readTableValueWhereLessThanOrEqual(sqp,t,crn,cr.getValue());
	    			}
	    			else if (cr.getCompareOperator().equals("in"))
	    			{
	    				Vector ic = cr.getInClause();
	    				for (int cit  =0;cit < ic.size();cit++)
	    				{
	    	 	    	h = readTableValueWhereEqual(sqp,t,crn,ic.elementAt(cit));
						if (h != null)
						h2.merge(h);	    				
	    				}
	    				h = (Hashtable)h2;
	    				
	    			}
	    			else if (cr.getCompareOperator().equals("is"))
	    			{
	 	    				if ( cr.getValue().toString().equalsIgnoreCase("null"))
	 	    				{
	 	    			h = readTableValueWhereEqual(sqp,t,crn,null);

	 	    				}
	    
	    			}







			if (h == null)return sh;

	    	



			if (incl.size() < 1)
				return h;
	    	Enumeration en = h.keys();
	    	
	    	while (en.hasMoreElements())
	    	{
	    		id = en.nextElement().toString();
	    		row = (NameValuePairs)h.get(id);

	    		for (int ct = 0;ct < incl.size();ct++){
	    			add = false;
	    			cr = (Criteria)incl.elementAt(ct);
	    			crn = cr.getRealName();
	    			if (cr.getCompareOperator().equals("="))
	    			{
	    				nvo = row.get(crn);
	    				//(nvo + " CMPR " + cr. () + ":" +  cr.getValue());
	    				if (nvo == null)break;
	    		typ = ti.getColumnInfo(crn).getColumnType();
	    				c1 = new jiqlCellValue(nvo,typ,sqp);
	    				c2 = new jiqlCellValue(cr.getValue(),typ,sqp);
	    			    if (c1.compareTo(c2) == 0)
		    				add = true;
						else break;
						
							    				
	    			}
else if (cr.getCompareOperator().equals(">"))
	    			{
	    				nvo = row.get(crn);
	    				if (nvo == null)break;
	    				typ = ti.getColumnInfo(crn).getColumnType();
	    				c1 = new jiqlCellValue(nvo,typ,sqp);
	    				c2 = new jiqlCellValue(cr.getValue(),typ,sqp);
	    			    if (c1.compareTo(c2) > 0)
	    			    	add = true;
	    			    else
	    			    	break;
	    			}
	    			else if (cr.getCompareOperator().equals("<"))
	    			{
	    				nvo = row.get(crn);
	    				if (nvo == null)break;
	    				typ = ti.getColumnInfo(crn).getColumnType();
	    				c1 = new jiqlCellValue(nvo,typ,sqp);
	    				c2 = new jiqlCellValue(cr.getValue(),typ,sqp);
	    			    if (c1.compareTo(c2) < 0)
	    			    	add = true;
	    			    else
	    			    	break;
	    			}

	    			else if (cr.getCompareOperator().equals(">="))
	    			{
	    				nvo = row.get(crn);
	    				if (nvo == null)break;
	    				typ = ti.getColumnInfo(crn).getColumnType();
	    				c1 = new jiqlCellValue(nvo,typ,sqp);
	    				c2 = new jiqlCellValue(cr.getValue(),typ,sqp);
	    			    if (c1.compareTo(c2) > -1)
	    			    	add = true;
	    			    else
	    			    	break;
	    			}
	    			else if (cr.getCompareOperator().equals("!="))
	    			{
	    				nvo = row.get(crn);
	    				if (nvo == null)break;
	    				typ = ti.getColumnInfo(crn).getColumnType();
	    				c1 = new jiqlCellValue(nvo,typ,sqp);
	    				c2 = new jiqlCellValue(cr.getValue(),typ,sqp);
	    			    if (c1.compareTo(c2) != 0)
	    			    	add = true;
	    			    else
	    			    	break;
	    			}
	    			else if (cr.getCompareOperator().equals("<="))
	    			{
	    				nvo = row.get(crn);
	    				if (nvo == null)break;
	    				typ = ti.getColumnInfo(crn).getColumnType();
	    				c1 = new jiqlCellValue(nvo,typ,sqp);
	    				c2 = new jiqlCellValue(cr.getValue(),typ,sqp);
	    			    if (c1.compareTo(c2) < 1)
	    			    	add = true;
	    			    else
	    			    	break;
	    			}
	    			else if (cr.getCompareOperator().equals("in"))
	    			{
	    				nvo = row.get(crn);
	    				if (nvo == null)break;
	 	    				//(nvo + " CMPR " + cr. () + ":" +  cr.getValue() + ":" + cr.getInClause() + ":" + cr.getInClause().contains(nvo.toString()));

	    				if (!cr.getInClause().contains(nvo.toString()))
	    					break;
	    				add = true;
	    				

	    			}
	    			else if (cr.getCompareOperator().equals("is"))
	    			{

	    				nvo = row.get(crn);
	    				//(nvo + " CMPR " + cr. () + ":" +  cr.getValue());
	    				if (nvo == null && cr.getValue().toString().equalsIgnoreCase("null"))
	    					add = true;
	    					else
	    					break;
	    			}
	    			
	    		}
	    		if (add)
	    		sh.put(id,row);
	    		
	    		
	    	}
	    	if (sh.size() < 1)return null;
	    	return sh;
			}




	public Hashtable readTableValue(String t,Vector incl,Vector selL,SQLParser sqp)throws SQLException{
		return readTableValue(t,incl,selL,null,sqp);
	}

	public Hashtable readTableValue(String t,SQLParser sqp)throws SQLException{
		return readTableValue(t,null,null,sqp);
	}


	/*public Hashtable  (String t,Vector incl,Vector selL,Vector jeor,SQLParser sqp)throws SQLException{
			return  (t,incl,selL,jeor,false,sqp);
	}*/

	public Hashtable readTableValue(String t,Vector incl,Vector selL,Vector jeor,boolean distinct,SQLParser sqp)throws SQLException{
	Hashtable h = readTableValue(t,incl,selL,jeor,sqp);
	//("distinct " + distinct);
	if (distinct){
		Hashtable dh = new Hashtable();
		Enumeration en = h.keys();
		Object o = null;
		Vector v = new Vector();
		String r = null;
		while (en.hasMoreElements()){
			r = en.nextElement().toString();
			o = h.get(r);
			if (!v.contains(o.toString())){
				dh.put(r,o);
				v.add(o.toString());
			}
		}
		return dh;
			
	}
	return h;
	}
public int getRowCount(String t,SQLParser sqp)throws SQLException{
return 0;
}

	public Hashtable readTableValue(String t,Vector incl,Vector selL,Vector jeor)throws SQLException{
		return readTableValue(t,incl,selL,jeor,null);
	}


	void action(SQLParser sqp,Hashtable heor,Row row,String id)throws SQLException{
		/*if (sqp.getAction().equals("update"))
		{
			Vector ul = sqp.getUpdateList();
			NameValue nv = null;

			for (int ct = 0; ct < ul.size();ct++)
			{
			nv = (NameValue)ul.elementAt(ct);
			row.put(nv.name,nv.value);
			}
			updateTableValue( sqp.getTable(),row);
			 .log(sqp.getTable() + " ACTION " + row);
		}
		else*/
			heor.put(id,row);
	}

	public Hashtable readTableValue(String t,Vector incl,Vector selL,Vector jeor,SQLParser sqp)throws SQLException{
	    	Hashtable h = readTableValue(t,incl,sqp);
			//(t + "   " + incl + ":" +   + ":" + jeor + ":" + sqp + ":" + h);
//realm_userrole   null:null:[SQLCriteria:realm_user=ruser2]:org.jiql.util.SQLParser@a02839:{7138={realm_rolename=role2, realm_user=ruser1}, 6140={realm_rolename=role2, realm_user=ruser2}} 
	    	if (h == null) return null;
	    	String id = null;
	    	Row row = null;
	    	Criteria cr = null;
	    	Object nvo = null;
	    	ColumnInfo ci = null;
	    	TableInfo ti = readTableInfo(t,sqp);
	    	jiqlCellValue c1 = null;
	    	jiqlCellValue c2 = null;
	    	int typ = 0;
		//	 .log(ti + ":" + t + "  2 " + incl + ":" +   + ":" + jeor + ":" + sqp);
//testable5  2 [SQLCriteria:countf=3]:[]:[]:org.jiql.util.SQLParser@1a5fb5a    	
			String crn = null;
	    	if (jeor != null && jeor.size() > 0)
	    	{
	    		Hashtable heor = new Hashtable();
	    		Enumeration eneor = h.keys();

		 	    while (eneor.hasMoreElements())
		    	{
		    		id = eneor.nextElement().toString();
		    		row = (Row)h.get(id);

	    	/*Enumeration enr = row.keys();
	    	while( enr.hasMoreElements())
	    	{
	    		String nr = enr.nextElement().toString();
	    		//( ":"+ nr + ":ROW VALUES:" + row.get(nr));
	    	}*/
		    		for (int eoct = 0 ; eoct < jeor.size();eoct++)
		    		{
		    			cr = (Criteria)jeor.elementAt(eoct);
		    			crn = cr.getRealName();
	    	    		if (cr.getCompareOperator().equals("="))
		    			{
		    				nvo = row.get(crn);
		    		//(cr. () + ":" + row.get("realm_user") + "  row " + row + ":" + nvo + ":" + cr.getValue());
		    	//	realm_user row {realm_rolename=role2, realm_user=ruser1}:null:ruser2 
		    		// + ":" + nvo.equals(cr.getValue()));
		    				if (nvo == null)continue;
		    				//if (!nvo.equals(cr.getValue()))
		    				//	continue;
			    		typ = ti.getColumnInfo(crn).getColumnType();
	    				c1 = new jiqlCellValue(nvo,typ,sqp);
	    				c2 = new jiqlCellValue(cr.getValue(),typ,sqp);
	    			    //(c2 + " PLESK WOK " + c1);
	    			    if (c1.compareTo(c2) == 0)
		    				action(sqp,heor,row,id);
						else continue;
		
		    			}

	    			else if (cr.getCompareOperator().equals(">"))
	    			{
	    				nvo = row.get(crn);
	    				if (nvo == null)continue;
	    				typ = ti.getColumnInfo(crn).getColumnType();
	    				c1 = new jiqlCellValue(nvo,typ,sqp);
	    				c2 = new jiqlCellValue(cr.getValue(),typ,sqp);
	    			    if (c1.compareTo(c2) > 0)
		    				action(sqp,heor,row,id);
	    			    else
	    			    	continue;
	    			}
	    			else if (cr.getCompareOperator().equals("<"))
	    			{
	    				nvo = row.get(crn);
	    				if (nvo == null)continue;
	    				typ = ti.getColumnInfo(crn).getColumnType();
	    				c1 = new jiqlCellValue(nvo,typ,sqp);
	    				c2 = new jiqlCellValue(cr.getValue(),typ,sqp);
	    			    if (c1.compareTo(c2) < 0)
		    				action(sqp,heor,row,id);
	    			    else
	    			    	continue;
	    			}

	    			else if (cr.getCompareOperator().equals(">="))
	    			{
	    				nvo = row.get(crn);
	    				if (nvo == null)continue;
	    				typ = ti.getColumnInfo(crn).getColumnType();
	    				c1 = new jiqlCellValue(nvo,typ,sqp);
	    				c2 = new jiqlCellValue(cr.getValue(),typ,sqp);
	    			    if (c1.compareTo(c2) > -1)
		    				action(sqp,heor,row,id);
	    			    else
	    			    	continue;
	    			}
	    			else if (cr.getCompareOperator().equals("!="))
	    			{
	    				nvo = row.get(crn);
	    				if (nvo == null)continue;
	    				typ = ti.getColumnInfo(crn).getColumnType();
	    				c1 = new jiqlCellValue(nvo,typ,sqp);
	    				c2 = new jiqlCellValue(cr.getValue(),typ,sqp);
	    			    if (c1.compareTo(c2) != 0)
		    				action(sqp,heor,row,id);
	    			    else
	    			    	continue;
	    			}
	    			else if (cr.getCompareOperator().equals("<="))
	    			{
	    				nvo = row.get(crn);
	    				if (nvo == null)continue;
	    				typ = ti.getColumnInfo(crn).getColumnType();
	    				c1 = new jiqlCellValue(nvo,typ,sqp);
	    				c2 = new jiqlCellValue(cr.getValue(),typ,sqp);
	    			    if (c1.compareTo(c2) < 1)
		    				action(sqp,heor,row,id);
	    			    else
	    			    	continue;
	    			}
		    		else if (cr.getCompareOperator().equals("in"))
	    			{
	    				nvo = row.get(crn);
	    				//(nvo + " CMPR " + cr. () + ":" +  cr.getValue());
	    				if (nvo == null)continue;
	    				if (!cr.getInClause().contains(nvo.toString()))
	    					continue;
		    				action(sqp,heor,row,id);
	    			}
		    		else if (cr.getCompareOperator().equals("is"))
	    			{
	    				nvo = row.get(crn);
	    				//(nvo + " CMPR " + cr. () + ":" +  cr.getValue());
	    				if (nvo == null && cr.getValue().toString().equalsIgnoreCase("null"))
	    					action(sqp,heor,row,id);
	    				else continue;
	    			}
		    					    			
		    						
		    		}
		    		
		    	}
		    	h = heor;
	    		
	    	}
	    	
	    	//("RD 34 " + h);
	    	if (selL == null || selL.size() < 1)return h;
	    	if(selL.contains("*") || sqp.isCount() || sqp.isJoin())return h;
	    	Enumeration en = h.keys();
	    	Hashtable sh = new Hashtable();
	    	Row nrow = null;

	    	cr = null;
	    	nvo = null;
	    	Object nvo2 = null;
	    	//boolean add = false;
	    	while (en.hasMoreElements())
	    	{
	    		id = en.nextElement().toString();
	    		row = (Row)h.get(id);
	    		nrow = new Row();
	    		nrow.setRowId(id);
	    		nrow.setSQLParser(sqp);
	    		for (int ct = 0;ct < selL.size();ct++){
	    			nvo = selL.elementAt(ct);
	    			nvo = sqp.getRealColName(nvo.toString());
	    			nvo2 = row.get(nvo);
	    			//( nvo + " GIAN " + nvo2);
	    			if (nvo2 != null)
	    				nrow.put(nvo,nvo2);
	    		}
	    		if (nrow.size() > 0)
	    		sh.put(id,nrow);
	    		
	    		
	    	}
	    	
	    	return sh;
			}








			public Hashtable readTableValue(String t)throws SQLException{
				return readTableValue(null,t);

			}

			public Hashtable readTableValue(SQLParser sqp,String t)throws SQLException{
	    	//("WRITE TALVE ROW  5" + t);
		
			return null;
			}





public int countTableValueWhereEqual(SQLParser sqp,String t,String n,Object v)throws SQLException{
return 0;

}



public int countTableValueWhereLessThan(SQLParser sqp,String t,String n,Object v)throws SQLException{

return 0;


}

public int countTableValueWhereGreaterThan(SQLParser sqp,String t,String n,Object v)throws SQLException{

return 0;


}

public int countTableValueWhereLessThanOrEqual(SQLParser sqp,String t,String n,Object v)throws SQLException{

return 0;


}

public int countTableValueWhereGreaterThanOrEqual(SQLParser sqp,String t,String n,Object v)throws SQLException{
return 0;
}




}