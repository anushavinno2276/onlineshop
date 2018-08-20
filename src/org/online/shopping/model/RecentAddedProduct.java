package org.online.shopping.model;

import java.util.Collection;

import org.online.shopping.entity.Product;
import org.online.shopping.entity.User;

public class RecentAddedProduct {

	private Collection<User> users;
	private Collection<Product> products;

	public Collection<User> getUsers() {
		return users;
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}

	public Collection<Product> getProducts() {
		return products;
	}

	public void setProducts(Collection<Product> products) {
		this.products = products;
	}

}
