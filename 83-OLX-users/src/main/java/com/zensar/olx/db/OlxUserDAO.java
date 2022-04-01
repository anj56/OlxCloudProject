package com.zensar.olx.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zensar.olx.bean.OlXUser;

@Repository
public interface OlxUserDAO  extends JpaRepository<OlXUser, Integer>{
 
	OlXUser findByUserName(String userName);
}
