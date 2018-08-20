package org.online.shopping.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.online.shopping.entity.User;

/**
 * Servlet implementation class OrderController
 */
@WebServlet("/orders")
public class OrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RequestDispacter requestDispacter = RequestDispacter.getInstance();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OrderController() {
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
		case "openorderpage":
			requestDispacter.dispatchToOrderPage(request, response);
			break;
		default:
			break;
		}
	}

}
