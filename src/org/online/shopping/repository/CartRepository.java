package org.online.shopping.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import org.online.shopping.entity.Cart;
import org.online.shopping.entity.Product;
import org.online.shopping.entity.User;
import org.online.shopping.exception.OnlineShoppingException;
import org.online.shopping.repository.query.ICartRepositoryQueries;

/**
 * Repository of {@link Cart}.
 * 
 * @author Akshay
 *
 */

public class CartRepository {

	private static CartRepository cartRepository;
	private IDatabase database = DatabaseFactory.getDatabase();

	private CartRepository() {

	}

	/***
	 * 
	 * @return Instance of {@link CartRepository}
	 */
	public static CartRepository getInstance() {
		if (cartRepository == null) {
			cartRepository = new CartRepository();
		}
		return cartRepository;
	}

	/***
	 * To <strong>save</strong> product into the cart.
	 * 
	 * @param cart
	 */
	public Cart saveToCart(Cart cart) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = ICartRepositoryQueries.SAVE_TO_CART_QUERY;
		try {
			connection = database.getConnection();
			preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, cart.getUserId());
			preparedStatement.setInt(2, cart.getProductId());
			preparedStatement.setInt(3, cart.getQuantity());
			preparedStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();

			if (resultSet.next()) {
				cart.setId(resultSet.getInt(1));
			}
		} catch (SQLException e) {
			throw new OnlineShoppingException(e.getMessage());
		} finally {
			database.close(resultSet);
			database.close(preparedStatement);
			database.close(connection);
		}
		return cart;
	}

	/***
	 * To get all the <strong>product id</strong> for particular user.
	 * 
	 * @param userId
	 * @return Product
	 */
	public Collection<Integer> getCartProductIds(int userId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Collection<Integer> productIds = new ArrayList<Integer>();
		String query = ICartRepositoryQueries.SELECT_CART_PRODUCT_IDS_QUERY;
		try {
			connection = database.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, userId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				productIds.add(resultSet.getInt(Cart.PRODUCT_ID));
			}
		} catch (SQLException e) {
			throw new OnlineShoppingException(e.getMessage());
		} finally {
			database.close(resultSet);
			database.close(preparedStatement);
			database.close(connection);
		}
		return productIds;
	}

	/***
	 * To <strong>update</strong> {@link Cart}.
	 * 
	 * @param Cart
	 */
	public Cart updateCart(Cart cart) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String query = ICartRepositoryQueries.UPDATE_QUERY;
		try {
			connection = database.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, cart.getQuantity());
			preparedStatement.setInt(2, cart.getProductId());
			preparedStatement.setInt(3, cart.getUserId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new OnlineShoppingException(e.getMessage());
		} finally {
			database.close(preparedStatement);
			database.close(connection);
		}
		return cart;
	}

	/***
	 * To get the <strong>quantity</strong> of products added into the cart for
	 * a {@link User}.
	 * 
	 * @param cart
	 * @return Quantity of {@link Product} added to {@link Cart}.
	 */
	public int getQuantity(Cart cart) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = ICartRepositoryQueries.GET_QUANTITY_QUERY;
		try {
			connection = database.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, cart.getProductId());
			preparedStatement.setInt(2, cart.getUserId());
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt(Cart.QUANTITY);
			}
		} catch (Exception e) {
			throw new OnlineShoppingException(e.getMessage());
		} finally {
			database.close(resultSet);
			database.close(preparedStatement);
			database.close(connection);
		}
		return 0;
	}

	/***
	 * To get the total {@link Product} added to {@link Cart} for particular
	 * {@link User}.
	 * 
	 * @param cart
	 * @return Total <strong>products</strong> added to the
	 *         <strong>cart</strong>.
	 */
	public int getTotalQuantity(int userId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = ICartRepositoryQueries.GET_CART_TOTAL_QUANTITY_QUERY;
		try {
			connection = database.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, userId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt(1);
			}
		} catch (SQLException e) {
			throw new OnlineShoppingException(e.getMessage());
		} finally {
			database.close(resultSet);
			database.close(preparedStatement);
			database.close(connection);
		}
		return 0;
	}

	/***
	 * To get the {@link Cart} details for <strong>productId</strong>.
	 * 
	 * @param productId
	 * @param userId
	 * @return {@link Cart}
	 */
	public Cart getCartByProductId(int productId, int userId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Cart cart = null;
		String query = ICartRepositoryQueries.GET_CART_BY_PRODUCT_ID_QUERY;
		try {
			connection = database.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, productId);
			preparedStatement.setInt(2, userId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				cart = getCartFromResultSet(resultSet);
			}
		} catch (SQLException e) {
			throw new OnlineShoppingException(e.getMessage());
		} finally {
			database.close(resultSet);
			database.close(preparedStatement);
			database.close(connection);
		}
		return cart;
	}

	/***
	 * To <strong>delete </strong> {@link Cart} for particular ProductId.
	 * 
	 * @param productId
	 */
	public void deleteCart(int id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String query = ICartRepositoryQueries.DELETE_CART_QUERY;
		try {
			connection = database.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new OnlineShoppingException(e.getMessage());
		} finally {
			database.close(preparedStatement);
			database.close(connection);
		}
	}

	/***
	 * To get all the products present in Cart for {@link User}
	 * 
	 * @param userId
	 * @return All products in cart
	 */
	public Collection<Product> getCartProducts(int userId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Collection<Product> products = new ArrayList<Product>();
		String query = ICartRepositoryQueries.GET_CART_PRODUCTS_QUERY;
		try {
			connection = database.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, userId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				products.add(getCartProductFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			throw new OnlineShoppingException(e.getMessage());
		} finally {
			database.close(resultSet);
			database.close(preparedStatement);
			database.close(connection);
		}
		return products;
	}

	public void updatedNotifiedtime(int cartId, Timestamp timestamp) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String query = ICartRepositoryQueries.UPDATE_NOTIFIED_TIME_QUERY;
		try {
			connection = database.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setTimestamp(1, timestamp);
			preparedStatement.setInt(2, cartId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new OnlineShoppingException(e.getMessage());
		} finally {
			database.close(preparedStatement);
			database.close(connection);
		}
	}

	/***
	 * To get the {@link Cart} from {@link ResultSet}
	 * 
	 * @param resultSet
	 * @return Cart
	 * @throws SQLException
	 */
	private Cart getCartFromResultSet(ResultSet resultSet) throws SQLException {
		Cart cart = new Cart();
		cart.setId(resultSet.getInt(User.ID));
		cart.setUserId(resultSet.getInt(Cart.USER_ID));
		cart.setProductId(resultSet.getInt(Cart.PRODUCT_ID));
		cart.setQuantity(resultSet.getInt(Cart.QUANTITY));
		return cart;
	}

	/***
	 * Cart product details
	 * 
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	private Product getCartProductFromResultSet(ResultSet resultSet) throws SQLException {
		Product product = new Product();
		product.setId(resultSet.getInt(User.ID));
		product.setName(resultSet.getString(User.NAME));
		product.setCost(resultSet.getDouble(User.COST));
		product.setColour(resultSet.getString(User.COLOUR));
		product.setImageURL(resultSet.getString(User.IMAGE_URL));
		product.setQuantity(resultSet.getInt(User.QUANTITY));
		product.setDiscount(resultSet.getInt(Product.PRODUCT_DISCOUNT));
		return product;
	}

}
