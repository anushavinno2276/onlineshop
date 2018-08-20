package org.online.shopping.service.executor;

import java.util.Collection;

import org.online.shopping.entity.Product;
import org.online.shopping.entity.User;
import org.online.shopping.model.RecentAddedProduct;
import org.online.shopping.service.impl.EmailAction;
import org.online.shopping.service.impl.ProductService;
import org.online.shopping.service.impl.UserService;

/***
 * Scheduler class runs for each 30 minutes and checks for recently added
 * products and sends notification to active {@link User}
 * 
 * @author Akshay
 *
 */
public class ProductAddedNotificationScheduler implements Runnable {

	private ProductService productService = ProductService.getInstance();
	private UserService userService = UserService.getInstance();
	private EmailAction emailAction = new EmailAction();

	@Override
	public void run() {
		Collection<Product> products = productService.getRecentlyAddedProducts();
		if (!products.isEmpty()) {
			Collection<User> users = userService.getActiveBuyer();
			if (!users.isEmpty()) {
				RecentAddedProduct addedProduct = new RecentAddedProduct();
				addedProduct.setProducts(products);
				addedProduct.setUsers(users);
				emailAction.sendProductAddedNotification(addedProduct);
				productService.deleteRecentProduct(products);
			}
		}
	}

}
