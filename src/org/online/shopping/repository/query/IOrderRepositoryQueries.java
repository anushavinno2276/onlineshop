package org.online.shopping.repository.query;

import org.online.shopping.repository.OrderRepository;

/***
 * * {@link OrderRepository} Queries.
 * 
 * @author Akshay
 *
 */
public interface IOrderRepositoryQueries {

	String SAVE_TO_ORDER_QUERY = "INSERT INTO order_product(id,user_id,product_id,quantity,order_timestamp) VALUES(?,?,?,?,?)";
	String GET_ORDER_BY_ID_QUERY = "SELECT * FROM order_product WHERE id=?";
	String SELECT_ORDER_PRODUCTS_QUERY = "SELECT op.id,p.id,p.name,op.quantity,p.colour,p.cost,p.imageurl,op.order_timestamp,p.discount FROM product p,order_product op WHERE op.id=? AND p.id=op.product_id";
	String GET_TOTAL_ORDER_COUNT_QUERY = "SELECT COUNT(*) FROM order_product WHERE user_id=?";
	String GET_ORDERS_QUERY = "SELECT *  FROM order_product op, product p WHERE p.id=op.product_id AND op.user_id=? ";

}
