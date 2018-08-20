package org.online.shopping.service.impl;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.online.shopping.model.EmailPayload;
import org.online.shopping.service.EmailService;
import org.online.shopping.util.EmailUtil;

/***
 * Email service Implementation class .
 * 
 * @author Akshay
 *
 */
public class EmailServiceImpl implements EmailService {

	private static EmailServiceImpl emailServiceImpl;
	private EmailUtil emailUtil = EmailUtil.getInstance();

	private EmailServiceImpl() {
	}

	public static EmailServiceImpl getInstance() {
		if (emailServiceImpl == null) {
			emailServiceImpl = new EmailServiceImpl();
		}
		return emailServiceImpl;
	}

	@Override
	/***
	 * To send an email to User.
	 * 
	 * @param emailPayload
	 */
	public void sendEmail(EmailPayload emailPayload) {
		Session session = Session.getInstance(emailUtil.getProperties(), new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(emailUtil.getFrom(), emailUtil.getPassword());
			}
		});
		try {
			MimeMessage message = new MimeMessage(session);
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailPayload.getRecepientEmail()));
			message.setSubject(emailPayload.getSubject());
			message.setContent(emailPayload.getMessage(), "text/html; charset=utf-8");
			Transport.send(message);
			System.out.println("message sent successfully");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}
}
