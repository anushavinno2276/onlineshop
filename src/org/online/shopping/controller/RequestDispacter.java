package org.online.shopping.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.online.shopping.controller.util.SessionUtility;
import org.online.shopping.entity.Cart;
import org.online.shopping.entity.Product;
import org.online.shopping.entity.User;
import org.online.shopping.model.OrderProducts;
import org.online.shopping.pagination.Pagination;
import org.online.shopping.pagination.PaginationFilter;
import org.online.shopping.repository.CartRepository;
import org.online.shopping.repository.OrderRepository;
import org.online.shopping.repository.ProductRepository;
import org.online.shopping.repository.SellerProductRepository;
import org.online.shopping.service.impl.UserService;

/**
 * To dispatch request to resource.
 * 
 * @author Akshay
 *
 */
public class RequestDispacter {

	private ProductRepository productRepository = ProductRepository.getInstance();
	private SellerProductRepository sellerProductRepository = SellerProductRepository.getInstance();
	private CartRepository cartRepository = CartRepository.getInstance();
	private OrderRepository orderRepository = OrderRepository.getInstance();
	private UserService userService = UserService.getInstance();
	private static RequestDispacter requestDispacter;

	private RequestDispacter() {

	}

	/**
	 * It makes the class singleton.
	 * 
	 * @return Instance of {@link RequestDispacter}
	 */
	public static RequestDispacter getInstance() {
		if (requestDispacter == null) {
			requestDispacter = new RequestDispacter();
		}
		return requestDispacter;
	}

	/**
	 * To display the buyer product details.
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void dispatchToBuyerProductDetailsPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Pagination pagination = PaginationFilter.pagination;
		if (pagination != null) {
			Collection<Product> products = productRepository.getAllProduct();
			int totalOrder = productRepository.getTotalProductCount();
			String name = request.getParameter(User.USERNAME);
			if (request.getParameter(User.NAME) != null) {
				name = request.getParameter(User.NAME);
			}
			int userId = userService.getUserByName(name).getId();
			int cartTotal = cartRepository.getTotalQuantity(userId);
			request.setAttribute(User.NAME, name);
			request.setAttribute(User.PRODUCTS, products);
			if (request.getAttribute(User.CART) != null) {
				Cart cart = request.getAttribute(User.CART) != null ? (Cart) request.getAttribute(User.CART) : null;
				request.setAttribute(User.QUANTITY, cart.getQuantity());
			}
			request.setAttribute(User.CART_QUANTITY, cartTotal);
			request.setAttribute("pagination", pagination);
			request.setAttribute("totalorder", totalOrder);
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("BuyerWelcome.jsp");
		dispatcher.forward(request, response);
	}

	/***
	 * To display the seller product details.
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void dispatchToSellerProductDetailsPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Pagination pagination = PaginationFilter.pagination;
		if (pagination != null) {
			String username = SessionUtility.getLoggedInUser(request.getSession(false));
			request.setAttribute(User.USERNAME, username);
			int userId = userService.getUserByName(username).getId();
			if (!sellerProductRepository.getAllSellerProduct(userId).isEmpty()) {
				Collection<Product> products = sellerProductRepository.getAllSellerProduct(userId);
				request.setAttribute(User.PRODUCTS, products);
				int totalOrder = sellerProductRepository.getCountOfSellerProduct(userId);
				request.setAttribute("pagination", pagination);
				request.setAttribute("totalorder", totalOrder);
			} else {
				request.setAttribute("message", "No Products Added");
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher("SellerWelcome.jsp");
			dispatcher.forward(request, response);
		}
	}

	/**
	 * To display user cart page.
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void dispatchToUserCartPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter(User.NAME);
		User user = userService.getUserByName(name);
		Collection<Product> products = cartRepository.getCartProducts(user.getId());
		request.setAttribute(User.PRODUCTS, products);
		request.setAttribute(User.NAME, name);
		RequestDispatcher dispatcher = request.getRequestDispatcher("Cart.jsp");
		dispatcher.include(request, response);
	}

	/***
	 * To display order page.
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */

	public void dispatchToOrderPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter(User.USERNAME);
		int userId = userService.getUserByName(name).getId();
		Pagination pagination = PaginationFilter.pagination;
		if (pagination != null) {
			Collection<OrderProducts> orderProducts = orderRepository.searchOrder(userId, pagination);
			int totalOrder = orderRepository.getTotalOrderCount(userId);
			request.setAttribute(OrderProducts.ORDER_PRODUCTS, orderProducts);
			request.setAttribute("pagination", pagination);
			request.setAttribute("totalorder", totalOrder);
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("Order.jsp");
		dispatcher.include(request, response);
	}

	/***
	 * To display product update page.
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void dispatchToProductUpdatePage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = SessionUtility.getLoggedInUser(request.getSession(false));
		request.setAttribute(User.USERNAME, username);
		int userId = userService.getUserByName(username).getId();
		Collection<Integer> productIds = sellerProductRepository.getProductIds(userId);
		Iterator<Integer> iterator = productIds.iterator();
		int productId = 0;
		while (iterator.hasNext()) {
			int buttonValue = iterator.next();
			if (request.getParameter(Integer.toString(buttonValue)) != null) {
				productId = buttonValue;
				break;
			}
		}
		if (request.getParameter(User.ID) != null) {
			productId = Integer.parseInt(request.getParameter(User.ID));
		}
		Product product = productRepository.getById(productId);
		request.setAttribute(Product.PRODUCT, product);
		RequestDispatcher dispatcher = request.getRequestDispatcher("ProductUpdate.jsp");
		dispatcher.forward(request, response);
	}

	/***
	 * To display product page
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void dispatchToProductPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("Product.jsp");
		dispatcher.forward(request, response);
	}
}