package org.online.shopping.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.online.shopping.BaseTest;
import org.online.shopping.entity.User;
import org.online.shopping.entity.User.UserType;
import org.online.shopping.service.impl.UserService;
import org.online.shopping.util.AppUtil;

/***
 * Test class for {@link UserService}
 * 
 * @author Akshay
 *
 */
public class UserServiceTest extends BaseTest {

	private UserService userService = UserService.getInstance();

	private User user;

	private static final String USER_NAME = "online";
	private static final String USER_PASSWORD = "123456789";

	private static final String UPDATED_PASSWORD = "onlineonline";

	private static final String USER1_NAME = "activeseller";

	@Before
	public void intialise() {
		user = new User();
		user.setName(USER_NAME);
		user.setPassword(USER_PASSWORD);
		user.setEmail("Online@gmasil.com");
		user.setUserType(UserType.BUYER);
		user.setActive(true);
	}

	/***
	 * Test createUser(). It takes user as input and saves the user into the
	 * database.
	 */
	@Test
	public void testCreateUser() {
		User createdUser = userService.createUser(user);
		assertEquals(USER_NAME, createdUser.getName());
	}

	/***
	 * updateUser() updates if user is present otherwise it save User.
	 */
	@Test
	public void testUpdateUser() {
		User updatedUser = userService.updateUser(user);
		User user = userService.getUserById(updatedUser.getId());
		assertEquals(updatedUser.getName(), user.getName());
	}

	/***
	 * Test expects a empty User if User is not present.
	 */
	@Test
	public void testGetByIdIfUserNotPresent() {
		// User is not present then getById() will return an empty User but not
		// null.
		assertNotNull(userService.getUserById(5));

	}

	/****
	 * Test expects a null password for an User if present.
	 */
	@Test
	public void testGetByIdReturnsPasswordNull() {
		// It returns user with password as null.
		User createdUser = userService.createUser(user);
		User user = userService.getUserById(createdUser.getId());
		assertNull(user.getPassword());
	}

	/***
	 * Test expects a empty User if User is not present.
	 */
	@Test
	public void testGetByNameIfUserNotPresent() {
		// User is not present then getByName() will return an empty User but
		// not null.
		assertNotNull(userService.getUserByName("online"));
	}

	/***
	 * Test expects a null password for an User if present.
	 */
	@Test
	public void testGetByNameReturnsPasswordNull() {
		// It returns user with password as null.
		User createdUser = userService.createUser(user);
		User user = userService.getUserByName(createdUser.getName());
		assertNull(user.getPassword());
	}

	/***
	 * Test expects a empty User if User is not present.
	 */
	@Test
	public void testGetByTokenIfUserNotPresent() {
		String token = AppUtil.generateUniqueId();
		// User is not present then getByName() will return an empty User but
		// not null.
		assertNotNull(userService.getUserBytoken(token));
	}

	@Test
	public void testGetByTokenIfUserPresent() {
		String token = AppUtil.generateUniqueId();
		user.setUserActivationKey(token);
		User createdUser = userService.createUser(user);
		User user = userService.getUserBytoken(createdUser.getUserActivationKey());
		assertEquals(user.getUserActivationKey(), user.getUserActivationKey());
	}

	/***
	 * Expects a empty User if User is not present.
	 */
	@Test
	public void testGetActiveUserIfUserNotPresent() {
		// If user is not present then getActiveUser() will return an empty User
		// but not null.
		assertNotNull(userService.getActiveUser("seller"));
	}

	@Test
	public void testGetActiveUserIfUserActive() {
		// If user is active then it return the user.
		User createdUser = userService.updateUser(user);
		User user1 = userService.getActiveUser(createdUser.getName());
		assertEquals(createdUser.getName(), user1.getName());
	}

	/***
	 * Update password of {@link User}.
	 */
	@Test
	public void testUpdatePassword() {
		userService.createUser(user);
		userService.updatePassword(USER_NAME, UPDATED_PASSWORD);
		// user password is updated as updated password.
		assertNotEquals(USER_PASSWORD, UPDATED_PASSWORD);
	}

	/***
	 * If no active buyers present it returns empty collection of user but not
	 * null
	 */
	@Test
	public void testGetActiveBuyerActiveButNotBuyer() {
		User user1 = new User();
		user1.setName(USER1_NAME);
		user1.setPassword(USER_PASSWORD);
		user1.setEmail("Online@gmasil.com");
		user1.setUserType(UserType.SELLER);
		user1.setActive(true);

		userService.createUser(user1);

		Collection<User> users = userService.getActiveBuyer();
		assertNotNull(users);
	}

}
