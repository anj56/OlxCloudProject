package com.zensar.olx.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zensar.olx.bean.OlXUser;
import com.zensar.olx.db.OlxUserDAO;

@Service
public class OlxUserService {

	@Autowired
	OlxUserDAO dao;
	
	public OlXUser addOlxUser(OlXUser olXUser) {
		return this.dao.save(olXUser);
		}
	
	public OlXUser updateOlxUser(OlXUser olXUser) {
		return this.dao.save(olXUser);
	}
	
	public OlXUser findOlxUser(int id) {
		Optional<OlXUser>optional=this.dao.findById(id);
		if(optional.isPresent())
			return optional.get();
		else 
			return null;
	
	
	}
	public OlXUser findOlxUserByName(String name) {
		OlXUser olXUser=this.dao.findByUserName(name);
		System.out.println(olXUser+"dao");
		return olXUser;
	}
	
}
