package org.online.shopping.model;

import org.online.shopping.entity.Product;

public class OrderProducts {
	public static final String ORDER_PRODUCTS = "orderProducts";

	private String orderId;
	private Product product;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
