package org.online.shopping.model;

public class SellerProduct {

	private int userId;
	private int productId;
	private int quantity;

	public int getUserId() {
		return userId;
	}

	/**
	 * <pre>
	 * <strong>Id</strong> of 
	 * <strong>Id</strong> is auto generated and unique.
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
	 * <strong>ProductId</strong> of product added to {@link SellerProduct}..
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
	 * <strong>Quantity</strong> of product added into the
	 * {@link SellerProduct}.
	 * 
	 * @param quantity
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
