package com.example.mercadinho.domain.repository;

import com.example.mercadinho.domain.repository.model.Product;
import com.example.mercadinho.domain.repository.model.entity.ShoppingCartEntity;
import com.example.mercadinho.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataMongoTest
@ActiveProfiles("test")
class ShoppingCartRepositoryTest {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @BeforeEach
    void setup() {
        shoppingCartRepository.deleteAll();

        shoppingCartRepository.save(ShoppingCartEntity.builder()
                .products(List.of(Product.builder()
                        .name("caderno")
                        .price(BigDecimal.valueOf(12.0))
                        .quantity(1)
                        .build(),
                        Product.builder()
                                .name("folha")
                                .price(BigDecimal.valueOf(5.0))
                                .quantity(1)
                                .build()))
                .userId("user123")
                .date(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(LocalDateTime.now()))
                .build());
    }

    @Test
    @DisplayName("Find shopping cart by userId")
    void findByUserIdCaseOne() {
        Optional<ShoppingCartEntity> result = shoppingCartRepository.findByUserId("user123");

        assertTrue(result.isPresent(), "The shopping cart should be present");
        assertEquals("user123", result.get().getUserId(), "UserId should match");
        assertEquals(2, result.get().getProducts().size(), "Cart should have 2 items");
    }

    @Test
    @DisplayName("Return empty when userId does not exist")
    void findByUserIdCaseTwo() {
        Optional<ShoppingCartEntity> result = shoppingCartRepository.findByUserId("unknownUser");

        assertTrue(result.isEmpty(), "The shopping cart should not be present");
    }

}