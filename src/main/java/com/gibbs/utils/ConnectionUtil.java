package com.gibbs.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionUtil {
	
	//this class follows the singleton design pattern
		private static ConnectionUtil cu = null;
		private static Properties prop;
		
		private ConnectionUtil() 
		{
			prop = new Properties();
			//use the class loader to get the properties file
			//then we don't have to rely on the file system
			try {
				InputStream dbProperties = ConnectionUtil.class.getClassLoader().
						getResourceAsStream("database.properties");
				prop.load(dbProperties);
				
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		public static synchronized ConnectionUtil getConnectionUtil() 
		{
			if(cu == null) 
			{
				cu = new ConnectionUtil();
			}
			return cu;
		}
		
		public Connection getConnection() 
		{
			Connection conn = null;
			try 
			{
				// registering the postgresql Driver class
				Class.forName(prop.getProperty("drv"));
				conn = DriverManager.getConnection(
						prop.getProperty("url"),
						prop.getProperty("usr"),
						prop.getProperty("psw")
						);
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
			return conn;
		}


}
