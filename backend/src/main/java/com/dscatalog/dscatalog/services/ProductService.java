package com.dscatalog.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

//injecao de dependencia automatizada - objeto da camada de servico 
//vai acessar o repositorio e chamar no banco de dado as categorias

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dscatalog.dscatalog.dto.CategoryDTO;
import com.dscatalog.dscatalog.dto.ProductDTO;
import com.dscatalog.dscatalog.entities.Category;
import com.dscatalog.dscatalog.entities.Product;
import com.dscatalog.dscatalog.repositories.CategoryRepository;
import com.dscatalog.dscatalog.repositories.ProductRepository;
import com.dscatalog.dscatalog.services.exceptions.DataBaseException;
import com.dscatalog.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional(readOnly = true)
	public List<ProductDTO> findAll(){
		List<Product> list = repository.findAll();
		
		// A funcao map transforma cada elemento original em alguma outra coisa, aplicando uma funcao a cada elemento da sua lista
		// o resultado sera uma stream entao para isso usar o .collect para transformar de volta para lista
		List<ProductDTO> listDto = list.stream().map(x -> new ProductDTO(x)).collect(Collectors.toList());
		return listDto;
		
		/*
		 * foreach de como converter
		List<ProductDTO> listDto = new ArrayList<>();
		for (Product category : list) {
			listDto.add(new ProductDTO(category));
		}
		
		return listDto;*/
		
	}

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {	
		Optional<Product> obj = repository.findById(id);
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));	
		return new ProductDTO(entity, entity.getCategories());
	}

	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new ProductDTO(entity);
	}


	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {
		Product entity = repository.getById(id);
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new ProductDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("id not found" + id);
		} 
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found" + id);
		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Violacao de integridade");
		}
	}

	public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
		Page<Product> list = repository.findAll(pageRequest);
		Page<ProductDTO> pageDto = list.map(x -> new ProductDTO(x));
		return pageDto;
	}
	
	private void copyDtoToEntity(ProductDTO dto, Product entity) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setImgUrl(dto.getImgUrl());
		entity.setPrice(dto.getPrice());
		entity.setDate(dto.getDate());
		
		entity.getCategories().clear();
		for (CategoryDTO catDto : dto.getCategories()) {
			Category category = categoryRepository.getById(catDto.getId());
			entity.getCategories().add(category);
		}
		
	}

}