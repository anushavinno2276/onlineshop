package org.online.shopping.service;

import org.online.shopping.model.EmailPayload;

/***
 * Email service interface
 * 
 * @author Akshay
 *
 */
public interface EmailService {

	void sendEmail(EmailPayload emailPayload);

}
