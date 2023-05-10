package com.dscatalog.dscatalog.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dscatalog.dscatalog.dto.OrderDTO;
import com.dscatalog.dscatalog.dto.ProductDTO;
import com.dscatalog.dscatalog.entities.DeliveryType;
import com.dscatalog.dscatalog.entities.Order;
import com.dscatalog.dscatalog.entities.OrderStatus;
import com.dscatalog.dscatalog.entities.Product;
import com.dscatalog.dscatalog.repositories.OrderRepository;
import com.dscatalog.dscatalog.repositories.ProductRepository;
import com.dscatalog.dscatalog.services.exceptions.DeliveryTypeWrongEnumException;
import com.dscatalog.dscatalog.services.exceptions.OrderWithoutProductsException;
import com.dscatalog.dscatalog.services.exceptions.ResourceNotFoundException;


@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<OrderDTO> findAll(){
        List<Order> list = repository.findOrdersWithProducts();
        return list.stream().map(OrderDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public OrderDTO insert(OrderDTO dto){        
        Order order = new Order(null, dto.getAddress(), dto.getLatitude(), dto.getLongitude(),
                Instant.now(), OrderStatus.EM_ATENDIMENTO, dto.getPayment(), dto.getDeliveryType());
        for (ProductDTO p : dto.getProducts()){
            Product product = productRepository.getOne(p.getId());
            order.getProducts().add(product);
        }
        order = repository.save(order);
        validateDeliveryType(dto.getDeliveryType());
        return new OrderDTO(order);
    }

    @Transactional
    public OrderDTO setDelivered(Long id){
        Order order = repository.getOne(id);
        // Verifica se há pelo menos um produto na order
        if (order.getProducts().isEmpty()) {
            throw new OrderWithoutProductsException("Cannot set delivered status for an empty order");
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
}
