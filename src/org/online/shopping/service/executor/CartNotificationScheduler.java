package org.online.shopping.service.executor;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import org.online.shopping.entity.User;
import org.online.shopping.model.CartNotification;
import org.online.shopping.repository.CartRepository;
import org.online.shopping.service.impl.EmailAction;
import org.online.shopping.service.impl.UserService;
import org.online.shopping.util.AppUtil;

/***
 * Scheduler class runs for each hours and checks whether any product is been
 * there in cart for more than 3 hours sends notification to {@link User}
 * 
 * @author Akshay
 *
 */
public class CartNotificationScheduler implements Runnable {

	private EmailAction emailAction = new EmailAction();
	private UserService userService = UserService.getInstance();
	private CartRepository cartRepository = CartRepository.getInstance();

	@Override
	public void run() {
		List<CartNotification> cartNotifications = userService.getCartproduct();
		Iterator<CartNotification> iterator = cartNotifications.iterator();
		while (iterator.hasNext()) {
			CartNotification cartNotification = iterator.next();
			if (cartNotification.getNotifiedTime() == null) {
				cartRepository.updatedNotifiedtime(cartNotification.getCartId(), cartNotification.getAddedTimestamp());
			} else if (AppUtil.differenceGreaterThan(3, cartNotification.getNotifiedTime().getTime())) {
				cartRepository.updatedNotifiedtime(cartNotification.getCartId(),
						new Timestamp(System.currentTimeMillis()));
				emailAction.sendCartNotificationEmail(cartNotification);
			}
		}
	}
}
