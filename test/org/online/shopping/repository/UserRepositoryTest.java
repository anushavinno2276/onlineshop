package org.online.shopping.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.online.shopping.BaseTest;
import org.online.shopping.entity.User;
import org.online.shopping.entity.User.UserType;

public class UserRepositoryTest extends BaseTest {

	private UserRepository userRepository = UserRepository.getInstance();

	private static final String USER_1 = "online";
	private static final String USER_2 = "newuser";

	private static User user1;

	@Before
	public void initiate() {
		user1 = new User();
		user1.setUserType(UserType.BUYER);
		user1.setActive(true);
		user1.setName(USER_1);
		user1.setUserActivationKey(UUID.randomUUID().toString());
		user1.setEmail("online@mail.com");
		user1.setCartQuantity(5);
		user1.setPassword("online12345");
	}

	/***
	 * To save {@link User}
	 */
	@Test
	public void testSave() {
		User savedUser = userRepository.save(user1);
		assertEquals(user1.getName(), savedUser.getName());
	}

	/***
	 * To update {@link User} if present in database otherwise have to save
	 * {@link User}
	 */
	@Test
	public void testUpdateUser() {
		User user2 = new User();
		user2.setName(USER_2);
		user2.setActive(false);
		user2.setUserType(UserType.BUYER);
		User updatedUser = userRepository.update(user2);
		assertEquals(USER_2, updatedUser.getName());
	}

	/***
	 * Get the User by id.
	 */
	@Test
	public void testGetById() {
		userRepository.save(user1);
		User user = userRepository.getById(user1.getId());
		assertEquals(USER_1, user.getName());
	}

	/***
	 * Get the User by name.
	 */
	@Test
	public void testGetByName() {
		userRepository.save(user1);
		User user = userRepository.getByName(USER_1);
		assertEquals(USER_1, user.getName());
	}

	/***
	 * Get the User by email.
	 */
	@Test
	public void testGetByEmail() {
		userRepository.save(user1);
		User user = userRepository.getByEmail(user1.getEmail());
		assertEquals(USER_1, user.getName());
	}

	/***
	 * getScativeUser() return that User if User Activated account.
	 */
	@Test
	public void testGetActiveUser() {
		User user2 = new User();
		user2.setName(USER_2);
		user2.setActive(false);
		user2.setUserType(UserType.BUYER);

		userRepository.save(user1);
		userRepository.save(user2);
		User user1 = userRepository.getActiveUser(USER_1);
		User user = userRepository.getActiveUser(USER_2);
		assertEquals(USER_1, user1.getName());
		assertNotEquals(USER_2, user.getName());
	}

	/***
	 * Get User based on the activation key.
	 */
	@Test
	public void testGetByToken() {
		userRepository.save(user1);
		User user = userRepository.getByToken(user1.getUserActivationKey());
		assertEquals(USER_1, user.getName());
	}

	/***
	 * Update password of {@link User}.
	 */
	@Test
	public void testUpdatepassword() {
		User saveUser = userRepository.save(user1);
		userRepository.updatePassword(user1.getId(), "onlineonline");
		User user = userRepository.getById(user1.getId());
		assertNotEquals(saveUser.getPassword(), user.getPassword());
	}

}
