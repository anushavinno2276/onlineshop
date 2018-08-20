package org.online.shopping.repository;

/***
 * Factory class to get IDatabase object.
 */
import org.online.shopping.TestDatabase;
import org.online.shopping.exception.OnlineShoppingException;

public class DatabaseFactory {

	private static IDatabase database;

	private DatabaseFactory() {
	}

	/***
	 * Initialize database.
	 * 
	 * @param database
	 */
	public static void initiateDatabase(IDatabase database) {
		DatabaseFactory.database = database;
	}

	/***
	 * Based on the instance of database it returns the IDatabase.
	 * 
	 * @return IDatabase
	 */
	public static IDatabase getDatabase() {
		if (database instanceof Database)
			return new Database();
		else if (database instanceof TestDatabase)
			return new TestDatabase();
		throw new OnlineShoppingException("Database has not been configured");
	}
}
