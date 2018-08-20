package org.online.shopping.controller.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.online.shopping.entity.Cart;
import org.online.shopping.entity.Product;
import org.online.shopping.entity.User;
import org.online.shopping.exception.BadRequestException;

/***
 * To get the parameters from the {@link HttpServletRequest}
 * 
 * @author Akshay
 *
 */

public class RequestUtility {

	/**
	 * To get {@link User} from {@link HttpServletRequest}.
	 * 
	 * @param request
	 * @return {@link User}
	 */
	public User getUserFromRequest(HttpServletRequest request) {
		User user = new User();
		if (request.getParameter(User.ID) != null) {
			user.setId(Integer.parseInt(request.getParameter(User.ID)));
		}
		user.setName(request.getParameter(User.NAME));
		user.setEmail(request.getParameter(User.EMAIL));
		user.setPassword(request.getParameter(User.PASSWORD));
		user.setAddress(request.getParameter(User.ADDRESS));
		if (request.getParameter(User.USERTYPE).equals(String.valueOf(User.UserType.BUYER))) {
			user.setUserType(User.UserType.BUYER);
		} else if (request.getParameter(User.USERTYPE).equals(String.valueOf(User.UserType.SELLER))) {
			user.setUserType(User.UserType.SELLER);
		} else {
			throw new BadRequestException("Please Choose Customer Type");
		}
		return user;
	}

	/**
	 * To get {@link Cart} from {@link HttpServletRequest}.
	 * 
	 * @param request
	 * @return {@link Cart}
	 */
	public Cart getCartFromRequest(HttpServletRequest request) {
		Cart cart = new Cart();
		cart.setUserId((int) request.getAttribute(User.USER_ID));
		cart.setProductId(Integer.parseInt(request.getParameter(User.ID)));
		return cart;
	}

	/***
	 * To fetch <strong>QueryParameter</strong> from the
	 * {@link HttpServletRequest}
	 * 
	 * @param request
	 * @return Map of query parameter.
	 */

	public Map<String, String> getQueryParamMap(HttpServletRequest request) {
		Map<String, String> queryMap = new HashMap<>();
		String query = request.getQueryString();
		if (query != null && !query.isEmpty()) {
			String[] array = query.split("&");
			for (int i = 0; i < array.length; i++) {
				queryMap.put(array[i].split("=")[0], array[i].split("=")[1]);
			}
		}
		return queryMap;
	}

	/***
	 * To get the {@link Product} from {@link HttpServletRequest}
	 * 
	 * @param request
	 * @return
	 */
	public Product getProductFromRequest(HttpServletRequest request) {
		Product product = new Product();
		if (request.getParameter(Product.PRODUCT_ID) != null) {
			product.setId(Integer.valueOf(request.getParameter(Product.PRODUCT_ID)));
		}
		product.setName(request.getParameter(Product.NAME));
		product.setCost(Double.valueOf(request.getParameter(Product.COST)));
		product.setImageURL(request.getParameter(Product.IMAGE_URL));
		product.setQuantity(Integer.valueOf(request.getParameter(Product.QUANTITY)));
		product.setColour(request.getParameter(Product.COLOUR));
		if (request.getParameter(Product.PRODUCT_DISCOUNT) != null) {
			product.setDiscount(Integer.parseInt(request.getParameter(Product.PRODUCT_DISCOUNT)));
		}
		return product;
	}
}
