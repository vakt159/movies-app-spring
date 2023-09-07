package com.moviesdb.moviesdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//exclude = { SecurityAutoConfiguration.class }
@SpringBootApplication()
public class MoviesDbSpringProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoviesDbSpringProjectApplication.class, args);
	}

}
