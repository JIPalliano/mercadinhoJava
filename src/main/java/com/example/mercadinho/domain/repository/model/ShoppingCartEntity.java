package com.example.mercadinho.domain.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "shoppingCartEntity")
public class ShoppingCartEntity {
        @Id
        private String id;
        private List<Product> products;
        @Indexed(unique = true)
        private String userId;
        private String date;
        
}

