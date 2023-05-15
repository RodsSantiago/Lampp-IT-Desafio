package com.dscatalog.dscatalog.dto;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.dscatalog.dscatalog.entities.Company;

public class CompanyDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	

	private Long id;
	private String name;
	private String description;
	private LocalTime openingTime;
	private LocalTime closingTime;
	
	Set<RoleDTO> roles = new HashSet<>();
	
	private List<ProductDTO> products = new ArrayList<>();

	public CompanyDTO() {
		
	}

	public CompanyDTO(Long id, String name, String description, LocalTime openingTime, LocalTime closingTime) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.openingTime = openingTime;
		this.closingTime = closingTime;
	}
	
	public CompanyDTO(Company entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.description = entity.getDescription();
		this.openingTime = entity.getOpeningTime();
		this.closingTime = entity.getClosingTime();
		entity.getRoles().forEach(role -> this.roles.add(new RoleDTO(role)));
		products = entity.getProducts().stream()
                .map(ProductDTO::new).collect(Collectors.toList());
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

	public LocalTime getOpeningTime() {
		return openingTime;
	}

	public void setOpeningTime(LocalTime openingTime) {
		this.openingTime = openingTime;
	}

	public LocalTime getClosingTime() {
		return closingTime;
	}

	public void setClosingTime(LocalTime closingTime) {
		this.closingTime = closingTime;
	}

	public Set<RoleDTO> getRoles() {
		return roles;
	}

	public List<ProductDTO> getProducts() {
		return products;
	}

}
