package org.online.shopping.exception;

/**
 * @exception OnlineShoppingException
 * @author Akshay
 *
 */
public class OnlineShoppingException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public OnlineShoppingException() {
		super();
	}

	public OnlineShoppingException(Throwable e) {
		super(e);
	}

	public OnlineShoppingException(String message) {
		super(message);
	}
}
