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
import org.online.shopping.pagination.Pagination;
import org.online.shopping.pagination.PaginationFilter;
import org.online.shopping.repository.query.IProductRepositoryQueries;
import org.online.shopping.util.AppUtil;

/***
 * Repository of {@link Product}.
 * 
 * @author Akshay
 *
 */
public class ProductRepository {

	private static ProductRepository productRepository;
	private IDatabase database = DatabaseFactory.getDatabase();

	private ProductRepository() {

	}

	public static ProductRepository getInstance() {
		if (productRepository == null) {
			productRepository = new ProductRepository();
		}
		return productRepository;
	}

	/***
	 * To <strong>save</strong> {@link Product} .
	 * 
	 * @param product
	 * @return {@link Product}
	 */
	public Product save(Product product) {
		Connection connection = null;
		String query = IProductRepositoryQueries.SAVE_QUERY;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {

			connection = database.getConnection();
			preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, product.getName());
			preparedStatement.setDouble(2, product.getCost());
			preparedStatement.setString(3, product.getColour());
			preparedStatement.setString(4, product.getImageURL());
			preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			preparedStatement.setInt(6, product.getQuantity());
			preparedStatement.setBoolean(7, false);
			preparedStatement.executeUpdate();

			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				product.setId(resultSet.getInt(1));
			}
		} catch (SQLException e) {
			throw new OnlineShoppingException(e.getMessage());
		} finally {
			database.close(resultSet);
			database.close(connection);
			database.close(preparedStatement);
		}
		return product;
	}

	/***
	 * To <strong>update</strong> {@link Product}.
	 * 
	 * @param product
	 * @return {@link Product}
	 */
	public Product update(Product product) {
		Connection connection = null;
		String query = IProductRepositoryQueries.UPDATE_QUERY;
		PreparedStatement preparedStatement = null;
		try {
			if (product.getId() != null) {
				connection = database.getConnection();
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, product.getName());
				preparedStatement.setDouble(2, product.getCost());
				preparedStatement.setString(3, product.getColour());
				preparedStatement.setString(4, product.getImageURL());
				preparedStatement.setInt(5, product.getQuantity());
				preparedStatement.setBoolean(6, product.isNotify());
				preparedStatement.setInt(7, product.getDiscount());
				preparedStatement.setInt(8, product.getId());
				preparedStatement.executeUpdate();
			} else {
				save(product);
			}

		} catch (SQLException e) {
			throw new OnlineShoppingException(e.getMessage());
		} finally {
			database.close(preparedStatement);
			database.close(connection);
		}
		return product;
	}

	/***
	 * To <strong>delete </strong> {@link Product} .
	 * 
	 * @param id
	 */
	public void delete(int id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String query = IProductRepositoryQueries.DELETE_QUERY;
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
	 * To get all the {@link Product}s.
	 * 
	 * @return All {@link Product}
	 */
	public Collection<Product> getAllProduct() {
		Connection connection = null;
		Collection<Product> products = new ArrayList<Product>();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Pagination pagination = PaginationFilter.pagination;
		String query = IProductRepositoryQueries.SELECT_PRODUCTS_QUERY + AppUtil.getRecordSortLimitQuery(pagination);
		try {
			connection = database.getConnection();
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				products.add(getProductFromResultSet(resultSet));
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
	 * Get {@link Product} by id
	 * 
	 * @param productId
	 * @return Product
	 */
	public Product getById(int productId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Product product = new Product();
		String query = IProductRepositoryQueries.SELECT_BY_ID_QUERY;
		try {
			connection = database.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, productId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				product = getProductFromResultSet(resultSet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			database.close(resultSet);
			database.close(preparedStatement);
			database.close(connection);
		}
		return product;
	}

	/***
	 * To get the {@link Product} from {@link ResultSet}
	 * 
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	private Product getProductFromResultSet(ResultSet resultSet) throws SQLException {
		Product product = new Product();
		product.setId(resultSet.getInt(User.ID));
		product.setName(resultSet.getString(User.NAME));
		product.setCost(resultSet.getDouble(User.COST));
		product.setColour(resultSet.getString(User.COLOUR));
		product.setImageURL(resultSet.getString(User.IMAGE_URL));
		product.setQuantity(resultSet.getInt(User.QUANTITY));
		product.setNotify(resultSet.getBoolean(Product.NOTIFY));
		product.setDiscount(resultSet.getInt(Product.PRODUCT_DISCOUNT));
		return product;

	}

	/***
	 * Get quantity of Product.
	 * 
	 * @param productId
	 * @return product quantity
	 */
	public int getQuantity(int productId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = IProductRepositoryQueries.GET_QUANTITY_QUERY;
		try {
			connection = database.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, productId);
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
	 * To check whether product is notified or not.
	 * 
	 * @param productId
	 * @return
	 */
	public boolean checkIsNotified(int productId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = IProductRepositoryQueries.CHECK_PRODUCT_NOTIFY_QUERY;
		try {
			connection = database.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, productId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getBoolean(Product.NOTIFY);
			}
		} catch (Exception e) {
			throw new OnlineShoppingException(e.getMessage());
		} finally {
			database.close(resultSet);
			database.close(preparedStatement);
			database.close(connection);
		}
		return false;
	}

	/***
	 * Save product into recently_added_product table .
	 * 
	 * @param productId
	 */

	public void saveProductToRecentlyAdded(int productId) {
		Connection connection = null;
		String query = IProductRepositoryQueries.SAVE_PRODUCT_TO_RECENTLY_ADDED_QUERY;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = database.getConnection();
			preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, productId);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new OnlineShoppingException(e.getMessage());
		} finally {
			database.close(resultSet);
			database.close(connection);
			database.close(preparedStatement);
		}
	}

	/***
	 * To get all recently added product by seller
	 * 
	 * @return Collection<Product>
	 */
	public Collection<Product> getRecentlyAddedProduct() {
		Connection connection = null;
		Collection<Product> products = new ArrayList<Product>();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = IProductRepositoryQueries.GET_RECENTLY_ADDED_PRODUCT_QUERY;
		try {
			connection = database.getConnection();
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Product product = new Product();
				product.setId(resultSet.getInt(User.ID));
				product.setName(resultSet.getString(User.NAME));
				product.setCost(resultSet.getDouble(User.COST));
				product.setImageURL(resultSet.getString(User.IMAGE_URL));
				products.add(product);
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
	 * To delete recently added Products from recently_added_product table.
	 * 
	 * @param id
	 */
	public void deleteRecentProduct(int id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String query = IProductRepositoryQueries.DELETE_RECENTLY_ADDED_PRODUCT_QUERY;
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
	 * To get total number of products present in database.
	 * 
	 * @return total products
	 */
	public int getTotalProductCount() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = IProductRepositoryQueries.GET_TOTAL_PRODUCT_COUNT_QUERY;
		try {
			connection = database.getConnection();
			preparedStatement = connection.prepareStatement(query);
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
