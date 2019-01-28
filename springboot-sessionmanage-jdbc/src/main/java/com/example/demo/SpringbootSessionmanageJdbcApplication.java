package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.example.controller.SpringSessionController;

@SpringBootApplication
@ComponentScan(basePackageClasses=SpringSessionController.class)
public class SpringbootSessionmanageJdbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootSessionmanageJdbcApplication.class, args);
	}

}

