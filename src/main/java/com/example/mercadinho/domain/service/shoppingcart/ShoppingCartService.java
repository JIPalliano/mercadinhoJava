package com.example.mercadinho.domain.service.shoppingcart;

import com.example.mercadinho.controller.response.ShoppingCartResponse;
import com.example.mercadinho.infrastructure.repository.ProductRepository;
import com.example.mercadinho.infrastructure.repository.ShoppingCartRepository;
import com.example.mercadinho.infrastructure.repository.model.Product;
import com.example.mercadinho.infrastructure.repository.model.entity.ShoppingCartEntity;
import com.example.mercadinho.domain.service.contractuser.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
                .flatMap(tuple -> shoppingCartRepository.save(ShoppingCartEntity.builder()
                        .products(List.of(Product.builder()
                                .id(idProduct)
                                .name(tuple.getT1().name())
                                .price(tuple.getT1().price())
                                .quantity(quantity > 0 ? quantity : tuple.getT1().quantity())
                                .build()))
                        .userId(tuple.getT2().getId())
                        .date(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(LocalDateTime.now()))
                        .build()));
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

    }

    private Mono<Void> updateProductQuantity(List<Product> products, String idProduct, Integer quantity) {
        return Flux.fromIterable(products) // transforma em uma lista
                .filter(product -> product.getId().equals(idProduct)) // filtra a lista e encontra o produto
                .map(existingProduct -> {
                    int newQuantity = existingProduct.getQuantity() + quantity;
                    if (newQuantity > 0) {
                        existingProduct.setQuantity(newQuantity);
                    } else {
                        products.remove(existingProduct);
                    }
                    return existingProduct;
                })
                .switchIfEmpty(
                        Mono.defer(() -> {
                            if (quantity > 0) {
                                return findProduct(idProduct, quantity)
                                        .doOnNext(products::add)
                                        .then(Mono.empty());
                            } else {
                                return Mono.error(new IllegalArgumentException("Product can't be added with zero or negative quantity!"));
                            }
                        }))
                .then();

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
