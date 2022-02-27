package com.example.fileserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorMessage> handleUpperLimitException(Exception exception) {

		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), "Please try again");

		return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.TOO_MANY_REQUESTS);
	}

}
