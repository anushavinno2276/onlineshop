package org.online.shopping.service.executor;

import org.online.shopping.model.DiscountProduct;
import org.online.shopping.service.impl.EmailAction;

/***
 * Thread to send discount notification to active buyer.
 * 
 * @author Akshay
 *
 */
public class DiscountNotificationEmailThread implements Runnable {
	private EmailAction emailAction = new EmailAction();
	private DiscountProduct discountProduct;

	public DiscountNotificationEmailThread(DiscountProduct discountProduct) {
		this.discountProduct = discountProduct;
	}

	@Override
	public void run() {
		emailAction.sendDiscountNotificationToUser(discountProduct);
	}

}
