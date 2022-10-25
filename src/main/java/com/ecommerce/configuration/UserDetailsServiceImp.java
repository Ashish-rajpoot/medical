package com.ecommerce.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;

@Service
public class UserDetailsServiceImp implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//		User user = userRepository.findByEmail("test@gmail.com");
		User user = userRepository.findByEmail(email);
		
		System.out.println(email);

		if(user!=null) {
//			System.out.println(user);
			return new CustomUserDetails(user);
		}
		
		throw new UsernameNotFoundException("User not Found");
		
	}

}
