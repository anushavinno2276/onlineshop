package org.online.shopping.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.online.shopping.controller.util.SessionUtility;
import org.online.shopping.entity.User;
import org.online.shopping.entity.User.UserType;
import org.online.shopping.service.impl.LoginService;
import org.online.shopping.service.impl.UserService;

@WebServlet("/login")
public class LoginController extends HttpServlet {

	/**
	 * Servlet implementation class LoginController
	 */
	private static final long serialVersionUID = 1L;
	private LoginService loginService = LoginService.getInstance();
	private UserService userService = UserService.getInstance();
	private RequestDispacter requestDispacter = RequestDispacter.getInstance();

	public LoginController() {
		super();
	}

	/**
	 * 
	 * Valid user will dispatched to buyer product details.
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		switch (action) {
		case "login":
			login(request, response);
			break;
		case "logout":
			logout(request, response);
			break;
		default:
			break;
		}
	}

	/***
	 * validate user and login to application
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter(User.USERNAME);
		String password = request.getParameter(User.PASSWORD);
		// validating the user.
		if (loginService.validate(username, password)) {
			request.setAttribute("isLoginSuccess", true);
			request.setAttribute(User.USERNAME, username);
			// Checks whether the user is an activated account or not.
			User user = userService.getActiveUser(username);
			if (user.getName() != null) {
				SessionUtility.setLogedInUser(request.getSession(), username);
				request.setAttribute(User.USER, user);
				if (user.getUserType() == UserType.BUYER)
					requestDispacter.dispatchToBuyerProductDetailsPage(request, response);
				else
					requestDispacter.dispatchToSellerProductDetailsPage(request, response);
			} else {
				response.getWriter().append("Please activate your account");
			}
		} else {
			request.setAttribute("isLoginSuccess", false);
			RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
			dispatcher.include(request, response);
		}
	}

	/***
	 * To logout and redirects to login page.
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.getSession().invalidate();
		response.sendRedirect(request.getContextPath() + "/login.jsp");
	}
}
