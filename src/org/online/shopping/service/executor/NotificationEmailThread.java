package org.online.shopping.service.executor;

import java.util.List;

import org.online.shopping.entity.User;
import org.online.shopping.model.Notification;
import org.online.shopping.service.impl.EmailAction;

/***
 * Thread to send email to notified {@link User}.
 * 
 * @author Akshay
 *
 */
public class NotificationEmailThread implements Runnable {

	private EmailAction emailAction = new EmailAction();
	private List<Notification> notifications;

	public NotificationEmailThread(List<Notification> notifications) {
		this.notifications = notifications;
	}

	@Override
	public void run() {
		emailAction.sendNotifications(notifications);
	}

}
