package com.dscatalog.dscatalog.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dscatalog.dscatalog.entities.Company;
import com.dscatalog.dscatalog.entities.Order;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
	
	@Query("SELECT DISTINCT obj FROM Company obj JOIN FETCH obj.products")
    List<Company> findCompanyWithProducts();
	
	Company findByName(String name);

}
