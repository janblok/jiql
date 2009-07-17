package org.jiql.test;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import org.jiql.jdbc.Driver;
import java.io.*;
import java.util.*;

public final class JIQLTestServlet extends HttpServlet
     {



  public void service(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {


    HttpSession session = request.getSession(true);
    ServletContext application = getServletContext();
    ServletConfig config = null;
    PrintWriter out = response.getWriter();
  

    try {
      response.setContentType("text/html");

      out.write(" \r\n\r\n \r\n \r\n");






ResultSet result = null;

Connection Conn = null;


	String status = "";
String url = "jdbc:jiql://local";
String host = "";
String password = "";
String user = "";
String dsName = "";

	if (request.getParameter("query") != null){
try{
	String sql = request.getParameter("sql");
	if (sql == null || sql.length() < 1)
	throw new SQLException("Please Enter a valid SQL Statement!");


	Properties props = new Properties();
	props.put("user",user);
	props.put("password",password);

	Class clazz = Class.forName("org.jiql.jdbc.Driver");
	Driver driver = (Driver) clazz.newInstance();

	Conn = driver.connect(url,props);


Statement Stmt = Conn.createStatement();
Stmt.execute(sql);
result = Stmt.getResultSet();
status = "SQL COMPLETED SUCESSFULLY";
}catch (Exception ex){
status = "<font color=\"red\">SQL FAILED " + ex.toString() + "</font>";
ex.printStackTrace(response.getWriter());

}
//Conn.close();
}


      out.write("\r\n\r\n<html>\r\n<head>\r\n  </head>\r\n  <body>\r\n\r\n    <form  method=\"post\">\r\n      <div>host:<input type=\"text\" size=\"50\" name=\"host\" value=\"");
      out.print(host);
      out.write("\"/></div>\r\n      <div>user:<input type=\"text\" name=\"user\" value=\"");
      out.print(user);
      out.write("\"/></div>\r\n <div>password:<input type=\"text\" name=\"password\" value=\"");
      out.print(password);
      out.write("\"/></div>\r\n <div>DataSource Name:<input type=\"text\" name=\"dsName\" value=\"");
      out.print(dsName);
      out.write("\"/></div>\r\n       <div>SQL:<input type=\"text\" name=\"sql\" size=\"100\"/></div>\r\n      <div><input type=\"submit\" name=\"query\" value=\"query\">\r\n      </div>\r\n    </form>\r\n    \r\n    <b>USER</b>: ");
      out.print(user);
      out.write("<br/>\r\n    <b>HOST</b>: ");
      out.print(host);
      out.write("<br/>\r\n    ");
      out.print(status);
      out.write("\r\n    \r\n    ");
 if (result != null) {
    		ResultSetMetaData mres = result.getMetaData();
    		//("mres,ext: " + mres);
    		int cc = mres.getColumnCount();
        		//("mres,ext: 2222 " + cc);


      out.write("\r\n    <div>Result INFO: Fetch Size:");
      out.print(result.getFetchSize());
      out.write("</div>\r\n\r\n    \t\t<table border=\"1\">\r\n    \t<tr>\r\n    \t\t ");
 for (int c = 0;c < cc;c++){
    		 //("mres,ext: 44444 " + c);

      out.write("\r\n    \t\t\r\n\t\t <th>");
      out.print(mres.getColumnName(c + 1));
      out.write("</th>\r\n    ");
}
      out.write("\r\n    </tr>\r\n    \r\n    ");
 while (result.next()){
      out.write("\r\n    <tr>\r\n       \t\t ");
 for (int c = 0;c < cc;c++){
      out.write("\r\n <td>");
      out.print(result.getObject(mres.getColumnName(c + 1)));
      out.write("</td>\r\n    ");
}
      out.write("\r\n    </tr>\r\n    \r\n    \r\n    ");
}
      out.write("\r\n    </table>\r\n    \r\n    ");
}
      out.write("\r\n    \r\n    <pre>\r\n    Follow the examples below and preferable in the order shown:\r\n    1)\r\n    \r\n    \r\n    4)Enter the following SQL statement to create a table:\r\n    <b><i>create  table  testable  ( name  varchar(18) ,countf int,yesno  varchar(90) )</b></i>\r\n    \r\n    5)Enter the following SQL statements to populate the table:\r\n    <b><i>INSERT into testable (name,countf,yesno) values ('counter',1,'yes');</b></i>\r\n    <b><i>INSERT into testable (name,countf,yesno) values ('counter',2,'no');</b></i>\r\n    <b><i>INSERT into testable (name,countf,yesno) values ('counter',3,'yes');</b></i>\r\n    \r\n    6)Enter the following SQL statement to select all from the table:\r\n    <b><i>select * from testable</b></i>\r\n \r\n    7)Enter the following SQL statements to select with filter from the table:\r\n    <b><i>select countf from testable where yesno='yes';</b></i>\r\n    <b><i>select countf,yesno from testable where yesno='yes' AND countf=3;</b></i>\r\n    \r\n    8)Enter the following SQL statements to update values in the table:\r\n");
      out.write("    <b><i>UPDATE testable SET  countf=4 where  countf = 3</b></i>\r\n    <b><i>UPDATE testable SET  countf=4 where  countf = 2</b></i>\r\n    <b><i>UPDATE testable SET  countf=6 where  countf = 4 AND yesno='no';</b></i>\r\n \r\n    9)Enter the following SQL statements to delete entries from the table:\r\n    <b><i>delete from testable where countf=1</b></i>\r\n    <b><i>delete from testable where yesno='no'</b></i>\r\n    <b><i>delete from testable</b></i>\r\n\r\n    10)Enter the following SQL statements to create tables with PRIMARY KEYS:\r\n    <b><i>CREATE TABLE realm_user (realm_username varchar(120),realm_passphrase varchar(120),PRIMARY KEY  (realm_username)) ;</b></i>\r\n    <b><i>CREATE TABLE realm_userrole (  realm_username varchar(120), realm_rolename varchar(120), PRIMARY KEY  (realm_username,realm_rolename)) ;</b></i>\r\n\r\n    11)Enter the following SQL statements to populate the tables:\r\n    <b><i>INSERT into realm_user (realm_username,realm_passphrase) values ('ruser1','tigres');</b></i>\r\n    <b><i>INSERT into realm_user (realm_username,realm_passphrase) values ('ruser1','tigres');</b></i>\r\n");
      out.write("    <b><i>INSERT into realm_userrole (realm_username,realm_rolename) values ('ruser1','role1');</b></i>\r\n    <b><i>INSERT into realm_userrole (realm_username,realm_rolename) values ('ruser1','role1');</b></i>\r\n    <b><i>INSERT into realm_userrole (realm_username,realm_rolename) values ('ruser1','role2');</b></i>\r\n \r\n    12)Enter the following SQL statements to ALTER with UNIQUE CONSTRAINT tables:\r\n    <b><i>drop table realm_user</b></i>\r\n    <b><i>CREATE TABLE realm_user (realm_username varchar(120),realm_passphrase varchar(120)) ;</b></i>\r\n    <b><i>alter table realm_user add constraint realm_user_uq unique ( realm_username );</b></i>\r\n    <b><i>INSERT into realm_user (realm_username,realm_passphrase) values ('ruser1','tigres');</b></i>\r\n    <b><i>INSERT into realm_user (realm_username,realm_passphrase) values ('ruser1','tigres');</b></i>\r\n \r\n    13)Enter the following SQL statements to ALTER with FOREIGN KEYS tables:\r\n    <b><i>drop table realm_user</b></i>\r\n    <b><i>drop table realm_userrole</b></i>\r\n    <b><i>CREATE TABLE realm_user (realm_username varchar(120) primary key,realm_passphrase varchar(120))</b></i>\r\n");
      out.write("    <b><i>CREATE TABLE realm_userrole (  realm_user varchar(120), realm_rolename varchar(120), PRIMARY KEY  (realm_user,realm_rolename))</b></i>\r\n    <b><i>INSERT into realm_user (realm_username,realm_passphrase) values ('ruser1','tigres');</b></i>\r\n    <b><i>alter table realm_user add constraint ws_userid_fk foreign key ( realm_username ) references realm_userrole ( realm_user )</b></i>\r\n    <b><i>INSERT into realm_userrole (realm_user,realm_rolename) values ('ruser1','role2')</b></i>\r\n    <b><i>alter table realm_user add constraint ws_userid_fk foreign key ( realm_username ) references realm_userrole ( realm_user )</b></i>\r\n    <b><i>INSERT into realm_user (realm_username,realm_passphrase) values ('ruser2','tigres');</b></i>\r\n    <b><i>INSERT into realm_userrole (realm_user,realm_rolename) values ('ruser2','role2');</b></i>\r\n    <b><i>INSERT into realm_user (realm_username,realm_passphrase) values ('ruser2','tigres');</b></i>\r\n\r\n\r\n    14)Enter the following SQL statements with not null primary key tables:\r\n    <b><i>drop table testable</b></i>\r\n");
      out.write("    <b><i>create  table  testable  ( name  varchar(18) not null primary key,countf int,yesno  varchar(90) )</b></i>\r\n    <b><i>INSERT into testable (name,countf,yesno) values ('counter',1,'yes');</b></i>\r\n    <b><i>INSERT into testable (name,countf,yesno) values ('counter',1,'yes');</b></i>\r\n\r\n    15)Enter the following SQL statements without not null tables:\r\n    <b><i>drop table testable</b></i>\r\n    <b><i>create  table  testable  ( name  varchar(18),countf int,yesno  varchar(90) )</b></i>\r\n    <b><i>INSERT into testable (name,countf,yesno) values ('counter',1,'yes');</b></i>\r\n    <b><i>INSERT into testable (name,countf,yesno) values (null,2,'yes');</b></i>\r\n    <b><i>INSERT into testable (countf,yesno) values (42,'yes');</b></i>\r\n    <b><i>select * from testable;</b></i>\r\n\r\n    16)Enter the following SQL statements with not null tables:\r\n    <b><i>drop table testable\r\n    <b><i>create  table  testable  ( name  varchar(18) not null,countf int,yesno  varchar(90) )</b></i>\r\n    <b><i>INSERT into testable (name,countf,yesno) values ('counter',1,'yes');</b></i>\r\n");
      out.write("    <b><i>INSERT into testable (name,countf,yesno) values (null,2,'yes');</b></i>\r\n\r\n    17)Enter the following SQL statements with DISTINCT and IN tables:\r\n    <b><i>drop table testable</b></i>\r\n    <b><i>create  table  testable  ( name  varchar(18),countf int,yesno  varchar(90) )</b></i>\r\n    <b><i>INSERT into testable (name,countf,yesno) values ('counter',1,'yes');</b></i>\r\n    <b><i>INSERT into testable (name,countf,yesno) values (null,2,'yes');</b></i>\r\n    <b><i>INSERT into testable (countf,yesno) values (3,'yes');</b></i>\r\n    <b><i>INSERT into testable (name,countf,yesno) values ('counter',1,'no');</b></i>\r\n    <b><i>INSERT into testable (name,countf,yesno) values (null,2,'yes');</b></i>\r\n    <b><i>INSERT into testable (countf,yesno) values (3,'no');</b></i>\r\n    <b><i>select * from testable;</b></i>\r\n    <b><i>select distinct * from testable</b></i>\r\n    <b><i>SELECT DISTINCT countf FROM testable WHERE countf IN (2,3);</b></i>\r\n    <b><i>SELECT DISTINCT t.countf FROM testable t WHERE t.countf IN (2,3);</b></i>\r\n");
      out.write("\r\n    18)Enter the following SQL statements with default value tables:\r\n    <b><i>drop table testable</b></i>\r\n    <b><i>create  table  testable  ( name  varchar(18),countf int default 22,yesno  varchar(90) )</b></i>\r\n    <b><i>INSERT into testable (name,countf,yesno) values ('counter',1,'yes');</b></i>\r\n    <b><i>INSERT into testable (name,yesno) values (counter2,'yes');</b></i>\r\n    <b><i>select * from testable;</b></i>\r\n\r\n    19)Enter the following SQL statements with 'space , commas () or where and not null primary key' tables:\r\n    <b><i>drop table testable</b></i>\r\n    <b><i>create  table  testable  ( name  varchar(18),countf int default 22,yesno  varchar(30) default 'Yes and :) (:where or, No not null primary key' not null )</b></i>\r\n    <b><i>INSERT into testable (name,countf,yesno) values ('counter7',7,'NO AND  (: not null primary key:) WHERE OR, YES');</b></i>\r\n    <b><i>INSERT into testable (name) values ('counter8')</b></i>\r\n    <b><i>select * from testable;</b></i>\r\n    <b><i>select * from testable where yesno = 'Yes and :) (:where or, No not null primary key';</b></i>\r\n");
      out.write("\r\n    20)Enter the following SQL statements with 'max column data length' tables:\r\n    <b><i>drop table testable</b></i>\r\n    <b><i>create  table  testable  ( name  varchar(18),countf int,yesno  varchar(3) )</b></i>\r\n    <b><i>INSERT into testable (name,countf,yesno) values ('counter4',1,'yesnono');</b></i>\r\n \r\n    21)Enter the following SQL statements to test ORDER BY:\r\n    <b><i>drop table testable</b></i>\r\n    <b><i>create  table  testable  ( name  varchar(18),countf int,yesno  varchar(30) )</b></i>\r\n    <b><i>INSERT into testable (name,countf,yesno) values ('counter4',1,'a');</b></i>\r\n    <b><i>INSERT into testable (name,yesno) values ('counter2','c');</b></i>\r\n    <b><i>INSERT into testable (name,countf,yesno) values (null,3,'b');</b></i>\r\n    <b><i>INSERT into testable (name,countf,yesno) values ('counter4',5,'e');</b></i>\r\n    <b><i>INSERT into testable (countf,yesno) values (4,'g');</b></i>\r\n    <b><i>INSERT into testable (name,countf,yesno) values ('counter6',6,'d');</b></i>\r\n    <b><i>INSERT into testable (name,countf,yesno) values ('counter6b',6,'g');</b></i>\r\n");
      out.write("    <b><i>INSERT into testable (name,countf,yesno) values ('counter7',7,'f');</b></i>\r\n    <b><i>INSERT into testable (countf,yesno) values (9,'h');</b></i>\r\n    <b><i>INSERT into testable (name,countf,yesno) values (null,8,'i');</b></i>\r\n    <b><i>select * from testable order by countf,yesno DESC</b></i>\r\n\r\n    21)Enter the following SQL statements to test IS NULL:\r\n    <b><i>drop table testable</b></i>\r\n    <b><i>create  table  testable  ( name  varchar(18),countf int,yesno  varchar(30) )</b></i>\r\n    <b><i>INSERT into testable (name,countf) values ('counter4',1);</b></i>\r\n    <b><i>INSERT into testable (name,countf,yesno) values ('counter6',6,'d');</b></i>\r\n    <b><i>INSERT into testable (name,countf) values ('counter4b',5);</b></i>\r\n    <b><i>INSERT into testable (name,countf,yesno) values ('counter7',7,'f');</b></i>\r\n    <b><i>select count(*) from testable where yesno is null</b></i>\r\n    <b><i>UPDATE testable SET  yesno='yep' where  yesno is null;</b></i>\r\n    <b><i>select * from testable where yesno is null;</b></i>\r\n");
      out.write("\r\n    23)Enter the following SQL statement to drop the tables:\r\n    <b><i>drop table testable</b></i>\r\n    <b><i>drop table realm_user</b></i>\r\n    <b><i>drop table realm_userrole</b></i>\r\n    </pre>\r\n  </body>\r\n</html>\r\n\r\n");

if (Conn != null)
Conn.close();

    } catch (Throwable t) {
      t.printStackTrace(out);
    } 
    	
  }
}
