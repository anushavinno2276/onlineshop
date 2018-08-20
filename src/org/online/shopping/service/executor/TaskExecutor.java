package org.online.shopping.service.executor;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.online.shopping.entity.User;
import org.online.shopping.model.DiscountProduct;
import org.online.shopping.model.Notification;
import org.online.shopping.model.OrderProduct;

/***
 * Executor class
 * 
 * @author Akshay
 *
 */
public class TaskExecutor {

	private static final ExecutorService executor = Executors.newFixedThreadPool(10);
	private static ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(2);

	private TaskExecutor() {

	}

	/**
	 * Execute the {@link RegisterEmailThread}.
	 * 
	 * @param user
	 */
	public static void executeSendRegisterEmailTask(User user) {
		Runnable worker = new RegisterEmailThread(user);
		executor.execute(worker);
	}

	/**
	 * Execute the {@link NotificationEmailThread}.
	 */
	public static void executeSendNotificationEmailTask(List<Notification> notifications) {
		Runnable worker = new NotificationEmailThread(notifications);
		executor.execute(worker);
	}

	/***
	 * Execute the {@link OrderEmailThread}
	 * 
	 * @param orderProduct
	 */
	public static void executeOrderNotificationEmailTask(OrderProduct orderProduct) {
		Runnable worker = new OrderEmailThread(orderProduct);
		executor.execute(worker);
	}

	/***
	 * Execute the {@link DiscountNotificationEmailThread}.
	 * 
	 * @param discountProduct
	 */
	public static void executeDiscountNotificationEmailTask(DiscountProduct discountProduct) {
		Runnable worker = new DiscountNotificationEmailThread(discountProduct);
		executor.execute(worker);
	}

	/***
	 * To shutdown the {@link TaskExecutor}
	 */
	public static void shutddownExecutor() {
		executor.shutdown();
		scheduledThreadPoolExecutor.shutdown();
	}

	/***
	 * Schedule {@link CartNotificationScheduler} for each 1 hour.
	 */
	public static void cartScheduler() {
		Runnable runnable = new CartNotificationScheduler();
		scheduledThreadPoolExecutor.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.HOURS);
	}

	/***
	 * Schedule {@link ProductAddedNotificationScheduler} for each 30 minutes
	 */
	public static void productScheduler() {
		Runnable runnable = new ProductAddedNotificationScheduler();
		scheduledThreadPoolExecutor.scheduleAtFixedRate(runnable, 0, 30, TimeUnit.MINUTES);
	}

}
