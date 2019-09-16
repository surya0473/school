package com.sv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.sv")
public class YourSchoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(YourSchoolApplication.class, args);
	}

}
