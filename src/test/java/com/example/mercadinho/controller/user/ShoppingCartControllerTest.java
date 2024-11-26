package com.example.mercadinho.controller.user;

import com.example.mercadinho.domain.repository.ShoppingCartRepository;
import com.example.mercadinho.domain.repository.model.Product;
import com.example.mercadinho.domain.repository.model.entity.ProductEntity;
import com.example.mercadinho.domain.repository.model.entity.ShoppingCartEntity;
import com.example.mercadinho.domain.repository.model.entity.UserEntity;
import com.example.mercadinho.service.product.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class ShoppingCartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    private UserEntity user;

//    @MockBean
//    private ProductService productService;

    @BeforeEach
    void setupUserService() {
        shoppingCartRepository.deleteAll();

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

        ProductEntity productA = ProductEntity.builder()
                .id("product1")
                .name("Product 1")
                .price(BigDecimal.valueOf(10.0))
                .quantity(2)
                .build();
        int quantity = 2;

        //when(productService.findById(productA)).thenReturn(productA);

        mockMvc.perform(put("/shopping-cart/{id-product}", productA.id())
                        .param("quantity", String.valueOf(quantity))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        ShoppingCartEntity shoppingCart = shoppingCartRepository.findByUserId(user.getId()).orElseThrow();

        assertThat(shoppingCart.getProducts()).anyMatch(p ->
                p.getId().equals(productA.id()) && p.getQuantity() == quantity
        );
    }

    @Test
    void testFindShoppingCart() throws Exception {

        ShoppingCartEntity cart = new ShoppingCartEntity();
        cart.setUserId(user.getId());
        cart.setProducts(new ArrayList<>(List.of(Product.builder()
                .id("product2")
                .name("Product 1")
                .price(BigDecimal.valueOf(10.0))
                .quantity(2)
                .build())));
        shoppingCartRepository.save(cart);

        mockMvc.perform(get("/shopping-cart/user-shopping-cart")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cart.getId()))
                .andExpect(jsonPath("$.totalPrice").value("20.00"))
                .andExpect(jsonPath("$.quantity").value(2));
    }

    @Test
    void testDeleteShoppingCart() throws Exception {

        ShoppingCartEntity cart = new ShoppingCartEntity();
        cart.setUserId(user.getId());
        shoppingCartRepository.save(cart);

        mockMvc.perform(delete("/shopping-cart/delete")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertThat(shoppingCartRepository.findByUserId(user.getId())).isEmpty();
    }

}