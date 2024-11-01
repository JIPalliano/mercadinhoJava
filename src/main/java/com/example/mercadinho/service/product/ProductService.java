package com.example.mercadinho.service.product;

import com.example.mercadinho.controller.request.ProductRequest;
import com.example.mercadinho.controller.response.ProductResonse;
import com.example.mercadinho.domain.repository.ShoppingCartRepository;
import com.example.mercadinho.domain.repository.model.ProductEntity;
import com.example.mercadinho.domain.repository.ProductRepository;
import com.example.mercadinho.domain.repository.model.UserEntity;
import com.example.mercadinho.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
                .build());
    }

    @Override
    public ProductEntity updateProduct(String idProduct, ProductRequest request){
        return productRepository.findById(idProduct).map(product -> ProductEntity.builder()
                .id(product.id())
                .name(request.name())
                .price(request.price())
                .build()).orElseThrow();
    }

    @Override
    public void deleteProductShoppingCart(String idProduct){
//        var userid = Optional.ofNullable(UserService.getCurrentUser()).map(UserEntity::getId).orElseThrow(RuntimeException::new);
//        final var shoppingCart = shoppingCartRepository.findByUserId(userid);
//        shoppingCart.map(cart -> {
//            final var product = this.repository.findById(idProduct).orElseThrow();
//            cart.products().remove(product);
//            this.shoppingCartRepository.save(cart);
//            return cart;
//        }).orElseThrow(RuntimeException::new);

        shoppingCartRepository.save(shoppingCartRepository.findByUserId(Optional.ofNullable(UserService.getCurrentUser())
                .map(UserEntity::getId).orElseThrow()).map(cart ->{
            productRepository.findById(idProduct).ifPresent(cart.products()::remove);
            return cart;
        }).orElseThrow(RuntimeException::new));
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
