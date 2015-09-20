package com.codeaspect.sheldon.exceptions;

/**
 * Exception thrown to denote failure of the framework.
 *
 * @author urvaksh.rogers
 *
 */
public class SheldonException extends RuntimeException {

	private static final long serialVersionUID = 3151987034455759133L;

	public SheldonException() {
	}

	public SheldonException(final String msg) {
		super(msg);
	}

	public SheldonException(final Exception e) {
		super(e);
	}

	public SheldonException(final String msg, final Exception e) {
		super(msg, e);
	}
}
