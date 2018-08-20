package org.online.shopping.controller.util;

import javax.servlet.http.HttpSession;

import org.online.shopping.entity.User;
import org.online.shopping.entity.User.UserType;
import org.online.shopping.repository.UserRepository;

public class SessionUtility {

	private static final String LOGGED_IN_USER = "loggedInUser";

	/***
	 * Set logged in USER
	 * 
	 * @param session
	 * @param username
	 */
	public static void setLogedInUser(HttpSession session, String username) {
		session.setAttribute(LOGGED_IN_USER, username);
	}

	/***
	 * To get logged in user.
	 * 
	 * @param session
	 * @return Logged in user name
	 */
	public static String getLoggedInUser(HttpSession session) {
		return String.valueOf(session.getAttribute(LOGGED_IN_USER));
	}

	/***
	 * Type of {@link User}.
	 * 
	 * @param httpSession
	 * @return {@link UserType}
	 */
	public static UserType getUsertype(HttpSession httpSession) {
		UserRepository userRepository = UserRepository.getInstance();
		String name = SessionUtility.getLoggedInUser(httpSession);
		User user = userRepository.getByName(name);
		return user.getUserType();
	}
}
