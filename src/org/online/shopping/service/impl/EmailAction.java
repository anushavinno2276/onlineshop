package org.online.shopping.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.online.shopping.entity.Order;
import org.online.shopping.entity.Product;
import org.online.shopping.entity.User;
import org.online.shopping.model.CartNotification;
import org.online.shopping.model.DiscountProduct;
import org.online.shopping.model.EmailPayload;
import org.online.shopping.model.EmailPayload.EmailType;
import org.online.shopping.model.Notification;
import org.online.shopping.model.OrderProduct;
import org.online.shopping.model.RecentAddedProduct;
import org.online.shopping.service.EmailService;
import org.online.shopping.util.templates.EmailTemplate;

/***
 * Email Service class
 * 
 * @author Akshay
 *
 */
public class EmailAction {

	private static final String ORDER_DATA = "ORDER_DATA";
	private static final String TOTAL_AMOUNT = "TOTAL_AMOUNT";
	private static final String PRODUCT_DATA = "PRODUCT_DATA";
	
	private EmailService emailService = EmailServiceImpl.getInstance();

	/***
	 * Send an registration email to {@link User}.
	 * 
	 * @param user
	 */
	public void sendRegistrationEmail(User user) {
		EmailPayload emailPayload = new EmailPayload();
		emailPayload.setEmailType(EmailType.REGISTRATION_EMAIL);
		Map<String, String> values = new HashMap<>();

		String htmlMessage = EmailTemplate.getRegistrationEmailTemplate();
		values.put(User.NAME, user.getName());
		values.put(User.ACTIVATION_KEY, user.getUserActivationKey());
		values.put(User.BASEURL, "http://localhost:8080/OnlineShop");

		StrSubstitutor sub = new StrSubstitutor(values, "%(", ")");
		htmlMessage = sub.replace(htmlMessage);

		emailPayload.setMessage(htmlMessage);
		emailPayload.setSubject("User Activation Email");
		emailPayload.setRecepientEmail(user.getEmail());
		emailService.sendEmail(emailPayload);
	}

	/***
	 * Send email to notified {@link User}.
	 */
	public void sendNotifications(List<Notification> notifications) {

		notifications.parallelStream().forEach(notification -> {
			EmailPayload emailPayload = new EmailPayload();
			emailPayload.setEmailType(EmailType.NOTIFICATION_EMAIL);
			Map<String, String> values = new HashMap<>();

			String htmlMessage = EmailTemplate.getNotificationEmailTemplate();
			values.put(User.NAME, notification.getUserEmail());
			values.put(Product.PRODUCT_NAME, notification.getProductName());
			values.put(User.BASEURL, "http://localhost:8080/OnlineShop");

			StrSubstitutor sub = new StrSubstitutor(values, "%(", ")");
			htmlMessage = sub.replace(htmlMessage);

			emailPayload.setMessage(htmlMessage);
			emailPayload.setSubject("Product upate Notification Email");
			emailPayload.setRecepientEmail(notification.getUserEmail());
			emailService.sendEmail(emailPayload);
		});

	}

	/***
	 * Send email Order email {@link User}
	 * 
	 * @param orderProduct
	 */
	public void sendOrderEmail(OrderProduct orderProduct) {
		EmailPayload emailPayload = new EmailPayload();
		emailPayload.setEmailType(EmailType.ORDER_EMAIL);
		Map<String, String> values = new HashMap<>();
		Collection<Product> products = orderProduct.getProducts();
		int total = 0;
		String tableBody = "";
		String tableHeader = "<table><tr><th>Product Name</th><th>Cost</th><th>Quantity</th></tr>";
		Iterator<Product> iterator = products.iterator();
		while (iterator.hasNext()) {
			Product product = iterator.next();
			tableBody += "<tr><td>" + product.getName() + "</td><td>" + product.getCost() * product.getQuantity()
					+ "</td><td>" + product.getQuantity() + "</td>" + "<td><img src=" + product.getImageURL()
					+ "></td></tr>";
			total += product.getCost() * product.getQuantity();
		}
		String htmlMessage = EmailTemplate.getOrderEmailTemplate();
		values.put(User.NAME, orderProduct.getUsername());
		values.put(Order.ORDER_ID, orderProduct.getOrderId());
		values.put(User.BASEURL, "http://localhost:8080/OnlineShop");
		values.put(ORDER_DATA, tableHeader + tableBody + "</table>");
		values.put(TOTAL_AMOUNT, total + "");

		StrSubstitutor sub = new StrSubstitutor(values, "%(", ")");

		htmlMessage = sub.replace(htmlMessage);

		emailPayload.setMessage(htmlMessage);
		emailPayload.setSubject("Order Confirmation");
		emailPayload.setRecepientEmail(orderProduct.getUserEmail());
		emailService.sendEmail(emailPayload);
	}

	/***
	 * Send cart notification email
	 * 
	 * @param cartNotifications
	 */

	public void sendCartNotificationEmail(CartNotification cartNotifications) {
		EmailPayload emailPayload = new EmailPayload();
		emailPayload.setEmailType(EmailType.NOTIFICATION_EMAIL);
		Map<String, String> values = new HashMap<>();
		String imageURL = "<img src=" + cartNotifications.getImageURL() + ">";
		String htmlMessage = EmailTemplate.getCartNotificationEmailTemplate();
		values.put(User.NAME, cartNotifications.getUserName());
		values.put(Product.PRODUCT_NAME, cartNotifications.getProductName());
		values.put(User.BASEURL, "http://localhost:8080/OnlineShop");
		values.put(CartNotification.IMAGE_URL, imageURL);

		StrSubstitutor sub = new StrSubstitutor(values, "%(", ")");
		htmlMessage = sub.replace(htmlMessage);

		emailPayload.setMessage(htmlMessage);
		emailPayload.setSubject("Cart Notification Email");
		emailPayload.setRecepientEmail(cartNotifications.getUserEmail());
		emailService.sendEmail(emailPayload);
	}

	/***
	 * Send product added email to all active {@link User}.
	 * 
	 * @param addedProducts
	 */
	public void sendProductAddedNotification(RecentAddedProduct addedProducts) {
		Collection<User> users = addedProducts.getUsers();
		for (User user : users) {
			EmailPayload emailPayload = new EmailPayload();
			emailPayload.setEmailType(EmailType.PRODUCT_ADDED_EMAIL);
			Map<String, String> values = new HashMap<>();
			Collection<Product> products = addedProducts.getProducts();
			String tableBody = "";
			String tableHeader = "<table><tr><th>Product Name</th><th>Cost</th><th>Cost</th></tr>";
			Iterator<Product> iterator = products.iterator();
			while (iterator.hasNext()) {
				Product product = iterator.next();
				tableBody += "<tr><td>" + product.getName() + "</td><td>" + "</td><td>" + product.getCost() + "</td>"
						+ "<td><img src=" + product.getImageURL() + "></td></tr>";

			}
			String htmlMessage = EmailTemplate.getProductAddedEmailTemplate();
			values.put(User.NAME, user.getName());
			values.put(User.BASEURL, "http://localhost:8080/OnlineShop");
			values.put(PRODUCT_DATA, tableHeader + tableBody + "</table>");

			StrSubstitutor sub = new StrSubstitutor(values, "%(", ")");

			htmlMessage = sub.replace(htmlMessage);

			emailPayload.setMessage(htmlMessage);
			emailPayload.setSubject("Product Notification");
			emailPayload.setRecepientEmail(user.getEmail());
			emailService.sendEmail(emailPayload);

		}

	}

	/***
	 * To send discount notification email to all active buyer.
	 * 
	 * @param discountProduct
	 */
	public void sendDiscountNotificationToUser(DiscountProduct discountProduct) {
		Collection<User> users = discountProduct.getUsers();
		for (User user : users) {
			EmailPayload emailPayload = new EmailPayload();
			emailPayload.setEmailType(EmailType.DISCOUNT_NOTIFICATION_EMAIL);
			Map<String, String> values = new HashMap<>();
			Product product = discountProduct.getProduct();
			String imageURL = "<img src=" + product.getImageURL() + ">";

			String htmlMessage = EmailTemplate.getDiscountNotificationEmailTemplate();
			values.put(User.NAME, user.getName());
			values.put(Product.PRODUCT_NAME, product.getName());
			values.put(User.BASEURL, "http://localhost:8080/OnlineShop");
			values.put(CartNotification.IMAGE_URL, imageURL);
			values.put(Product.COST, product.getCost() + "");
			values.put(Product.PRODUCT_DISCOUNT, product.getDiscount() + "");
			values.put(Product.PRODUCT_DISCOUNT_COST,
					(product.getCost()) - (((product.getCost()) * (product.getDiscount())) / 100) + "");

			StrSubstitutor sub = new StrSubstitutor(values, "%(", ")");
			htmlMessage = sub.replace(htmlMessage);

			emailPayload.setMessage(htmlMessage);
			emailPayload.setSubject("Product Discount Email");
			emailPayload.setRecepientEmail(user.getEmail());
			emailService.sendEmail(emailPayload);
		}

	}

}
