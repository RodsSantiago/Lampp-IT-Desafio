package com.dscatalog.dscatalog.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.dscatalog.dscatalog.services.exceptions.CompanyClosedException;
import com.dscatalog.dscatalog.services.exceptions.CompanyNotFoundException;
import com.dscatalog.dscatalog.services.exceptions.DataBaseException;
import com.dscatalog.dscatalog.services.exceptions.DeliveryTypeWrongEnumException;
import com.dscatalog.dscatalog.services.exceptions.OrderPaymentWrongEnumException;
import com.dscatalog.dscatalog.services.exceptions.OrderWithoutProductsException;
import com.dscatalog.dscatalog.services.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request) {
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(HttpStatus.NOT_FOUND.value());
		err.setError("Resource not found");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
	@ExceptionHandler(DataBaseException.class)
	public ResponseEntity<StandardError> dataBase(DataBaseException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("DataBase excpetion");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(OrderWithoutProductsException.class)
	public ResponseEntity<StandardError> orderWithoutProducts(OrderWithoutProductsException e, HttpServletRequest request) {
	    StandardError err = new StandardError();
	    err.setTimestamp(Instant.now());
	    err.setStatus(HttpStatus.BAD_REQUEST.value());
	    err.setError("Order without products");
	    err.setMessage(e.getMessage());
	    err.setPath(request.getRequestURI());
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(DeliveryTypeWrongEnumException.class)
	public ResponseEntity<StandardError> deliveryTypeWrongEnum(DeliveryTypeWrongEnumException e, HttpServletRequest request) {
	    StandardError err = new StandardError();
	    err.setTimestamp(Instant.now());
	    err.setStatus(HttpStatus.BAD_REQUEST.value());
	    err.setError("Delivery Type wrong value");
	    err.setMessage(e.getMessage());
	    err.setPath(request.getRequestURI());
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(CompanyNotFoundException.class)
	public ResponseEntity<StandardError> CompanyNotFoundException(CompanyNotFoundException e, HttpServletRequest request) {
	    StandardError err = new StandardError();
	    err.setTimestamp(Instant.now());
	    err.setStatus(HttpStatus.BAD_REQUEST.value());
	    err.setError("Company not found");
	    err.setMessage(e.getMessage());
	    err.setPath(request.getRequestURI());
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(CompanyClosedException.class)
	public ResponseEntity<StandardError> CompanyClosedException(CompanyClosedException e, HttpServletRequest request) {
	    StandardError err = new StandardError();
	    err.setTimestamp(Instant.now());
	    err.setStatus(HttpStatus.BAD_REQUEST.value());
	    err.setError("Opening Company time closed");
	    err.setMessage(e.getMessage());
	    err.setPath(request.getRequestURI());
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(OrderPaymentWrongEnumException.class)
	public ResponseEntity<StandardError> orderPaymentWrongEnum(OrderPaymentWrongEnumException e, HttpServletRequest request) {
	    StandardError err = new StandardError();
	    err.setTimestamp(Instant.now());
	    err.setStatus(HttpStatus.BAD_REQUEST.value());
	    err.setError("Order Payment wrong value");
	    err.setMessage(e.getMessage());
	    err.setPath(request.getRequestURI());
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}


}
