package com.app.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil {

	private static final String URL = "jdbc:mysql://localhost:3306/qms_db";
	private static final String USER = "root";
	private static final String PASSWORD = "manager";
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
		
	}
}
