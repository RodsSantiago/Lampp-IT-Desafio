package com.dscatalog.dscatalog.services.exceptions;

public class OrderWithoutProductsException extends RuntimeException {
	
private static final long serialVersionUID = 1L;
	
	public OrderWithoutProductsException(String msg) {
		super(msg);
	}

}
