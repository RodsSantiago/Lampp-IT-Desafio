package com.dscatalog.dscatalog.services.exceptions;

public class CompanyClosedException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public CompanyClosedException(String msg) {
		super(msg);
	}

}
