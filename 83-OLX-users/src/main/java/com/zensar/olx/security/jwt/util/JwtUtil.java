package com.zensar.olx.security.jwt.util;

import java.sql.Date;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Component
public class JwtUtil {
	
	
	private long expirationtime =60000*10;
	//secret key is very sensitive it should be complex and not be shared with anyone
	private final String SECRET_KEY="zensar@12345";
	
	public String generateToken(String username) 
	{
		//ToDo generate token
		return JWT.create()
			.withClaim("username", username)
			.withExpiresAt(new Date(System.currentTimeMillis()+expirationtime))
			.sign(Algorithm.HMAC512(SECRET_KEY));
	}
	
	public String validateToken(String token)
	{
		//To Do 
		//we need to validate the token
		return JWT.require(Algorithm.HMAC512(SECRET_KEY))
		.build()
		.verify(token)
		.getPayload();
		
	}
}
