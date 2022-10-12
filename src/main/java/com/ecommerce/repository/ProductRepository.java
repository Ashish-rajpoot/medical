package com.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

	List<Product> findAllByCategory_Id(int id);
	
	
}
