package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.ecommerce.model.Category;


public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
