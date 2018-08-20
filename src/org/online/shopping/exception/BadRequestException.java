package org.online.shopping.exception;

/***
 * @exception BadRequestException
 * @author Akshay
 *
 */
public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BadRequestException() {
		super();
	}

	public BadRequestException(Throwable e) {
		super(e);
	}

	public BadRequestException(String message) {
		super(message);
	}
}
