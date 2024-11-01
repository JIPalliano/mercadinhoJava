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

    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;

    @Override
    public ShoppingCartEntity createShoppingCart(String idProduct, ShoppingCartRequest request) {
        return shoppingCartRepository.save(ShoppingCartEntity.builder()
                .products(Stream.of(idProduct).map(productRepository::findById)
                        .flatMap(Optional::stream).collect(Collectors.toList()))
                .userId(Optional.ofNullable(UserService.getCurrentUser()).map(UserEntity::getId).orElseThrow())
                .date(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(LocalDateTime.now()))
                .build());
    }

    @Override
    public ShoppingCartEntity findShoppingCartAdd(String idProduct) {
        return shoppingCartRepository.save(shoppingCartRepository.findByUserId(Optional.ofNullable(UserService.getCurrentUser())
                .map(UserEntity::getId).orElseThrow()).map(cart ->{
            productRepository.findById(idProduct).ifPresent(cart.products()::add);
            return cart;
        }).orElseThrow());
    }

    @Override
    public void deleteShoppingCart(String idShoppingCart){
        shoppingCartRepository.deleteById(idShoppingCart);
    }

    @Override
    public ShoppingCartEntity findShoppingCartByUser(){
        return shoppingCartRepository.findByUserId(Objects.requireNonNull(UserService.getCurrentUser()).getId()).orElseThrow();
    }

    @Override
    public List<ShoppingCartEntity> findAll() {
        return this.shoppingCartRepository.findAll();
    }

    @Override
    public ShoppingCartEntity findShoppingCartCookie(){
        return null;//repository.findById(cookie.readCookie(request, "Carrinho-salvado")).orElse(null);
    }

}
