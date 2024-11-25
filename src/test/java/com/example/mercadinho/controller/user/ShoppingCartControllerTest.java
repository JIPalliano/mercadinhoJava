package com.example.mercadinho.controller.user;

import com.example.mercadinho.domain.repository.ShoppingCartRepository;
import com.example.mercadinho.domain.repository.model.Product;
import com.example.mercadinho.domain.repository.model.entity.ShoppingCartEntity;
import com.example.mercadinho.domain.repository.model.entity.UserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ShoppingCartControllerTest {

    @Mock
    private MockMvc mockMvc;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private UserEntity user;

    @BeforeEach
    void setupUserService() {
        user = UserEntity.builder()
                .id("user1")
                .username("user1")
                .password("password1")
                .role("ADMIN")
                .build();

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user, null, List.of());

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void testAddProductToShoppingCart() throws Exception {
        // Prepara os dados para o teste
        String idProduct = "12345";
        int quantity = 2;

        // Executa a chamada PUT
        mockMvc.perform(put("/shopping-cart/{id-product}", idProduct)
                        .param("quantity", String.valueOf(quantity))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Verifica se o produto foi adicionado ao carrinho
        var shoppingCart = shoppingCartRepository.findByUserId(user.getId()).orElseThrow();

        assertThat(shoppingCart.getProducts()).anyMatch(p ->
                p.getId().equals(idProduct) && p.getQuantity() == quantity
        );
    }

    @Test
    void testFindShoppingCart() throws Exception {
        // Prepara um carrinho no banco
        ShoppingCartEntity cart = new ShoppingCartEntity();
        cart.setUserId(user.getId());
        cart.setProducts(List.of(Product.builder()
                .id("product1")
                .name("Product 1")
                .price(BigDecimal.valueOf(10.0))
                .quantity(1)
                .build()));
        shoppingCartRepository.save(cart);

        // Executa a chamada GET
        mockMvc.perform(get("/shopping-cart/user-shopping-cart")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cart.getId()))
                .andExpect(jsonPath("$.totalPrice").value("20.00"))
                .andExpect(jsonPath("$.quantity").value(2));
    }

    @Test
    void testDeleteShoppingCart() throws Exception {
        // Prepara um carrinho no banco
        ShoppingCartEntity cart = new ShoppingCartEntity();
        cart.setUserId(user.getId());
        shoppingCartRepository.save(cart);

        // Executa a chamada DELETE
        mockMvc.perform(delete("/shopping-cart/delete")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Verifica se o carrinho foi deletado
        assertThat(shoppingCartRepository.findByUserId(user.getId())).isEmpty();
    }

}