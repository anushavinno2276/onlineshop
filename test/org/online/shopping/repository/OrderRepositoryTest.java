package org.online.shopping.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.LinkedHashMap;

import org.junit.Before;
import org.junit.Test;
import org.online.shopping.BaseTest;
import org.online.shopping.entity.Cart;
import org.online.shopping.entity.Order;
import org.online.shopping.entity.Product;
import org.online.shopping.model.OrderProducts;
import org.online.shopping.pagination.Pagination;
import org.online.shopping.pagination.PaginationFilter;
import org.online.shopping.pagination.Pagination.SortOrder;
import org.online.shopping.util.AppUtil;

/****
 * Test class for {@link OrderRepository}
 * 
 * @author Akshay
 *
 */
public class OrderRepositoryTest extends BaseTest {
	private OrderRepository orderRepository = OrderRepository.getInstance();
	private ProductRepository productRepository = ProductRepository.getInstance();
	private static Cart cart1;
	private static Product product1;

	private static final String PRODUCT_1 = "DELL";
	private static final String PRODUCT_2 = "HP";
	private static final String PRODUCT_3 = "Redmi Note 5";

	@Before
	public void initiate() {

		product1 = new Product();
		product1.setId(1);
		product1.setName(PRODUCT_1);

		cart1 = new Cart();
		cart1.setUserId(1);
		cart1.setProductId(product1.getId());

	}

	/***
	 * To Test saveToOrder() in {@link OrderRepository}.
	 */
	@Test
	public void testSaveToOrder() {
		String orderId = AppUtil.generateUniqueId();
		orderRepository.saveToOrder(cart1, orderId);
		Order order = orderRepository.getById(orderId);
		assertEquals(order.getProductId(), product1.getId().intValue());
	}

	/***
	 * Get {@link Order} by its id.
	 */
	@Test
	public void testGetById() {
		String orderId = AppUtil.generateUniqueId();
		orderRepository.saveToOrder(cart1, orderId);
		Order order = orderRepository.getById(orderId);
		assertEquals(order.getProductId(), product1.getId().intValue());
	}

	/***
	 * Expects an empty collection of orders if no orders placed.
	 */
	@Test
	public void testGetProductsForNoOrders() {
		// No orders are placed it returns an empty collection.
		String orderId1 = AppUtil.generateUniqueId();
		assertTrue(orderRepository.getProducts(orderId1).isEmpty());
	}

	/***
	 * To test getProducts() in {@link OrderRepository}. getProducts() take
	 * orderId as an input and returns all the Product details for that
	 * {@link Order}
	 */
	@Test
	public void testGetProductsOrdersPresent() {

		productRepository.save(product1);

		Product product2 = new Product();
		product2.setId(2);
		product2.setName(PRODUCT_2);
		productRepository.save(product2);

		String orderId2 = AppUtil.generateUniqueId();
		orderRepository.saveToOrder(cart1, orderId2);

		Cart cart2 = new Cart();
		cart2.setUserId(1);
		cart2.setProductId(product2.getId());
		orderRepository.saveToOrder(cart2, orderId2);

		Collection<Product> orderProducts = orderRepository.getProducts(orderId2);
		// Assert product1 name with orderProducts 1st element name.
		assertEquals(PRODUCT_1, orderProducts.iterator().next().getName());
	}

	/***
	 * Total orders present is greater than limit method returns only limit
	 * number of orders.
	 */
	@Test
	public void testPaginationIfOrdersInTableIsGreaterThanLimit() {
		Pagination pagination = new Pagination();
		pagination.setLimit(2);
		pagination.setSkip(0);
		pagination.sort = new LinkedHashMap<String, SortOrder>();
		pagination.sort.put("timestamp", SortOrder.desc);
		PaginationFilter.pagination = pagination;

		// Order for product1
		String orderId1 = AppUtil.generateUniqueId();
		productRepository.save(product1);
		orderRepository.saveToOrder(cart1, orderId1);

		// Order for product2
		String orderId2 = AppUtil.generateUniqueId();
		Product product2 = new Product();
		product2.setName(PRODUCT_2);
		product2.setCost(100);
		productRepository.save(product2);
		Cart cart2 = new Cart();
		cart2.setUserId(1);
		cart2.setProductId(product2.getId());
		orderRepository.saveToOrder(cart2, orderId2);
		// Order for product3
		String orderId3 = AppUtil.generateUniqueId();
		Product product3 = new Product();
		product3.setName(PRODUCT_3);
		product3.setCost(20000);
		productRepository.save(product3);
		Cart cart3 = new Cart();
		cart3.setUserId(1);
		cart3.setProductId(product3.getId());
		orderRepository.saveToOrder(cart3, orderId3);

		int totalRecords = orderRepository.getTotalOrderCount(1);
		Collection<OrderProducts> orderProducts = orderRepository.searchOrder(1, pagination);
		int totalRecordsReturn = orderProducts.size();
		// Total record in table is not equals to records returned but its
		// equal to pagination limit
		assertNotEquals(totalRecords, totalRecordsReturn);
		assertEquals(pagination.getLimit(), totalRecordsReturn);

	}

	/***
	 * After fetching first two records skip is incremented as limit+skip and
	 * remaining records returned.
	 */
	@Test
	public void testPaginationAfterFirstLimitOrders() {
		Pagination pagination = new Pagination();
		pagination.setLimit(2);
		pagination.setSkip(2);
		pagination.sort = new LinkedHashMap<String, SortOrder>();
		pagination.sort.put("timestamp", SortOrder.desc);
		PaginationFilter.pagination = pagination;
		// Order for product1
		String orderId1 = AppUtil.generateUniqueId();
		productRepository.save(product1);
		orderRepository.saveToOrder(cart1, orderId1);
		// Order for product2
		String orderId2 = AppUtil.generateUniqueId();
		Product product2 = new Product();
		product2.setName(PRODUCT_2);
		product2.setCost(100);
		productRepository.save(product2);
		Cart cart2 = new Cart();
		cart2.setUserId(1);
		cart2.setProductId(product2.getId());
		orderRepository.saveToOrder(cart2, orderId2);
		// Order for product3
		String orderId3 = AppUtil.generateUniqueId();
		Product product3 = new Product();
		product3.setName(PRODUCT_3);
		product3.setCost(20000);
		productRepository.save(product3);
		Cart cart3 = new Cart();
		cart3.setUserId(1);
		cart3.setProductId(product3.getId());
		orderRepository.saveToOrder(cart3, orderId3);

		Collection<OrderProducts> orderProducts = orderRepository.searchOrder(1, pagination);
		int totalRecordsReturn = orderProducts.size();
		// Total 3 orders present 2 has already fetched so remaining orders
		// returned here limit is greater than difference between total products
		// present and skip
		assertEquals(1, totalRecordsReturn);
	}

	/***
	 * Test if total product present in table is less than the limitTest if
	 * total product present in table is less than the limit
	 */
	@Test
	public void testPaginationIfOrderInTableIslessThanLimit() {
		Pagination pagination = new Pagination();
		pagination.setLimit(4);
		pagination.setSkip(0);
		pagination.sort = new LinkedHashMap<String, SortOrder>();
		pagination.sort.put("timestamp", SortOrder.desc);
		PaginationFilter.pagination = pagination;

		// Order for product1
		String orderId1 = AppUtil.generateUniqueId();
		productRepository.save(product1);
		orderRepository.saveToOrder(cart1, orderId1);
		// Order for product2
		String orderId2 = AppUtil.generateUniqueId();
		Product product2 = new Product();
		product2.setName(PRODUCT_2);
		product2.setCost(100);
		productRepository.save(product2);
		Cart cart2 = new Cart();
		cart2.setUserId(1);
		cart2.setProductId(product2.getId());
		orderRepository.saveToOrder(cart2, orderId2);

		int totalRecords = orderRepository.getTotalOrderCount(1);
		Collection<OrderProducts> orderProducts = orderRepository.searchOrder(1, pagination);
		int totalRecordsReturn = orderProducts.size();

		// Total record in table is equal to records returned but its
		// not equal to pagination limit
		assertEquals(totalRecords, totalRecordsReturn);
		assertNotEquals(pagination.getLimit(), totalRecordsReturn);

	}
}
