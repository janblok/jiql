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
import java.io.*;
import java.util.*;
import java.sql.*;
//import org.jiql.jdbc.*;

public class TestSuite {

	 public static void main(String args[])throws Exception {
	 	Connection	Conn = null;
	 					int tcomp = 0;
		int ttries = 0;
	long stime = System.currentTimeMillis();
Statement stat = null;
	 	
	 	try{
	 	



String url = "jdbc:jiql://local";
if (args != null && args.length > 0)
url = "jdbc:jiql:" + args[0];
	Properties props = new Properties();
	//props.put("user","admin");
	//props.put("password","jiql");

	Class clazz = Class.forName("org.jiql.jdbc.Driver");
	Driver driver = (Driver) clazz.newInstance();

	Conn = driver.connect(url,props);


		
		
		
		
		
		
	stat = Conn.createStatement();


		


	
	
	
	ttries = ttries + 1;
try{

stat.execute("INSERT into testablez (name,countf,yesno) values ('counter9',9,'no')");
ResultSet result = stat.executeQuery("select * from testablez");
	
displayResult(result);
System.exit(0);	
}catch (SQLException e){
	System.out.println("TEST ERROR: " + e.toString());
}
	tcomp = tcomp + 1;	
	
	
	
	ttries = ttries + 1;
try{	
stat.execute("drop table testable");
}catch (SQLException e){
	System.out.println("TEST ERROR: " + e.toString());
}
	tcomp = tcomp + 1;








try{

	ttries = ttries + 1;
	System.out.println(ttries + " count test " + new java.util.Date());

	stat.execute("create  table  testable  ( name  varchar(18),countf int,yesno  varchar(30) )");

	stat.execute("INSERT into testable (name,countf,yesno) values ('counter4',1,'a')");
	stat.execute("INSERT into testable (name,yesno) values ('counter2','c')");
	stat.execute("INSERT into testable (name,countf,yesno) values (null,3,'b')");
	stat.execute("INSERT into testable (name,countf,yesno) values ('counter4',5,'e')");
	stat.execute("INSERT into testable (countf,yesno) values (4,'g')");
	stat.execute("INSERT into testable (name,countf,yesno) values ('counter6',6,'d')");
	stat.execute("INSERT into testable (name,countf,yesno) values ('counter6b',6,'g')");

	stat.execute("INSERT into testable (name,countf,yesno) values ('counter7',7,'f')");
	stat.execute("INSERT into testable (countf,yesno) values (9,'h')");
	stat.execute("INSERT into testable (name,countf,yesno) values (null,8,'i')");
//SELECT COUNT(t0.id) FROM rolleruser t0 WHERE (t0.isenabled = ?)

//		PreparedStatement pstat = Conn.prepareStatement("SELECT COUNT(t0.id) FROM testable t0 WHERE (t0.countf = ?)");
		PreparedStatement pstat = Conn.prepareStatement("SELECT COUNT(name) FROM testable WHERE (countf = ?)");

	pstat.setInt(1,6);
	
	ResultSet result = pstat.executeQuery();

displayResult(result);
System.exit(0);

int ct = 0;
	if (result.next())
		ct = result.getInt(1);
	if (ct != 2)
		 throw new SQLException(ttries + " FAILED COUNT TEST TEST a " + ct);
		tcomp = tcomp + 1;

try{	
stat.execute("drop table testable");
}catch (SQLException e){
	System.out.println("DROP TEST ERROR: " + e.toString());
}


}catch (SQLException e){
	e.printStackTrace();
	System.out.println(ttries + " TEST ERROR *************************: " + e.toString());
	throw e;
}











try{

	ttries = ttries + 1;
	System.out.println(ttries + " Date test " + new java.util.Date());

//stat.execute("create table rolleruser (    id              varchar(48) not null primary key,    username        varchar(255) not null,    passphrase      varchar(255) not null,    screenname      varchar(255) not null,    fullname        varchar(255) not null,    emailaddress    varchar(255) not null,    activationcode	varchar(48),    datecreated     datetime not null,    locale          varchar(20),      timezone        varchar(50),        isenabled       tinyint(1) default 1 not null)");

	stat.execute("create  table  testable  ( name  varchar(18),thedate datetime )");

	PreparedStatement pstat = Conn.prepareStatement("INSERT into testable (name,thedate) values (?,?);");
	pstat.setString(1,"time1");
	java.sql.Date d1 = new java.sql.Date(System.currentTimeMillis());
	pstat.setDate(2,d1);
	pstat.execute();
	ResultSet result = stat.executeQuery("select t.thedate from testable as t where t.name='time1'");
	if (!result.next())
		throw new SQLException("Date not in table");

	if (!result.getDate(1).equals(d1))
		throw new SQLException("Date not equal in table");
		tcomp = tcomp + 1;


try{	
stat.execute("drop table testable");
}catch (SQLException e){
	System.out.println("DROP TEST ERROR: " + e.toString());
}

}catch (SQLException e){
	e.printStackTrace();
	System.out.println("TEST ERROR: " + e.toString());
	throw e;
}



try{

	ttries = ttries + 1;
	System.out.println(ttries + " >< comparison test " + new java.util.Date());

	stat.execute("create  table  testable  ( name  varchar(18),countf int,yesno  varchar(30) )");

	stat.execute("INSERT into testable (name,countf,yesno) values ('counter4',1,'a');");
	stat.execute("INSERT into testable (name,yesno) values ('counter2','c');");
	stat.execute("INSERT into testable (name,countf,yesno) values (null,3,'b');");
	stat.execute("INSERT into testable (name,countf,yesno) values ('counter4',5,'e');");
	stat.execute("INSERT into testable (countf,yesno) values (4,'g');");
	stat.execute("INSERT into testable (name,countf,yesno) values ('counter6',6,'d');");
	stat.execute("INSERT into testable (name,countf,yesno) values ('counter6b',6,'g');");

	stat.execute("INSERT into testable (name,countf,yesno) values ('counter7',7,'f');");
	stat.execute("INSERT into testable (countf,yesno) values (9,'h');");
	stat.execute("INSERT into testable (name,countf,yesno) values (null,8,'i');");

	ResultSet result = stat.executeQuery("select count(*) from testable where countf > 6 AND countf < 9 order by countf,yesno DESC");
int ct = 0;
	if (result.next())
		ct = result.getInt(1);
	if (ct != 2)
		 throw new SQLException(ttries + " FAILED COMPARISON TEST TEST a " + ct);
		tcomp = tcomp + 1;

try{	
stat.execute("drop table testable");
}catch (SQLException e){
	System.out.println("DROP TEST ERROR: " + e.toString());
}


}catch (SQLException e){
	e.printStackTrace();
	System.out.println(ttries + " TEST ERROR *************************: " + e.toString());
	throw e;
}
















try{

	ttries = ttries + 1;
	System.out.println(ttries + " IS NULL " + new java.util.Date());

	stat.execute("create  table  testable  ( name  varchar(18),countf int,yesno  varchar(30) )");

	stat.execute("INSERT into testable (name,countf) values ('counter4',1);");
	stat.execute("INSERT into testable (name,countf,yesno) values ('counter6',6,'d');");
	stat.execute("INSERT into testable (name,countf) values ('counter4b',5);");
	stat.execute("INSERT into testable (name,countf,yesno) values ('counter7',7,'f');");

	int ct = 0;
	ResultSet result = stat.executeQuery("select count(*) from testable where yesno is null");

	if (result.next())
		ct = result.getInt(1);
	if (ct != 2)
		 throw new SQLException(ttries + " FAILED IS NULL TEST a " + ct);


	stat.execute("UPDATE testable SET  yesno='yep' where  yesno is null");

	result = stat.executeQuery("select * from testable where yesno is null");

	if (result.next())
		 throw new SQLException(ttries + " FAILED IS NULL TEST b ");



try{	
stat.execute("drop table testable");
}catch (SQLException e){
	System.out.println("DROP TEST ERROR: " + e.toString());
}
	tcomp = tcomp + 1;

}catch (SQLException e){
	e.printStackTrace();
	System.out.println(ttries + " TEST ERROR *************************: " + e.toString());
	throw e;
}















try{

	ttries = ttries + 1;
	System.out.println(ttries + " ORDER BY " + new java.util.Date());

	stat.execute("create  table  testable  ( name  varchar(18),countf int,yesno  varchar(30) )");

	stat.execute("INSERT into testable (name,countf,yesno) values ('counter4',1,'a');");
	stat.execute("INSERT into testable (name,yesno) values ('counter2','c');");
	stat.execute("INSERT into testable (name,countf,yesno) values (null,3,'b');");
	stat.execute("INSERT into testable (name,countf,yesno) values ('counter4',5,'e');");
	stat.execute("INSERT into testable (countf,yesno) values (4,'g');");
	stat.execute("INSERT into testable (name,countf,yesno) values ('counter6',6,'d');");
	stat.execute("INSERT into testable (name,countf,yesno) values ('counter6b',6,'g');");

	stat.execute("INSERT into testable (name,countf,yesno) values ('counter7',7,'f');");
	stat.execute("INSERT into testable (countf,yesno) values (9,'h');");
	stat.execute("INSERT into testable (name,countf,yesno) values (null,8,'i');");

	ResultSet result = stat.executeQuery("select * from testable order by countf,yesno DESC");

//displayResult(result);
//System.exit(0);
String comp = "";
	while (result.next())
		comp = comp + result.getInt("countf") + "+";
try{	
stat.execute("drop table testable");
}catch (SQLException e){
	System.out.println("DROP TEST ERROR: " + e.toString());
}
	if (comp.equals("9+8+7+6+6+5+4+3+1+0+"))
	tcomp = tcomp + 1;
	else throw new SQLException(ttries + " FAILED TEST ORDER BY ************************* ");

}catch (SQLException e){
	e.printStackTrace();
	System.out.println(ttries + " TEST ERROR *************************: " + e.toString());
	throw e;
}


try{	
stat.execute("drop table testable");
}catch (SQLException e){
	System.out.println("DROP TEST ERROR: " + e.toString());
}








try{

	ttries = ttries + 1;
	System.out.println(ttries + " 100 multiple inserts " + new java.util.Date());

	stat.execute("create  table  testable  ( name  varchar(18),countf int,yesno  varchar(43) )");

	for (int c = 1;c <= 100;c++)
	stat.execute("INSERT into testable (name,countf,yesno) values ('counter" + c + "'," + c + ",'yes');");
	//ResultSet result = stat.executeQuery("select t.name from testable t where countf in (7,1) and name='counter4'");
	System.out.println(tcomp + " count 100 multiple inserts " + new java.util.Date());

	ResultSet result = stat.executeQuery("select count(*) from testable");
	displayResult(result);
	
	result = stat.executeQuery("select count(*) from testable");
	
	int ct = 0;
	if (result.next())
		ct = result.getInt(1);
	if (ct == 100)
	tcomp = tcomp + 1;
		else throw new SQLException(ttries + " FAILED TEST a " + ct);

	System.out.println(ct +  ":" + tcomp + " 100 multiple inserts " + new java.util.Date());
	
	
	stat.execute("drop table testable");

}catch (SQLException e){
	e.printStackTrace();
	System.out.println(ttries + " FAILED TEST ERROR: ***************** " + e.toString());
	throw e;
}
System.out.println("a");
try{

	ttries = ttries + 1;
	System.out.println(ttries + " max column data length " + new java.util.Date());

	stat.execute("create  table  testable  ( name  varchar(18),countf int,yesno  varchar(3) )");

	stat.execute("INSERT into testable (name,countf,yesno) values ('counter4',1,'yesnono');");
	System.out.println(ttries + " FAILED TEST b ********************************** ");
throw new Exception("FAILED CHECK");
}catch (SQLException e){
	tcomp = tcomp + 1;
	System.out.println(ttries + " EXPECTED TEST ERROR:  " + e.toString());
}
System.out.println("b");

try{

	ttries = ttries + 1;
	System.out.println(ttries + " t from table name " + new java.util.Date());
try{	
stat.execute("drop table testable");
}catch (SQLException e){
	System.out.println("DROP TEST ERROR: " + e.toString());
}
	stat.execute("create  table  testable  ( name  varchar(18),countf int,yesno  varchar(30) )");

	stat.execute("INSERT into testable (name,countf,yesno) values ('counter4',1,'yes');");
	stat.execute("INSERT into testable (name,yesno) values ('counter2','no');");
	stat.execute("INSERT into testable (name,countf,yesno) values (null,2,'yes');");
	stat.execute("INSERT into testable (name,countf,yesno) values ('counter4',7,'no');");
	stat.execute("INSERT into testable (countf,yesno) values (3,'yes');");
	stat.execute("INSERT into testable (name,countf,yesno) values ('counter6',6,'no');");

	stat.execute("INSERT into testable (name,countf,yesno) values ('counter7',7,'maybe');");
	stat.execute("INSERT into testable (countf,yesno) values (8,'yes');");
	stat.execute("INSERT into testable (name,countf,yesno) values (null,9,'maybe');");

	ResultSet result = stat.executeQuery("select t.name from testable t where countf in (7,1) and name='counter4'");

//displayResult(result);
	int ct = 0;
	while (result.next())
		ct = ct + 1;
try{	
stat.execute("drop table testable");
}catch (SQLException e){
	System.out.println("DROP TEST ERROR: " + e.toString());
}
	if (ct == 2)
	tcomp = tcomp + 1;
	else throw new SQLException(ttries + " FAILED TEST c ************************* ");

}catch (SQLException e){
	e.printStackTrace();
	System.out.println(ttries + " TEST ERROR *************************: " + e.toString());
	throw e;
}

System.out.println("c");
	
	try{

	ttries = ttries + 1;
	System.out.println(ttries + " 'space , commas () or where and not null primary key' " + new java.util.Date());

	stat.execute("create  table  testable  ( name  varchar(18),countf int default 22,yesno  varchar(90) default 'Yes and :) (:where or, No not null primary key' not null )");
	stat.execute("INSERT into testable (name,countf,yesno) values ('counter7',7,'NO AND  (:  not null primary key:) WHERE OR, YES');");
	stat.execute("INSERT into testable (name) values ('counter8');");


	//ResultSet result = stat.executeQuery("select * from testable where yesno = 'NO AND  (:  not null primary key:) WHERE OR, YES'");
	//displayResult(result);
	/*int ct = 0;
	while (result.next())
		ct = ct + 1;
	if (ct == 2)*/

	ResultSet result = stat.executeQuery("select distinct * from testable where name = 'counter8'");

	if (result.next()){
	if (result.getString("yesno").equals("Yes and :) (:where or, No not null primary key") && result.getInt("countf") == 22)	
	tcomp = tcomp + 1;
	else throw new SQLException(ttries + " FAILED TEST d ");

	}
try{	
stat.execute("drop table testable");
}catch (SQLException e){
	System.out.println("DROP TEST ERROR: " + e.toString());
}
}catch (SQLException e){
	e.printStackTrace();
	throw e;
	//System.out.println(ttries + " TEST ERROR: " + e.toString());
}
	
System.out.println("d");	
	try{

	ttries = ttries + 1;
	System.out.println(ttries + " space " + new java.util.Date());

	stat.execute("create  table  testable  ( name  varchar(18),countf int default 22,yesno  varchar(30) default 'Y N' not null )");
	stat.execute("INSERT into testable (name,countf,yesno) values ('counter7',7,'maybe');");
	stat.execute("INSERT into testable (name) values ('counter8');");

	ResultSet result = stat.executeQuery("select distinct * from testable where name = 'counter8'");

//	ResultSet result = stat.executeQuery("select distinct countf from testable");
	//displayResult(result);
	/*int ct = 0;
	while (result.next())
		ct = ct + 1;
	if (ct == 2)*/
	if (result.next()){
	if (result.getString("yesno").equals("Y N") && result.getInt("countf") == 22)	
	tcomp = tcomp + 1;
	else throw new SQLException(ttries + " FAILED TEST e *********************** ");

	}
	//stat.execute("drop table testable");

}catch (SQLException e){
	e.printStackTrace();
	throw e;
	//System.out.println(ttries + " TEST ERROR: " + e.toString());
}
	

System.out.println("e");


try{	
stat.execute("drop table testable");
}catch (SQLException e){
	System.out.println("TEST ERROR: " + e.toString());
}





	ttries = ttries + 1;
	stat.execute("create  table  testable  ( name  varchar(18) ,countf int,yesno  varchar(43) )");
	tcomp = tcomp + 1;

try{

	ttries = ttries + 1;
	System.out.println("INSERT into testable (name,countf,yesno) values ('counter',1,'yes');");
	stat.execute("INSERT into testable (name,countf,yesno) values ('counter',1,'yes');");
	tcomp = tcomp + 1;
}catch (SQLException e){
	e.printStackTrace();
	System.out.println("TEST ERROR: " + e.toString());
	throw e;
}


System.out.println("f");




try{

	ttries = ttries + 1;
	System.out.println("INSERT into testable (name,countf,yesno) values ('counter',2,'no');");
	stat.execute("INSERT into testable (name,countf,yesno) values ('counter',2,'no');");
	tcomp = tcomp + 1;
}catch (SQLException e){
	e.printStackTrace();
	System.out.println(ttries + " TEST ERROR: " + e.toString());
	throw e;
}

try{

	ttries = ttries + 1;
	System.out.println("INSERT into testable (name,countf,yesno) values ('counter',3,'yes');");
	stat.execute("INSERT into testable (name,countf,yesno) values ('counter',3,'yes');");
	tcomp = tcomp + 1;
}catch (SQLException e){
	e.printStackTrace();
	System.out.println(ttries + " TEST ERROR: " + e.toString());
	throw e;
}

System.out.println("g");
try{

	ttries = ttries + 1;
	System.out.println("select * from testable");
	ResultSet result = stat.executeQuery("select * from testable");
	displayResult(result);
	tcomp = tcomp + 1;
}catch (SQLException e){
	e.printStackTrace();
	System.out.println(ttries + " TEST ERROR: " + e.toString());
	throw e;
}

try{

	ttries = ttries + 1;
	System.out.println("select countf from testable where yesno='yes';");
	ResultSet result = stat.executeQuery("select countf from testable where yesno='yes';");
	displayResult(result);
	tcomp = tcomp + 1;
}catch (SQLException e){
	e.printStackTrace();
	System.out.println(ttries + " TEST ERROR: " + e.toString());
	throw e;
}

try{

	ttries = ttries + 1;
	System.out.println("select countf,yesno from testable where yesno='yes' AND countf=3;");
	ResultSet result = stat.executeQuery("select countf,yesno from testable where yesno='yes' AND countf=3;");
	displayResult(result);
	tcomp = tcomp + 1;
}catch (SQLException e){
	e.printStackTrace();
	System.out.println(ttries + " TEST ERROR: " + e.toString());
	throw e;
}
System.out.println("h");

try{

	ttries = ttries + 1;
	System.out.println("UPDATE testable SET  countf=4 where  countf = 3");
	stat.execute("UPDATE testable SET  countf=4 where  countf = 3");
	tcomp = tcomp + 1;
}catch (SQLException e){
	e.printStackTrace();
	System.out.println("TEST ERROR: " + e.toString());
	throw e;
}

try{

	ttries = ttries + 1;
	System.out.println("UPDATE testable SET  countf=4 where  countf = 2");
	stat.execute("UPDATE testable SET  countf=4 where  countf = 2");
	tcomp = tcomp + 1;
}catch (SQLException e){
	e.printStackTrace();
	System.out.println("TEST ERROR: " + e.toString());
	throw e;
}
System.out.println("i");
try{

	ttries = ttries + 1;
	System.out.println("UPDATE testable SET  countf=6 where  countf = 4 AND yesno='no';");
	stat.execute("UPDATE testable SET  countf=6 where  countf = 4 AND yesno='no';");
	tcomp = tcomp + 1;
}catch (SQLException e){
	e.printStackTrace();
	System.out.println("TEST ERROR: " + e.toString());
	throw e;
}

try{

	ttries = ttries + 1;
	System.out.println("select * from testable");
	ResultSet result = stat.executeQuery("select * from testable");
	displayResult(result);
	tcomp = tcomp + 1;
}catch (SQLException e){
	e.printStackTrace();
	System.out.println(ttries + " TEST ERROR: " + e.toString());
	throw e;
}
System.out.println("j");
try{

	ttries = ttries + 1;
	System.out.println("select * from testable where countf=1 OR countf=6");
	ResultSet result = stat.executeQuery("select * from testable where countf=1 OR countf=6");
	displayResult(result);
	tcomp = tcomp + 1;
}catch (SQLException e){
	e.printStackTrace();
	System.out.println(ttries + " TEST ERROR: " + e.toString());
	throw e;
}
System.out.println("k");
try{

	ttries = ttries + 1;
	ResultSet result = stat.executeQuery("select * from testable where countf=1");
	if (!result.next())
		throw new SQLException("countf=1 not in table");
	System.out.println("delete from testable where countf=?");
	PreparedStatement pstat = Conn.prepareStatement("delete from testable where countf=?");
	pstat.setInt(1,1);
	pstat.execute();
	result = stat.executeQuery("select * from testable where countf=1");
	if (!result.next())
		tcomp = tcomp + 1;
	else throw new SQLException(ttries + " FAILED TEST f ");

}catch (SQLException e){
	e.printStackTrace();
	System.out.println("TEST ERROR: " + e.toString());
	throw e;
}


try{

	ttries = ttries + 1;
	System.out.println("select * from testable");
	ResultSet result = stat.executeQuery("select * from testable");
	displayResult(result);
	tcomp = tcomp + 1;
}catch (SQLException e){
	e.printStackTrace();
	System.out.println(ttries + " TEST ERROR: " + e.toString());
	throw e;
}
System.out.println("l");

try{

	ttries = ttries + 1;
	stat.execute("drop table realm_user");
	stat.execute("drop table realm_userrole");
	
	tcomp = tcomp + 1;
}catch (SQLException e){
	//e.printStackTrace();
	tcomp = tcomp + 1;

	System.out.println(ttries + " TEST ERROR: " + e.toString());
}

try{

	ttries = ttries + 1;
	System.out.println(ttries + " pkey " + new java.util.Date());

	stat.execute("CREATE TABLE realm_user (realm_username varchar(120) primary key,realm_passphrase varchar(120))");
	stat.execute("CREATE TABLE realm_userrole (  realm_user varchar(120), realm_rolename varchar(120), PRIMARY KEY  (realm_user,realm_rolename))");
	stat.execute("INSERT into realm_user (realm_username,realm_passphrase) values ('ruser1','tigres');");
	System.out.println(ttries + " End Task " + new java.util.Date());

	tcomp = tcomp + 1;
}catch (SQLException e){
	e.printStackTrace();
	System.out.println(ttries + " TEST ERROR: " + e.toString());
	throw e;
}
System.out.println("m");
try{

	ttries = ttries + 1;
	stat.execute("alter table realm_user add constraint ws_userid_fk foreign key ( realm_username ) references realm_userrole ( realm_user )");
	System.out.println(ttries + " FAILED TEST g *************** ");

}catch (SQLException e){

	//if (e.toString().indexOf("Cannot add or update a child row: a foreign key constraint fails") > -1)
		tcomp = tcomp + 1;
	//else{
	
	//e.printStackTrace();
	System.out.println(ttries + " EXPECTED TEST ERROR: " + e.toString());
	//}
}

try{

	ttries = ttries + 1;

	stat.execute("INSERT into realm_userrole (realm_user,realm_rolename) values ('ruser1','role2');");
	stat.execute("alter table realm_user add constraint ws_userid_fk foreign key ( realm_username ) references realm_userrole ( realm_user )");

	tcomp = tcomp + 1;
}catch (SQLException e){
	e.printStackTrace();
	System.out.println(ttries + " TEST ERROR: " + e.toString());
	throw e;
}
System.out.println("n");
try{

	ttries = ttries + 1;
	stat.execute("INSERT into realm_user (realm_username,realm_passphrase) values ('ruser2','tigres');");
	System.out.println(ttries + " FAILED TEST h *************** ");
}catch (SQLException e){
	//if (e.toString().indexOf("Cannot add or update a child row: a foreign key constraint fails") > -1)
		tcomp = tcomp + 1;
	//else{
	
	//e.printStackTrace();
	System.out.println(ttries + " EXPECTED TEST ERROR: " + e.toString());
	//}
}

System.out.println("o");
try{

	ttries = ttries + 1;
	stat.execute("INSERT into realm_userrole (realm_user,realm_rolename) values ('ruser2','role2');");
tcomp = tcomp + 1;
	ttries = ttries + 1;

	stat.execute("INSERT into realm_user (realm_username,realm_passphrase) values ('ruser2','tigres');");
//	tcomp = tcomp + 1;

}catch (SQLException e){

	System.out.println(ttries + " TEST ERROR: " + e.toString());
	tcomp = tcomp + 1;
}
System.out.println("p");

try{

	ttries = ttries + 1;
try{	
stat.execute("drop table testable");
}catch (SQLException e){
	System.out.println("DROP TEST ERROR: " + e.toString());
}
	stat.execute("create  table  testable  ( name  varchar(18) not null primary key,countf int,yesno  varchar(43) )");
	stat.execute("INSERT into testable (name,countf,yesno) values ('counter',1,'yes');");
try{

	stat.execute("INSERT into testable (name,countf,yesno) values ('counter',1,'yes');");
}catch (SQLException e2){
	tcomp = tcomp + 1;
	}
}catch (SQLException e){
	e.printStackTrace();
	System.out.println(ttries + " TEST ERROR: " + e.toString());
	throw e;
}

System.out.println("q");
try{

	ttries = ttries + 1;

try{	
stat.execute("drop table testable");
}catch (SQLException e){
	System.out.println("DROP TEST ERROR: " + e.toString());
}
	stat.execute("create  table  testable  ( name  varchar(18),countf int,yesno  varchar(43) )");

	stat.execute("INSERT into testable (name,countf,yesno) values ('counter',1,'yes');");
	stat.execute("INSERT into testable (countf,yesno) values (42,'yes');");
	stat.execute("INSERT into testable (name,countf,yesno) values (null,2,'yes');");
	stat.execute("INSERT into testable (name,countf,yesno) values ('counter',1,'no');");
	stat.execute("INSERT into testable (countf,yesno) values (3,'yes');");
	stat.execute("INSERT into testable (name,countf,yesno) values (null,2,'no');");

	stat.execute("INSERT into testable (name,countf,yesno) values ('counter',1,'no');");
	stat.execute("INSERT into testable (countf,yesno) values (3,'yes');");
	stat.execute("INSERT into testable (name,countf,yesno) values (null,2,'no');");


	ResultSet result = stat.executeQuery("select * from testable");
	displayResult(result);

	tcomp = tcomp + 1;

}catch (SQLException e){
	e.printStackTrace();
	System.out.println(ttries + " TEST ERROR: " + e.toString());
	throw e;
}

System.out.println("r");
try{

	ttries = ttries + 1;
try{	
stat.execute("drop table testable");
}catch (SQLException e){
	System.out.println("DROP TEST ERROR: " + e.toString());
}
	System.out.println("rb");
	//Conn.close();
	//Conn = driver.connect(url,props);
	//stat = Conn.createStatement();

	stat.execute("create  table  testable  ( name  varchar(18) not null,countf int,yesno  varchar(43) );");
	stat.execute("INSERT into testable (name,countf,yesno) values ('counter',1,'yes');");
	stat.execute("INSERT into testable (countf,yesno) values (2,'yes');");
	System.out.println("rc");
try{
	
	stat.execute("INSERT into testable (name,countf,yesno) values (null,2,'yes');");
	ResultSet result = stat.executeQuery("select * from testable;");
	displayResult(result);
	System.out.println(ttries + " FAILED TEST i *************** ");

}catch (SQLException e2){
System.out.println(ttries + " EXPECTED ERROR: " + e2.toString());
	tcomp = tcomp + 1;
	}
}catch (SQLException e){
	e.printStackTrace();
	System.out.println(ttries + " TEST ERROR: " + e.toString());
	throw e;
}
System.out.println("s");
try{

	ttries = ttries + 1;

try{	
stat.execute("drop table testable");
}catch (SQLException e){
	System.out.println("DROP TEST ERROR: " + e.toString());
}
	stat.execute("create  table  testable  ( name  varchar(18),countf int,yesno  varchar(43) )");

	stat.execute("INSERT into testable (name,countf,yesno) values ('counter',1,'yes');");
	stat.execute("INSERT into testable (countf,yesno) values (3,'no');");
	stat.execute("INSERT into testable (name,countf,yesno) values (null,2,'yes');");
	stat.execute("INSERT into testable (name,countf,yesno) values ('counter',1,'no');");
	stat.execute("INSERT into testable (countf,yesno) values (3,'yes');");
	stat.execute("INSERT into testable (name,countf,yesno) values (null,2,'no');");

	stat.execute("INSERT into testable (name,countf,yesno) values ('counter',1,'no');");
	stat.execute("INSERT into testable (countf,yesno) values (3,'yes');");
	stat.execute("INSERT into testable (name,countf,yesno) values (null,2,'no');");


	ResultSet result = stat.executeQuery("select distinct countf from testable");
	//displayResult(result);
	int ct = 0;
	while (result.next())
		ct = ct + 1;
	if (ct == 3)
	tcomp = tcomp + 1;
	else throw new SQLException(ttries + " FAILED TEST j ");


}catch (SQLException e){
	e.printStackTrace();
	System.out.println(ttries + " TEST ERROR: " + e.toString());
	throw e;
}

System.out.println("t");

try{

	ttries = ttries + 1;

try{	
stat.execute("drop table testable");
}catch (SQLException e){
	System.out.println("DROP TEST ERROR: " + e.toString());
}
	stat.execute("create  table  testable  ( name  varchar(18),countf int,yesno  varchar(43) )");

	stat.execute("INSERT into testable (name,countf,yesno) values ('counter4',1,'yes');");
	stat.execute("INSERT into testable (name,yesno) values ('counter2','no');");
	stat.execute("INSERT into testable (name,countf,yesno) values (null,2,'yes');");
	stat.execute("INSERT into testable (name,countf,yesno) values ('counter4',7,'no');");
	stat.execute("INSERT into testable (countf,yesno) values (3,'yes');");
	stat.execute("INSERT into testable (name,countf,yesno) values ('counter6',6,'no');");

	stat.execute("INSERT into testable (name,countf,yesno) values ('counter7',7,'maybe');");
	stat.execute("INSERT into testable (countf,yesno) values (8,'yes');");
	stat.execute("INSERT into testable (name,countf,yesno) values (null,9,'maybe');");

	ResultSet result = stat.executeQuery("select * from testable where countf in (7,1) and name='counter4'");

//	ResultSet result = stat.executeQuery("select distinct countf from testable");
	//displayResult(result);
	int ct = 0;
	while (result.next())
		ct = ct + 1;
	if (ct == 2)
	tcomp = tcomp + 1;
	else throw new SQLException(ttries + " FAILED TEST k ");


}catch (SQLException e){
	e.printStackTrace();
	System.out.println(ttries + " TEST ERROR: " + e.toString());
	throw e;
}

System.out.println("u");

try{

	ttries = ttries + 1;

try{	
stat.execute("drop table testable");
}catch (SQLException e){
	System.out.println("DROP TEST ERROR: " + e.toString());
}
	stat.execute("create  table  testable  ( name  varchar(18),countf int,yesno  varchar(43) )");

	stat.execute("INSERT into testable (name,countf,yesno) values ('counter4',1,'yes');");
	stat.execute("INSERT into testable (name,yesno) values ('counter2','no');");
	stat.execute("INSERT into testable (name,countf,yesno) values (null,2,'yes');");
	stat.execute("INSERT into testable (name,countf,yesno) values ('counter4',7,'no');");
	stat.execute("INSERT into testable (countf,yesno) values (3,'yes');");
	stat.execute("INSERT into testable (name,countf,yesno) values ('counter6',6,'no');");

	stat.execute("INSERT into testable (name,countf,yesno) values ('counter7',7,'maybe');");
	stat.execute("INSERT into testable (countf,yesno) values (8,'yes');");
	stat.execute("INSERT into testable (name,countf,yesno) values (null,9,'maybe');");

	ResultSet result = stat.executeQuery("select * from testable t where countf in (7,1) and name='counter4'");

//	ResultSet result = stat.executeQuery("select distinct countf from testable");
	//displayResult(result);
	int ct = 0;
	while (result.next())
		ct = ct + 1;
	if (ct == 2)
	tcomp = tcomp + 1;
	else throw new SQLException(ttries + " FAILED TEST l ");

}catch (SQLException e){
	e.printStackTrace();
	System.out.println(ttries + " TEST ERROR: " + e.toString());
throw e;
}

try{

	ttries = ttries + 1;

try{	
stat.execute("drop table testable");
}catch (SQLException e){
	System.out.println("DROP TEST ERROR: " + e.toString());
}
	stat.execute("create  table  testable  ( name  varchar(18),countf int,yesno  varchar(43) )");

	stat.execute("INSERT into testable (name,countf,yesno) values ('counter4',1,'yes');");
	stat.execute("INSERT into testable (name,yesno) values ('counter2','no');");
	stat.execute("INSERT into testable (name,countf,yesno) values (null,2,'yes');");
	stat.execute("INSERT into testable (name,countf,yesno) values ('counter4',7,'no');");
	stat.execute("INSERT into testable (countf,yesno) values (3,'yes');");
	stat.execute("INSERT into testable (name,countf,yesno) values ('counter6',6,'no');");

	stat.execute("INSERT into testable (name,countf,yesno) values ('counter7',7,'maybe');");
	stat.execute("INSERT into testable (countf,yesno) values (8,'yes');");
	stat.execute("INSERT into testable (name,countf,yesno) values (null,9,'maybe');");

	ResultSet result = stat.executeQuery("select * from testable t where t.countf in (7,1) and t.name='counter4'");

//	ResultSet result = stat.executeQuery("select distinct countf from testable");
	//displayResult(result);
	int ct = 0;
	while (result.next())
		ct = ct + 1;
	if (ct == 2)
	tcomp = tcomp + 1;
	else throw new SQLException(ttries + " FAILED TEST m  ");

}catch (SQLException e){
	e.printStackTrace();
	System.out.println(ttries + " TEST ERROR: " + e.toString());
	throw e;
}

System.out.println("v");

try{

	ttries = ttries + 1;

try{	
stat.execute("drop table testable");
}catch (SQLException e){
	System.out.println("DROP TEST ERROR: " + e.toString());
}
	stat.execute("create  table  testable  ( name  varchar(18),countf int,yesno  varchar(43) )");

	ResultSet result = stat.executeQuery("select * from testable t where t.coudntf in (7,1) and t.name='counter4'");

//	ResultSet result = stat.executeQuery("select distinct countf from testable");
	displayResult(result);
	/*int ct = 0;
	while (result.next())
		ct = ct + 1;
	if (ct == 2)*/

}catch (SQLException e){
	tcomp = tcomp + 1;
	//System.out.println(ttries + " TEST ERROR: " + e.toString());
}
System.out.println("x");
try{

	ttries = ttries + 1;
	System.out.println(ttries + " default value " + new java.util.Date());

try{	
stat.execute("drop table testable");
}catch (SQLException e){
	System.out.println("DROP TEST ERROR: " + e.toString());
}
	stat.execute("create  table  testable  ( name  varchar(18),countf int default 22,yesno  varchar(43) default 'yes' not null )");
	stat.execute("INSERT into testable (name,countf,yesno) values ('counter7',7,'maybe');");
	stat.execute("INSERT into testable (name) values ('counter8');");

	ResultSet result = stat.executeQuery("select distinct * from testable where name = 'counter8'");

//	ResultSet result = stat.executeQuery("select distinct countf from testable");
	//displayResult(result);
	/*int ct = 0;
	while (result.next())
		ct = ct + 1;
	if (ct == 2)*/
	if (result.next()){
	//(result.getString("yesno") + " :WASUP " + result.getInt("countf"));
	if (result.getString("yesno").equals("yes") && result.getInt("countf") == 22)	
	tcomp = tcomp + 1;
		else throw new SQLException(ttries + " FAILED TEST n ");

	}

}catch (SQLException e){
	e.printStackTrace();
	//System.out.println(ttries + " TEST ERROR: " + e.toString());
	throw e;
}
//alter table realm_user add constraint ws_userid_fk foreign key ( realm_username ) references realm_userrole ( realm_user )

System.out.println("Completed " + tcomp + " tests out of " + ttries);
System.out.println("Started " + new java.util.Date(stime) + " and ended " + new java.util.Date());
System.out.println("Duration in seconds: " + ((System.currentTimeMillis() - stime)/1000));
    /*9)Enter the following SQL statements to delete entries from the table:
    delete from testable where countf=1
    delete from testable where yesno='no'
    delete from testable*/


//	stat.execute("INSERT into realm_user (realm_username,realm_passphrase) values ('ruser3','tigres2');");

//stat.execute("alter table realm_user add constraint realm_user_uq unique ( realm_username );");
//stat.execute("CREATE TABLE realm_user (realm_username varchar(120),realm_passphrase varchar(120)) ;");
//stat.execute("alter table rolleruser add constraint ru_username_uq unique ( username(40) )");
//stat.execute("alter table rolleruser add constraint ru_username_uq unique ( username2 )");
//stat.execute("alter table rolleruser add constraint ru_username_uq unique ( username(40),usernameb(41),usernamec(43) )");
//stat.execute("alter table rolleruser add constraint ru_username_uq unique ( username2a,username2b,username2c )");


	//stat.execute("create  user testu identified by  tiger role user");
	//stat.execute("create  table  testable  ( name  varchar(18) ,countf int )");
	//stat.execute("INSERT into testable (name,countf) values ('counter',2);");

	//stat.execute("create  table  testable2  ( name  varchar(18) ,countf int,yesno  varchar(3) )");
	//stat.execute("INSERT into testable2 (name,countf,yesno) values ('counter',1,'yes');");
	//stat.execute("INSERT into testable2 (name,countf,yesno) values ('counter',2,'no');");
	//stat.execute("INSERT into testable2 (name,countf,yesno) values ('counter',3,'yes');");
	//stat.execute("delete from testable2 where countf=1");
	//stat.execute("UPDATE testable SET  countf=6 where  countf = 2 AND yesno='no'");
//stat.execute("INSERT into testable (name,countf,yesno) values ('cou nter1',2,'yes');");
//	System.out.println("start " + new java.util.Date());
	/*for (int ct = 0;ct <  101;ct++){
	Conn.close();
	Conn = driver.connect(url,props);
	stat = Conn.createStatement();
	stat.execute("UPDATE testable SET  countf=" + ct + " where  name = 'counter'");
	}
	ResultSet v = stat.executeQuery("select countf from testable where name='counter'");*/
	//ResultSet v = stat.executeQuery("select * from testable2 where name='counter' AND yesno='yes'");
	/*ResultSet v = stat.executeQuery("select * from testable where name='cou nter'");
	ResultSetMetaData mres = v.getMetaData(); 
	int cc = mres.getColumnCount();
	System.out.println(cc + " column count");
	while (v.next())
		System.out.println(new java.util.Date() + " testable result from statement " + v.getString("name") + "," + v.getString("countf")+ "," + v.getString("yesno"));
	
		PreparedStatement pstat = Conn.prepareStatement("select * from testable2 where name='counter' AND yesno=?");
		pstat.setString(1,"yes");
		v = pstat.executeQuery();
		while (v.next())
		//	System.out.println(new java.util.Date() + " testable result from Pstatement " + v.getString("countf"));
			System.out.println(new java.util.Date() + " testable result from Pstatement " + v.getString("name") + "," + v.getString("countf")+ "," + v.getString("yesno"));
*/
//ResultSet v = stat.executeQuery("select * from testable where yesno='yes' AND countf=3;");
//	while (v.next())
//		System.out.println(new java.util.Date() + " testable result from statement " + v.getString("name") + "," + v.getString("countf")+ "," + v.getString("yesno"));
	 	}catch (Exception ex){
	 		ex.printStackTrace();
	 	System.out.println("TEST FAILED TO COMPLETE");
	 }finally{
	 	if (Conn != null)
	 		Conn.close();
	 }


}



static void displayResult(ResultSet result)throws SQLException{
	if (result != null) {
    
    		ResultSetMetaData mres = result.getMetaData(); 
    		int cc = mres.getColumnCount();
    		
 for (int c = 0;c < cc;c++){
    		
		 System.out.print(mres.getColumnName(c + 1) + "|" );
    }
    System.out.println("");
    
    while (result.next()){
 for (int c = 0;c < cc;c++){
        		
    		 System.out.print(result.getObject(mres.getColumnName(c + 1)) + ",");
    }
    System.out.println("");
    }
    }
	
}


}
