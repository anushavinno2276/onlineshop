package org.online.shopping.util.templates;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/***
 * Email templates .
 * 
 * @author Akshay
 *
 */
public class EmailTemplate {
	private static EmailTemplate emailTemplate;

	private static String REGISTRATION_EMAIL_TEMPLATE;
	private static String NOTIFICATION_EMAIL_TEMPLATE;
	private static String ORDER_EMAIL_TEMPLATE;
	private static String CART_NOTIFICATION_TEMPLATE;
	private static String PRODUCT_ADDED_NOTIFICATION_TEMPLATE;
	private static String PRODUCT_DISCOUNT_NOTIFICATION_TEMPLATE;

	static {
		// REGISTRATION_EMAIL_TEMPLATE = readRegistrationEmailTemplate();
	}

	public static EmailTemplate getInstance() {
		if (emailTemplate == null)
			emailTemplate = new EmailTemplate();
		return emailTemplate;
	}

	/***
	 * To get REGISTRATION_EMAIL_TEMPLATE.
	 * 
	 * @return REGISTRATION_EMAIL_TEMPLATE
	 */
	public static String getRegistrationEmailTemplate() {
		REGISTRATION_EMAIL_TEMPLATE = readRegistrationEmailTemplate();
		return REGISTRATION_EMAIL_TEMPLATE;
	}

	/***
	 * To get NOTIFICATION_EMAIL_TEMPLATE.
	 * 
	 * @return NOTIFICATION_EMAIL_TEMPLATE
	 */
	public static String getNotificationEmailTemplate() {
		NOTIFICATION_EMAIL_TEMPLATE = readNotificationEmailTemplate();
		return NOTIFICATION_EMAIL_TEMPLATE;
	}

	/***
	 * To get ORDER_EMAIL_TEMPLATE
	 * 
	 * @return ORDER_EMAIL_TEMPLATE
	 */
	public static String getOrderEmailTemplate() {
		ORDER_EMAIL_TEMPLATE = readOrderEmailTemplate();
		return ORDER_EMAIL_TEMPLATE;
	}

	/***
	 * To get CART_NOTIFICATION_TEMPLATE
	 * 
	 * @return CART_NOTIFICATION_TEMPLATE
	 */
	public static String getCartNotificationEmailTemplate() {
		CART_NOTIFICATION_TEMPLATE = readCartNotificationEmailTemplate();
		return CART_NOTIFICATION_TEMPLATE;
	}

	/***
	 * To get PRODUCT_ADDED_NOTIFICATION_TEMPLATE
	 * 
	 * @return PRODUCT_ADDED_NOTIFICATION_TEMPLATE
	 */
	public static String getProductAddedEmailTemplate() {
		PRODUCT_ADDED_NOTIFICATION_TEMPLATE = readProductAddedEmailTemplate();
		return PRODUCT_ADDED_NOTIFICATION_TEMPLATE;
	}

	/***
	 * To get PRODUCT_DISCOUNT_NOTIFICATION_TEMPLATE
	 * 
	 * @return PRODUCT_DISCOUNT_NOTIFICATION_TEMPLATE
	 */
	public static String getDiscountNotificationEmailTemplate() {
		PRODUCT_DISCOUNT_NOTIFICATION_TEMPLATE = readProductDiscountEmailTemplate();
		return PRODUCT_DISCOUNT_NOTIFICATION_TEMPLATE;
	}

	private static String readProductDiscountEmailTemplate() {
		String html = getInstance().getFileContent("product_discount_notification_email_template.html");
		return html;
	}

	private static String readProductAddedEmailTemplate() {
		String html = getInstance().getFileContent("product_added_notification_email_template.html");
		return html;
	}

	private static String readCartNotificationEmailTemplate() {
		String html = getInstance().getFileContent("Cart_notification_email_template.html");
		return html;
	}

	private static String readOrderEmailTemplate() {
		String html = getInstance().getFileContent("Order_email_template.html");
		return html;
	}

	private static String readNotificationEmailTemplate() {
		String html = getInstance().getFileContent("Notification_email_template.html");
		return html;
	}

	/***
	 * To read template.
	 * 
	 * @return template Content.
	 */
	private static String readRegistrationEmailTemplate() {
		String html = getInstance().getFileContent("user_activation_email_template.html");
		return html;
	}

	/***
	 * To get the file content .
	 * 
	 * @param fileName
	 * @return file Content
	 */
	public String getFileContent(String fileName) {
		String path = null;
		try {
			path = getPath();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		StringBuilder builder = new StringBuilder();
		String temp = path.concat(fileName);
		try (BufferedReader br = new BufferedReader(new FileReader(temp))) {
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				builder.append(sCurrentLine).append('\n');
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}

	// TODO : Will work on this later
	public String getPath() throws UnsupportedEncodingException {
		String path = this.getClass().getClassLoader().getResource("").getPath();
		String fullPath = URLDecoder.decode(path, "UTF-8");
		String pathArr[] = fullPath.split("/WEB-INF");
		fullPath = pathArr[0];
		String reponsePath = new File(fullPath).getPath() + File.separatorChar + "/email_templates/";
		return reponsePath;
	}

}
