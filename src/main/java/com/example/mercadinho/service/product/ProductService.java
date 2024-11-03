package com.example.mercadinho.service.product;

import com.example.mercadinho.controller.request.ProductRequest;
import com.example.mercadinho.domain.repository.ProductRepository;
import com.example.mercadinho.domain.repository.ShoppingCartRepository;
import com.example.mercadinho.domain.repository.model.ProductEntity;
import com.example.mercadinho.domain.repository.model.UserEntity;
import com.example.mercadinho.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductFacade{

    private final ProductRepository productRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    @Override
    public ProductEntity createProduct(ProductRequest request){
        return productRepository.save(ProductEntity.builder()
                .name(request.name())
                .price(request.price())
                .quantity(request.quantity())
                .build());
    }

    @Override
    public ProductEntity updateProduct(String idProduct, ProductRequest request){
        return productRepository.findById(idProduct).map(product -> ProductEntity.builder()
                .id(product.id())
                .name(request.name())
                .price(request.price())
                .quantity(request.quantity())
                .build()).orElseThrow();
    }

    @Override
    public void deleteProductShoppingCart(String idProduct){
        shoppingCartRepository.save(shoppingCartRepository.findByUserId(Optional.ofNullable(UserService.getCurrentUser())
                .map(UserEntity::getId).orElseThrow()).map(cart ->{
            productRepository.findById(idProduct).ifPresent(cart.products()::remove);
            return cart;
        }).orElseThrow(RuntimeException::new));
    }

    @Override
    public void removeQuantityProductShoppingCart(ProductRequest request) {


        shoppingCartRepository.findByUserId(Optional.ofNullable(UserService.getCurrentUser()).map(UserEntity::getId).orElseThrow()).ifPresent(cart ->{
            var cartProducts = cart.products().stream()
                    .filter(product -> product.id().equals(request.id()))
                    .toList();

            if (CollectionUtils.isEmpty(cartProducts) || cartProducts.size() < request.quantity()) throw new RuntimeException();
        });

        // verificar a quantidade da lista de produtos dentro do carrinho com id requisitado

        // subtrair o valor da lista pela quantidade requisitada
        //tenho que alterar essa configurações


        shoppingCartRepository.findByUserId(Optional.ofNullable(UserService.getCurrentUser())
                .map(UserEntity::getId)
                .orElseThrow()).ifPresent(cart -> productRepository.findById(request.id()).ifPresent(product -> {
                    Long newQuantity = product.quantity() - request.quantity();
                    if (newQuantity > 0) {
                        Optional<ProductEntity> findProduct = cart.products().stream().filter(p->p.id().equals(request.id())).findFirst();
                        ProductEntity newProduct = findProduct.orElse(null);
                        int index = cart.products().indexOf(newProduct);
                        cart.products().set(index, ProductEntity.builder().id(product.id()).name(request.name()).quantity(request.quantity()).build());
                        shoppingCartRepository.save(cart);
                    } else {
                        shoppingCartRepository.save(shoppingCartRepository.findByUserId(Optional.ofNullable(UserService.getCurrentUser())
                                .map(UserEntity::getId).orElseThrow()).map(newCart->{
                            productRepository.findById(product.id()).ifPresent(newCart.products()::remove);
                            return newCart;
                        }).orElseThrow(RuntimeException::new));
                    }
                }));
//        shoppingCartRepository.save(shoppingCartRepository.findByUserId(Optional.ofNullable(UserService.getCurrentUser())
//                .map(UserEntity::getId).orElseThrow()).map(cart ->{
//            productRepository.findById(idProduct).ifPresent(cart.products()::remove);
//            return cart;
//        }).orElseThrow(RuntimeException::new));
    }

    @Override
    public void deleteProduct(String idProduct){
        productRepository.deleteById(idProduct);
    }

    @Override
    public ProductEntity findById(ProductEntity id){
        return productRepository.findById(id.id()).orElse(null);
    }

    @Override
    public List<ProductEntity> findAll(){
        return productRepository.findAll();
    }

}
