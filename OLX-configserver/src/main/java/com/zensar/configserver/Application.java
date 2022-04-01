package com.zensar.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient

/**
 * 
 * @author AS65761
 * @EnableConfigServer- This annotation makes the application as config server 
 * Config server acts as client to Eureka Server . Hence @EnableConfigServer
 *
 */
@EnableConfigServer
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
