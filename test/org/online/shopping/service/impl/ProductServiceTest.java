package org.online.shopping.service.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.online.shopping.BaseTest;
import org.online.shopping.entity.Product;
import org.online.shopping.repository.ProductRepository;

public class ProductServiceTest extends BaseTest {
	private ProductRepository productRepository = ProductRepository.getInstance();
	private ProductService productService = ProductService.getInstance();
	private static final String PRODUCT_1 = "Redmi Note 5 ";
	private static final String PRODUCT_2 = "DELL";

	private static Product product;

	@Before
	public void intialise() {
		product = new Product();
		product.setName(PRODUCT_1);
	}

	/***
	 * To test getRecentlyAddedProduct() in {@link ProductService} if no
	 * products are added it returns and empty collection of products.
	 */
	@Test
	public void testGetRecentlyAddedProductsNoProductsAdded() {
		Collection<Product> products = productService.getRecentlyAddedProducts();
		assertNotNull(products);
	}

	/***
	 * Test getRecentlyAddedProduct() if product is added.
	 */
	@Test
	public void testGetRecentlyAddedProductsForProductsAdded() {
		Product savedProduct = productRepository.save(product);
		productRepository.saveProductToRecentlyAdded(savedProduct.getId());
		Collection<Product> products = productService.getRecentlyAddedProducts();
		assertTrue(products.contains(product));
	}

	/***
	 * the deleteRecentProduct() remove all records from table if try to fetch
	 * the records getRecentlyAddedProducts() returns empty collection
	 */
	@Test
	public void testGeDeleteRecentlyAddedProductsForProductsAdded() {
		productRepository.save(product);
		productRepository.saveProductToRecentlyAdded(product.getId());

		Product product1 = new Product();
		product1 = new Product();
		product1.setName(PRODUCT_2);
		product1.setColour("Black");
		product1.setQuantity(4);
		productRepository.save(product1);
		productRepository.saveProductToRecentlyAdded(product1.getId());

		Collection<Product> savedProducts = new ArrayList<Product>();
		savedProducts.add(product);
		savedProducts.add(product1);
		productService.deleteRecentProduct(savedProducts);

		Collection<Product> products = productService.getRecentlyAddedProducts();
		assertNotNull(products);
	}

}
