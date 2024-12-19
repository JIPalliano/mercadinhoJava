package com.example.mercadinho.service.shoppingcart;

import com.example.mercadinho.controller.response.ShoppingCartResponse;
import com.example.mercadinho.domain.repository.ProductRepository;
import com.example.mercadinho.domain.repository.ShoppingCartRepository;
import com.example.mercadinho.domain.repository.model.Product;
import com.example.mercadinho.domain.repository.model.entity.ProductEntity;
import com.example.mercadinho.domain.repository.model.entity.ShoppingCartEntity;
import com.example.mercadinho.domain.repository.model.entity.UserEntity;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShoppingCartServiceTest {


    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ShoppingCartEntity shoppingCartEntity;


    private UserEntity user;

    @InjectMocks
    private ShoppingCartService shoppingCartService;

    //cria um contexto apenas para os testes
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
    @DisplayName("When shopping cart exists and product exists should update quantity")
    void whenShoppingCartExistsAndProductExists_shouldUpdateQuantity() {
        Product productA = Product.builder()
                .id("product1")
                .name("Product 1")
                .price(BigDecimal.valueOf(10.0))
                .quantity(1)
                .build();

        shoppingCartEntity = ShoppingCartEntity.builder()
                .id("shoppingCart1")
                .products(List.of(productA))
                .userId(user.getId())
                .build();

        when(shoppingCartRepository.save(shoppingCartEntity))
                .thenReturn(shoppingCartEntity);

        when(shoppingCartRepository.findByUserId(user.getId()))
            .thenReturn(Optional.of(shoppingCartEntity));


        ShoppingCartEntity result = shoppingCartService.addProduct(productA.getId(), 1);

        Product updatedProduct = result.getProducts().get(0);
        assertNotNull(result);
        assertEquals(1, result.getProducts().size());
        assertEquals(productA.getId(), updatedProduct.getId());
        assertEquals("Product 1", updatedProduct.getName());
        assertEquals(2, updatedProduct.getQuantity());
        verify(shoppingCartRepository).save(shoppingCartEntity);

    }

    @Test
    @DisplayName("Should create new shopping cart if not exist")
    void shouldCreateNewShoppingCartIfNotExist() {

        ProductEntity product = ProductEntity.builder()
                .id("product1")
                .name("Product 1")
                .price(BigDecimal.valueOf(10.0))
                .quantity(1)
                .build();

        when(shoppingCartRepository.findByUserId(user.getId())).thenReturn(Optional.empty());
        when(productRepository.findById(product.id())).thenReturn(Optional.of(product));
        when(shoppingCartRepository.save(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));


        ShoppingCartEntity result = shoppingCartService.addProduct(product.id(), 1);

        assertNotNull(result);
        assertEquals(1, result.getProducts().size());
        assertEquals(product.id(), result.getProducts().get(0).getId());
        assertEquals(product.quantity(), result.getProducts().get(0).getQuantity());
        Mockito.verify(shoppingCartRepository).save(Mockito.any());
    }

    @Test
    @DisplayName("When containing shopping cart and product, but updating with quantity 0 must remove the product")
    void whenShoppingCartExistsAndProductExists_shouldRemoveProduct() {
        Product productA = Product.builder()
                .id("product1")
                .name("Product 1")
                .price(BigDecimal.valueOf(10.0))
                .quantity(1)
                .build();

        shoppingCartEntity = ShoppingCartEntity.builder()
                .id("shoppingCart1")
                .products(new ArrayList<>(List.of(productA)))
                .userId(user.getId())
                .build();

        when(shoppingCartRepository.findByUserId(user.getId()))
                .thenReturn(Optional.of(shoppingCartEntity));
        when(shoppingCartRepository.save(shoppingCartEntity))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ShoppingCartEntity result = shoppingCartService.addProduct(productA.getId(), -1);

        assertTrue(shoppingCartEntity.getProducts().isEmpty(), "Product list should be empty after removal");
        assertEquals(0, result.getProducts().size());

        verify(shoppingCartRepository).save(shoppingCartEntity);

        verify(productRepository, never()).findById(Mockito.any());
    }

    @Test
    @DisplayName("When containing shopping cart and product, but updating get quantity 0")
    void whenShoppingCartExistsAndProductExists_ShouldAddOneQuantity (){
        ProductEntity productB = ProductEntity.builder()
                .id("product1")
                .name("Product 1")
                .price(BigDecimal.valueOf(10.0))
                .quantity(1)
                .build();
        int quantity = 0;
        when(shoppingCartRepository.findByUserId(user.getId())).thenReturn(Optional.empty());
        when(productRepository.findById(productB.id())).thenReturn(Optional.of(productB));
        when(shoppingCartRepository.save(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));

        ShoppingCartEntity result = shoppingCartService.addProduct(productB.id(), quantity);

        assertTrue(shoppingCartEntity.getProducts().isEmpty(), "Product list should be empty after removal");
        assertEquals(1, result.getProducts().stream().mapToInt(Product::getQuantity).sum());
        assertEquals(0, quantity);

        Mockito.verify(shoppingCartRepository).save(Mockito.any());
    }

    @Test
    @DisplayName("When containing shopping cart and product, but product no content in list, should add product in list.")
    void whenShoppingCartExistsAndProductExists_shouldAddProduct() {
        ProductEntity productA = ProductEntity.builder()
                .id("product1")
                .name("Product 1")
                .price(BigDecimal.valueOf(10.0))
                .quantity(1)
                .build();

        Product productB = Product.builder()
                .id("product2")
                .name("Product 2")
                .price(BigDecimal.valueOf(15.0))
                .quantity(1)
                .build();

        shoppingCartEntity = ShoppingCartEntity.builder()
                .id("shoppingCart1")
                .products(new ArrayList<>(List.of(productB)))
                .userId(user.getId())
                .build();

        when(shoppingCartRepository.findByUserId(user.getId()))
                .thenReturn(Optional.of(shoppingCartEntity));
        when(productRepository.findById(productA.id())).thenReturn(Optional.of(productA));
        when(shoppingCartRepository.save(shoppingCartEntity))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ShoppingCartEntity cart = shoppingCartService.addProduct(productA.id(), 2);

        assertEquals(2, cart.getProducts().size());
        assertNotNull(cart, "O método nunca deve retornar null.");

        verify(shoppingCartRepository).save(shoppingCartEntity);
    }

    @Test
    @DisplayName("Should delete shopping cart.")
    void shouldDeleteShoppingCart() {
        Product productA = Product.builder()
                .id("product1")
                .name("Product 1")
                .price(BigDecimal.valueOf(10.0))
                .quantity(1)
                .build();

        Product productB = Product.builder()
                .id("product2")
                .name("Product 2")
                .price(BigDecimal.valueOf(15.0))
                .quantity(1)
                .build();

        shoppingCartEntity = ShoppingCartEntity.builder()
                .id("shoppingCart1")
                .products(new ArrayList<>(List.of(productB, productA)))
                .userId(user.getId())
                .build();

        when(shoppingCartRepository.findByUserId(user.getId()))
                .thenReturn(Optional.of(shoppingCartEntity));

        shoppingCartService.delete();

        verify(shoppingCartRepository).findByUserId(user.getId());
        verify(shoppingCartRepository).deleteById(shoppingCartEntity.getId());
    }

    @Test
    @DisplayName("Should find shopping cart the user.")
    void shouldFindShoppingCartInUser() {

        Product productA = Product.builder()
                .id("product1")
                .name("Product 1")
                .price(BigDecimal.valueOf(10.0))
                .quantity(1)
                .build();

        Product productB = Product.builder()
                .id("product2")
                .name("Product 2")
                .price(BigDecimal.valueOf(15.0))
                .quantity(1)
                .build();

        shoppingCartEntity = ShoppingCartEntity.builder()
                .id("shoppingCart1")
                .products(new ArrayList<>(List.of(productB, productA)))
                .userId(user.getId())
                .date(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(LocalDateTime.now()))
                .build();

        when(shoppingCartRepository.findByUserId(user.getId()))
                .thenReturn(Optional.of(shoppingCartEntity));

        ShoppingCartResponse shoppingCart = shoppingCartService.find();

        assertNotNull(shoppingCart, "O método nunca deve retornar null.");
        assertEquals(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(LocalDateTime.now()), shoppingCart.date());
        assertEquals(2,shoppingCart.products().size());
        assertEquals(shoppingCart.userId(), user.getId());
        assertEquals(2, shoppingCart.quantity());
        assertEquals(shoppingCart.totalPrice(), BigDecimal.valueOf(25.0));
    }

    @Nested
    class ExceptionsMessage{

        @Test
        @DisplayName("Should throw exception when product not found in the repository")
        void shouldThrowExceptionWhenProductNotFound(){
            shoppingCartEntity = ShoppingCartEntity.builder()
                    .id("shoppingCart1")
                    .products(List.of())
                    .userId(user.getId())
                    .build();
            String idProduct = "Product1";

            when(productRepository.findById(idProduct))
                    .thenReturn(Optional.empty());

            RuntimeException exception = assertThrows(RuntimeException.class,
                    () -> shoppingCartService.addProduct(idProduct, 1));

            assertEquals("Product not found!", exception.getMessage());

            verify(shoppingCartRepository).findByUserId(user.getId());
            verify(productRepository).findById(idProduct);
        }

        @Test
        @DisplayName("")
        void whenShoppingCartExistsAndProductExists_shouldAddProductNegative() {

            ProductEntity productA = ProductEntity.builder()
                    .id("product3")
                    .name("Product 3")
                    .price(BigDecimal.valueOf(10.0))
                    .quantity(1)
                    .build();

            Product productB = Product.builder()
                    .id("product2")
                    .name("Product 2")
                    .price(BigDecimal.valueOf(15.0))
                    .quantity(1)
                    .build();

            shoppingCartEntity = ShoppingCartEntity.builder()
                    .id("shoppingCart1")
                    .products(new ArrayList<>(List.of(productB)))
                    .userId(user.getId())
                    .build();
            int quantity = 0;

            when(shoppingCartRepository.findByUserId(user.getId()))
                    .thenReturn(Optional.of(shoppingCartEntity));
            RuntimeException exception = assertThrows(
                    RuntimeException.class,
                    () -> shoppingCartService.addProduct(productA.id(), 0)
            );

            assertEquals("Product cant be added!", exception.getMessage());
            assertEquals(0, quantity);

        }

        @Test
        @DisplayName("When containing shopping cart and product,but quantity the product 0, should execption.")
        void shouldThrowExceptionWhenAddingNegativeQuantity() {
            ProductEntity productA = ProductEntity.builder()
                    .id("product1")
                    .name("Product 1")
                    .price(BigDecimal.valueOf(10.0))
                    .quantity(1)
                    .build();

            Product productB = Product.builder()
                    .id("product2")
                    .name("Product 2")
                    .price(BigDecimal.valueOf(15.0))
                    .quantity(1)
                    .build();

            shoppingCartEntity = ShoppingCartEntity.builder()
                    .id("shoppingCart1")
                    .products(new ArrayList<>(List.of(productB)))
                    .userId(user.getId())
                    .build();

            when(shoppingCartRepository.findByUserId(user.getId()))
                    .thenReturn(Optional.of(shoppingCartEntity));

            RuntimeException exception = assertThrows(
                    RuntimeException.class,
                    () -> shoppingCartService.addProduct(productA.id(), -2)
            );

            assertEquals("Product cant be added!", exception.getMessage());
            assertEquals(1, shoppingCartEntity.getProducts().size());
        }

        @Test
        @DisplayName("Should to throw exception not find shopping cart.")
        void shouldExceptionNoFindShoppingCart() {

            when(shoppingCartRepository.findByUserId(user.getId()))
                    .thenReturn(Optional.empty());

            RuntimeException exception = assertThrows(
                    RuntimeException.class,()->shoppingCartService.delete()
            );

            assertEquals("ShoppingCart not found!", exception.getMessage());

        }

        @Test
        @DisplayName("")
        void shouldThrowExceptionWhenAllShoppingCartNotFound(){

            when(shoppingCartRepository.findAll())
                    .thenReturn(List.of());

            RuntimeException exception = assertThrows(RuntimeException.class,
                    () -> shoppingCartService.findAll());

            assertEquals("ShoppingCart not found!", exception.getMessage());

        }

        @Test
        @DisplayName("")
        void shouldThrowExceptionWhenAllShoppingCartNotFoundListEmpty(){
            ShoppingCartEntity cart = ShoppingCartEntity.builder()
                    .id("shoppingCart1")
                    .userId(user.getId())
                    .products(new ArrayList<>(List.of()))
                    .build();

            when(shoppingCartRepository.findAll())
                    .thenReturn(List.of(cart));

            List<ShoppingCartEntity> result = shoppingCartService.findAll();

            assertFalse(result.isEmpty(), "A lista não deveria estar vazia.");

        }
    }
}