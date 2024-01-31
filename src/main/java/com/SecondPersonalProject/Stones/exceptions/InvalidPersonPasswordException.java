package com.SecondPersonalProject.Stones.exceptions;

import org.springframework.security.core.AuthenticationException;

public class InvalidPersonPasswordException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7395201854397920893L;
	public InvalidPersonPasswordException(String msg) {
		super(msg);
	}
	public InvalidPersonPasswordException(String msg, Throwable cause) {
		super(msg, cause);
		// TODO Auto-generated constructor stub
	}

}
