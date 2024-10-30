package com.example.mercadinho.service.shoppingcart;

import com.example.mercadinho.controller.request.ShoppingCartRequest;
import com.example.mercadinho.domain.repository.ProductRepository;
import com.example.mercadinho.domain.repository.ShoppingCartRepository;
import com.example.mercadinho.domain.repository.model.ProductEntity;
import com.example.mercadinho.domain.repository.model.ShoppingCartEntity;
import com.example.mercadinho.domain.repository.model.UserEntity;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ShoppingCartService implements ShoppingCartFacade {

    private final ShoppingCartRepository repository;
    private final ProductRepository productRepository;
    //final HttpServletRequest request;
    //CookieService cookie;

    @Override
    public ShoppingCartEntity createShoppingCart(String idProduct, ShoppingCartRequest request) {
        LocalDateTime dateLocal = LocalDateTime.now();
        DateTimeFormatter dt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ShoppingCartEntity object = ShoppingCartEntity.builder()
                .products(new ArrayList<>())
                .userId(((UserEntity)principal).getId())
                .date(dt.format(dateLocal))
                .build();
        productRepository.findById(idProduct).ifPresent(obj -> object.products().add(obj));
        return repository.save(object);
    }

    @Override
    public ShoppingCartEntity findShoppingCartAdd(String idProduct, String idShoppingCart, HttpServletResponse response, ProductEntity productResponse) {
        ShoppingCartEntity object = repository.findById(idShoppingCart).orElse(null);
        productRepository.findById(idProduct).ifPresent(shoppingCart -> Objects.requireNonNull(object).products().add(shoppingCart));
       //cookie.createCookie(response ,"Carrinho-salvado", object.id(), 3600);
        return repository.save(Objects.requireNonNull(object));
    }

    @Override
    public ShoppingCartEntity findShoppingCartByUser(){
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return repository.findByUserId(user.getId()).orElseThrow();
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
