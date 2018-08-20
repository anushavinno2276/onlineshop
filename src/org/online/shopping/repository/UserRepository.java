package org.online.shopping.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.online.shopping.entity.Cart;
import org.online.shopping.entity.User;
import org.online.shopping.entity.User.UserType;
import org.online.shopping.exception.OnlineShoppingException;
import org.online.shopping.model.CartNotification;
import org.online.shopping.repository.query.IUserRepositoryQueries;

/**
 * Crud repository for {@link User}
 * 
 * @author Akshay
 *
 */
public class UserRepository {

	private static UserRepository userRepository;
	private IDatabase database = DatabaseFactory.getDatabase();

	private UserRepository() {

	}

	public static UserRepository getInstance() {
		if (userRepository == null) {
			userRepository = new UserRepository();
		}
		return userRepository;
	}

	/**
	 * Saving a new {@link User}
	 * 
	 * @param user
	 * @return Saved {@link User}
	 */
	public User save(User user) {
		Connection connection = null;
		String query = IUserRepositoryQueries.SAVE_QUERY;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {

			connection = database.getConnection();
			preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getEmail());
			preparedStatement.setString(4, user.getAddress());
			preparedStatement.setTimestamp(5, user.getCreatedTimestamp());
			preparedStatement.setString(6, user.getUserActivationKey());
			preparedStatement.setBoolean(7, user.isActive());
			preparedStatement.setString(8, String.valueOf(user.getUserType()));
			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();

			if (resultSet.next()) {
				user.setId(resultSet.getInt(1));
			}
		} catch (SQLException e) {
			throw new OnlineShoppingException(e.getMessage());
		} finally {
			database.close(resultSet);
			database.close(preparedStatement);
			database.close(connection);
		}
		return user;
	}

	/**
	 * Update a {@link User}.
	 * 
	 * @param user
	 * @return User.
	 */
	public User update(User user) {
		Connection connection = null;
		String query = IUserRepositoryQueries.UPDATE_QUERY;
		PreparedStatement preparedStatement = null;
		try {
			if (user.getId() != null) {
				connection = database.getConnection();
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, user.getName());
				preparedStatement.setString(2, user.getPassword());
				preparedStatement.setString(3, user.getEmail());
				preparedStatement.setString(4, user.getAddress());
				preparedStatement.setTimestamp(5, user.getUpdatedTimestamp());
				preparedStatement.setString(6, user.getUserActivationKey());
				preparedStatement.setBoolean(7, user.isActive());
				preparedStatement.setInt(8, user.getId());
				preparedStatement.executeUpdate();
			} else {
				save(user);
			}

		} catch (SQLException e) {
			throw new OnlineShoppingException(e.getMessage());
		} finally {
			database.close(preparedStatement);
			database.close(connection);
		}
		return user;
	}

	/***
	 * To <strong>delete </strong> {@link User} .
	 * 
	 * @param id
	 */
	public void delete(int id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String query = IUserRepositoryQueries.DELETE_QUERY;
		try {
			connection = database.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw new OnlineShoppingException(e.getMessage());
		} finally {
			database.close(preparedStatement);
			database.close(connection);
		}
	}

	/***
	 * To get the {@link User} by <strong>Id</strong>.
	 * 
	 * @param id
	 * @return User.
	 */
	public User getById(int id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		User user = new User();
		String query = IUserRepositoryQueries.SELECT_BY_ID_QUERY;
		try {
			connection = database.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				user = getUserFromResultSet(resultSet);
			}
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			database.close(resultSet);
			database.close(preparedStatement);
			database.close(connection);
		}
		return user;
	}

	/***
	 * To get the {@link User} by <strong>Name</strong>
	 * 
	 * @param name
	 * @return User
	 */
	public User getByName(String name) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		User user = new User();
		String query = IUserRepositoryQueries.SELECT_BY_NAME_QUERY;
		try {
			connection = database.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, name);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				user = getUserFromResultSet(resultSet);
			}
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			database.close(resultSet);
			database.close(preparedStatement);
			database.close(connection);
		}
		return user;
	}

	/***
	 * To get the {@link User} by <strong>Email</strong>
	 * 
	 * @param email
	 * @return User
	 */
	public User getByEmail(String email) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		User user = new User();
		String query = IUserRepositoryQueries.SELECT_BY_EMAIL_QUERY;
		try {
			connection = database.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, email);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				user = getUserFromResultSet(resultSet);
			}
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			database.close(resultSet);
			database.close(preparedStatement);
			database.close(connection);
		}
		return user;
	}

	/***
	 * To get the {@link User} by <strong>Token</strong>
	 * 
	 * @param token
	 * @return User
	 */
	public User getByToken(String token) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		User user = new User();
		String query = IUserRepositoryQueries.SELECT_BY_TOKEN_QUERY;
		try {
			connection = database.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, token);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				user = getUserFromResultSet(resultSet);
			}
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			database.close(resultSet);
			database.close(preparedStatement);
			database.close(connection);
		}
		return user;
	}

	/***
	 * To get the <strong>active</strong> {@link User}.
	 * 
	 * @param username
	 * @return Active User
	 */
	public User getActiveUser(String username) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		User user = new User();
		String query = IUserRepositoryQueries.SELECT_BY_ACTIVE_QUERY;
		try {
			connection = database.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, username);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				user = getUserFromResultSet(resultSet);
			}
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			database.close(resultSet);
			database.close(preparedStatement);
			database.close(connection);
		}
		return user;
	}

	/***
	 * To update {@link User} password
	 * 
	 * @param id
	 * @param newPassword
	 */
	public void updatePassword(int id, String newPassword) {
		Connection connection = null;
		String query = IUserRepositoryQueries.UPDATE_PASSWORD_QUANTITY_QUERY;
		PreparedStatement preparedStatement = null;
		try {
			connection = database.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, newPassword);
			preparedStatement.setInt(2, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new OnlineShoppingException(e.getMessage());
		} finally {
			database.close(preparedStatement);
			database.close(connection);
		}
	}

	/***
	 * To get Products in {@link Cart} for all {@link User}.
	 * 
	 * @return
	 */
	public List<CartNotification> getCartProduct() {
		Connection connection = null;
		List<CartNotification> cartNotifications = new ArrayList<>();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = IUserRepositoryQueries.GET_CART_QUERY;
		try {
			connection = database.getConnection();
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				CartNotification cartNotification = new CartNotification();
				cartNotification.setCartId(resultSet.getInt(CartNotification.CART_ID));
				cartNotification.setProductName(resultSet.getString(CartNotification.PRODUCT_NAME));
				cartNotification.setUserEmail(resultSet.getString(CartNotification.USER_EMAIL));
				cartNotification.setUserName(resultSet.getString(CartNotification.USER_NAME));
				cartNotification.setAddedTimestamp(resultSet.getTimestamp(CartNotification.TIMESTAMP));
				cartNotification.setImageURL(resultSet.getString(CartNotification.IMAGE_URL));
				cartNotification.setNotifiedTime(resultSet.getTimestamp(CartNotification.NOTIFIED_TIME));
				cartNotifications.add(cartNotification);
			}
		} catch (SQLException e) {
			throw new OnlineShoppingException(e.getMessage());
		} finally {
			database.close(resultSet);
			database.close(preparedStatement);
			database.close(connection);
		}
		return cartNotifications;
	}

	/***
	 * To get all active {@link User} and UserType as Buyer
	 * 
	 * @return
	 */
	public Collection<User> getActiveBuyer() {
		Connection connection = null;
		Collection<User> users = new ArrayList<User>();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = IUserRepositoryQueries.GET_ACTIVE_BUYER_QUERY;
		try {
			connection = database.getConnection();
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				User user = new User();
				user.setName(resultSet.getString(User.NAME));
				user.setEmail(resultSet.getString(User.EMAIL));
				users.add(user);
			}
		} catch (SQLException e) {
			throw new OnlineShoppingException(e.getMessage());
		} finally {
			database.close(resultSet);
			database.close(preparedStatement);
			database.close(connection);
		}
		return users;

	}

	/***
	 * To get the {@link User} from {@link ResultSet}
	 * 
	 * @param resultSet
	 * @return User
	 * @throws SQLException
	 */
	private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
		User user = new User();
		user.setId(resultSet.getInt(User.ID));
		user.setName(resultSet.getString(User.NAME));
		user.setPassword(resultSet.getString(User.PASSWORD));
		user.setEmail(resultSet.getString(User.EMAIL));
		user.setAddress(resultSet.getString(User.ADDRESS));
		user.setUserActivationKey(resultSet.getString(User.USER_ACTIVATION_KEY));
		user.setActive(resultSet.getBoolean(User.ACTIVE));
		user.setUserType(UserType.valueOf(resultSet.getString("user_type")));
		return user;
	}

}
