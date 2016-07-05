package com.stocks.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBConnection {

	/*
	 * uncomment this if you want to do unit testing
	public Connection getConnection(){
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","jbossben","Y1tchak");
		} catch (ClassNotFoundException cnfe){
			System.out.println("Missing JDBC Driver");
			cnfe.printStackTrace();
		} catch (SQLException se){
			System.out.println("Connection Failed!");
			se.printStackTrace();
		}
		return conn;
	}
	*/
	public Connection getConnection(){
		Connection conn = null;
		try {
			conn = getConnection(getDataSource("jdbc/UCPPool"));
		} catch (Exception se){
			System.out.println("Connection Failed!");
			se.printStackTrace();
		}
		return conn;
	}
	
	private DataSource getDataSource (String dataSourceLocation) throws NamingException {
		// Get a context for the JNDI look up
		Context ctx = new InitialContext();
		Context envContext = (Context) ctx.lookup("java:/comp/env");
		
		// Look up a data source
		javax.sql.DataSource ds = (javax.sql.DataSource) envContext.lookup(dataSourceLocation);
		return ds;
	}
	
	private Connection getConnection (DataSource ds) throws SQLException{
		Connection conn = null;
		// Get a connection object
		conn = ds.getConnection();
		return conn;
	}
}
