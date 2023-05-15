package com.dscatalog.dscatalog.resources;
//resource implementa o controlador rest

//sao os recursos que serao disponibilizados para as aplicacoes utilizarem
//seram os controladores rest, respondera requisicoes
//nessa classe teram endpoints (para listar, buscar, deletar as categorias)

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dscatalog.dscatalog.dto.DeliveryPersonDTO;
import com.dscatalog.dscatalog.dto.DeliveryPersonInsertDTO;
import com.dscatalog.dscatalog.services.DeliveryPersonService;

@RestController
@RequestMapping(value = "/deliveryPersons")
public class DeliveryPersonResource {

	@Autowired
	private DeliveryPersonService service;

	// com paginacao
	@GetMapping
	public ResponseEntity<Page<DeliveryPersonDTO>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "firstName") String orderBy) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		Page<DeliveryPersonDTO> list = service.findAllPaged(pageRequest);
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<DeliveryPersonDTO> findById(@PathVariable Long id) {
		DeliveryPersonDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);

	}

	@PostMapping
	public ResponseEntity<DeliveryPersonDTO> insert(@RequestBody DeliveryPersonInsertDTO dto) {
		DeliveryPersonDTO newDto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newDto.getId()).toUri();

		return ResponseEntity.created(uri).body(newDto);

	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<DeliveryPersonDTO> update(@PathVariable Long id, @RequestBody DeliveryPersonDTO dto) {
		dto = service.update(id, dto);
		return ResponseEntity.ok().body(dto);

	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();

	}

}
