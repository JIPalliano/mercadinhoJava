package com.example.mercadinho.service.product;

import com.example.mercadinho.controller.request.ProductRequest;
import com.example.mercadinho.domain.repository.ProductRepository;
import com.example.mercadinho.domain.repository.model.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductService implements ProductFacade{

    private final ProductRepository productRepository;

    @Override
    public Mono<ProductEntity> createProduct(ProductRequest request){
        return productRepository.save(ProductEntity.builder()
                .name(request.name())
                .price(request.price())
                .quantity(request.quantity())
                .build()).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"))
        );
    }

    @Override
    public Mono<ProductEntity> updateProduct(String idProduct, ProductRequest request){
        return productRepository.findById(idProduct).flatMap(product -> productRepository.save(ProductEntity.builder()
                .id(product.id())
                .name(request.name())
                .price(request.price())
                .quantity(request.quantity())
                .build()));
    }

    @Override
    public Mono<Void> deleteProduct(String idProduct){
        return productRepository.deleteById(idProduct);
    }

    @Override
    public Mono<ProductEntity> findById(ProductEntity id){
        return productRepository.findById(id.id());
    }

    @Override
    public Flux<ProductEntity> findAll(){
        return productRepository.findAll();
    }

}
