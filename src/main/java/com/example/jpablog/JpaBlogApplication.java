package com.example.jpablog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class JpaBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaBlogApplication.class, args);
	}

}
