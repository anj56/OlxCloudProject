package com.zensar.olx.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.zensar.olx.security.jwt.filter.JWTAuthenticationFilter;


@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	//httpstatus code 401--- which is specify that user is not passing right username and pass word
	
	//Authentication  -username ,password  (BioM )
	//Prove whatever user is claiming,password
	
	//userDetailService  is an iterface given by spring security
	//this interface is only one method loadUserByUserName(String userName)
	//this method is responsible for loading the user object from database
	//if user object could not found in database this method should throw userName not founfd exception
	//it is responsibility of developer to give implementation of interface 
	@Autowired
	private UserDetailsService userDetailService;
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			//.inMemoryAuthentication() //we are storing credentials in memory
			//.withUser("zensar")
			//.password("$2a$10$Qr6/hOLvUaoIB3NRUHFQoOo05uSR48spWM1VJrJkgoXlVVnwjgCTe") //password stored here Must be in encoded from //No Operation // password//this is bad to stored password in plain text
										//we must store password in encoded form 
										//BcryptPasswordEncoder is recommneded  for password encoding
			//.roles("USER")
			
			//.and()
			.userDetailsService(userDetailService)
			.passwordEncoder(getPasswordEncoder()); //this line tells spring secirity to use Bcryptpasswordencoder
	
		
	}
	
	
	
	
	//to take chaires home
	
	//Authorization-specifying access right to a resources
	
	//Access based on roles
	//what are you allowed to do?
	//To use PC,chair
	
	
	//http status code 403(forbidden) ---specifuy user is suthenticated but not authorized to access this resources
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/user/authenticate","/token/validate").permitAll()//this url must be public so that user can login(permitAll means everyone allowed to access )
			.antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
			.anyRequest()
			.authenticated()
			.and()
			//.httpBasic(); //this ask user to enter username and password using prompt
			.addFilter(new JWTAuthenticationFilter(authenticationManager()))
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);//This must be restservice
		                                                            //Rest should be stateless
	}
	@Override
	
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	//Following Bean is used for password Encoding
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
		return passwordEncoder;
	} 
	
}
