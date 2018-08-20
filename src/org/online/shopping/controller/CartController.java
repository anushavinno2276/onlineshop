package org.online.shopping.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.online.shopping.controller.util.RequestUtility;
import org.online.shopping.entity.Cart;
import org.online.shopping.entity.Product;
import org.online.shopping.entity.User;
import org.online.shopping.repository.CartRepository;
import org.online.shopping.repository.OrderRepository;
import org.online.shopping.repository.ProductRepository;
import org.online.shopping.repository.SellerProductRepository;
import org.online.shopping.service.impl.NotificationService;
import org.online.shopping.service.impl.UserService;
import org.online.shopping.util.AppUtil;

/**
 * Servlet implementation class {@link CartController}
 */
@WebServlet("/cart")
public class CartController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private CartRepository cartRepository = CartRepository.getInstance();
	private OrderRepository orderRepository = OrderRepository.getInstance();
	private ProductRepository productRepository = ProductRepository.getInstance();
	private SellerProductRepository sellerProductRepository = SellerProductRepository.getInstance();
	private UserService userService = UserService.getInstance();
	private NotificationService notificationService = NotificationService.getInstance();
	private RequestUtility requestUtility = new RequestUtility();
	private RequestDispacter requestDispacter = RequestDispacter.getInstance();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CartController() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		switch (action) {
		case "addtocart":
			addToCart(request, response);
			break;
		case "opencart":
			requestDispacter.dispatchToUserCartPage(request, response);
			break;
		case "updatecart":
			updateCartQuantity(request, response);
			break;
		case "pay":
			orders(request, response);
			break;
		default:
			break;
		}

	}

	/***
	 * To place an order on a click of pay
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void orders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter(User.NAME);
		Cart cart = null;
		int userId = userService.getUserByName(name).getId();
		// Get all cart product id for particular User.
		Collection<Integer> productIds = cartRepository.getCartProductIds(userId);
		Iterator<Integer> iterator = productIds.iterator();
		String orderId = AppUtil.generateUniqueId();
		// Delete the product in cart and save the in order.
		while (iterator.hasNext()) {
			int productId = iterator.next();
			cart = cartRepository.getCartByProductId(productId, userId);
			orderRepository.saveToOrder(cart, orderId);
			cartRepository.deleteCart(cart.getId());
			Product product = productRepository.getById(productId);
			product.setQuantity(product.getQuantity() - cart.getQuantity());
			productRepository.update(product);
			sellerProductRepository.updateProductQuantity(product);
			notificationService.sendOrderEmail(orderId, userId);
		}
		request.setAttribute(User.NAME, name);
		requestDispacter.dispatchToBuyerProductDetailsPage(request, response);
	}

	/**
	 * To add Products into cart.If Product already added to cart then quantity
	 * of product will increment of product is available.
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void addToCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Cart cart = new Cart();
		String name = request.getParameter(User.NAME);
		int userId = userService.getUserByName(name).getId();
		request.setAttribute(User.USER_ID, userId);
		cart = requestUtility.getCartFromRequest(request);
		int quantity = cartRepository.getQuantity(cart);
		Product product = productRepository.getById(cart.getProductId());
		if (product.getQuantity() - quantity > 0) {
			if (quantity != 0) {
				cart.setQuantity(quantity + 1);
				cartRepository.updateCart(cart);
			} else {
				cart.setQuantity(1);
				cartRepository.saveToCart(cart);
			}
		} else {
			request.setAttribute(Product.MESSAGE, "Not Available");
			request.setAttribute(Product.PRODUCT_ID, product.getId());
		}
		request.setAttribute(User.NAME, name);
		request.setAttribute(User.CART, cart);
		requestDispacter.dispatchToBuyerProductDetailsPage(request, response);
	}

	/**
	 * Update the {@link Cart} quantity on any change in quantity and delete
	 * from the {@link Cart} if quantity is equal to 0.
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void updateCartQuantity(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int quantity = Integer.parseInt(request.getParameter("values"));
		String name = request.getParameter(User.NAME);
		int userId = userService.getUserByName(name).getId();
		Collection<Integer> productIds = cartRepository.getCartProductIds(userId);
		Iterator<Integer> iterator = productIds.iterator();
		int productId = 0;

		while (iterator.hasNext()) {
			int buttonValue = iterator.next();
			if (request.getParameter(Integer.toString(buttonValue)) != null) {
				productId = buttonValue;
				break;
			}
		}
		Cart cart = cartRepository.getCartByProductId(productId, userId);
		int productQuantity = productRepository.getQuantity(cart.getProductId());
		if (quantity <= productQuantity) {
			if (quantity == 0) {
				cartRepository.deleteCart(cart.getId());
			} else {
				if (cart.getQuantity() != 0) {
					cart.setQuantity(quantity);
					cartRepository.updateCart(cart);
				} else {
					cart.setQuantity(quantity);
					cartRepository.saveToCart(cart);
				}
				request.setAttribute(User.PRODUCT_ID, productIds);
			}
		} else {
			request.setAttribute(Product.PRODUCT_ID, cart.getProductId());
			request.setAttribute(Product.MESSAGE, "Not Available");
		}
		request.setAttribute(User.NAME, request.getParameter(User.NAME));
		requestDispacter.dispatchToUserCartPage(request, response);
	}
}
