package com.example.mercadinho.domain.service.shoppingcart;

import com.example.mercadinho.controller.response.ShoppingCartResponse;
import com.example.mercadinho.infrastructure.repository.ProductRepository;
import com.example.mercadinho.infrastructure.repository.ShoppingCartRepository;
import com.example.mercadinho.infrastructure.repository.UserRepository;
import com.example.mercadinho.infrastructure.repository.model.Product;
import com.example.mercadinho.infrastructure.repository.model.entity.ProductEntity;
import com.example.mercadinho.infrastructure.repository.model.entity.ShoppingCartEntity;
import com.example.mercadinho.domain.service.contractuser.UserService;
import com.example.mercadinho.infrastructure.repository.model.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShoppingCartService implements ShoppingCartFacade {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;
    private final UserService userService;
    //private final ShippingService shippingService;

    @Override
    public Mono<ShoppingCartEntity> create(String idProduct, Integer quantity) {
        return productRepository.findById(idProduct)
                .zipWith(userService.getCurrentUser()) // Esse maldito zipWith junta 2 fluxos(Product e user)
                .flatMap(tuple -> {
                    ProductEntity product = tuple.getT1();
                    UserEntity user = tuple.getT2();

                    ShoppingCartEntity shoppingCart = ShoppingCartEntity.builder()
                            .products(List.of(Product.builder()
                                    .id(idProduct)
                                    .name(product.name())
                                    .price(product.price())
                                    .quantity(quantity > 0 ? quantity : product.quantity())
                                    .build()))
                            .userId(user.getId())
                            .date(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(LocalDateTime.now()))
                            .build();

                    return shoppingCartRepository.save(shoppingCart);
                });
    }

    @Override
    public Mono<ShoppingCartEntity> addProduct(String idProduct, Integer quantity) {
        return userService.getCurrentUser()
                .flatMap(currentUser ->
                        shoppingCartRepository.findByUserId(currentUser.getId())
                                .flatMap(shoppingCartEntity ->
                                        updateProductQuantity(shoppingCartEntity.getProducts(), idProduct, quantity)
                                                .then(shoppingCartRepository.save(shoppingCartEntity))
                                )
                                .switchIfEmpty(create(idProduct, quantity))
                );
//        return userService.getCurrentUser()
//                .flatMap(currentUser ->
//                        shoppingCartRepository.findByUserId(currentUser.getId())
//                                .flatMap(shoppingCartEntity -> {
//                                    updateProductQuantity(
//                                            shoppingCartEntity.getProducts(),
//                                            idProduct,
//                                            quantity
//                                    );
//                                    return shoppingCartRepository.save(shoppingCartEntity);
//                                })
//                                .switchIfEmpty(create(idProduct, quantity))
//                );

    }

    private Mono<Void> updateProductQuantity(List<Product> products, String idProduct, Integer quantity) {
        return Mono.defer(() -> {
            // Verifica se o produto já existe na lista
            return products.stream()
                    .filter(product -> product.getId().equals(idProduct))
                    .findFirst()
                    .map(existingProduct -> {
                        // Atualiza a quantidade do produto existente
                        int newQuantity = existingProduct.getQuantity() + quantity;
                        if (newQuantity > 0) {
                            existingProduct.setQuantity(newQuantity);
                        } else {
                            products.remove(existingProduct); // Remove o produto se a quantidade for <= 0
                        }
                        return Mono.<Void>empty(); // Retorna um Mono<Void>
                    })
                    .orElseGet(() -> {
                        // Adiciona um novo produto se ele não existir
                        if (quantity > 0) {
                            return findProduct(idProduct, quantity)
                                    .doOnNext(products::add)
                                    .then(Mono.empty()); // Garante que retorna Mono<Void>
                        } else {
                            return Mono.error(new RuntimeException("Product can't be added with zero or negative quantity!"));
                        }
                    });
        });
    }

    private Mono<Product> findProduct( String idProduct, Integer quantity) {
        return productRepository.findById(idProduct)
                .map(productEntity -> Product.builder()
                .id(idProduct)
                .name(productEntity.name())
                .price(productEntity.price())
                .quantity(quantity)
                .build());
    }

    @Override
    public Mono<Void> delete() {
        return userService.getCurrentUser()
                .flatMap(currentUser ->
                        shoppingCartRepository.findByUserId(currentUser.getId())
                )
                .flatMap(shoppingCart ->
                        shoppingCartRepository.deleteById(shoppingCart.getId())
                );
    }

    @Override
    public Mono<ShoppingCartResponse> find() {
        return userService.getCurrentUser()
                .flatMap(currentUser ->
                        shoppingCartRepository.findByUserId(currentUser.getId())
                                .flatMap(shoppingCartEntity -> {

                                    int totalQuantity = shoppingCartEntity.getProducts()
                                            .stream()
                                            .mapToInt(Product::getQuantity)
                                            .sum();


                                    BigDecimal totalPrice = shoppingCartEntity.getProducts()
                                            .stream()
                                            .map(product -> product.getPrice().multiply(BigDecimal.valueOf(product.getQuantity())))
                                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                                    return Mono.just(ShoppingCartResponse.builder()
                                            .id(shoppingCartEntity.getId())
                                            .products(shoppingCartEntity.getProducts())
                                            .totalPrice(totalPrice)
                                            .quantity(totalQuantity)
                                            .userId(shoppingCartEntity.getUserId())
                                            .date(shoppingCartEntity.getDate())
                                            .build());
                                })
                );
    }

    @Override
    public Flux<ShoppingCartEntity> findAll() {
        return shoppingCartRepository.findAll()
                .switchIfEmpty(Flux.error(new RuntimeException("ShoppingCart not found!")));
    }

}
