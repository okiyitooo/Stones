package com.SecondPersonalProject.Stones.exceptions;

import org.springframework.security.core.AuthenticationException;

public class PersonLoginAuthenticationException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7395201854397920893L;
	public PersonLoginAuthenticationException(String msg) {
		super(msg);
	}
	public PersonLoginAuthenticationException(String msg, Throwable cause) {
		super(msg, cause);
		// TODO Auto-generated constructor stub
	}

}
