package com.zensar.olx.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/* 
 * @author Vishal
 * this configuration file is needed for Customizing Cors configuration
 * cors-cross origin resource sharing
 * 
 * */

@Configuration
public class WebConfiguration implements WebMvcConfigurer{
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		
		registry
				.addMapping("/api/**")
				.allowedMethods("GET","DELETE","PUT","OPTIONS","POST")
				//or
				//.allowedMethods("*")
				.allowedOrigins("http://localhost:4200");
	}
	
}
