package org.online.shopping.model;

import java.util.Collection;

import org.online.shopping.entity.Product;
import org.online.shopping.entity.User;

/***
 * Model class {@link DiscountProduct}
 * 
 * @author Akshay
 *
 */
public class DiscountProduct {

	private Product product;
	private Collection<User> users;

	public Product getProduct() {
		return product;
	}

	/***
	 * Product which has discount.
	 * 
	 * @param product
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	public Collection<User> getUsers() {
		return users;
	}

	/**
	 * Collection of active buyer .
	 * 
	 * @param users
	 */
	public void setUsers(Collection<User> users) {
		this.users = users;
	}

}
