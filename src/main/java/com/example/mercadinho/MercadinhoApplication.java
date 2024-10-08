package com.example.mercadinho;

import com.example.mercadinho.model.ProductEntity;
import com.example.mercadinho.model.ShoppingCartEntity;
import com.example.mercadinho.repository.ProductRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class MercadinhoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MercadinhoApplication.class, args);
//		ProductEntity product = new ProductEntity("id", "laranja", 120.0F);
//		ProductEntity product1 = new ProductEntity("id", "laranja", 120.0F);
//		ShoppingCartEntity shoppingCart = new ShoppingCartEntity(12345L, List.of(product1, product));
//		System.out.println(shoppingCart);
	}

}
