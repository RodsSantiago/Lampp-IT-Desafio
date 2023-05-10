package com.dscatalog.dscatalog.resources;
//resource implementa o controlador rest

//sao os recursos que serao disponibilizados para as aplicacoes utilizarem
//seram os controladores rest, respondera requisicoes
//nessa classe teram endpoints (para listar, buscar, deletar as categorias)

import java.net.URI;

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

import com.dscatalog.dscatalog.dto.CompanyDTO;
import com.dscatalog.dscatalog.services.CompanyService;

@RestController
@RequestMapping(value = "/company")
public class CompanyResource {

	@Autowired
	private CompanyService service;

	// com paginacao
	@GetMapping
	public ResponseEntity<Page<CompanyDTO>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		Page<CompanyDTO> list = service.findAllPaged(pageRequest);
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<CompanyDTO> findById(@PathVariable Long id) {
		CompanyDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);

	}

	@PostMapping
	public ResponseEntity<CompanyDTO> insert(@RequestBody CompanyDTO dto) {
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();

		return ResponseEntity.created(uri).body(dto);

	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<CompanyDTO> update(@PathVariable Long id, @RequestBody CompanyDTO dto) {
		dto = service.update(id, dto);
		return ResponseEntity.ok().body(dto);

	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();

	}

}
