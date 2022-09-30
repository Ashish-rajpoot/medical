package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	public User findUserByEmail(String email);
	

	
	
//	@Query("select u form User where u.email=:email")
//	public User getUserByEmail(@Param ("email") String email);
	
}
