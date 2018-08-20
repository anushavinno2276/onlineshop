package org.online.shopping.model;

import java.sql.Timestamp;

/**
 * Model for cart notifications
 * 
 * @author Akshay
 *
 */
public class CartNotification {

	public static final String CART_ID = "cartId";
	public static final String USER_EMAIL = "userEmail";
	public static final String USER_NAME = "userName";
	public static final String PRODUCT_NAME = "productName";
	public static final String TIMESTAMP = "addedTimestamp";
	public static final String IMAGE_URL = "imageURL";
	public static final String NOTIFIED_TIME = "notifiedTime";

	private int cartId;
	private String userEmail;
	private String userName;
	private String productName;
	private String imageURL;
	private Timestamp addedTimestamp;
	private Timestamp notifiedTime;

	public String getUserEmail() {
		return userEmail;
	}

	/***
	 * Email of the user .
	 * 
	 * @param userEmail
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getProductName() {
		return productName;
	}

	/***
	 * Product name which is present in cart
	 * 
	 * @param productName
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getUserName() {
		return userName;
	}

	/***
	 * Name of user .
	 * 
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getCartId() {
		return cartId;
	}

	/***
	 * Id of cart.
	 * 
	 * @param cartId
	 */
	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public Timestamp getAddedTimestamp() {
		return addedTimestamp;
	}

	/***
	 * Time at which product added to cart
	 * 
	 * @param addedTimestamp
	 */
	public void setAddedTimestamp(Timestamp addedTimestamp) {
		this.addedTimestamp = addedTimestamp;
	}

	public String getImageURL() {
		return imageURL;
	}

	/***
	 * ImageURL of the product
	 * 
	 * @param imageURL
	 */
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public Timestamp getNotifiedTime() {
		return notifiedTime;
	}

	/***
	 * Last this cart product notified time
	 * 
	 * @param notifiedTime
	 */
	public void setNotifiedTime(Timestamp notifiedTime) {
		this.notifiedTime = notifiedTime;
	}

}
