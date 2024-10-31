package com.example.mercadinho.service.shoppingcart;

import com.example.mercadinho.controller.request.ShoppingCartRequest;
import com.example.mercadinho.domain.repository.ProductRepository;
import com.example.mercadinho.domain.repository.ShoppingCartRepository;
import com.example.mercadinho.domain.repository.model.ShoppingCartEntity;
import com.example.mercadinho.domain.repository.model.UserEntity;
import com.example.mercadinho.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ShoppingCartService implements ShoppingCartFacade{

    private final ShoppingCartRepository repository;
    private final ProductRepository productRepository;

    @Override
    public ShoppingCartEntity createShoppingCart(String idProduct, ShoppingCartRequest request) {
        return repository.save(ShoppingCartEntity.builder()
                .products(Stream.of(idProduct).map(productRepository::findById)
                        .flatMap(Optional::stream).collect(Collectors.toList()))
                .userId(Optional.ofNullable(UserService.getCurrentUser()).map(UserEntity::getId).orElseThrow())
                .date(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(LocalDateTime.now()))
                .build());
    }

    @Override
    public ShoppingCartEntity findShoppingCartAdd(String idProduct) {
        return repository.save(repository.findByUserId(Optional.ofNullable(UserService.getCurrentUser())
                .map(UserEntity::getId).orElseThrow()).map(cart ->{
            productRepository.findById(idProduct).ifPresent(cart.products()::add);
            return cart;
        }).orElseThrow());
    }

    @Override
    public ShoppingCartEntity findShoppingCartByUser(){
        return repository.findByUserId(Objects.requireNonNull(UserService.getCurrentUser()).getId()).orElseThrow();
    }

    @Override
    public List<ShoppingCartEntity> findAll() {
        return this.repository.findAll();
    }

    @Override
    public ShoppingCartEntity findShoppingCartCookie(){
        return null;//repository.findById(cookie.readCookie(request, "Carrinho-salvado")).orElse(null);
    }

}
