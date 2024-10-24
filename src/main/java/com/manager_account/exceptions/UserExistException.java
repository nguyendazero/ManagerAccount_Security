package com.manager_account.exceptions;

@SuppressWarnings("serial")
public class UserExistException extends RuntimeException{

	public UserExistException(String message) {
		super(message);
	}
	
}
