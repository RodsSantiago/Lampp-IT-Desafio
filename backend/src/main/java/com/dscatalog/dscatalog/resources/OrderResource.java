package com.dscatalog.dscatalog.resources;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dscatalog.dscatalog.dto.OrderDTO;
import com.dscatalog.dscatalog.dto.ProductDTO;
import com.dscatalog.dscatalog.services.OrderService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value="/orders")
public class OrderResource {

    @Autowired
    private OrderService service;

    @GetMapping
    public ResponseEntity<List<OrderDTO>> findAll(){
        List<OrderDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity<OrderDTO> insert(@RequestBody OrderDTO dto){
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}/delivered")
    public ResponseEntity<OrderDTO> setDelivered(@PathVariable Long id){
        OrderDTO dto = service.setDelivered(id);
        return ResponseEntity.ok().body(dto);
    }
    
    @GetMapping(value = "/{id}")
	public ResponseEntity<OrderDTO> findById(@PathVariable Long id) {
		OrderDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);

	}

}
