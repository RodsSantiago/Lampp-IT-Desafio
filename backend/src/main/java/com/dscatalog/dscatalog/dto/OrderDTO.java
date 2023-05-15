package com.dscatalog.dscatalog.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.dscatalog.dscatalog.entities.DeliveryType;
import com.dscatalog.dscatalog.entities.Order;
import com.dscatalog.dscatalog.entities.OrderPayment;
import com.dscatalog.dscatalog.entities.OrderStatus;


public class OrderDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
    private String address;
    private Double latitude;
    private Double longitude;
    private Instant moment;
    private OrderStatus status;
    private OrderPayment payment;
    private DeliveryType deliveryType;
    private String companyName;
    
    private CompanyDTO company;

    private List<ProductDTO> products = new ArrayList<>();

    public OrderDTO(){
    }

    public OrderDTO(Long id, String address, Double latitude, Double longitude, Instant moment, OrderStatus status, OrderPayment payment, DeliveryType deliveryType, String companyName) {
        this.id = id;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.moment = moment;
        this.status = status;
        this.payment = payment;
        this.deliveryType = deliveryType;
        this.companyName = company.getName();
        	
    }

    public OrderDTO(Order entity) {
        id = entity.getId();
        address = entity.getAddress();
        latitude = entity.getLatitude();
        longitude = entity.getLongitude();
        moment = entity.getMoment();
        status = entity.getStatus();
        payment = entity.getPayment();
        deliveryType = entity.getDeliveryType();
        products = entity.getProducts().stream()
                .map(ProductDTO::new).collect(Collectors.toList());
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Instant getMoment() {
		return moment;
	}

	public void setMoment(Instant moment) {
		this.moment = moment;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public List<ProductDTO> getProducts() {
		return products;
	}

	public void setProducts(List<ProductDTO> products) {
		this.products = products;
	}

	public OrderPayment getPayment() {
		return payment;
	}

	public void setPayment(OrderPayment payment) {
		this.payment = payment;
	}

	public DeliveryType getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(DeliveryType deliveryType) {
		this.deliveryType = deliveryType;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	
    
    
    
}
