package com.moviesdb.moviesdb;

import com.moviesdb.moviesdb.s3.S3Service;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

//exclude = { SecurityAutoConfiguration.class }
@SpringBootApplication()
public class MoviesDbSpringProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoviesDbSpringProjectApplication.class, args);
    }
}
