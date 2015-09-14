package com.codeaspect.sheldon.exceptions;

public class ConversionException extends RuntimeException {

	private static final long serialVersionUID = 3151987034455759133L;

	public ConversionException() {
	}

	public ConversionException(String msg) {
		super(msg);
	}

	public ConversionException(Exception e) {
		super(e);
	}

	public ConversionException(String msg, Exception e) {
		super(msg, e);
	}
}
