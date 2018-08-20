package org.online.shopping.repository.query;

import org.online.shopping.repository.CartRepository;

/***
 * {@link CartRepository} Queries.
 * 
 * @author Akshay
 *
 */
public interface ICartRepositoryQueries {
	String SAVE_TO_CART_QUERY = "INSERT INTO user_cart(user_id,product_id,quantity,timestamp) VALUES(?,?,?,?)";
	String SELECT_CART_PRODUCT_IDS_QUERY = "SELECT * FROM user_cart WHERE user_id=?";
	String UPDATE_QUERY = "UPDATE user_cart SET quantity=? WHERE product_id=? AND user_id=?";
	String GET_QUANTITY_QUERY = "SELECT  quantity FROM user_cart WHERE product_id=? AND user_id=?";
	String GET_CART_TOTAL_QUANTITY_QUERY = "SELECT SUM(quantity) FROM user_cart WHERE user_id=?";
	String GET_CART_BY_PRODUCT_ID_QUERY = "SELECT * FROM user_cart WHERE product_id=? AND user_id=?";
	String DELETE_CART_QUERY = "DELETE FROM user_cart WHERE id=?";
	String GET_CART_PRODUCTS_QUERY = "SELECT p.id,p.name,p.cost,p.colour,p.imageurl,uc.quantity,p.discount FROM product p,user_cart uc WHERE uc.user_id=? AND p.id=uc.product_id";
	String UPDATE_NOTIFIED_TIME_QUERY = "UPDATE user_cart SET last_notified_time=? WHERE id=?";
}
