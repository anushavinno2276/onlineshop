package org.online.shopping.exception;

/***
 * @exception NotFoundException
 * @author Akshay
 *
 */
public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotFoundException() {
		super();
	}

	public NotFoundException(Throwable e) {
		super(e);
	}

	public NotFoundException(String message) {
		super(message);
	}
}
