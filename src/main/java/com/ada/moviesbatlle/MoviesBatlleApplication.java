package com.ada.moviesbatlle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class MoviesBatlleApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoviesBatlleApplication.class, args);
	}

}
