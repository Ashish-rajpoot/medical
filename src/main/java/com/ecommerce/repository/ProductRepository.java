package com.ecommerce.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

	Set<Product> findAllByCategory_Id(int id);

	Set<Product> findAllById(int id);


	
}
