package com.didrik.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class UserDAO {

	private DataSource dataSource; //"connection pool"
	
	public UserDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public List<User> getUsers() throws Exception {
		
		ArrayList<User> users = new ArrayList<>(); 
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = dataSource.getConnection();
			String sql = "SELECT * FROM user";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);
			while(myRs.next()) {
				int id = myRs.getInt("id");
				String userName = myRs.getString("user_name");
				String password = myRs.getString("password");
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String email = myRs.getString("email");
				String sex = myRs.getString("sex");
				String country = myRs.getString("country");
				int age = myRs.getInt("age");
				
				users.add(new User(id, userName, password, firstName, lastName, email, sex, country, age));
			}
			return users;
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

	
	
	public User getUser(String id) throws Exception {
		User user = null;
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		int userId;
		try {
			myConn = dataSource.getConnection();
			userId = Integer.parseInt(id);
			String sql = "SELECT * FROM user WHERE id=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, userId);
			myRs = myStmt.executeQuery();
			
			if(myRs.next()) {
				user = new User(userId,
						myRs.getString("user_name"),
						myRs.getString("password"),
						myRs.getString("first_name"),
						myRs.getString("last_name"),
						myRs.getString("email"),
						myRs.getString("sex"),
						myRs.getString("country"),
						myRs.getInt("age"));
			}
			else {
				throw new Exception("Could not find user id: " + userId);
			}
			
			return user;	
		}
		finally {
			close(myConn, myStmt, myRs);
		}
	}

	
	
	public void addUser(User user) throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		String sql = "INSERT INTO user "
				+ "(user_name, password, first_name, last_name, email, sex, country, age) " 
				+ "values (?,?,?,?,?,?,?,?)";
		
		try {
			myConn = dataSource.getConnection(); // from connection pool
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setString(1, user.getUserName());
			myStmt.setString(2, user.getPassword());
			myStmt.setString(3, user.getFirstName());
			myStmt.setString(4, user.getLastName());
			myStmt.setString(5, user.getEmail());
			myStmt.setString(6, user.getSex());
			myStmt.setString(7, user.getCountry());
			myStmt.setInt(8, user.getAge());
			// execute sql insert
			myStmt.execute();
		}
		finally {
			// clean up JDBC object
			close(myConn, myStmt, null);
		}
		
	}
	
	

	public void deleteUser(String id) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		String sql = "DELETE FROM user WHERE id=?";
		int deleteId = Integer.parseInt(id);
		try {
			myConn = dataSource.getConnection();
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, deleteId);
			myStmt.execute();
		}
		finally {
			close(myConn, myStmt, null);
		}
	}

	
	
	public void updateUser(User user) throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		String sql = "UPDATE user "
				+ "SET user_name=?, password=?, "
				+ "first_name=?, last_name=?, "
				+ "email=?, sex=?, country=?, age=? "
				+ "WHERE id=?";
		try {
			myConn = dataSource.getConnection();
			myStmt = myConn.prepareStatement(sql);
			//System.out.println(user.getUserName());
			myStmt.setString(1, user.getUserName());
			myStmt.setString(2, user.getPassword());
			myStmt.setString(3, user.getFirstName());
			myStmt.setString(4, user.getLastName());
			myStmt.setString(5, user.getEmail());
			myStmt.setString(6, user.getSex());
			myStmt.setString(7, user.getCountry());
			myStmt.setInt(8, user.getAge());
			myStmt.setInt(9, user.getId());
			myStmt.execute();
		}
		finally {
			close(myConn, myStmt, null);
		}
	}
	
	
	
//TODO Optimize the while.
	public List<User> searchUsers(String[] searchTypes, String searchStr) throws Exception {
		ArrayList<User> users = new ArrayList<>(); 
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		String sql = "";
		for(int i=0; i<searchTypes.length; i++) {
			sql = sql + searchTypes[i] + " LIKE " + "'%" + searchStr + "%'";
			if(i!=searchTypes.length-1) {
				sql = sql + " OR ";
			}
		}
		sql = "SELECT * FROM user WHERE " + sql;
		// System.out.println(sql);
		try {
			myConn = dataSource.getConnection();
			myStmt = myConn.createStatement();
			// System.out.println(sql);
			myRs = myStmt.executeQuery(sql);
			while(myRs.next()) {
				int id = myRs.getInt("id");
				String userName = myRs.getString("user_name");
				String password = myRs.getString("password");
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String email = myRs.getString("email");
				String sex = myRs.getString("sex");
				String country = myRs.getString("country");
				int age = myRs.getInt("age");
				
				users.add(new User(id, userName, password, firstName, lastName, email, sex, country, age));
			}
			return users;
		}
		finally {
			close(myConn,myStmt,myRs);
		}
	}
	
}
