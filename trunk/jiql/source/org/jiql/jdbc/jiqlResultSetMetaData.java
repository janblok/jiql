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

package org.jiql.jdbc;
import java.util.*;
import java.sql.*;
import java.io.*;
import org.jiql.db.*;
import org.jiql.util.*;
import org.jiql.db.objs.jiqlTableInfo;
import org.jiql.db.select.FunctionBase;
import org.jiql.db.jdbc.stat.ResultMetaObj;

public class jiqlResultSetMetaData implements java.sql.ResultSetMetaData, java.io.Serializable
{
	ResultMetaObj rmo = null;
	TableInfo ti = null;
	SQLParser sqp = null;
	static Vector <Integer> tcolstrings = new Vector<Integer>();
	static Vector <Integer> ecolints = new Vector<Integer>();
	
	static{
		tcolstrings.add(1);
		tcolstrings.add(2);
		tcolstrings.add(3);
		tcolstrings.add(4);
		tcolstrings.add(6);
		tcolstrings.add(12);
		tcolstrings.add(13);
		tcolstrings.add(18);
		
		ecolints.add(9);
		ecolints.add(10);
		ecolints.add(11);
		ecolints.add(14);

	}
	public void setResultMetaObj(ResultMetaObj ro){
		rmo = ro;
	}
	public jiqlResultSetMetaData(SQLParser sp)throws SQLException{
			sqp = sp;
if (!sqp.showTables() && !sqp.isSpecial()){

			ti = sqp.getTableInfo();
			if (ti == null)
				ti = Gateway.get(sqp.getProperties()).readTableInfo(sqp.getTable());
			ti = (TableInfo)ti.clone();
			ti.include(sqp);
	
	Enumeration en =  sqp.getUnion().listSQLParsers();
	while (en.hasMoreElements()){
		SQLParser s = (SQLParser)en.nextElement();
		TableInfo tis = s.getTableInfo();
		 tis = (TableInfo)tis.clone();
		tis.include(s);

		//Vector ez = sqp.getOriginalSelectList();
		//for (int ct = 0;ct < tis.size();ct++)
		//	ti.add(tis.elementAt(ct));
	
			for (int ct = 0;ct < tis.size();ct++)
			ti.add(tis.elementAt(ct));

			
	}
	}

	}
	
	
public	boolean 	isWrapperFor(Class<?> iface)throws SQLException{
	return false;
}
      //    Returns true if this either implements the interface argument or is directly or indirectly a wrapper for an object that does.
public <T> T unwrap(Class<T> iface)throws SQLException
{
resLog("RL " + 7);
	return null;
}
          //Returns an object that implements the given interface to allow access to non-standard methods, or standard methods not exposed by the proxy.
          	
	

public String 	getCatalogName(int column)throws SQLException{
resLog("getCatalogName " + column);
		if (rmo != null)
			return rmo.getCatalogName(column);
		if (isCatalog())
			return "TABLE_CAT";

	if (sqp.isSpecial()){
		if (sqp.getAction().equals("getIdentity"))
		return "@@identity";
		else if (sqp.getAction().equals("getFoundRows"))
		return "FOUND_ROWS()";
		else if (sqp.getAction().equals("getTypeInfo"))
		return sqp.getAction();
		else if (sqp.getAction().equals("getGeneratedKeys"))
		return sqp.getAction();
	}
		if (sqp.showTables()){
			if (column == 1)
				return "TABLE_CAT";
			if (column == 2)
				return "TABLE_SCHEM";
			if (column == 4)
				return "TABLE_TYPE";

			return "tables";
		}

	return ti.getTableName();
}
          //Gets the designated column's table's catalog name.
public  String 	getColumnClassName(int column)throws SQLException{
resLog("RL " + 8);
	return null;
}
          //Returns the fully-qualified name of the Java class whose instances are manufactured if the method ResultSet.getObject is called to retrieve a value from the column.
public int 	getColumnCount()throws SQLException{
resLog("getColumnCount " );
			if (rmo != null)
			return rmo.getColumnCount();
	
		if (isSchema())
		{
return 2;
			
		}
		if (sqp.showTables())
			return 4;
	if (sqp.isSpecial()){
		if (sqp.getAction().equals("getIdentity"))
		return org.jiql.jdbc.ResultSet.identity.size();
		else if (sqp.getAction().equals("getFoundRows"))
		return org.jiql.jdbc.ResultSet.foundrows.size();
		else if (sqp.getAction().equals("getIndex"))
		return org.jiql.jdbc.ResultSet.indexv.size();
		else if (sqp.getAction().equals("getTypeInfo"))
		return Gateway.get(sqp.getProperties()).getTypeinfoCols().size();
		else if (sqp.getAction().equals("getGeneratedKeys"))
		return 1;

	}
	
	if (sqp.getAction().equals("describeTable"))
		return org.jiql.jdbc.ResultSet.descols.size();
	if (sqp.getAction().equals("getColumns"))
		return org.jiql.jdbc.ResultSet.tcols.size();
	if (sqp.getAction().equals("getPrimaryKeys"))
		return org.jiql.jdbc.ResultSet.pkcols.size();
	if (sqp.getAction().equals("getExportedKeys")|| sqp.getAction().equals("getImportedKeys"))
		return org.jiql.jdbc.ResultSet.ekcols.size();

	if (sqp.getAction().equals("jiqldescribeTable"))
		return org.jiql.jdbc.ResultSet.jiqldescols.size();
		
if (sqp.isCount())
return 1;
return ti.size();
}
          //Returns the number of columns in this ResultSet object.
public int 	getColumnDisplaySize(int column)throws SQLException{
resLog("getColumnDisplaySize " + column);
			if (rmo != null)
			return rmo.getColumnDisplaySize(column);

		if (sqp.showTables()){
					if (column == 1)
				return 9;
			if (column == 2)
				return 11;
			if (column == 4)
				return 10;
				
			return 6;
		}
		
			if (sqp.isSpecial()){
		if (sqp.getAction().equals("getIdentity"))
		return org.jiql.jdbc.ResultSet.identity.elementAt(column - 1).toString().length();
		else if (sqp.getAction().equals("getFoundRows"))
		return org.jiql.jdbc.ResultSet.foundrows.elementAt(column - 1).toString().length();
		else if (sqp.getAction().equals("getIndex"))
		return org.jiql.jdbc.ResultSet.indexv.elementAt(column - 1).toString().length();
		else if (sqp.getAction().equals("getTypeInfo"))
		return Gateway.get(sqp.getProperties()).getTypeinfoCols().elementAt(column -1).toString().length();
		else if (sqp.getAction().equals("getGeneratedKeys"))
			return 13;

	}
	if (sqp.getAction().equals("describeTable"))
		return org.jiql.jdbc.ResultSet.descols.elementAt(column - 1).toString().length();
	if (sqp.getAction().equals("getColumns"))
		return org.jiql.jdbc.ResultSet.tcols.elementAt(column - 1).toString().length();
	if (sqp.getAction().equals("getPrimaryKeys"))
		return org.jiql.jdbc.ResultSet.pkcols.elementAt(column - 1).toString().length();
	if (sqp.getAction().equals("getExportedKeys") || sqp.getAction().equals("getImportedKeys"))
		return org.jiql.jdbc.ResultSet.ekcols.elementAt(column - 1).toString().length();

	if (sqp.getAction().equals("jiqldescribeTable"))
		return org.jiql.jdbc.ResultSet.jiqldescols.elementAt(column - 1).toString().length();


ColumnInfo ci = ti.getColumnInfo(column);
return ci.getName().length();
}

boolean catalog = false;
public boolean isCatalog(){
//("RL " + 9);
	return catalog;
}

public void setIsCatalog(boolean tf){
	catalog = tf;
}

boolean schema = false;
public boolean isSchema(){
	return schema;
}

public void setIsSchema(boolean tf){
	schema = tf;
}

public String 	getColumnLabel(int column)throws SQLException{
resLog("getColumnLabel " + column);
				if (rmo != null)
			return rmo.getColumnLabel(column);

		if (isSchema())
			return "TABLE_SCHEM";

		if (isCatalog())
			return "TABLE_CAT";
		if (sqp.showTables()){
					if (column == 1)
				return "TABLE_CAT";
			if (column == 2)
				return "TABLE_SCHEM";
			if (column == 4)
				return "TABLE_TYPE";
			return "tables";
		}
	
				if (sqp.isSpecial()){
		if (sqp.getAction().equals("getIdentity"))
		return org.jiql.jdbc.ResultSet.identity.elementAt(column - 1).toString();
		else if (sqp.getAction().equals("getFoundRows"))
		return org.jiql.jdbc.ResultSet.foundrows.elementAt(column - 1).toString();
		else if (sqp.getAction().equals("getIndex"))
		return org.jiql.jdbc.ResultSet.indexv.elementAt(column - 1).toString();
		else if (sqp.getAction().equals("getTypeInfo"))
		return Gateway.get(sqp.getProperties()).getTypeinfoCols().elementAt(column -1).toString();
		else if (sqp.getAction().equals("getGeneratedKeys"))
		return "GENERATED_KEY";
	}
	if (sqp.getAction().equals("describeTable"))
		return org.jiql.jdbc.ResultSet.descols.elementAt(column - 1).toString();
	if (sqp.getAction().equals("getColumns"))
		return org.jiql.jdbc.ResultSet.tcols.elementAt(column - 1).toString();
	if (sqp.getAction().equals("getPrimaryKeys"))
		return org.jiql.jdbc.ResultSet.pkcols.elementAt(column - 1).toString();
	if (sqp.getAction().equals("getExportedKeys")|| sqp.getAction().equals("getImportedKeys"))
		return org.jiql.jdbc.ResultSet.ekcols.elementAt(column - 1).toString();

	if (sqp.getAction().equals("jiqldescribeTable"))
		return org.jiql.jdbc.ResultSet.jiqldescols.elementAt(column - 1).toString();

ColumnInfo ci = ti.getColumnInfo(column);
return ci.getName();

}
          //Gets the designated column's suggested title for use in printouts and displays.
public String 	getColumnName(int column)throws SQLException{
resLog("getColumnName " + column);
				if (rmo != null)
			return rmo.getColumnName(column);

		if (isSchema())
		{
			
			if (column > 1)
				return "TABLE_CATALOG";
			return "TABLE_SCHEM";
		}

		if (isCatalog())
			return "TABLE_CAT";
		if (sqp.showTables()){
					if (column == 1)
				return "TABLE_CAT";
			if (column == 2)
				return "TABLE_SCHEM";
			if (column == 4)
				return "TABLE_TYPE";
			return "tables";
}
				if (sqp.isSpecial()){
		if (sqp.getAction().equals("getIdentity"))
		return org.jiql.jdbc.ResultSet.identity.elementAt(column - 1).toString();
		else if (sqp.getAction().equals("getFoundRows"))
		return org.jiql.jdbc.ResultSet.foundrows.elementAt(column - 1).toString();
		else if (sqp.getAction().equals("getIndex"))
		return org.jiql.jdbc.ResultSet.indexv.elementAt(column - 1).toString();
		else if (sqp.getAction().equals("getTypeInfo"))
		return Gateway.get(sqp.getProperties()).getTypeinfoCols().elementAt(column -1).toString();
		else if (sqp.getAction().equals("getGeneratedKeys"))
		return "GENERATED_KEY";

	}
	if (sqp.getAction().equals("describeTable"))
		return org.jiql.jdbc.ResultSet.descols.elementAt(column - 1).toString();
	if (sqp.getAction().equals("getColumns"))
		return org.jiql.jdbc.ResultSet.tcols.elementAt(column - 1).toString();
	if (sqp.getAction().equals("getPrimaryKeys"))
		return org.jiql.jdbc.ResultSet.pkcols.elementAt(column - 1).toString();
	if (sqp.getAction().equals("getExportedKeys")|| sqp.getAction().equals("getImportedKeys"))
		return org.jiql.jdbc.ResultSet.ekcols.elementAt(column - 1).toString();

	if (sqp.getAction().equals("jiqldescribeTable"))
		return org.jiql.jdbc.ResultSet.jiqldescols.elementAt(column - 1).toString();

if (sqp.isCount()){
Hashtable<String,String> sas = sqp.getSelectAS();
Enumeration<String> en = sas.keys();
String k = null;
while (en.hasMoreElements()){
	k = en.nextElement();
	if (sas.get(k).equals("*"))
		return k;
}
return "count(*)";
}
ColumnInfo ci = ti.getColumnInfo(column);
String dna = (String)(sqp.getSelectAS()).get(ci.getName());
if (dna != null)return dna;
return ci.getDisplayName();
}
          //Get the designated column's name.
public int 	getColumnType(int column)throws SQLException{
resLog("getColumnType " + column);
				if (rmo != null)
			return rmo.getColumnType(column);

		if (sqp.showTables())
			return Types.VARCHAR;

				if (sqp.isSpecial()){
		if (sqp.getAction().equals("getIdentity"))
		return Types.VARCHAR;
		else if (sqp.getAction().equals("getFoundRows"))
		return Types.INTEGER;
		else if (sqp.getAction().equals("getTypeInfo")){
		int ir = ColumnInfo.getTypeFromName(Gateway.get(sqp.getProperties()).getTypeinfoColTypeNames().elementAt(column -1).toString());
		return ir;
		}		else if (sqp.getAction().equals("getGeneratedKeys"))
		return -5;

				else if (sqp.getAction().equals("getIndex"))
				{
					if (column == 7||column == 8 || column == 11 || column == 12)
					return Types.INTEGER;
					if (column == 4)
					return Types.BOOLEAN;	
					return Types.VARCHAR;
				}

	}
		if (sqp.getAction().equals("getPrimaryKeys")){
			if (5 == (column))
				return Types.INTEGER;
				return Types.VARCHAR;
		}
			if (sqp.getAction().equals("getExportedKeys")|| sqp.getAction().equals("getImportedKeys"))
			{
			if (!ecolints.contains(column))
				return Types.VARCHAR;
			else return Types.INTEGER;
		}
		if (sqp.getAction().equals("getColumns")){
			if (tcolstrings.contains(column))
				return Types.VARCHAR;
			else return Types.INTEGER;
		}
	if (sqp.getAction().equals("describeTable"))
		return Types.VARCHAR;
	if (sqp.getAction().equals("jiqldescribeTable"))
	{
		if (column == 1 || column == 3)
		return Types.BOOLEAN;
		if ( column == 4)
		return Types.INTEGER;
		return Types.VARCHAR;
	}

ColumnInfo ci = ti.getColumnInfo(column);
FunctionBase fb = sqp.getSelectParser().getSQLFunctionParser().getFunction(ci.getDisplayName());
if (fb != null)return fb.getType(); 

	return ci.getColumnType();


}
          //Retrieves the designated column's SQL type.
public String 	getColumnTypeName(int column)throws SQLException{
resLog("getColumnType " + column);
				if (rmo != null)
			return rmo.getColumnTypeName(column);

		if (sqp.showTables())
			return "varchar";
					if (sqp.isSpecial()){
		if (sqp.getAction().equals("getIdentity"))
		return "varchar";
		else if (sqp.getAction().equals("getFoundRows"))
		return "int";
		else if (sqp.getAction().equals("getTypeInfo"))
		return Gateway.get(sqp.getProperties()).getTypeinfoColTypeNames().elementAt(column -1).toString();
		else if (sqp.getAction().equals("getGeneratedKeys"))
		return "UNKNOWN";

				else if (sqp.getAction().equals("getIndex"))
				{
					if (column == 7||column == 8 || column == 11 || column == 12)
					return "int";
					if (column == 4)
					return "bool";	
					return "varchar";
				}
	}
			if (sqp.getAction().equals("getPrimaryKeys")){
			if (5 == (column))
				return "int";
				return "varchar";
		}
				if (sqp.getAction().equals("getExportedKeys")|| sqp.getAction().equals("getImportedKeys"))
			{
			if (!ecolints.contains(column))
				return "varchar";
			else return "int";
		}
				if (sqp.getAction().equals("getColumns")){
			if (tcolstrings.contains(column))
				return "varchar";
			else return "int";
		}
	if (sqp.getAction().equals("describeTable"))
		return "varchar";
	if (sqp.getAction().equals("jiqldescribeTable"))
	{
		if (column == 1 || column == 3)
		return "bool";
		if ( column == 4)
		return "int";
		return "varchar";
	}
ColumnInfo ci = ti.getColumnInfo(column);
FunctionBase fb = sqp.getSelectParser().getSQLFunctionParser().getFunction(ci.getDisplayName());
if (fb != null)return fb.getTypeName(); 

return ci.getTypeName();

}
          //Retrieves the designated column's database-specific type name.
public int 	getPrecision(int column)throws SQLException{
resLog("getPrecision " + column);

return -1;
}
void resLog(String t){
	//tools.util.LogMgr.debug("resLog:" + t);
}
          //Get the designated column's specified column size.
public int 	getScale(int column)throws SQLException{
resLog("getScale " + column);

return -1;
}
          //Gets the designated column's number of digits to right of the decimal point.
public String 	getSchemaName(int column)throws SQLException{
resLog("getSchemaName " + column);
return "";
}
          //Get the designated column's table's schema.
public String 	getTableName(int column)throws SQLException{
resLog("getTableName " + column);
				if (rmo != null)
			return rmo.getTableName(column);

		if (isCatalog())
			return "TABLE_CAT";
		if (sqp.showTables())
			return "tables";
					if (sqp.isSpecial()){
		if (sqp.getAction().equals("getIdentity"))
		return "identity";
		else if (sqp.getAction().equals("getFoundRows"))
		return "rows";
		else if (sqp.getAction().equals("getTypeInfo"))
		return sqp.getAction();
		else if (sqp.getAction().equals("getGeneratedKeys"))
		return sqp.getAction();

	}
	
		if (sqp.getAction().equals("describeTable")|| sqp.getAction().equals("getColumns") || sqp.getAction().equals("getPrimaryKeys") || sqp.getAction().equals("getExportedKeys")|| sqp.getAction().equals("getImportedKeys"))
		return sqp.getTable();

	return ti.getTableName();

}
          //Gets the designated column's table name.
public boolean 	isAutoIncrement(int column)throws SQLException{
resLog("isAutoIncrement " + column);
return sqp.getJiqlTableInfo().listAutoIncrements().contains(getColumnName(column));
//return false;
}
          //Indicates whether the designated column is automatically numbered.
public boolean 	isCaseSensitive(int column)throws SQLException{
resLog("RL " + 2);
return true;
}
          //Indicates whether a column's case matters.
public boolean 	isCurrency(int column)throws SQLException{
return false;
}
          //Indicates whether the designated column is a cash value.
public boolean 	isDefinitelyWritable(int column)throws SQLException{
resLog("isDefinitelyWritable " + column);

return true;
}
          //Indicates whether a write on the designated column will definitely succeed.
public int 	isNullable(int column)throws SQLException{
	String cn = getColumnName(column);
	jiqlTableInfo jti = sqp.getJiqlTableInfo();
	if (jti == null)
	{
		
		resLog(cn + " isNullable NOT jiqlTableInfo " + column  + ":" + sqp.getTable());
		return columnNullableUnknown;
	}
boolean nn = jti.getNotNulls().contains(cn);
resLog(cn + " isNullable " + column  + ":" + nn + ":" + sqp.getTable());
if (nn)
 return columnNoNulls;
	return columnNullable;
}
          //Indicates the nullability of values in the designated column.
public boolean 	isReadOnly(int column)throws SQLException{
String cn = getColumnName(column);
resLog(cn + " isReadOnly " + column);

return false;
}
          //Indicates whether the designated column is definitely not writable.
public boolean 	isSearchable(int column)throws SQLException{
resLog("RL " + 4);
return true;
}
          //Indicates whether the designated column can be used in a where clause.
public boolean 	isSigned(int column)throws SQLException{
//String cn = getColumnName(column);
if (ti == null){
resLog(getColumnName(column) + " isSigned " + column + ":TI NULL FALSE");
 return false;
}

ColumnInfo ci = ti.getColumnInfo(column);
if (ci == null || !ci.isNumeric()){
resLog(getColumnName(column) + " isSigned " + column + ":FALSE");
 return false;
}
String ct = ci.getTypeName();
int b = ct.toLowerCase().indexOf("unsigned");
resLog(getColumnName(column) + " isSigned " + column + ":" + ct + ":" + b);
return (b < 0);
}
          //Indicates whether values in the designated column are signed numbers.
public boolean 	isWritable(int column)throws SQLException{
resLog("RL " + 6);
return true;
}
          //Indicates whether it is possible for a write on the designated column to succeed.

}


