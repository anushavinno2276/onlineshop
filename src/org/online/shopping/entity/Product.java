package org.online.shopping.entity;

import java.sql.Timestamp;

/**
 * {@link Product} Entity class.
 * 
 * @author Akshay
 *
 */
public class Product {

	public static final String NAME = "name";
	public static final String COST = "cost";
	public static final String IMAGE_URL = "image";
	public static final String QUANTITY = "quantity";
	public static final String COLOUR = "colour";
	public static final String PRODUCT_ID = "productId";
	public static final String PRODUCT = "Product";
	public static final String MESSAGE = "message";
	public static final String NOTIFY = "notify";
	public static final String USER_ID = "user_id";
	public static final String PRODUCT_NAME = "productname";
	public static final String PRODUCT_DISCOUNT = "discount";
	public static final String PRODUCT_DISCOUNT_COST = "discountcost";

	private Integer id;
	private String name;
	private double cost;
	private String colour;
	private String imageURL;
	private int quantity;
	private Timestamp timestamp;
	private boolean notify;
	private int discount;

	public Integer getId() {
		return id;
	}

	/**
	 * <strong>Id</strong> of {@link Product}. <strong>Id</strong> is auto
	 * generated.
	 * 
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public boolean isNotify() {
		return notify;
	}

	public void setNotify(boolean notify) {
		this.notify = notify;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", cost=" + cost + ", colour=" + colour + ", imageUrl="
				+ imageURL + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((colour == null) ? 0 : colour.hashCode());
		long temp;
		temp = Double.doubleToLongBits(cost);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + id;
		result = prime * result + ((imageURL == null) ? 0 : imageURL.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (notify ? 1231 : 1237);
		result = prime * result + quantity;
		result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (colour == null) {
			if (other.colour != null)
				return false;
		} else if (!colour.equals(other.colour))
			return false;
		if (Double.doubleToLongBits(cost) != Double.doubleToLongBits(other.cost))
			return false;
		if (id != other.id)
			return false;
		if (imageURL == null) {
			if (other.imageURL != null)
				return false;
		} else if (!imageURL.equals(other.imageURL))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (notify != other.notify)
			return false;
		if (quantity != other.quantity)
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		return true;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

}
