package com.zensar.olx.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.zensar.olx.bean.OlXUser;
import com.zensar.olx.db.OlxUserDAO;
@Service
public class OlxUserDetailsServiceImpl  implements UserDetailsService{

	@Autowired
	private OlxUserDAO dao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO  a talk to database and fetch user details
		System.out.println("In loadUserByUsername");
		//doing now todo-
		OlXUser foundUser=dao.findByUserName(username);
		System.out.println("In loadUserByUsername"+ foundUser);
		if(foundUser==null) {
			throw new UsernameNotFoundException(username);
		}

		
		//userDetails is an interface given by spring security
		//we are free to implement this interface but for simplicity spring security has given a class 
		//implements UserDetails information
		//name of the class is User
		//In this method we need to create an object  of user and return it
		//if(username.equals("zensar")) {	

		String roles=foundUser.getRoles();
		
		User authenticated=new User(foundUser.getUserName(),foundUser.getPassword(),
				AuthorityUtils.createAuthorityList(roles));
		return authenticated;

		//}


		//404 authorized 
		//401 not authorized
	}


}
