package org.online.shopping.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.online.shopping.entity.Cart;
import org.online.shopping.entity.Product;
import org.online.shopping.entity.User;
import org.online.shopping.exception.OnlineShoppingException;
import org.online.shopping.model.Notification;
import org.online.shopping.model.SellerProduct;
import org.online.shopping.pagination.Pagination;
import org.online.shopping.pagination.PaginationFilter;
import org.online.shopping.repository.query.ISellerProductRepositoryQuries;
import org.online.shopping.util.AppUtil;

/**
 * Repository for {@link SellerProduct}
 * 
 * @author Akshay
 *
 */
public class SellerProductRepository {

	private static SellerProductRepository sellerProductRepository;
	private IDatabase database = DatabaseFactory.getDatabase();

	private SellerProductRepository() {

	}

	public static SellerProductRepository getInstance() {
		if (sellerProductRepository == null) {
			sellerProductRepository = new SellerProductRepository();
		}
		return sellerProductRepository;
	}

	/***
	 * To get all seller products for seller
	 * 
	 * @param username
	 * @return collection of products
	 */
	public Collection<Product> getAllSellerProduct(int userId) {
		Connection connection = null;
		Collection<Product> products = new ArrayList<Product>();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Pagination pagination = PaginationFilter.pagination;
		String query = ISellerProductRepositoryQuries.SELECT_SELLER_PRODUCT_QUERY
				+ AppUtil.getRecordSortLimitQuery(pagination);
		try {
			connection = database.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, userId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				products.add(getSellerProductFromResultset(resultSet));
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

	/***
	 * To save product
	 * 
	 * @param userId
	 * @param product
	 */
	public void saveProduct(int userId, Product product) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String query = ISellerProductRepositoryQuries.SAVE_QUERY;
		connection = database.getConnection();
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, userId);
			preparedStatement.setInt(2, product.getId());
			preparedStatement.setInt(3, product.getQuantity());
			preparedStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new OnlineShoppingException(e.getMessage());
		} finally {
			database.close(preparedStatement);
			database.close(connection);
		}
	}

	/***
	 * To get all product id .
	 * 
	 * @param userId
	 * @return collection of product id
	 */
	public Collection<Integer> getProductIds(int userId) {
		Connection connection = null;
		Collection<Integer> productIds = new ArrayList<Integer>();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = ISellerProductRepositoryQuries.SELECT_SELLER_PRODUCT_ID_QUERY;
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
	 * To update the product quantity.
	 * 
	 * @param product
	 */
	public void updateProductQuantity(Product product) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String query = ISellerProductRepositoryQuries.UPDATE_QUERY;
		try {
			connection = database.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, product.getQuantity());
			preparedStatement.setInt(2, product.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new OnlineShoppingException(e.getMessage());
		} finally {
			database.close(preparedStatement);
			database.close(connection);
		}
	}

	/***
	 * To save product to notify
	 * 
	 * @param userId
	 * @param productId
	 */
	public void saveProductToNotify(int userId, int productId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String query = ISellerProductRepositoryQuries.SAVE_PRODUCT_TO_NOTIFY_QUERY;
		connection = database.getConnection();
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, userId);
			preparedStatement.setInt(2, productId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new OnlineShoppingException(e.getMessage());
		} finally {
			database.close(preparedStatement);
			database.close(connection);
		}
	}

	/***
	 * To get seller product from {@link ResultSet}
	 * 
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	private Product getSellerProductFromResultset(ResultSet resultSet) throws SQLException {
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

	/***
	 * To get all {@link Notification} for particular productId.
	 * 
	 * @param productId
	 * @return collection of notifiedUser
	 */
	public List<Notification> getNotifiedUsers(int productId) {
		Connection connection = null;
		List<Notification> notifications = new ArrayList<>();
		Notification notifiedUser = new Notification();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = ISellerProductRepositoryQuries.SELECT_NOTIFICATIONS;
		try {
			connection = database.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, productId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				notifiedUser.setNotificationId(resultSet.getInt(Notification.NOTIFICATION_ID));
				notifiedUser.setProductName(resultSet.getString(Notification.PRODUCT_NAME));
				notifiedUser.setUserEmail(resultSet.getString(Notification.USER_EMAIL));
				notifiedUser.setUserName(resultSet.getString(Notification.USER_NAME));
				notifications.add(notifiedUser);
			}
		} catch (SQLException e) {
			throw new OnlineShoppingException(e.getMessage());
		} finally {
			database.close(resultSet);
			database.close(preparedStatement);
			database.close(connection);
		}
		return notifications;
	}

	/***
	 * To delete notifiedProduct from notify_Product table
	 * 
	 * @param id
	 */
	public void delete(Integer id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String query = ISellerProductRepositoryQuries.DELETE_QUERY;
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
	 * To get sellerProduct based on productId
	 * 
	 * @param productId
	 * @return
	 */
	public SellerProduct getByProductId(int productId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		SellerProduct sellerProduct = new SellerProduct();
		String query = ISellerProductRepositoryQuries.GET_SELLER_PRODUCT_QUERY;
		try {
			connection = database.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, productId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				sellerProduct.setUserId(resultSet.getInt(Product.USER_ID));
				sellerProduct.setProductId(resultSet.getInt(Cart.PRODUCT_ID));
				sellerProduct.setQuantity(resultSet.getInt(Product.QUANTITY));
			}
		} catch (SQLException e) {
			throw new OnlineShoppingException(e.getMessage());
		} finally {
			database.close(preparedStatement);
			database.close(connection);
		}
		return sellerProduct;
	}

	/***
	 * To get total count of product added by particular seller
	 * 
	 * @param userId
	 * @return
	 */
	public int getCountOfSellerProduct(Integer userId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = ISellerProductRepositoryQuries.GET_TOTAL_PRODUCT_COUNT_QUERY;
		try {
			connection = database.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, userId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				return resultSet.getInt(1);
			}
		} catch (SQLException e) {
			throw new OnlineShoppingException(e.getMessage());
		} finally {
			database.close(preparedStatement);
			database.close(connection);
		}
		return 0;
	}

}
