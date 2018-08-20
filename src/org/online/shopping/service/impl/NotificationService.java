package org.online.shopping.service.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.online.shopping.entity.Product;
import org.online.shopping.entity.User;
import org.online.shopping.model.DiscountProduct;
import org.online.shopping.model.Notification;
import org.online.shopping.model.OrderProduct;
import org.online.shopping.repository.OrderRepository;
import org.online.shopping.repository.SellerProductRepository;
import org.online.shopping.service.executor.TaskExecutor;

/***
 * Service class
 * 
 * @author Akshay
 *
 */
public class NotificationService {
	private SellerProductRepository sellerProductRepository = SellerProductRepository.getInstance();
	private OrderRepository orderRepository = OrderRepository.getInstance();
	private UserService userService = UserService.getInstance();

	private static NotificationService sellerProductService;

	private NotificationService() {

	}

	public static NotificationService getInstance() {
		if (sellerProductService == null) {
			sellerProductService = new NotificationService();
		}
		return sellerProductService;
	}

	/***
	 * Send notification to notified {@link User}.
	 * 
	 * @param product
	 */
	public void sendEmailToNotifiedUser(Product product) {
		List<Notification> notifications = sellerProductRepository.getNotifiedUsers(product.getId());
		if (!notifications.isEmpty()) {
			TaskExecutor.executeSendNotificationEmailTask(notifications);
			product.setNotify(false);
			// TODO: Will learn on java8 stream library.
			List<Integer> notificationIds = notifications.stream().map(notification -> notification.getNotificationId())
					.collect(Collectors.toList());
			for (Iterator<Integer> iterator = notificationIds.iterator(); iterator.hasNext();) {
				Integer notificationId = (Integer) iterator.next();
				sellerProductRepository.delete(notificationId);
			}
		}
	}

	/***
	 * To send order email.
	 * 
	 * @param orderId
	 * @param userId
	 */
	public void sendOrderEmail(String orderId, int userId) {
		User user = userService.getUserById(userId);
		Collection<Product> products = orderRepository.getProducts(orderId);
		OrderProduct orderProduct = new OrderProduct();
		orderProduct.setOrderId(orderId);
		orderProduct.setUserEmail(user.getEmail());
		orderProduct.setUsername(user.getName());
		orderProduct.setProducts(products);

		TaskExecutor.executeOrderNotificationEmailTask(orderProduct);

		// emailAction.sendOrderEmail(orderProduct);
	}

	/***
	 * To send email to user if any product has discount in cost .
	 * 
	 * @param product
	 */
	public void sendNotificationForDiscountOnProduct(Product product) {
		if (product.getDiscount() != 0) {
			Collection<User> users = userService.getActiveBuyer();
			DiscountProduct discountProduct = new DiscountProduct();
			discountProduct.setProduct(product);
			discountProduct.setUsers(users);
			TaskExecutor.executeDiscountNotificationEmailTask(discountProduct);
		}
	}

}
