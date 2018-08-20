package org.online.shopping.service.executor;

import org.online.shopping.entity.User;
import org.online.shopping.service.impl.EmailAction;

/***
 * Thread to send activation mail after successful registration.
 * 
 * @author Akshay
 *
 */
public class RegisterEmailThread implements Runnable {

	private EmailAction emailAction = new EmailAction();
	private User user;

	public RegisterEmailThread(User user) {
		this.user = user;
	}

	@Override
	public void run() {
		emailAction.sendRegistrationEmail(user);
	}

}
