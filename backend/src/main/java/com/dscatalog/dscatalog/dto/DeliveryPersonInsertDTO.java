package com.dscatalog.dscatalog.dto;

public class DeliveryPersonInsertDTO extends DeliveryPersonDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String password;
	
	DeliveryPersonInsertDTO() {
		super();
	}	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
