package org.online.shopping.service.executor;

import org.online.shopping.entity.Order;
import org.online.shopping.entity.User;
import org.online.shopping.model.OrderProduct;
import org.online.shopping.service.impl.EmailAction;

/***
 * Thread to send email to {@link User} on successful {@link Order}.
 * 
 * @author Akshay
 *
 */
public class OrderEmailThread implements Runnable {

	private EmailAction emailAction = new EmailAction();
	private OrderProduct orderProduct;

	public OrderEmailThread(OrderProduct orderProduct) {
		this.orderProduct = orderProduct;
	}

	@Override
	public void run() {
		emailAction.sendOrderEmail(orderProduct);
	}

}
