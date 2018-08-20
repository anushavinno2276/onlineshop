package org.online.shopping.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.online.shopping.exception.OnlineShoppingException;

/***
 *
 * @author Akshay
 *
 */
public interface IDatabase {
	/***
	 * To get connection.
	 * 
	 * @return {@link Connection}
	 */
	public Connection getConnection();

	/***
	 * To close {@link PreparedStatement}.
	 * 
	 * @param preparedStatement
	 */
	default void close(PreparedStatement preparedStatement) {
		if (preparedStatement != null) {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				throw new OnlineShoppingException(e.getMessage());
			}
		}
	}

	/***
	 * To close {@link Statement}.
	 * 
	 * @param preparedStatement
	 */
	default void close(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				throw new OnlineShoppingException(e.getMessage());
			}
		}
	}

	/***
	 * To close {@link ResultSet}.
	 * 
	 * @param resultSet
	 */
	default void close(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				throw new OnlineShoppingException(e.getMessage());
			}
		}
	}

	/***
	 * To close {@link Connection}.
	 * 
	 * @param connection
	 */
	default void close(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new OnlineShoppingException(e.getMessage());
			}
		}
	}

}