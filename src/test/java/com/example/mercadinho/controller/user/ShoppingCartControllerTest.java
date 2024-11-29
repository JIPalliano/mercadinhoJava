package com.example.mercadinho.controller.user;

import com.example.mercadinho.domain.repository.ShoppingCartRepository;
import com.example.mercadinho.domain.repository.model.Product;
import com.example.mercadinho.domain.repository.model.entity.ShoppingCartEntity;
import com.example.mercadinho.domain.repository.model.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class ShoppingCartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private ShoppingCartEntity shoppingCartEntity;

    @Mock
    private UserEntity user;

    @BeforeEach
    void setupUserService() {
        shoppingCartRepository.deleteAll();

        user = UserEntity.builder()
                .id("user2")
                .username("user2")
                .password("password2")
                .role("USER")
                .build();

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user, null, List.of());

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    @DisplayName("Should update quantity for product")
    void testAddProductToShoppingCart() throws Exception {

        Product productA = Product.builder()
                .id("product2")
                .name("Product 2")
                .price(BigDecimal.valueOf(10.0))
                .quantity(1)
                .build();

        shoppingCartEntity = ShoppingCartEntity.builder()
                .id("shoppingCart1")
                .products(List.of(productA))
                .userId(user.getId())
                .build();

        shoppingCartRepository.save(shoppingCartEntity);
        mockMvc.perform(put("/v1/shopping-cart/{id-product}", productA.getId())
                        .param("quantity", String.valueOf(2))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        ShoppingCartEntity result = shoppingCartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Shopping cart not found"));

        assertNotNull(result);
        assertEquals(1, result.getProducts().size());

        Product updatedProduct = result.getProducts().get(0);
        assertEquals(productA.getId(), updatedProduct.getId());
        assertEquals("Product 2", updatedProduct.getName());
        assertEquals(productA.getQuantity() + 2, updatedProduct.getQuantity());
    }

    @Test
    @DisplayName("Should look for the shopping cart")
    void testFindShoppingCart() throws Exception {

        Product productA = Product.builder()
                .id("product2")
                .name("Product 2")
                .price(BigDecimal.valueOf(10.0))
                .quantity(1)
                .build();

        shoppingCartEntity = ShoppingCartEntity.builder()
                .id("shoppingCart1")
                .products(List.of(productA))
                .userId(user.getId())
                .build();
        shoppingCartRepository.save(shoppingCartEntity);

        mockMvc.perform(get("/v1/shopping-cart/user-shopping-cart")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(shoppingCartEntity.getId()))
                .andExpect(jsonPath("$.totalPrice").value(10.0))
                .andExpect(jsonPath("$.quantity").value(1))
                .andExpect(jsonPath("$.userId").value(user.getId()));
    }

    @Test
    @DisplayName("Should delete the shopping cart")
    void testDeleteShoppingCart() throws Exception {

        ShoppingCartEntity cart = new ShoppingCartEntity();
        cart.setUserId(user.getId());
        shoppingCartRepository.save(cart);

        mockMvc.perform(delete("/v1/shopping-cart/delete")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertThat(shoppingCartRepository.findByUserId(user.getId())).isEmpty();
    }

}