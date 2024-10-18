package com.example.mercadinho.service;

import com.example.mercadinho.repository.ProductRepository;
import com.example.mercadinho.repository.ShoppingCartRepository;
import com.example.mercadinho.repository.model.ProductEntity;
import com.example.mercadinho.repository.model.ShoppingCartEntity;
import com.example.mercadinho.service.cookies.CookieService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
public class ShoppingCartService implements ShoppingCartFacade {

    final ShoppingCartRepository repository;
    final ProductRepository productRepository;
    final HttpServletRequest request;
    CookieService cookie;

    @Override
    public ShoppingCartEntity createShoppingCart(String id, ShoppingCartEntity request) {
        LocalDateTime dateLocal = LocalDateTime.now();
        DateTimeFormatter dt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        var object = ShoppingCartEntity.builder().id(request.id()).products(request.products()).date(dt.format(dateLocal)).build();
        productRepository.findById(id).ifPresent(shoppingCart -> object.products().add(shoppingCart));
        return repository.save(object);
    }

    @Override
    public ShoppingCartEntity findShoppingCartAdd(String idProduct, String idShoppingCart, HttpServletResponse response, ProductEntity productResponse) {
        var object = repository.findById(idShoppingCart).orElse(null);
        productRepository.findById(idProduct).ifPresent(shoppingCart -> object.products().add(shoppingCart));
        cookie.createCookie(response ,"Carrinho-salvado", object.id(), 3600);
        System.out.println(cookie.readCookie(request, "Carrinho-salvado"));
        return object;
    }

    @Override
    public List<ShoppingCartEntity> findAll() {
        return this.repository.findAll();
    }

    @Override
    public ShoppingCartEntity findShoppingCartCookie(){
        return repository.findById(cookie.readCookie(request, "Carrinho-salvado")).orElse(null);
    }

}
