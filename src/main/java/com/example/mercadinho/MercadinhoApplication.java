package com.example.mercadinho;

import com.example.mercadinho.model.ProductEntity;
import com.example.mercadinho.repository.ProductRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MercadinhoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MercadinhoApplication.class, args);
		ProductEntity product = new ProductEntity("id", "laranja", 120.0F);
		System.out.println();
	}

}
