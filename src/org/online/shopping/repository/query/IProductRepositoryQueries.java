package org.online.shopping.repository.query;

import org.online.shopping.repository.ProductRepository;

/***
 * {@link ProductRepository} Queries.
 * 
 * @author Akshay
 *
 */
public interface IProductRepositoryQueries {

	String SAVE_QUERY = "INSERT INTO product(name,cost,colour,imageurl,timestamp,quantity,notify) VALUES(?,?,?,?,?,?,?)";
	String UPDATE_QUERY = "UPDATE product SET name=? ,cost=?,colour=?,imageurl=?,quantity=?,notify=?,discount=? WHERE id=?";
	String DELETE_QUERY = "DELETE FROM product WHERE id=?";
	String SELECT_PRODUCTS_QUERY = "SELECT * FROM product";
	String SELECT_BY_ID_QUERY = "SELECT * FROM product WHERE id=?";
	String GET_QUANTITY_QUERY = "SELECT quantity FROM product WHERE id=?";
	String CHECK_PRODUCT_NOTIFY_QUERY = "SELECT notify FROM  product  WHERE id=?";
	String SAVE_PRODUCT_TO_RECENTLY_ADDED_QUERY = "INSERT INTO recently_added_product(product_id) VALUES(?)";
	String GET_RECENTLY_ADDED_PRODUCT_QUERY = "SELECT rp.id id,p.name,p.cost,p.imageurl FROM recently_added_product rp,product p WHERE rp.product_id=p.id";
	String DELETE_RECENTLY_ADDED_PRODUCT_QUERY = "DELETE FROM recently_added_product WHERE id=?";
	String GET_TOTAL_PRODUCT_COUNT_QUERY = "SELECT COUNT(*) FROM product";
}
