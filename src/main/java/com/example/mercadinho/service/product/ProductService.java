package com.example.mercadinho.service.product;

import com.example.mercadinho.controller.request.ProductRequest;
import com.example.mercadinho.domain.repository.ProductRepository;
import com.example.mercadinho.domain.repository.ShoppingCartRepository;
import com.example.mercadinho.domain.repository.model.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductFacade{

    private final ProductRepository productRepository;

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
        return productRepository.findById(idProduct).map(product -> productRepository.save(ProductEntity.builder()
                .id(product.id())
                .name(request.name())
                .price(request.price())
                .quantity(request.quantity())
                .build())).orElseThrow();
    }

    @Override
    public void deleteProduct(String idProduct){
        productRepository.deleteById(idProduct);
    }

    @Override
    public ProductEntity findById(ProductEntity id){
        return productRepository.findById(id.id()).orElseThrow();
    }

    @Override
    public List<ProductEntity> findAll(){
        return productRepository.findAll();
    }

}
