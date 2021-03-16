package com.bk.progresstracker;

/**
 * Marker class for error caused as a result of bad client/user input.
 * 
 * @author bkorando
 *
 */
public class ClientException extends RuntimeException {

	public ClientException() {
	}

	public ClientException(String message) {
		super(message);
	}

	public ClientException(Throwable cause) {
		super(cause);
	}

	public ClientException(String message, Throwable cause) {
		super(message, cause);
	}

	public ClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
