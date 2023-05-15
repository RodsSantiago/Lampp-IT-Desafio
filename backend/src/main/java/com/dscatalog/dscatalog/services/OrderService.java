package com.dscatalog.dscatalog.services;

import java.time.Instant;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dscatalog.dscatalog.dto.OrderDTO;
import com.dscatalog.dscatalog.dto.ProductDTO;
import com.dscatalog.dscatalog.entities.Company;
import com.dscatalog.dscatalog.entities.DeliveryType;
import com.dscatalog.dscatalog.entities.Order;
import com.dscatalog.dscatalog.entities.OrderPayment;
import com.dscatalog.dscatalog.entities.OrderStatus;
import com.dscatalog.dscatalog.entities.Product;
import com.dscatalog.dscatalog.repositories.CompanyRepository;
import com.dscatalog.dscatalog.repositories.OrderRepository;
import com.dscatalog.dscatalog.repositories.ProductRepository;
import com.dscatalog.dscatalog.services.exceptions.CompanyClosedException;
import com.dscatalog.dscatalog.services.exceptions.CompanyNotFoundException;
import com.dscatalog.dscatalog.services.exceptions.DeliveryTypeWrongEnumException;
import com.dscatalog.dscatalog.services.exceptions.OrderPaymentWrongEnumException;
import com.dscatalog.dscatalog.services.exceptions.OrderWithoutProductsException;
import com.dscatalog.dscatalog.services.exceptions.ResourceNotFoundException;


@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CompanyRepository companyRepository;

    @Transactional(readOnly = true)
    public List<OrderDTO> findAll(){
        List<Order> list = repository.findOrdersWithProducts();
        return list.stream().map(OrderDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public OrderDTO insert(OrderDTO dto){
    		
        validateCompanyName(dto.getCompanyName());
    	validateDeliveryType(dto.getDeliveryType());
        validateCompanySchedule(dto.getCompanyName());
        validateOrderPayment(dto.getPayment());
        validateProducts(dto.getProducts());
    	
        Order order = new Order(null, dto.getAddress(), dto.getLatitude(), dto.getLongitude(),
                Instant.now(), OrderStatus.EM_ATENDIMENTO, dto.getPayment(), dto.getDeliveryType());
        for (ProductDTO p : dto.getProducts()){
            Product product = productRepository.getOne(p.getId());
            order.getProducts().add(product);
        }
        order = repository.save(order);
        return new OrderDTO(order);
    }

    @Transactional
    public OrderDTO setDelivered(Long id){
        Order order = repository.getOne(id);
        // VERIFICA SE HA PELO MENOS UM PRODUTO NA ORDER
        if (order.getProducts().isEmpty()) {
            throw new OrderWithoutProductsException("Cannot set delivered status for an empty order");
        }
        
        // VERIFICA SE E PERMITIDO VOLTAR PARA O STATUS ANTERIOR
        OrderStatus previousStatus = order.getStatus();
        if (previousStatus == OrderStatus.CONCLUIDO) {
            throw new IllegalStateException("Cannot revert from CONCLUIDO to AGUARDANDO_ENTREGA");
        }
        
        order.setStatus(OrderStatus.CONCLUIDO);
        order = repository.save(order);
        return new OrderDTO(order);
    }
    
    @Transactional(readOnly = true)
	public OrderDTO findById(Long id) {	
		Optional<Order> obj = repository.findById(id);
		Order entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));	
		return new OrderDTO(entity);
	}
    
    // VALIDAR SE O DELIVERY TYPE ESTA CORRETO CONFORME O ENUM
    private void validateDeliveryType(DeliveryType deliveryType) {
        if (deliveryType == null) {
            throw new DeliveryTypeWrongEnumException("Delivery type cannot be null");
        }
        if (deliveryType != DeliveryType.DELIVERY && deliveryType != DeliveryType.RETIRADA_NO_LOCAL) {
            throw new DeliveryTypeWrongEnumException("Invalid delivery type");
        }
    }
    
 // VALIDAR SE O TIPO DE PAGAMENTO
    private void validateOrderPayment(OrderPayment orderPayment) {
        if (orderPayment == null) {
            throw new OrderPaymentWrongEnumException("Order Payment cannot be null");
        }
        if (orderPayment != OrderPayment.DINHEIRO && orderPayment != OrderPayment.CARTAO && orderPayment != OrderPayment.PIX) {
            throw new OrderPaymentWrongEnumException("Invalid order payment");
        }
    }
    
    //VALIDAR SE TEM PRODUTOS
    private void validateProducts(List<ProductDTO> products) {
        if (products == null || products.isEmpty()) {
            throw new OrderWithoutProductsException("Cannot create an order without products");
        }
    }
    
    //VALIDAR NOME EMPRESA
    private void validateCompanyName(String companyName) {
    	Company company = companyRepository.findByName(companyName);
        if (company == null) {
            throw new CompanyNotFoundException("Company not found");
        }
    }
    
    
    //VALIDAR HORARIO DE FUNCIONAMENTO
    private void validateCompanySchedule(String companyName) {
        Company company = companyRepository.findByName(companyName);
        System.out.println(companyName);
        LocalTime now = LocalTime.now();
        if (!now.isAfter(company.getOpeningTime()) || !now.isBefore(company.getClosingTime())) {
            throw new CompanyClosedException("Company is closed");
        }
    }
    
}
