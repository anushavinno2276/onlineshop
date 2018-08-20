package org.online.shopping.entity;

/***
 * {@link Cart} Entity class.
 * 
 * @author Akshay
 *
 */
public class Cart {
	public static final String USER_ID = "user_id";
	public static final String PRODUCT_ID = "product_id";
	public static final String QUANTITY = "quantity";
	public static final String COUPON = "coupon";
	public static final String AMOUNT = "amount";
	public static final String DISCOUNT = "discount";

	private int id;
	private int userId;
	private int productId;
	private int quantity;

	public int getUserId() {
		return userId;
	}

	/**
	 * <pre>
	 * <strong>userId</strong> of
	 * </pre>
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
	 * <strong>ProductId</strong> of product added to {@link Cart}..
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
	 * <strong>Quantity</strong> of product added into the {@link Cart}.
	 * 
	 * @param quantity
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getId() {
		return id;
	}

	/**
	 * <pre>
	 * <strong>Id</strong> of 
	 * <strong>Id</strong> is auto generated and unique.
	 * </pre>
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

}
