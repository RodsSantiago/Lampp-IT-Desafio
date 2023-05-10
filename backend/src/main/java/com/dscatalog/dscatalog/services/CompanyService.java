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

import com.dscatalog.dscatalog.dto.CompanyDTO;
import com.dscatalog.dscatalog.entities.Company;
import com.dscatalog.dscatalog.repositories.CompanyRepository;
import com.dscatalog.dscatalog.services.exceptions.DataBaseException;
import com.dscatalog.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class CompanyService {
	
	@Autowired
	private CompanyRepository repository;
	
	@Transactional(readOnly = true)
	public List<CompanyDTO> findAll(){
		List<Company> list = repository.findAll();
		
		// A funcao map transforma cada elemento original em alguma outra coisa, aplicando uma funcao a cada elemento da sua lista
		// o resultado sera uma stream entao para isso usar o .collect para transformar de volta para lista
		List<CompanyDTO> listDto = list.stream().map(x -> new CompanyDTO(x)).collect(Collectors.toList());
		return listDto;
	
	}

	@Transactional(readOnly = true)
	public CompanyDTO findById(Long id) {	
		Optional<Company> obj = repository.findById(id);
		Company entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));	
		return new CompanyDTO(entity);
	}

	@Transactional
	public CompanyDTO insert(CompanyDTO dto) {
		Company entity = new Company();
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity = repository.save(entity);
		return new CompanyDTO(entity);
	}


	@Transactional
	public CompanyDTO update(Long id, CompanyDTO dto) {
		try {
		Company entity = repository.getById(id);
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new CompanyDTO(entity);
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

	public Page<CompanyDTO> findAllPaged(PageRequest pageRequest) {
		Page<Company> list = repository.findAll(pageRequest);
		Page<CompanyDTO> pageDto = list.map(x -> new CompanyDTO(x));
		return pageDto;
	}

}
