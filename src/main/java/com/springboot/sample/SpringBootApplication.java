package com.springboot.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@org.springframework.boot.autoconfigure.SpringBootApplication
public class SpringBootApplication extends SpringBootServletInitializer {
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringBootApplication.class);
	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootApplication.class, args);
	}
}
