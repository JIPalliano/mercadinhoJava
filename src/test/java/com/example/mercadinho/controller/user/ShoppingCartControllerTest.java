package com.example.mercadinho.controller.user;

import com.example.mercadinho.domain.repository.ProductRepository;
import com.example.mercadinho.domain.repository.ShoppingCartRepository;
import com.example.mercadinho.domain.repository.model.Product;
import com.example.mercadinho.domain.repository.model.entity.ProductEntity;
import com.example.mercadinho.domain.repository.model.entity.ShoppingCartEntity;
import com.example.mercadinho.domain.repository.model.entity.UserEntity;
import com.example.mercadinho.service.product.ProductService;
import com.example.mercadinho.service.shoppingcart.ShoppingCartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ShoppingCartEntity shoppingCartEntity;

    @Mock
    private UserEntity user;

    @InjectMocks
    private ShoppingCartService shoppingCartService;

    @BeforeEach
    void setupUserService() {
        shoppingCartRepository.deleteAll();

        user = UserEntity.builder()
                .id("user2")
                .username("user2")
                .password("password2")
                .role("ADMIN")
                .build();

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user, user.getRole(), List.of());

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
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

        mockMvc.perform(get("/v1/shopping-cart/user-shopping-cart")
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

        mockMvc.perform(delete("/v1/shopping-cart/delete")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertThat(shoppingCartRepository.findByUserId(user.getId())).isEmpty();
    }

}