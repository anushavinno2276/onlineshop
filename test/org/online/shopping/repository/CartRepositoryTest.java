package org.online.shopping.repository;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.online.shopping.BaseTest;
import org.online.shopping.entity.Cart;
import org.online.shopping.entity.Product;
import org.online.shopping.entity.User;
import org.online.shopping.exception.OnlineShoppingException;

/***
 * Test class for {@link CartRepository}.
 * 
 * @author Akshay
 *
 */
public class CartRepositoryTest extends BaseTest {

	private CartRepository cartRepository = CartRepository.getInstance();
	private ProductRepository productRepository = ProductRepository.getInstance();
	private UserRepository userRepository = UserRepository.getInstance();

	private static User user;

	private static String PRODUCT_1 = "DELL";
	private static String PRODUCT_2 = "Torch";

	@Before
	public void initiate() {
		user = new User();
		user.setId(1);
		user.setActive(true);
		user.setName("testUserName");
	}

	/***
	 * To check saveToCart() in {@link CartRepository}. save product into cart
	 * 
	 */
	@Test
	public void testSaveCart() {
		user = userRepository.save(user);

		Product product1 = new Product();
		product1.setName(PRODUCT_1);
		product1.setCost(100);
		product1 = productRepository.save(product1);

		Cart cart = new Cart();
		cart.setUserId(user.getId());
		cart.setProductId(product1.getId());
		cart.setQuantity(2);
		Cart savedCart = cartRepository.saveToCart(cart);

		assertEquals(savedCart.getProductId(), product1.getId().intValue());

	}

	/***
	 * 
	 * To check update {@link Cart}
	 */
	@Test
	public void testUpdateCart() {
		Cart cart1 = new Cart();
		cart1.setId(1);
		cart1.setUserId(123);
		cart1.setProductId(1);
		cart1.setQuantity(2);

		cartRepository.saveToCart(cart1);
		cart1.setQuantity(3);
		int beforeUpdatequantity = cartRepository.getQuantity(cart1);
		cartRepository.updateCart(cart1);
		int afterUpdatequantity = cartRepository.getQuantity(cart1);
		assertNotSame(afterUpdatequantity, beforeUpdatequantity);
	}

	/***
	 * To check whether getQuantity throws exception for null {@link Cart}
	 */
	@Test(expected = OnlineShoppingException.class)
	public void testGetQuantity() {
		Cart cart = null;
		cartRepository.getQuantity(cart);
	}

	/***
	 * To check deleteCart() in {@link CartRepository}
	 */
	@Test
	public void testDeleteCart() {
		user = userRepository.save(user);

		Cart cart = new Cart();
		cart.setUserId(233);
		cart.setProductId(2);
		cartRepository.saveToCart(cart);
		cartRepository.deleteCart(4);
		assertNull(cartRepository.getCartByProductId(4, user.getId()));

	}

	/***
	 * To test getProductIds in {@link CartRepository}. No products are added to
	 * Cart by User getProductIds return an empty list
	 */
	@Test
	public void testGetProductsIds() {
		user = userRepository.save(user);
		assertTrue(cartRepository.getCartProductIds(user.getId()).isEmpty());
	}

	/***
	 * To test getTotal in {@link CartRepository}
	 */
	@Test
	public void testGetTotal() {
		Product product1 = new Product();
		product1.setName(PRODUCT_1);
		product1.setCost(100);

		Product product2 = new Product();
		product2.setName(PRODUCT_2);
		product2.setCost(200);

		user = userRepository.save(user);
		productRepository.save(product1);
		productRepository.save(product2);

		Cart cart2 = new Cart();
		cart2.setUserId(user.getId());
		cart2.setProductId(product1.getId());
		cart2.setQuantity(3);
		cartRepository.saveToCart(cart2);

		Cart cart3 = new Cart();
		cart3.setUserId(user.getId());
		cart3.setProductId(product2.getId());
		cart3.setQuantity(2);
		cartRepository.saveToCart(cart3);
		// Total products added on to the cart by particular user.
		assertEquals(cart2.getQuantity() + cart3.getQuantity(), cartRepository.getTotalQuantity(user.getId()));
	}

	/***
	 * To test getCartProducts() in {@link CartRepository}. getCartProducts()
	 * take userId as an input and returns all the Product details in cart for
	 * that {@link User}
	 */
	@Test
	public void testGetCartProducts() {
		Product product1 = new Product();
		product1.setName(PRODUCT_1);
		product1.setCost(100);
		productRepository.save(product1);

		Product product2 = new Product();
		product2.setName(PRODUCT_2);
		product2.setCost(200);
		productRepository.save(product2);

		Cart cart2 = new Cart();
		cart2.setUserId(user.getId());
		cart2.setProductId(product1.getId());
		cart2.setQuantity(3);
		cartRepository.saveToCart(cart2);

		Cart cart3 = new Cart();
		cart3.setUserId(user.getId());
		cart3.setProductId(product2.getId());
		cart3.setQuantity(2);
		cartRepository.saveToCart(cart3);

		Product cartProduct1 = new Product();
		cartProduct1.setId(product1.getId());
		cartProduct1.setName(PRODUCT_1);
		cartProduct1.setCost(100);
		cartProduct1.setQuantity(3);

		Product cartProduct2 = new Product();
		cartProduct2.setId(product2.getId());
		cartProduct2.setName(PRODUCT_2);
		cartProduct2.setCost(200);
		cartProduct2.setQuantity(2);
		Collection<Product> cartProducts = new ArrayList<Product>();
		cartProducts.add(cartProduct1);
		cartProducts.add(cartProduct2);

		Collection<Product> testCartProducts = cartRepository.getCartProducts(user.getId());
		// testCartProducts is result from getCartProducts,
		// where cartProducts is collection of Products added to cart
		assertEquals(cartProducts, testCartProducts);

	}
}
