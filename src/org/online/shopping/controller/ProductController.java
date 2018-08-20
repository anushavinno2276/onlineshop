package org.online.shopping.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.online.shopping.entity.Product;
import org.online.shopping.entity.User;
import org.online.shopping.repository.ProductRepository;
import org.online.shopping.repository.SellerProductRepository;
import org.online.shopping.repository.UserRepository;

/**
 * Servlet implementation class ProductController
 */
@WebServlet("/products")
public class ProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserRepository userRepository = UserRepository.getInstance();
	private ProductRepository productRepository = ProductRepository.getInstance();
	private SellerProductRepository sellerProductRepository = SellerProductRepository.getInstance();
	private RequestDispacter requestDispacter = RequestDispacter.getInstance();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProductController() {
		super();
	}

	/**
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter(User.ACTION);
		switch (action) {
		case "displaybuyerproducts":
			requestDispacter.dispatchToBuyerProductDetailsPage(request, response);
			break;
		case "addproducttonotify":
			addProductToNotify(request, response);
			break;
		default:
			break;
		}

	}

	/***
	 * Product is set to notify
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void addProductToNotify(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int productId = Integer.parseInt(request.getParameter(User.ID));
		String username = request.getParameter(User.USERNAME);
		int userId = userRepository.getByName(username).getId();
		sellerProductRepository.saveProductToNotify(userId, productId);
		Product product = productRepository.getById(productId);
		product.setNotify(true);
		productRepository.update(product);
		requestDispacter.dispatchToBuyerProductDetailsPage(request, response);
	}

}
