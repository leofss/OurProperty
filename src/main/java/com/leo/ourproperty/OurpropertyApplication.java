package com.leo.ourproperty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class OurpropertyApplication {

	public static void main(String[] args) {
		SpringApplication.run(OurpropertyApplication.class, args);
	}

}
