package com.ecommerce.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public UserDetailsService getUserDetailsService() {
		return new UserDetailsServiceImp();
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder(); 
	}
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		
		daoAuthenticationProvider.setUserDetailsService(getUserDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
		
		return daoAuthenticationProvider;
	}
	
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}



	@Override
	protected void configure(HttpSecurity http) throws Exception {


		http 
				.authorizeHttpRequests()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/user/**").hasRole("USER")
				.antMatchers("/**").permitAll()
				.and()
				.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/login")
				.usernameParameter("email")
				.defaultSuccessUrl("/default")
				.and()
				.csrf()
				.disable();
	}

//	@Override
//	public void configure(WebSecurity web) throws Exception {
//		web.ignoring().antMatchers("/resources/**","/static/**","/images/**","/js/**","/css/**","/productImages/**");
//		
//	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	@Autowired
//	GoogleOAuth2SuccessHandler googleOAuth2SuccessHandler;
	
//	@Autowired
//	UserDetailService customUserDetailService;
//	
//	@Autowired
//	BCryptPasswordEncoder bCryptPasswordEncoder;
//	
////	@Override
//	protected void configure(HttpSecurity http) throws Exception{
//		http
//				.authorizeRequests()
//				.antMatchers("/","/shop/**","/register","/h2-console/**").permitAll()
//				.antMatchers("/admin/**").hasRole("ADMIN")
//				.antMatchers("/user/**").hasRole("USER")
//				.anyRequest()
//				.authenticated()
//				.and()
//				.formLogin()
//				.loginPage("/login")
//				.permitAll()
////				.failureUrl("/login?error=true")
////				.defaultSuccessUrl("/")
////				.usernameParameter("email")
////				.passwordParameter("password")
////				.and()
////				.oauth2Login()
////				.loginPage("/signin")
////				.successHandler(null)
//////				.successHandler(googleOAuth2SuccessHandler)
////				.and()
////				.logout()
////				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
////				.logoutSuccessUrl("/login")
////				.invalidateHttpSession(true)
////				.deleteCookies("JSESSIONID")
//				.and()
//				.exceptionHandling()
//				.and()
//				.csrf()
//				.disable();
//		http.headers().frameOptions().disable();
//				
//	}
//	
//	@Bean
//	public BCryptPasswordEncoder bCryptPasswordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//	
//	@Bean
//	public UserDetailService customUserDetailService() {
//		return new UserDetailService();
//	}
////	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////		auth.userDetailsService(customUserDetailService);
////	}
//
//	@Bean
//	DaoAuthenticationProvider geAuthenticationProvider() {
//		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//		daoAuthenticationProvider.setUserDetailsService(customUserDetailService);
//		daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
//		return daoAuthenticationProvider;
//	}
//	
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.authenticationProvider(geAuthenticationProvider());
//	}
//
//	public void configure(WebSecurity web) throws Exception {
//		web.ignoring().antMatchers("/resoureces/**","/static/**","/images/**","/css/**","/productImages/**","/js/**");
//	}
}
