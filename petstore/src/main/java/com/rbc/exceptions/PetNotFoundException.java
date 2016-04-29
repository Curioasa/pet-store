package com.rbc.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="The pet with the specified id was not found")
public class PetNotFoundException extends RuntimeException {

	public PetNotFoundException(String message) {
		super(message);
	}
	
}
