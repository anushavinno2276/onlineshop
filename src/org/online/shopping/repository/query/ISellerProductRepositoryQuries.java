package org.online.shopping.repository.query;

public interface ISellerProductRepositoryQuries {

	String SAVE_QUERY = "INSERT INTO seller_product(user_id, product_id,quantity,added_timestamp) VALUES(?,?,?,?)";
	String SELECT_SELLER_PRODUCT_QUERY = "SELECT p.id,p.name,p.cost,p.colour,p.imageurl,p.quantity,p.discount FROM product p,seller_product sp WHERE sp.user_id=? AND p.id=sp.product_id";
	String UPDATE_QUERY = "UPDATE seller_product SET quantity=? WHERE product_id=?";
	String SELECT_SELLER_PRODUCT_ID_QUERY = "SELECT product_id FROM seller_product WHERE user_id=?";
	String SAVE_PRODUCT_TO_NOTIFY_QUERY = "INSERT INTO notify_product(user_id, product_id) VALUES(?,?)";
	String SELECT_NOTIFICATIONS = "SELECT u.email userEmail,np.id notificationId,p.name productName,u.name userName  FROM notify_product np,user u, product p WHERE p.id=? AND np.user_id=u.id";
	String DELETE_QUERY = "DELETE FROM notify_product WHERE id=?";
	String GET_SELLER_PRODUCT_QUERY = "SELECT * FROM seller_product WHERE product_id=?";
	String GET_TOTAL_PRODUCT_COUNT_QUERY = "SELECT COUNT(*) FROM online_shopping.seller_product WHERE user_id=?";

}
