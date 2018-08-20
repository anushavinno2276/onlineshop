package org.online.shopping.entity;

import java.sql.Timestamp;

/**
 * {@link Order} Entity class.
 * 
 * @author Akshay
 *
 */
public class Order {
	public static final String ID = "id";
	public static final String TIMESTAMP = "order_timestamp";
	public static final String ORDER_ID = "orderId";

	private String id;
	private int userId;
	private int productId;
	private int quantity;
	private Timestamp timestamp;

	public int getUserId() {
		return userId;
	}

	/**
	 * <strong>userId</strong> of {@link User} .
	 * 
	 * @param userId
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getProductId() {
		return productId;
	}

	/**
	 * <strong>ProductId</strong> of product ordered. One id can have multiple
	 * ProductId.
	 * 
	 * @param productId
	 */
	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	/**
	 * <strong>Quantity</strong> of product ordered.
	 * 
	 * @param quantity
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * <strong>Id</strong> of {@link Order}.
	 * 
	 * @return id
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * <strong>Timestamp</strong> of {@link Order}.
	 * 
	 * @return ordered time
	 */
	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
}
