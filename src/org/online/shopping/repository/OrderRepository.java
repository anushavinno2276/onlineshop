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
import org.online.shopping.entity.Order;
import org.online.shopping.entity.Product;
import org.online.shopping.entity.User;
import org.online.shopping.exception.OnlineShoppingException;
import org.online.shopping.model.OrderProducts;
import org.online.shopping.pagination.Pagination;
import org.online.shopping.repository.query.IOrderRepositoryQueries;
import org.online.shopping.util.AppUtil;

/***
 * Repository of {@link Order}.
 * 
 * @author Akshay
 *
 */
public class OrderRepository {

	private static OrderRepository orderRepository;
	private IDatabase database = DatabaseFactory.getDatabase();

	private OrderRepository() {

	}

	/***
	 * To make class singleton.
	 * 
	 * @return OrderRepository
	 */
	public static OrderRepository getInstance() {
		if (orderRepository == null) {
			orderRepository = new OrderRepository();
		}
		return orderRepository;
	}

	/***
	 * To <strong>save</strong> {@link Order} .
	 * 
	 * @param cart
	 * @param orderId
	 */
	public void saveToOrder(Cart cart, String orderId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String query = IOrderRepositoryQueries.SAVE_TO_ORDER_QUERY;
		try {
			connection = database.getConnection();
			preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, orderId);
			preparedStatement.setInt(2, cart.getUserId());
			preparedStatement.setInt(3, cart.getProductId());
			preparedStatement.setInt(4, cart.getQuantity());
			preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new OnlineShoppingException(e.getMessage());
		} finally {
			database.close(preparedStatement);
			database.close(connection);
		}
	}

	/***
	 * To get the {@link Order} by <strong>id</strong>.
	 * 
	 * @param orderId
	 * @return {@link Order}
	 */
	public Order getById(String orderId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Order order = new Order();
		String query = IOrderRepositoryQueries.GET_ORDER_BY_ID_QUERY;
		try {
			connection = database.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, orderId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				order = getOrderFromResultSet(resultSet);
			}
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			database.close(resultSet);
			database.close(preparedStatement);
			database.close(connection);
		}
		return order;
	}

	/***
	 * Get all Products for a particular orderId
	 * 
	 * @param orderId
	 * @return collection of products
	 */
	public Collection<Product> getProducts(String orderId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Collection<Product> products = new ArrayList<Product>();
		String query = IOrderRepositoryQueries.SELECT_ORDER_PRODUCTS_QUERY;
		try {
			connection = database.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, orderId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				products.add(getOrderProductFromResultser(resultSet));
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

	public int getTotalOrderCount(int userId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = IOrderRepositoryQueries.GET_TOTAL_ORDER_COUNT_QUERY;
		try {
			connection = database.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, userId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			database.close(resultSet);
			database.close(preparedStatement);
			database.close(connection);
		}
		return 0;

	}

	public Collection<OrderProducts> searchOrder(int userId, Pagination pagination) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Collection<OrderProducts> orderProducts = new ArrayList<OrderProducts>();
		String query = IOrderRepositoryQueries.GET_ORDERS_QUERY + AppUtil.getRecordSortLimitQuery(pagination);
		try {
			connection = database.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, userId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				OrderProducts orderProduct = new OrderProducts();
				orderProduct.setOrderId(resultSet.getString("id"));
				orderProduct.setProduct(getOrderProductFromResultser(resultSet));
				orderProducts.add(orderProduct);
			}
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			database.close(resultSet);
			database.close(preparedStatement);
			database.close(connection);
		}
		return orderProducts;
	}

	/***
	 * To get {@link Order} from {@link ResultSet}.
	 * 
	 * @param resultSet
	 * @return {@link Order}
	 * @throws SQLException
	 */

	private Order getOrderFromResultSet(ResultSet resultSet) throws SQLException {
		Order order = new Order();
		order.setId(resultSet.getString(Order.ID));
		order.setProductId(resultSet.getInt(Cart.PRODUCT_ID));
		order.setUserId(resultSet.getInt(Cart.USER_ID));
		order.setQuantity(resultSet.getInt(Cart.QUANTITY));
		order.setTimestamp(resultSet.getTimestamp(Order.TIMESTAMP));
		return order;
	}

	/***
	 * To get {@link Product} from {@link ResultSet}.
	 * 
	 * @param resultSet
	 * @return Product
	 * @throws SQLException
	 */
	private Product getOrderProductFromResultser(ResultSet resultSet) throws SQLException {
		Product product = new Product();
		product.setName(resultSet.getString(User.NAME));
		product.setCost(resultSet.getDouble(User.COST));
		product.setColour(resultSet.getString(User.COLOUR));
		product.setImageURL(resultSet.getString(User.IMAGE_URL));
		product.setQuantity(resultSet.getInt(User.QUANTITY));
		product.setTimestamp(resultSet.getTimestamp(Order.TIMESTAMP));
		product.setDiscount(resultSet.getInt(Product.PRODUCT_DISCOUNT));
		return product;

	}

}
