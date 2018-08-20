package org.online.shopping.util;

import java.util.Properties;

/***
 * Email Utility
 * 
 * @author Akshay
 *
 */
public class EmailUtil {

	private static EmailUtil emailUtil;
	private Properties properties = new Properties();

	private EmailUtil() {

	}

	public static EmailUtil getInstance() {
		if (emailUtil == null) {
			emailUtil = new EmailUtil();
		}
		return emailUtil;
	}

	private String from = "onlineshoppingdummy1@gmail.com";
	private String password = "onlinedummy1";

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Properties getProperties() {
		setProperties(properties);
		return properties;
	}

	public void setProperties(Properties properties) {
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", "465");
		this.properties = properties;
	}

}
