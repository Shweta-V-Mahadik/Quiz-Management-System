package com.app.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.app.models.User;
import com.app.utils.DbUtil;

public class UserDao implements AutoCloseable{

	Connection connection = null;
	
	public UserDao() throws SQLException {
		connection = DbUtil.getConnection();
	}
	
	//Student Registration Dao
	public void register(User user) throws SQLException {
		
		String sql = "INSERT INTO users (name, email, password_hash, role) VALUES (?,?,?,?)";
		try(PreparedStatement insertStatement = connection.prepareStatement(sql)){
			insertStatement.setString(1, user.getName());
			insertStatement.setString(2, user.getEmail());
			insertStatement.setString(3, user.getPassword());
			insertStatement.setString(4, "STUDENT");
			insertStatement.executeUpdate();
		}
	}
	
	//Login Dao
	public User login(String email, String password) throws SQLException {
		
		String sql = "SELECT * FROM users WHERE email = ? AND password_hash = ?";
		try(PreparedStatement selectStatement = connection.prepareStatement(sql)){
			selectStatement.setString(1, email);
			selectStatement.setString(2, password);
			ResultSet rs = selectStatement.executeQuery();
			if(rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String emailId = rs.getString(3);
				String passwd = rs.getString(4);
				String role = rs.getString(4);
				User user = new User(id, name, emailId, passwd, role);
				return user;
			}
		}
		return null;
		
	}
	
	@Override
	public void close() throws Exception {
		if(connection != null) {
			connection.close();
			connection = null;
		}
		
	}

}
