package com.SecondPersonalProject.Stones.exceptions;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonProcessingException;

public class JsonProcessingColorException extends JsonProcessingException {

	public JsonProcessingColorException(String string, JsonLocation currentLocation, NumberFormatException e) {
		// TODO Auto-generated constructor stub
		super(string, currentLocation,e);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 7727876152910409968L;

}
