package com.mansoori;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;




@SpringBootApplication(scanBasePackages = "com.mansoori")
public class MansooriApplication {

	public static void main(String[] args) {
		SpringApplication.run(MansooriApplication.class, args);
	}

}
