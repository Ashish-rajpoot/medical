//package com.ecommerce.services;
//
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.ecommerce.model.CustomUserDetail;
//import com.ecommerce.model.User;
//import com.ecommerce.repository.UserRepository;
//
//@Service
//public class UserDetailService implements UserDetailsService {
//
//	@Autowired
//	UserRepository userRepository;
//	@Override
//	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//		// TODO Auto-generated method stub
////		Optional<User> user = userRepository.findUserByEmail(email);
////		user.orElseThrow(()-> new UsernameNotFoundException("User Not Find"));
////		return user;
//		
//		User user = userRepository.findUserByEmail(email);
//		if(user!=null) {
//			return new CustomUserDetail(user);
//		}
//		throw new UsernameNotFoundException("User not Found");
//		
//	}
//
//}
