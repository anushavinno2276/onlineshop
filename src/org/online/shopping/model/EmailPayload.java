package org.online.shopping.model;

/**
 * <strong>EmailPayload</strong>
 * 
 * @author Akshay
 *
 */
public class EmailPayload {

	private String recepientEmail;
	private String subject;
	private String message;
	private EmailType emailType;

	public enum EmailType {
		REGISTRATION_EMAIL, FORGOT_PASSWORD, RESET_PASSWORD, WELCOME_EMAIL, NOTIFICATION_EMAIL, ORDER_EMAIL, PRODUCT_ADDED_EMAIL, DISCOUNT_NOTIFICATION_EMAIL;
	}

	public String getRecepientEmail() {
		return recepientEmail;
	}

	public void setRecepientEmail(String recepientEmail) {
		this.recepientEmail = recepientEmail;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public EmailType getEmailType() {
		return emailType;
	}

	public void setEmailType(EmailType emailType) {
		this.emailType = emailType;
	}

}
