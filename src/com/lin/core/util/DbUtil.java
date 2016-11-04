package com.lin.core.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbUtil {
	
	private static Properties SOURCE_PROPERTIES = null;
	
	public static Connection getSourceConnection(){
		
		SOURCE_PROPERTIES = PropertiesHelper.getProperties();
		
		String username = SOURCE_PROPERTIES.getProperty("source_username");
		String password = SOURCE_PROPERTIES.getProperty("source_password");
		String url = SOURCE_PROPERTIES.getProperty("source_url");
		String className = SOURCE_PROPERTIES.getProperty("source_driver_class_name");
		
		
		Connection conn = null;
		
		try {
			Class.forName(className);
			conn = DriverManager.getConnection(url,username,password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
}
