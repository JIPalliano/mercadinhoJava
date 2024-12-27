package com.example.mercadinho.infrastructure.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product{
        private String id;
        private String name;
        private BigDecimal price;
        private Integer quantity;

}
