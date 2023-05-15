package com.dscatalog.dscatalog.services.exceptions;

public class OrderPaymentWrongEnumException extends RuntimeException {
	
private static final long serialVersionUID = 1L;
	
	public OrderPaymentWrongEnumException(String msg) {
		super(msg);
	}

}
