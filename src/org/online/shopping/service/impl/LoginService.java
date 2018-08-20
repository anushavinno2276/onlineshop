package org.online.shopping.service.impl;

import org.online.shopping.entity.User;
import org.online.shopping.repository.UserRepository;

/***
 * Service class for Login
 * 
 * @author Akshay
 *
 */
public class LoginService {

	private UserRepository userRepository = UserRepository.getInstance();
	private static LoginService loginService;

	private LoginService() {

	}

	public static LoginService getInstance() {
		if (loginService == null) {
			loginService = new LoginService();
		}
		return loginService;
	}

	/***
	 * To check whether {@link User} is valid.
	 * 
	 * @param username
	 * @param password
	 * @return is user valid or not
	 */
	public boolean validate(String username, String password) {
		return password.equals(userRepository.getByName(username).getPassword());
	}

}
