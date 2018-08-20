package org.online.shopping;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.online.shopping.pagination.PaginationFilter;
import org.online.shopping.repository.DatabaseFactory;

public abstract class BaseTest {

	private TestDatabaseUtil testDatabaseUtil = new TestDatabaseUtil();

	@BeforeClass
	public static void initDatabase() {
		DatabaseFactory.initiateDatabase(new TestDatabase());
	}

	@Before
	public void createTable() throws SQLException {
		testDatabaseUtil.dropTables();
		testDatabaseUtil.createUserTable();
		testDatabaseUtil.createProductTable();
		testDatabaseUtil.createUserCartTable();
		testDatabaseUtil.createOrderTable();
		testDatabaseUtil.createSellerProductTable();
		testDatabaseUtil.createNotifyProductTable();
		testDatabaseUtil.createRecentlyAddedProductTable();
	}

	@Before
	public void destroyPagination() {
		PaginationFilter.pagination = null;
	}

	@After
	public void destroy() throws SQLException {
		testDatabaseUtil.closeConnection();
	}

}
