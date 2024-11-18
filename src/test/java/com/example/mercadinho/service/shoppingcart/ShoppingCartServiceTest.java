package com.example.mercadinho.service.shoppingcart;

import com.example.mercadinho.domain.repository.ProductRepository;
import com.example.mercadinho.domain.repository.ShoppingCartRepository;
import com.example.mercadinho.domain.repository.model.Product;
import com.example.mercadinho.domain.repository.model.entity.ProductEntity;
import com.example.mercadinho.domain.repository.model.entity.ShoppingCartEntity;
import com.example.mercadinho.domain.repository.model.entity.UserEntity;
import com.example.mercadinho.service.user.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


class ShoppingCartServiceTest {


    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private ShoppingCartService shoppingCartService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        UserEntity currentUser = UserEntity.builder()
                .id("user1")
                .username("user1")
                .password("password1")
                .role("USER")
                .build();

        // Criar uma autenticação fictícia
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(currentUser, null, List.of());

        // Configurar o SecurityContextHolder com a autenticação fictícia
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    @DisplayName("Testing method")
    void addProduct() {
        String idProduct = "product1";
        Integer quantity = 2;
        String userId = "user1";

        Product product = Product.builder()
                .id(idProduct)
                .name("Product 1")
                .price(BigDecimal.valueOf(10.0))
                .quantity(quantity)
                .build();

        ShoppingCartEntity existingCart = ShoppingCartEntity.builder()
                .products(List.of(product))
                .userId(userId)
                .build();

            when(shoppingCartRepository.findByUserId(userId))
                    .thenReturn(Optional.of(existingCart));
            when(productRepository.findById(idProduct))
                    .thenReturn(Optional.of(ProductEntity.builder()
                            .id(idProduct)
                            .name("Product 1")
                            .price(BigDecimal.valueOf(10.0))
                            .quantity(quantity)
                            .build()));
            when(shoppingCartRepository.save(any(ShoppingCartEntity.class)))
                    .thenAnswer(invocation -> invocation.getArgument(0));// Retorna o carrinho salvo

        // Execute
        ShoppingCartEntity result = shoppingCartService.addProduct(idProduct, quantity);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getProducts().size());
        Product updatedProduct = result.getProducts().get(0);
        assertEquals(idProduct, updatedProduct.getId());
        assertEquals("Product 1", updatedProduct.getName());
        assertEquals(4, updatedProduct.getQuantity());
    }

    @Test
    void shouldCreateNewShoppingCartIfNotExist() {
        // Mock data
        String idProduct = "product2";
        Integer quantity = 0;
        String userId = "user1";

        Product product = Product.builder()
                .id(idProduct)
                .name("Product 2")
                .price(BigDecimal.valueOf(15.0))
                .quantity(quantity)
                .build();

            when(shoppingCartRepository.findByUserId(userId))
                    .thenReturn(Optional.empty());
            when(productRepository.findById(idProduct))
                    .thenReturn(Optional.of(ProductEntity.builder()
                            .id(idProduct)
                            .name("Product 2")
                            .price(BigDecimal.valueOf(15.0))
                            .quantity(quantity)
                            .build()));
            when(shoppingCartRepository.save(any(ShoppingCartEntity.class)))
                    .thenAnswer(invocation -> invocation.getArgument(0));


        // Execute
        ShoppingCartEntity result = shoppingCartService.addProduct(idProduct, quantity);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getProducts().size());
        Product addedProduct = result.getProducts().get(0);
        assertEquals(idProduct, addedProduct.getId());
        assertEquals("Product 2", addedProduct.getName());
        assertEquals(quantity, addedProduct.getQuantity());
    }
}