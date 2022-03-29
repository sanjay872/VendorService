package com.vendor.exceptionHandler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.vendor.exception.CustomException;
import com.vendor.exception.ExceptionResponse;

@ControllerAdvice
public class CustomExceptionHandler {
	
	@ExceptionHandler
	public ResponseEntity<ExceptionResponse> exceptionHandler(CustomException ex){
		ExceptionResponse res=new ExceptionResponse();
		res.setTimeStamp(new Date());
		res.setMessage(ex.getMessage());
		res.setStatus(HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<ExceptionResponse>(res,HttpStatus.BAD_REQUEST);
	}
	
}
