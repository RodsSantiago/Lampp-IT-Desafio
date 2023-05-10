package com.dscatalog.dscatalog.dto;

import java.io.Serializable;

import com.dscatalog.dscatalog.entities.Company;

public class CompanyDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	

	private Long id;
	private String name;
	private String description;

	public CompanyDTO() {
		
	}

	public CompanyDTO(Long id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}
	
	public CompanyDTO(Company entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.description = entity.getDescription();

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


}
