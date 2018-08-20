package org.online.shopping.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.online.shopping.controller.util.RequestUtility;
import org.online.shopping.controller.util.SessionUtility;
import org.online.shopping.entity.Product;
import org.online.shopping.entity.User;
import org.online.shopping.repository.ProductRepository;
import org.online.shopping.repository.SellerProductRepository;
import org.online.shopping.repository.UserRepository;
import org.online.shopping.service.impl.NotificationService;

/**
 * Servlet implementation class SellerProductController
 */
@WebServlet("/seller")
public class SellerProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserRepository userRepository = UserRepository.getInstance();
	private ProductRepository productRepository = ProductRepository.getInstance();
	private SellerProductRepository sellerProductRepository = SellerProductRepository.getInstance();
	private NotificationService sellerProductService = NotificationService.getInstance();
	private RequestUtility requestUtility = new RequestUtility();
	private RequestDispacter requestDispacter = RequestDispacter.getInstance();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SellerProductController() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter(User.ACTION);
		switch (action) {
		case "addproduct":
			addProductToSell(request, response);
			break;
		case "displaysellerproducts":
			requestDispacter.dispatchToSellerProductDetailsPage(request, response);
			break;
		case "productupdatepage":
			requestDispacter.dispatchToProductUpdatePage(request, response);
			break;
		case "updatesellerproduct":
			updateSellerProduct(request, response);
			break;
		case "displayproductpage":
			requestDispacter.dispatchToProductPage(request, response);
			break;
		default:
			break;
		}
	}

	/***
	 * Update seller product details .
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void updateSellerProduct(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = SessionUtility.getLoggedInUser(request.getSession(false));
		request.setAttribute(User.USERNAME, username);
		Product product = requestUtility.getProductFromRequest(request);
		productRepository.update(product);
		sellerProductRepository.updateProductQuantity(product);
		sellerProductService.sendEmailToNotifiedUser(product);
		sellerProductService.sendNotificationForDiscountOnProduct(product);
		requestDispacter.dispatchToSellerProductDetailsPage(request, response);
	}

	/***
	 * Add products to sell by seller.
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void addProductToSell(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = SessionUtility.getLoggedInUser(request.getSession(false));
		Product product = requestUtility.getProductFromRequest(request);
		int userId = userRepository.getByName(username).getId();
		productRepository.save(product);
		productRepository.saveProductToRecentlyAdded(product.getId());
		sellerProductRepository.saveProduct(userId, product);
		requestDispacter.dispatchToSellerProductDetailsPage(request, response);
	}

}
