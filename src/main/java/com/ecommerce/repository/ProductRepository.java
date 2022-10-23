package com.ecommerce.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

	Set<Product> findAllByCategory_Id(int id);

	Set<Product> findAllById(int id);
	
	@Query("SELECT p FROM Product p WHERE p.name LIKE %?1%"
	        + "OR p.description LIKE %?1%")
	 Set<Product> findAllByKey(String keyword);


	
}
