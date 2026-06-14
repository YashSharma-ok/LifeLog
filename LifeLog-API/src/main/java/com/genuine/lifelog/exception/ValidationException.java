package com.genuine.lifelog.exception;

import java.util.List;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final List<String> errorList;
	
	public ValidationException(List<String> errorList) {
		super("Validation failed");
        this.errorList = errorList;
	}
	
	public ValidationException(String message, List<String> errorList) {
		super(message);
        this.errorList = errorList;
	}

}
