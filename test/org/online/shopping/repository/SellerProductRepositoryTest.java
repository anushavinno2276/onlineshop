package org.online.shopping.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.online.shopping.BaseTest;
import org.online.shopping.entity.Product;
import org.online.shopping.entity.User;
import org.online.shopping.entity.User.UserType;
import org.online.shopping.model.Notification;
import org.online.shopping.model.SellerProduct;

public class SellerProductRepositoryTest extends BaseTest {

	private UserRepository userRepository = UserRepository.getInstance();
	private ProductRepository productRepository = ProductRepository.getInstance();
	private SellerProductRepository sellerProductRepository = SellerProductRepository.getInstance();
	private static final String USER_NAME = "username";

	private static final String PRODUCT_1 = "Redmi Note 5 ";

	private static final String PRODUCT_2 = "Washing Machine";

	private static Product product1;
	private static User user;

	@Before
	public void initiate() {
		product1 = new Product();
		product1.setName(PRODUCT_1);
		product1.setColour("Black");
		product1.setQuantity(4);

		user = new User();
		user.setName(USER_NAME);
		user.setUserType(UserType.SELLER);

	}

	/***
	 * If no product is added by the seller getAllSellerProduct() returns empty
	 * collection but not null.
	 */
	@Test
	public void testGetProductIdsForNoProductsAddedByUser() {
		User savedUser = userRepository.save(user);
		assertNotNull(sellerProductRepository.getAllSellerProduct(savedUser.getId()));
	}

	/***
	 * If seller added products getAllSellerProduct() return added productIds.
	 */
	@Test
	public void testGetAllProductsForProductsAdded() {

		User savedUser = userRepository.save(user);

		productRepository.save(product1);
		sellerProductRepository.saveProduct(savedUser.getId(), product1);

		Product product2 = new Product();
		product2.setName(PRODUCT_2);
		product2.setColour("Blue");
		product2.setQuantity(2);
		productRepository.save(product2);
		sellerProductRepository.saveProduct(savedUser.getId(), product2);
		Collection<Product> products = new ArrayList<Product>();
		products.add(product1);
		products.add(product2);

		Collection<Product> sellerProducts = sellerProductRepository.getAllSellerProduct(savedUser.getId());

		assertEquals(products, sellerProducts);
	}

	/***
	 * save product into seller_product and Product table .
	 */
	@Test
	public void testSaveProduct() {
		User savedUser = userRepository.save(user);
		productRepository.save(product1);
		sellerProductRepository.saveProduct(savedUser.getId(), product1);
		SellerProduct sellerProduct = sellerProductRepository.getByProductId(product1.getId());
		assertEquals(user.getId().intValue(), sellerProduct.getUserId());

	}

	/***
	 * If no product is added by the seller getProductIds() returns empty
	 * collection but not null.
	 */
	@Test
	public void testGetAllProductsForNoProductsAdded() {
		User savedUser = userRepository.save(user);
		assertNotNull(sellerProductRepository.getProductIds(savedUser.getId()));
	}

	/***
	 * If seller added products getProductIds() return added productIds.
	 */
	@Test
	public void testGetProductIds() {
		User savedUser = userRepository.save(user);
		productRepository.save(product1);
		sellerProductRepository.saveProduct(savedUser.getId(), product1);

		Product product2 = new Product();
		product2.setName(PRODUCT_2);
		product2.setColour("Blue");
		product2.setQuantity(2);
		productRepository.save(product2);
		sellerProductRepository.saveProduct(savedUser.getId(), product2);
		Collection<Integer> ids = new ArrayList<Integer>();
		ids.add(product1.getId());
		ids.add(product2.getId());

		Collection<Integer> productIds = sellerProductRepository.getProductIds(savedUser.getId());

		assertEquals(ids, productIds);
	}

	/***
	 * Product is not notified by any user it returns an empty
	 * {@link Notification}
	 */
	@Test
	public void testgetNotifiedUsersIfProductNotNotified() {
		User savedUser = userRepository.save(user);
		productRepository.save(product1);
		sellerProductRepository.saveProduct(savedUser.getId(), product1);

		assertNotNull(sellerProductRepository.getNotifiedUsers(product1.getId()));
	}

}
