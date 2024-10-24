package com.manager_account.exceptions;

@SuppressWarnings("serial")
public class ErrorLoginException extends RuntimeException{

	public ErrorLoginException(String message) {
		super(message);
	}
	
}
