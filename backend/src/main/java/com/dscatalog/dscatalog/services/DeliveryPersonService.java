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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//injecao de dependencia automatizada - objeto da camada de servico 
//vai acessar o repositorio e chamar no banco de dado as categorias

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dscatalog.dscatalog.dto.RoleDTO;
import com.dscatalog.dscatalog.dto.DeliveryPersonDTO;
import com.dscatalog.dscatalog.dto.DeliveryPersonInsertDTO;
import com.dscatalog.dscatalog.entities.Role;
import com.dscatalog.dscatalog.entities.DeliveryPerson;
import com.dscatalog.dscatalog.repositories.RoleRepository;
import com.dscatalog.dscatalog.repositories.DeliveryPersonRepository;
import com.dscatalog.dscatalog.services.exceptions.DataBaseException;
import com.dscatalog.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class DeliveryPersonService {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private DeliveryPersonRepository repository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Transactional(readOnly = true)
	public List<DeliveryPersonDTO> findAll(){
		List<DeliveryPerson> list = repository.findAll();
		
		List<DeliveryPersonDTO> listDto = list.stream().map(x -> new DeliveryPersonDTO(x)).collect(Collectors.toList());
		return listDto;
		
	}

	@Transactional(readOnly = true)
	public DeliveryPersonDTO findById(Long id) {	
		Optional<DeliveryPerson> obj = repository.findById(id);
		DeliveryPerson entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));	
		return new DeliveryPersonDTO(entity);
	}

	@Transactional
	public DeliveryPersonDTO insert(DeliveryPersonInsertDTO dto) {
		DeliveryPerson entity = new DeliveryPerson();
		copyDtoToEntity(dto, entity);
		entity.setPassword(passwordEncoder.encode(dto.getPassword()));
		entity = repository.save(entity);
		return new DeliveryPersonDTO(entity);
	}


	@Transactional
	public DeliveryPersonDTO update(Long id, DeliveryPersonDTO dto) {
		try {
		DeliveryPerson entity = repository.getById(id);
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new DeliveryPersonDTO(entity);
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

	public Page<DeliveryPersonDTO> findAllPaged(PageRequest pageRequest) {
		Page<DeliveryPerson> list = repository.findAll(pageRequest);
		Page<DeliveryPersonDTO> pageDto = list.map(x -> new DeliveryPersonDTO(x));
		return pageDto;
	}
	
	private void copyDtoToEntity(DeliveryPersonDTO dto, DeliveryPerson entity) {
		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
		entity.setEmail(dto.getEmail());
		
		
		entity.getRoles().clear();
		for (RoleDTO roleDto : dto.getRoles()) {
			Role roles = roleRepository.getById(roleDto.getId());
			entity.getRoles().add(roles);
		}
		
	}

}