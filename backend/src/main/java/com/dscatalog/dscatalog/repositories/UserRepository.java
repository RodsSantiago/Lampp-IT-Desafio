package com.dscatalog.dscatalog.repositories;

//repositorio jpa criado para ter muitas informacoes jpa ja prontas para acessar no banco de dados
//componente injetavel pelo mecanismo do spirng - gerenciado pelo spring

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dscatalog.dscatalog.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	

}
