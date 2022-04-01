package com.zensar.olx.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.zensar.olx.bean.LoginResponse;
import com.zensar.olx.bean.LoginUser;
import com.zensar.olx.bean.OlXUser;
import com.zensar.olx.db.TokenStorage;
import com.zensar.olx.security.jwt.util.JwtUtil;
import com.zensar.olx.service.OlxUserService;

@RestController
public class OlxUserController {

	@Autowired
	OlxUserService service;
	@Autowired

	private AuthenticationManager manager;
	@Autowired

	private JwtUtil util;

	/**
	 * THis is rest specification for authenticating token for user details
	 * 
	 * @param Anjali
	 * @return
	 */

	@PostMapping("/user/authenticate")
	public LoginResponse login(@RequestBody LoginUser loginuser) {

		UsernamePasswordAuthenticationToken authenticationToken;
		authenticationToken = new UsernamePasswordAuthenticationToken(loginuser.getUserName(), loginuser.getPassword());

		try {
			manager.authenticate(authenticationToken);

			String token = util.generateToken(loginuser.getUserName());
			
			TokenStorage.storeToken(token, token);

			LoginResponse userResponse = new LoginResponse();
			userResponse.setJwt(token);

			return userResponse;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@PostMapping("/user")
	public OlXUser addOlxUser(@RequestBody OlXUser olXUser) {
		return this.service.addOlxUser(olXUser);
	}

	@GetMapping("/user/{uid}")
	public OlXUser findOlxUserById(@PathVariable(name = "uid") int id) {
		return this.service.findOlxUser(id);
	}

	@GetMapping("/user/find/{userName}")
	public OlXUser findOlxUserByName(@PathVariable(name = "userName") String name) {
		return this.service.findOlxUserByName(name);
	}

	@GetMapping("/token/validate")
	public ResponseEntity<Boolean> isValidate(@RequestHeader("Authorization") String authtoken) {
		try 
		{
			String validatedtoken = util.validateToken(authtoken.substring(7));
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}

	}

	@DeleteMapping("/user/logout")
	public ResponseEntity<Boolean> logout(@RequestHeader("Authorization") String authtoken) {
		
		String token = authtoken.substring(7);
		
		try
		{
			TokenStorage.removeToken(token);
			
			ResponseEntity<Boolean> responseEntity=new ResponseEntity<Boolean>(true,HttpStatus.OK);
					return responseEntity;
			}
		
		catch(Exception e)
		{
			ResponseEntity<Boolean> responseEntity=new ResponseEntity<Boolean>(false,HttpStatus.BAD_REQUEST);
			return responseEntity;
			
		}
		
		}
}
