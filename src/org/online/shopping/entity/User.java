package org.online.shopping.entity;

import java.sql.Timestamp;

/**
 * {@link User} Entity Class.
 * 
 * @author Akshay
 *
 */
public class User {

	public static final String USER = "user";
	public static final String EMAIL = "email";
	public static final String PASSWORD = "password";
	public static final String ADDRESS = "address";
	public static final String ID = "id";
	public static final String USER_ACTIVATION_KEY = "user_Activation_Key";
	public static final String ACTIVE = "active";
	public static final String USER_ID = "userid";
	public static final String CART = "cart";
	public static final String PRODUCT_ID = "productid";
	public static final String USERNAME = "username";
	public static final String NAME = "name";
	public static final String COST = "cost";
	public static final String COLOUR = "colour";
	public static final String IMAGE_URL = "imageUrl";
	public static final String ACTION = "action";
	public static final String PRODUCTS = "products";
	public static final String CART_QUANTITY = "cart_quantity";
	public static final String ACTIVATION_KEY = "activationKey";
	public static final String BASEURL = "baseUrl";
	public static final String OLD_PASSWORD = "oldpassword";
	public static final String NEW_PASSWORD = "newpassword";
	public static final String CONFIRM_PASSWORD = "confirmpassword";
	public static final String USERTYPE = "usertype";
	public static final String QUANTITY = "quantity";

	private Integer id;
	private String name;
	private String password;
	private String email;
	private String address;
	private boolean active;
	private String userActivationKey;
	private Timestamp createdTimestamp;
	private Timestamp updatedTimestamp;
	private int cartQuantity;
	private UserType userType;

	/**
	 * List of type of user.
	 *
	 */
	public enum UserType {
		BUYER, SELLER
	}

	public int getCartQuantity() {
		return cartQuantity;
	}

	/**
	 * Total products in cart .
	 * 
	 * @param cartQuantity
	 */
	public void setCartQuantity(int cartQuantity) {
		this.cartQuantity = cartQuantity;
	}

	public Integer getId() {
		return id;
	}

	/**
	 * id of the user. id is auto generated and it can not be null and
	 * duplicated.
	 * 
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	/**
	 * Name of the user.Name can not be blank or null.
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	/**
	 * password of the user.Must contain minimum of 8 characters.
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	/**
	 * email of the user. email can not be null.
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	/**
	 * Address of user.
	 * 
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	public Timestamp getCreatedTimestamp() {
		return createdTimestamp;
	}

	/**
	 * created timestamp of user.
	 * 
	 * @param createdTimestamp
	 */
	public void setCreatedTimestamp(Timestamp createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public Timestamp getUpdatedTimestamp() {
		return updatedTimestamp;
	}

	/***
	 * Updated timestamp of user.
	 * 
	 * @param updatedTimestamp
	 */
	public void setUpdatedTimestamp(Timestamp updatedTimestamp) {
		this.updatedTimestamp = updatedTimestamp;
	}

	public String getUserActivationKey() {
		return userActivationKey;
	}

	/**
	 * It is a Random key generated for the User while sending Activation mail.
	 * 
	 * @param userActivationKey
	 */
	public void setUserActivationKey(String userActivationKey) {
		this.userActivationKey = userActivationKey;
	}

	public boolean isActive() {
		return active;
	}

	/**
	 * State of user account.
	 * 
	 * @param active
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	public UserType getUserType() {
		return userType;
	}

	/**
	 * Type of user.
	 * 
	 * @param userType
	 */
	public void setUserType(UserType userType) {
		this.userType = userType;
	}

}
