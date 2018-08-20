package org.online.shopping.service.impl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.online.shopping.BaseTest;
import org.online.shopping.entity.User;
import org.online.shopping.entity.User.UserType;
import org.online.shopping.service.impl.LoginService;
import org.online.shopping.service.impl.UserService;

/***
 * Test class for {@link LoginService}
 * 
 * @author Akshay
 *
 */
public class LoginServiceTest extends BaseTest {
	private LoginService loginService = LoginService.getInstance();
	private UserService userService = UserService.getInstance();
	private static User user;

	private static final String VALID_USER_NAME = "validUserName";
	private static final String VALID_USER_PASSWORD = "validPassword";

	private static final String INVALID_USER_NAME = "inValidUserName";
	private static final String INVALID_USER_PASSWORD = "inValidPassword";

	@Before
	public void intialise() {
		user = new User();
		user.setName(VALID_USER_NAME);
		user.setPassword(VALID_USER_PASSWORD);
		user.setEmail("Online@gmasil.com");
		user.setUserType(UserType.BUYER);
	}

	/***
	 * Test validate() in {@link LoginService}. It checks whether entered user
	 * name and password is valid or not.
	 */
	@Test
	public void testValidateUsingValidCredentials() {
		userService.createUser(user);
		// If user name and password matches it return true.
		assertTrue(loginService.validate(VALID_USER_NAME, VALID_USER_PASSWORD));
	}

	/***
	 * Test validate() in {@link LoginService}. It checks whether entered user
	 * name and password is valid or not.
	 */
	@Test
	public void testValidateUsingInvalidPassword() {
		userService.createUser(user);
		// If user name and password does not match it return false.
		assertFalse(loginService.validate(VALID_USER_NAME, INVALID_USER_PASSWORD));
	}

	/***
	 * Test validate() in {@link LoginService}. It checks whether entered user
	 * name and password is valid or not.
	 */
	@Test
	public void testValidateUsingInvalidUserName() {
		userService.createUser(user);
		// If user is not present then it return false.
		assertFalse(loginService.validate(INVALID_USER_NAME, INVALID_USER_PASSWORD));
	}
}
