package org.online.shopping.service.impl;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.jws.soap.SOAPBinding.Use;

import org.online.shopping.entity.User;
import org.online.shopping.entity.User.UserType;
import org.online.shopping.model.CartNotification;
import org.online.shopping.repository.UserRepository;

/**
 * Service class for {@link User}.
 * 
 * @author Akshay
 *
 */
public class UserService {

	private UserRepository userRepository = UserRepository.getInstance();
	private static UserService userService;

	private UserService() {

	}

	public static UserService getInstance() {
		if (userService == null) {
			userService = new UserService();
		}
		return userService;
	}

	/***
	 * To create a new {@link User}
	 * 
	 * @param user
	 * @return User.
	 */
	public User createUser(User user) {
		user.setEmail(user.getEmail().toLowerCase());
		user.setCreatedTimestamp(new Timestamp(System.currentTimeMillis()));
		user.setActive(false);
		user.setUserActivationKey(UUID.randomUUID().toString());
		return userRepository.save(user);
	}

	/***
	 * To update the {@link User}
	 * 
	 * @param user
	 * @return User
	 */
	public User updateUser(User user) {
		user.setUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));
		return userRepository.update(user);
	}

	/***
	 * To get the {@link User} by <strong>Id</strong>.
	 * 
	 * @param userId
	 * @return User
	 */
	public User getUserById(int userId) {
		User user = userRepository.getById(userId);
		removeUserPassword(Collections.singletonList(user));
		return user;
	}

	/***
	 * To get the {@link User} by <strong>Name</strong>.
	 * 
	 * @param name
	 * @return User
	 */
	public User getUserByName(String name) {
		User user = userRepository.getByName(name);
		removeUserPassword(Collections.singletonList(user));
		return user;
	}

	/***
	 * To get the {@link User} by <strong>token</strong>.
	 * 
	 * @param token
	 * @return User
	 */
	public User getUserBytoken(String token) {
		return userRepository.getByToken(token);
	}

	/**
	 * 
	 * To get Active {@link User}.
	 * 
	 * @param username
	 * @return An updated {@link User} removing the password.
	 */
	public User getActiveUser(String username) {
		User user = userRepository.getActiveUser(username);
		removeUserPassword(Collections.singletonList(user));
		return user;
	}

	/***
	 * To update {@link Use} password.
	 * 
	 * @param username
	 * @param newPassword
	 */
	public void updatePassword(String username, String newPassword) {
		int id = getUserByName(username).getId();
		userRepository.updatePassword(id, newPassword);
	}

	public List<CartNotification> getCartproduct() {
		return userRepository.getCartProduct();
	}

	/***
	 * To remove {@link User} password
	 * 
	 * @param users
	 */
	private void removeUserPassword(Collection<User> users) {
		for (User user : users) {
			user.setPassword(null);
		}
	}

	/***
	 * To get all active {@link User} and {@link UserType} Buyer
	 * 
	 * @return users
	 */
	public Collection<User> getActiveBuyer() {
		return userRepository.getActiveBuyer();
	}

}
