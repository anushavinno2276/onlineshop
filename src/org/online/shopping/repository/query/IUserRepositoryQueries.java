package org.online.shopping.repository.query;

import org.online.shopping.repository.UserRepository;

/***
 * {@link UserRepository} Queries.
 * 
 * @author Akshay
 *
 */
public interface IUserRepositoryQueries {

	String SAVE_QUERY = "INSERT INTO user(name,password,email,address,created_timestamp,user_activation_key,active,user_type) VALUES(?,?,?,?,?,?,?,?)";
	String UPDATE_QUERY = "UPDATE user SET name=? ,password=?,email=?,address=?,updated_timestamp=?, user_activation_key=?,active=? WHERE id=?";
	String DELETE_QUERY = "DELETE FROM user WHERE id=?";
	String SELECT_BY_ID_QUERY = "SELECT * FROM user WHERE id=?";
	String SELECT_BY_NAME_QUERY = "SELECT * FROM user WHERE name=?";
	String SELECT_BY_EMAIL_QUERY = "SELECT * FROM user WHERE email=?";
	String SELECT_BY_TOKEN_QUERY = "SELECT * FROM user WHERE user_activation_key=?";
	String SELECT_BY_ACTIVE_QUERY = "SELECT * FROM user WHERE name=? AND active=1";
	String UPDATE_PASSWORD_QUANTITY_QUERY = "UPDATE user SET password=? WHERE id=?";
	String GET_CART_QUERY = "SELECT u.email userEmail,uc.id cartId,p.name productName,u.name userName,uc.timestamp addedTimestamp,"
			+ "p.imageurl imageURL,uc.last_notified_time notifiedTime "
			+ "FROM user_cart uc,user u, product p WHERE p.id=uc.product_id AND uc.user_id=u.id";
	String GET_ACTIVE_BUYER_QUERY = "SELECT name,email FROM user WHERE active=1 AND user_type='BUYER'";
}