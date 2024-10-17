package com.example.mercadinho;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class MercadinhoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MercadinhoApplication.class, args);
//		Cookie cookie = new Cookie("JSESSIONID", "JSESSIONID");
//		System.out.println("JSESSIONID: " + cookie.getValue());
	}

	@Bean
	ApplicationRunner runner(PasswordEncoder passwordEncoder) {
		return args -> System.out.println(passwordEncoder.encode("password"));
	}

}
