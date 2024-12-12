package com.example.mercadinho.service.shoppingcart;

import com.example.mercadinho.controller.response.ShoppingCartResponse;
import com.example.mercadinho.domain.repository.ProductRepository;
import com.example.mercadinho.domain.repository.ShoppingCartRepository;
import com.example.mercadinho.domain.repository.model.Product;
import com.example.mercadinho.domain.repository.model.entity.ShoppingCartEntity;
import com.example.mercadinho.service.shipping.ShippingService;
import com.example.mercadinho.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShoppingCartService implements ShoppingCartFacade {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;
    //private final ShippingService shippingService;

    @Override
    public Mono<ShoppingCartEntity> create(String idProduct, Integer quantity) {
        return productRepository.findById(idProduct)
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found!")))
                .flatMap(product -> {
                    ShoppingCartEntity shoppingCart = ShoppingCartEntity.builder()
                            .products(List.of(Product.builder()
                                    .id(idProduct)
                                    .name(product.name())
                                    .price(product.price())
                                    .quantity(quantity > 0 ? quantity : product.quantity())
                                    .build()))
                            .userId(UserService.getCurrentUser().getId())
                            .date(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(LocalDateTime.now()))
                            .build();

                    return shoppingCartRepository.save(shoppingCart);
                });
    }

    @Override
    public Mono<ShoppingCartEntity> addProduct(String idProduct, Integer quantity) {
        return shoppingCartRepository.findByUserId(UserService.getCurrentUser().getId())
                .flatMap(shoppingCartEntity -> {
                    updateProductQuantity(
                            shoppingCartEntity.map(ShoppingCartEntity::getProducts).orElseThrow(()->new RuntimeException("No products")),
                            idProduct,
                            quantity
                    );
                    return shoppingCartRepository.save(shoppingCartEntity.orElseThrow(() -> new RuntimeException("No products!")));
                })
                .switchIfEmpty(create(idProduct, quantity));

    }

    private void updateProductQuantity(List<Product> products, String idProduct, Integer quantity) {
        products.stream()
                .filter(product -> product.getId().equals(idProduct))
                .findFirst()
                .ifPresentOrElse(
                        existingProduct -> {
                            int newQuantity = existingProduct.getQuantity() + quantity;
                            if (newQuantity > 0) {
                                existingProduct.setQuantity(newQuantity);
                            } else {
                                products.remove(existingProduct);
                            }
                        },
                        () -> {
                            if (quantity > 0) {
                                products.add(findProduct(idProduct, quantity).switchIfEmpty(Mono.error(new RuntimeException("Product not found!"))).block());
                            }else{
                                throw new RuntimeException("Product cant be added!");
                            }
                        }
                );
    }

    private Mono<Product> findProduct( String idProduct, Integer quantity) {
        return productRepository.findById(idProduct)
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found!")))
                .map(productEntity -> Product.builder()
                .id(idProduct)
                .name(productEntity.name())
                .price(productEntity.price())
                .quantity(quantity)
                .build());
    }

    @Override
    public Mono<Void> delete() {
        return shoppingCartRepository.findByUserId(UserService.getCurrentUser().getId())
                .doOnNext(
                        shoppingCart -> shoppingCartRepository.deleteById(String.valueOf(shoppingCart.map(ShoppingCartEntity::getId)))
                ).then();
    }

    @Override
    public Mono<ShoppingCartResponse> find() {
        return shoppingCartRepository.findByUserId(UserService.getCurrentUser().getId())
                .switchIfEmpty(Mono.error(new RuntimeException("ShoppingCart not found!")))
                .map(shoppingCartEntity -> {
                    int totalQuantity = shoppingCartEntity.map(products ->
                            products.getProducts().stream()
                                    .mapToInt(Product::getQuantity)
                                    .sum()
                    ).orElse(0);

                    BigDecimal totalPrice = shoppingCartEntity.map(shoppingCart->shoppingCart.getProducts().stream()
                            .map(product -> product.getPrice().multiply(BigDecimal.valueOf(product.getQuantity())))
                            .reduce(BigDecimal.ZERO, BigDecimal::add)).orElse(BigDecimal.ZERO);

                    return ShoppingCartResponse.builder()
                            .id(shoppingCartEntity.map(ShoppingCartEntity::getId).orElseThrow())
                            .products(shoppingCartEntity.map(ShoppingCartEntity::getProducts).orElseThrow())
                            .totalPrice(totalPrice)
                            .quantity(totalQuantity)
                            .userId(shoppingCartEntity.map(ShoppingCartEntity::getUserId).orElseThrow())
                            .date(shoppingCartEntity.map(ShoppingCartEntity::getDate).orElseThrow())
                            .build();
                });
    }
    @Override
    public Flux<ShoppingCartEntity> findAll() {
        return shoppingCartRepository.findAll()
                .switchIfEmpty(Flux.error(new RuntimeException("ShoppingCart not found!")));
    }

}
