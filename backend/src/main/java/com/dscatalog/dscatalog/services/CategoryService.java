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
import com.dscatalog.dscatalog.entities.Category;
import com.dscatalog.dscatalog.repositories.CategoryRepository;
import com.dscatalog.dscatalog.services.exceptions.DataBaseException;
import com.dscatalog.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll(){
		List<Category> list = repository.findAll();
		
		// A funcao map transforma cada elemento original em alguma outra coisa, aplicando uma funcao a cada elemento da sua lista
		// o resultado sera uma stream entao para isso usar o .collect para transformar de volta para lista
		List<CategoryDTO> listDto = list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
		return listDto;
		
		/*
		 * foreach de como converter
		List<CategoryDTO> listDto = new ArrayList<>();
		for (Category category : list) {
			listDto.add(new CategoryDTO(category));
		}
		
		return listDto;*/
		
	}

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {	
		Optional<Category> obj = repository.findById(id);
		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));	
		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		try {
		Category entity = repository.getById(id);
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new CategoryDTO(entity);
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

	public Page<CategoryDTO> findAllPaged(PageRequest pageRequest) {
		Page<Category> list = repository.findAll(pageRequest);
		Page<CategoryDTO> pageDto = list.map(x -> new CategoryDTO(x));
		return pageDto;
	}

}
