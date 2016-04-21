package com.didrik.web.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;

import sun.security.action.GetIntegerAction;



public class SqlUtil {

	private DataSource dataSource; //"connection pool"
	
	public SqlUtil(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	
	
	public String getStatisticHtmlTable() throws Exception {
		
		StringBuilder htmlStatisticTable = new StringBuilder(); // de Tor prata om ;)
		//System.out.println("num of users: " + getNumOfUsers());
		//System.out.println("num of countries: " +getNumOfCountries());
		//System.out.println("average: " +getAverageAge());
		
		htmlStatisticTable.append("<table border='2px'>");
		htmlStatisticTable.append("<tr>");
		htmlStatisticTable.append("<th>Number of users </th>");
		htmlStatisticTable.append("<th>Number of countries</th>");
		htmlStatisticTable.append("<th>Average age</th>");
		htmlStatisticTable.append("</tr>");
		htmlStatisticTable.append("<tr>");
		htmlStatisticTable.append("<td>"+getNumOfUsers()+"</td>");
		htmlStatisticTable.append("<td>"+getNumOfCountries()+"</td>");
		htmlStatisticTable.append("<td>"+getAverageAge()+"</td>");
		htmlStatisticTable.append("</tr>");
		htmlStatisticTable.append("</table>");
		
		return htmlStatisticTable.toString();

	}
	
	
	
	private int getNumOfUsers() throws Exception {
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = dataSource.getConnection();
			String sql = "SELECT * FROM user";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);
			int count=0;
			while(myRs.next()) {
				count++;
			}
			return count; //asnwr
		}
		finally {
			close(myConn,myStmt,myRs);
		}		
	}
	
	
	
	private int getNumOfCountries() throws Exception {
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = dataSource.getConnection();
			String sql = "SELECT COUNT(*) FROM user GROUP BY country";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);
			int count=0;
			while(myRs.next()) {
				count++;
			}
			
			return count; //asnwr
		}
		finally {
			close(myConn,myStmt,myRs);
		}		
	}
	
	
	
	private int getAverageAge() throws Exception {
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = dataSource.getConnection();
			String sql = "SELECT AVG(age) AS 'average' FROM user;";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);
			myRs.next();
			return myRs.getInt("average");
		}
		finally {
			close(myConn,myStmt,myRs);
		}		
	}
	
	
	
	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
		
		try {
			if(myRs != null)
				myRs.close();
			if(myStmt != null)
				myStmt.close();
			if(myConn != null)
				myConn.close(); // doesn't rly close database connection, it basically puts it back to the connection pool, 
				//makes it avaible for other users to use it. It's just like renting a car, u use it and then u leave it back!
		}
		catch(Exception exc) {
			exc.printStackTrace();
		}
	}
	
}
