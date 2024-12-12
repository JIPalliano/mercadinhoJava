package com.example.mercadinho;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class MercadinhoApplication {

    public static void main(String[] args) {
		SpringApplication.run(MercadinhoApplication.class, args);
	}
}
