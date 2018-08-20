package org.online.shopping;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.online.shopping.repository.DatabaseFactory;
import org.online.shopping.repository.IDatabase;

public class TestDatabaseUtil {
	private IDatabase database = DatabaseFactory.getDatabase();
	private Connection connection = database.getConnection();
	private Statement statement = null;

	/***
	 * To create Cart table.
	 * 
	 * @throws SQLException
	 */
	public void createUserCartTable() throws SQLException {
		statement = connection.createStatement();
		statement.executeUpdate("CREATE TABLE  IF NOT EXISTS `user_cart` (" + "`id` int(11) NOT NULL AUTO_INCREMENT, "
				+ "`user_id` int(11) DEFAULT NULL, " + "`product_id` int(11) DEFAULT NULL, "
				+ "`quantity` int(11) DEFAULT NULL, " + "`timestamp` timestamp NULL DEFAULT NULL, "
				+ "PRIMARY KEY (`id`)) " + "ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8");
	}

	/***
	 * To create Product table.
	 * 
	 * @throws SQLException
	 */
	public void createProductTable() throws SQLException {
		statement = connection.createStatement();
		statement.executeUpdate("CREATE TABLE `product` ("
				+ "`id` int(11) NOT NULL AUTO_INCREMENT, `name` varchar(25) DEFAULT NULL,"
				+ " `cost` decimal(10,0) DEFAULT NULL,`colour` varchar(20) DEFAULT NULL,"
				+ "`imageurl` mediumtext, `timestamp` timestamp NULL DEFAULT NULL," + "`quantity` int(11) DEFAULT NULL,"
				+ "`notify` tinyint(4) DEFAULT NULL,`discount` int(11) DEFAULT NULL,"
				+ "PRIMARY KEY (`id`)) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8");
	}

	/***
	 * To create User table.
	 * 
	 * @throws SQLException
	 */
	public void createUserTable() throws SQLException {
		statement = connection.createStatement();
		statement.executeUpdate("CREATE TABLE IF NOT EXISTS `user`(" + "`id` int(11) NOT NULL AUTO_INCREMENT,"
				+ "`name` varchar(55) DEFAULT NULL," + "`password` varchar(16) DEFAULT NULL,"
				+ "`email` varchar(40) DEFAULT NULL," + "`address` varchar(255) DEFAULT NULL,"
				+ "`created_timestamp` timestamp NULL DEFAULT NULL," + "`updated_timestamp` varchar(45) DEFAULT NULL,"
				+ "`user_activation_key` varchar(45) DEFAULT NULL," + "`active` tinyint(4) DEFAULT NULL,"
				+ "`cart_quantity` int(11) DEFAULT NULL," + "`user_type` varchar(45) DEFAULT NULL,"
				+ "PRIMARY KEY (`id`)," + "UNIQUE KEY `email_UNIQUE` (`email`)"
				+ ") ENGINE=InnoDB AUTO_INCREMENT=173 DEFAULT CHARSET=utf8");
	}

	/***
	 * To create Order table.
	 * 
	 * @throws SQLException
	 */
	public void createOrderTable() throws SQLException {
		statement = connection.createStatement();
		statement.executeUpdate("CREATE TABLE  IF NOT EXISTS `order_product` ( " + "`id` varchar(40) NOT NULL,"
				+ "`user_id` int(11) DEFAULT NULL," + " `product_id` int(11) DEFAULT NULL,"
				+ "`quantity` int(11) DEFAULT NULL," + "`order_timestamp` timestamp NULL DEFAULT NULL"
				+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8");

	}

	public void createSellerProductTable() throws SQLException {
		statement = connection.createStatement();
		statement.executeUpdate("CREATE TABLE `seller_product` (" + "`user_id` int(11) DEFAULT NULL, "
				+ "`product_id` int(11) DEFAULT NULL," + "`quantity` int(11) DEFAULT NULL,"
				+ "`added_timestamp` timestamp NULL DEFAULT NULL) " + "ENGINE=InnoDB DEFAULT CHARSET=utf8");
	}

	public void createNotifyProductTable() throws SQLException {
		statement = connection.createStatement();
		statement.executeUpdate("CREATE TABLE `notify_product` ( " + "`id` int(11) NOT NULL AUTO_INCREMENT,"
				+ "`user_id` int(11) DEFAULT NULL," + " `product_id` int(11) DEFAULT NULL," + "PRIMARY KEY (`id`)"
				+ ") ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8");
	}

	public void createRecentlyAddedProductTable() throws SQLException {
		statement = connection.createStatement();
		statement.executeUpdate("CREATE TABLE `recently_added_product`(`id` int(11) NOT NULL AUTO_INCREMENT,"
				+ "`product_id` int(11) DEFAULT NULL,PRIMARY KEY (`id`)"
				+ ") ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8");
	}

	/***
	 * To drop all tables.
	 * 
	 * @throws SQLException
	 */
	public void dropTables() throws SQLException {
		statement = connection.createStatement();
		statement.executeUpdate("DROP TABLE  IF EXISTS  user_cart");
		statement.executeUpdate("DROP TABLE  IF EXISTS  product");
		statement.executeUpdate("DROP TABLE  IF EXISTS  user");
		statement.executeUpdate("DROP TABLE  IF EXISTS  order_product");
		statement.executeUpdate("DROP TABLE  IF EXISTS  seller_product");
		statement.executeUpdate("DROP TABLE  IF EXISTS  notify_product");
		statement.executeUpdate("DROP TABLE  IF EXISTS  recently_added_product");

	}

	/***
	 * To close all the resources used .
	 */
	public void closeConnection() {
		database.close(statement);
		database.close(connection);
	}

}