package org.online.shopping.service.impl;

import java.util.Collection;

import org.online.shopping.entity.Product;
import org.online.shopping.repository.ProductRepository;

/***
 * Service class for {@link Product}
 * 
 * @author Akshay
 *
 */
public class ProductService {
	private ProductRepository productRepository = ProductRepository.getInstance();

	private static ProductService productService;

	private ProductService() {

	}

	public static ProductService getInstance() {
		if (productService == null) {
			productService = new ProductService();
		}
		return productService;
	}

	/***
	 * To get recently added product.
	 * 
	 * @return products
	 */
	public Collection<Product> getRecentlyAddedProducts() {
		return productRepository.getRecentlyAddedProduct();
	}

	/**
	 * Delete product from recently_added_product table
	 * 
	 * @param products
	 */
	public void deleteRecentProduct(Collection<Product> products) {
		for (Product product : products) {
			productRepository.deleteRecentProduct(product.getId());
		}
	}
}
