package org.online.shopping.repository;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/***
 *
 * @author Akshay
 *
 */
public class Database implements IDatabase {

	/***
	 * To get connection.
	 * 
	 * @return {@link Connection}
	 */
	public Connection getConnection() {
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");
			DataSource datasource = (DataSource) envContext.lookup("jdbc/UsersDB");
			return datasource.getConnection();
		} catch (NamingException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}