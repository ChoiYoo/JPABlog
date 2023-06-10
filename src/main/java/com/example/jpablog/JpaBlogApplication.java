package com.example.jpablog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAspectJAutoProxy
@SpringBootApplication
@EnableScheduling
public class JpaBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaBlogApplication.class, args);
	}

}
