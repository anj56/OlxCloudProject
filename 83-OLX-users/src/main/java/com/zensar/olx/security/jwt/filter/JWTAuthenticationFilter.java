package com.zensar.olx.security.jwt.filter;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

//spring mvc,Rest 
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.zensar.olx.db.TokenStorage;
import com.zensar.olx.security.jwt.util.JwtUtil;




//this is custom filter
// you need add this filter in spring Security filter chain otherwiae it is not executed
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {
	private String authorizationHeader = "Authorization";
	private final String BEARER = "Bearer ";

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager)
	{
		super(authenticationManager);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException
	{

		JwtUtil jwtUtil = new JwtUtil(); // using class which are made for validate token

		System.out.println("IN doFilterInternal");
		// 1. check if useer has passed token, we do that by fetching value from
		// authorization header
		// authorization is predefind header
		String authorizationHeaderValue = request.getHeader(authorizationHeader);

		if (authorizationHeaderValue == null || !authorizationHeaderValue.startsWith(BEARER)) {
			chain.doFilter(request, response);// invoke next security filter chain
			return;
		}

		if (authorizationHeaderValue != null && authorizationHeaderValue.startsWith(BEARER))
		{

			// Bearer//baerer is prefix to token value this is predefined
			String token = authorizationHeaderValue.substring(7).trim();// we want to remove Bearer from token value

			if (token != null) 
			{
				// Authorization Bearer token
				System.out.println("authorizationHeaderValue : " + authorizationHeaderValue);
				System.out.println("token value : " + token);
				
				//Check if this token is exsist or not
				String tokenExists = TokenStorage.getToken(token);
				
				//if token is null means user has loggedout
				if(tokenExists==null) 
				{
					chain.doFilter(request, response);
					return;
				}
				

				
				try 
				{
					// validate the token
					String encodedpayload = jwtUtil.validateToken(token);
				
					//token is valid
					String payload = new String(Base64.getDecoder().decode(encodedpayload));
					
					// from this payload we need to fetch username
					JsonParser jsonParser =JsonParserFactory.getJsonParser();
					Map<String, Object> parsemap =jsonParser.parseMap(payload);
					String username= (String) parsemap.get("username");
					
					// create UsernamePasswordAuthenticationValue
					UsernamePasswordAuthenticationToken authenticationToken;
					authenticationToken =new UsernamePasswordAuthenticationToken(username, null,
							AuthorityUtils.createAuthorityList("ROLE_USER"));
					
					// Authenticate user
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				
				} catch (Exception e)
				{
					// if token is not valid
					e.printStackTrace();
				}
				// 2. If token not present ask user to login.
				// 3.If token present fetch it and validate it.
			}
		}

		chain.doFilter(request, response);
	}

}
