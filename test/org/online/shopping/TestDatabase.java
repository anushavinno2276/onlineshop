package org.online.shopping;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.online.shopping.repository.IDatabase;

public class TestDatabase implements IDatabase {

	/***
	 * Connection from DriverManager
	 */
	@Override
	public Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection("jdbc:h2:~/test2", "sa", "");
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}

}
