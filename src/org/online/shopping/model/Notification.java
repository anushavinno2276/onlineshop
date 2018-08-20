package org.online.shopping.model;

import java.util.Map;

/***
 * Model class for {@link Notification}
 * 
 * @author Akshay
 *
 */
public class Notification {

	public static final String NOTIFICATION_ID = "notificationId";
	public static final String USER_EMAIL = "userEmail";
	public static final String USER_NAME = "userName";
	public static final String PRODUCT_NAME = "productName";

	private int notificationId;
	private String userEmail;
	private String userName;
	private String productName;
	private Map<String, Object> criteria;

	public int getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(int notificationId) {
		this.notificationId = notificationId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Map<String, Object> getCriteria() {
		return criteria;
	}

	public void setCriteria(Map<String, Object> criteria) {
		this.criteria = criteria;
	}

}
