package hr.fer.zemris.java.dao;

/**
 * Class extends {@link RuntimeException} and represents specific exception
 * which is thrown to user during program running. In program,any other
 * exception <code>cannot</code> be thrown
 * 
 * @author Mihael
 *
 */
public class DAOException extends RuntimeException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */
	public DAOException() {
	}

	/**
	 * Constructor which accepts cause of exception
	 * 
	 * @param message
	 *            - message
	 * @param cause
	 *            - cause of exception
	 * @param enableSuppression
	 *            - shows is suppression is enabled
	 * @param writableStackTrace
	 *            - shows if stack trace is writable
	 */
	public DAOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Constructor accepts {@link Throwable} cause with appropriate message
	 * @param message - message
	 * @param cause - exception cause
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor accepts exception message
	 * 
	 * @param message
	 *            - exception message
	 */
	public DAOException(String message) {
		super(message);
	}

	/**
	 * Constructor accepts only throwable cause
	 * @param cause
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
}