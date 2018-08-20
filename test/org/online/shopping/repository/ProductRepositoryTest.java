package org.online.shopping.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

import org.junit.Before;
import org.junit.Test;
import org.online.shopping.BaseTest;
import org.online.shopping.entity.Product;
import org.online.shopping.pagination.Pagination;
import org.online.shopping.pagination.PaginationFilter;
import org.online.shopping.pagination.Pagination.SortOrder;

public class ProductRepositoryTest extends BaseTest {
	private ProductRepository productRepository = ProductRepository.getInstance();

	private static final String PRODUCT_1 = "Redmi Note 5 ";
	private static final String PRODUCT_2 = "Washing Machine";
	private static final String PRODUCT_3 = "DELL";

	private static Product product1;
	private Product product2;

	@Before
	public void initiate() {
		product1 = new Product();
		product1.setName(PRODUCT_1);

		product2 = new Product();
		product2.setName(PRODUCT_2);

	}

	/***
	 * Save Product
	 */
	@Test
	public void testSave() {
		Product product = productRepository.save(product1);
		// Assert product name with saved product name.
		assertEquals(product.getName(), PRODUCT_1);
	}

	/***
	 * Update {@link Product}
	 */
	@Test
	public void testupdate() {
		Product updatedProduct = productRepository.update(product1);
		// Assert product name with updated product name.
		assertEquals(updatedProduct.getName(), PRODUCT_1);
	}

	/***
	 * Delete product
	 */
	@Test
	public void testDelete() {
		Product product = productRepository.save(product1);
		productRepository.delete(product.getId());
		Product product2 = productRepository.getById(product.getId());
		assertNotEquals(product.getId(), product2.getId());
	}

	/***
	 * Get all products saved.
	 */
	@Test
	public void testGetAllProduct() {

		productRepository.save(product1);
		productRepository.save(product2);
		Collection<Product> products = new ArrayList<Product>();
		products.add(product1);
		products.add(product2);
		Collection<Product> savedProducts = productRepository.getAllProduct();

		assertEquals(savedProducts, products);
	}

	/***
	 * Get Product by its id .
	 */
	@Test
	public void testGetById() {
		productRepository.save(product1);
		Product product = productRepository.getById(product1.getId());
		assertEquals(PRODUCT_1, product.getName());
	}

	/***
	 * Return quantity of the {@link Product}.
	 */
	@Test
	public void testGetQuantity() {
		Product savedProduct = productRepository.save(product1);
		productRepository.getQuantity(savedProduct.getId());
		assertEquals(product1.getQuantity(), savedProduct.getQuantity());
	}

	/***
	 * Total products present is greater than limit method returns only limit
	 * number of {@link Product}.
	 */
	@Test
	public void testPaginationIfProductsInTableIsGreaterThanLimit() {
		Pagination pagination = new Pagination();
		pagination.setLimit(2);
		pagination.setSkip(0);
		pagination.sort = new LinkedHashMap<String, SortOrder>();
		pagination.sort.put("id", SortOrder.asc);
		PaginationFilter.pagination = pagination;

		Product product3 = new Product();
		product3.setName(PRODUCT_3);
		product3.setCost(20000);
		productRepository.save(product1);
		productRepository.save(product2);
		productRepository.save(product3);

		int totalRecords = productRepository.getTotalProductCount();
		Collection<Product> products = productRepository.getAllProduct();
		int totalRecordsReturn = products.size();
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
	public void testPaginationAfterFirstLimitProducts() {
		Pagination pagination = new Pagination();
		pagination.setLimit(2);
		pagination.setSkip(2);
		pagination.sort = new LinkedHashMap<String, SortOrder>();
		pagination.sort.put("id", SortOrder.asc);
		PaginationFilter.pagination = pagination;
		Product product3 = new Product();
		product3.setName(PRODUCT_3);
		product3.setCost(20000);
		productRepository.save(product1);
		productRepository.save(product2);
		productRepository.save(product3);

		Collection<Product> products = productRepository.getAllProduct();
		int totalRecordsReturn = products.size();

		// Total 3 product present 2 has already fetched so remaining product
		// returned here limit is greater than difference between total products
		// present and skip
		assertEquals(1, totalRecordsReturn);
	}

	/***
	 * Test if total product present in table is less than the limit
	 */
	@Test
	public void testPaginationIfproductsInTableIslessThanLimit() {
		Pagination pagination = new Pagination();
		pagination.setLimit(4);
		pagination.setSkip(0);
		pagination.sort = new LinkedHashMap<String, SortOrder>();
		pagination.sort.put("id", SortOrder.asc);
		PaginationFilter.pagination = pagination;

		productRepository.save(product1);
		productRepository.save(product2);

		int totalRecords = productRepository.getTotalProductCount();
		Collection<Product> products = productRepository.getAllProduct();
		int totalRecordsReturn = products.size();

		// Total record in table is equal to records returned but its
		// not equal to pagination limit
		assertEquals(totalRecords, totalRecordsReturn);
		assertNotEquals(pagination.getLimit(), totalRecordsReturn);
	}
}
