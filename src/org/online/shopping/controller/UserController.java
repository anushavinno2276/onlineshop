
package org.online.shopping.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.online.shopping.controller.util.RequestUtility;
import org.online.shopping.entity.User;
import org.online.shopping.exception.BadRequestException;
import org.online.shopping.exception.NotFoundException;
import org.online.shopping.repository.UserRepository;
import org.online.shopping.service.executor.TaskExecutor;
import org.online.shopping.service.impl.LoginService;
import org.online.shopping.service.impl.UserService;
import org.online.shopping.util.AppUtil;

/**
 * Servlet implementation class UserController
 */
@WebServlet("/user")
public class UserController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private UserRepository userRepository = UserRepository.getInstance();
	private UserService userService = UserService.getInstance();
	private LoginService loginService = LoginService.getInstance();
	private RequestUtility requestUtility = new RequestUtility();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserController() {
		super();
	}

	/**
	 * To get the {@link User}.
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User user = null;
		Map<String, String> queryParameter = requestUtility.getQueryParamMap(request);
		String action = queryParameter.get(User.ACTION);
		switch (action) {
		// To get the User by Id.
		case "id":
			user = userService.getUserById(Integer.parseInt(queryParameter.get(User.ID)));
			break;
		// To get the User by name.
		case "name":
			user = userService.getUserByName(queryParameter.get(User.NAME));
			break;
		// To delete particular record.
		case "delete":
			doDelete(request, response);
		default:
			// TODO: Handle exceptions in filter.
			throw new NotFoundException("Invalid Action");
		}
		response.getWriter().append("userdetails :" + "  ID:" + user.getId() + " Name: " + user.getName());
	}

	/**
	 * To register {@link User},verify the {@link User} ,{@link User} Profile
	 * Update.
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, String> queryParameter = requestUtility.getQueryParamMap(request);
		String action = queryParameter.get(User.ACTION);
		switch (action) {
		case "register":
			register(request, response);
			break;
		case "verify":
			verify(request, response);
			break;
		case "updatepassword":
			updatepassword(request, response);
			break;
		default:
			break;
		}
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = requestUtility.getUserFromRequest(request);
		userService.updateUser(user);
	}

	/**
	 * 
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, String> queryParameter = requestUtility.getQueryParamMap(request);
		int id = Integer.parseInt(queryParameter.get(User.ID));
		userRepository.delete(id);
	}

	/***
	 * Register the user by validating each field.
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void register(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Checks whether the Email is already registered.
		if (userRepository.getByEmail(request.getParameter(User.EMAIL)).getEmail() == null) {
			request.setAttribute("isRegistrationSuccess", true);
			try {
				User user = requestUtility.getUserFromRequest(request);
				// validates the each field.
				validate(user);
				user = userService.createUser(user);
				TaskExecutor.executeSendRegisterEmailTask(user);
				request.setAttribute("isActive", false);
				RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
				dispatcher.forward(request, response);
			} catch (BadRequestException exception) {
				request.setAttribute("Badrequest", exception);
				request.setAttribute("messege", exception.getMessage());
				RequestDispatcher dispatcher = request.getRequestDispatcher("Register.jsp");
				dispatcher.include(request, response);
			}
		} else {
			request.setAttribute("isRegistrationSuccess", false);
			RequestDispatcher dispatcher = request.getRequestDispatcher("Register.jsp");
			dispatcher.include(request, response);
		}
	}

	/**
	 * 
	 * To verify User is active.
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void verify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String token = request.getParameter("token");
		User user = userService.getUserBytoken(token);
		if (user.getUserActivationKey() != null) {
			user.setActive(true);
			user.setUserActivationKey(null);
			userService.updateUser(user);
			request.setAttribute("isActive", true);
			response.getWriter().println("Sucessfully activated");
		} else {
			request.setAttribute("isActive", false);
			response.getWriter().println("Already activated");
		}
	}

	/***
	 * Validate User .
	 * 
	 * @param user
	 * @throw {@link BadRequestException}
	 */
	private void validate(User user) {
		if (!AppUtil.validateName(user.getName())) {
			throw new BadRequestException("User name is mandatory");
		}
		if (!AppUtil.validateEmail(user.getEmail())) {
			throw new BadRequestException("Enter Proper EmailId");
		}
		if (!AppUtil.validatePassword(user.getPassword())) {
			throw new BadRequestException("Password must contain 8 characters");
		}
	}

	/***
	 * On request to change password updates the password.
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void updatepassword(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String password = request.getParameter(User.OLD_PASSWORD);
		String username = request.getParameter(User.NAME);
		String newPassword = request.getParameter(User.NEW_PASSWORD);
		String confirmPassword = request.getParameter(User.CONFIRM_PASSWORD);
		// checks whether old password entered is valid to that User and
		// new password and old password is same and validates the password.
		if (loginService.validate(username, password) && newPassword.equals(confirmPassword)
				&& AppUtil.validatePassword(newPassword)) {
			userService.updatePassword(username, newPassword);
			request.setAttribute(User.USERNAME, username);
			RequestDispatcher dispatcher = request.getRequestDispatcher("Account.jsp");
			dispatcher.forward(request, response);
		} else {
			request.setAttribute("message", "Please enter valid password");
			RequestDispatcher dispatcher = request.getRequestDispatcher("Password.jsp");
			dispatcher.include(request, response);

		}
	}
}
