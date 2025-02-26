package com.example.mercadinho.domain.service.product;

import com.example.mercadinho.controller.request.ProductRequest;
import com.example.mercadinho.domain.config.KafkaStreamsBuilder;
import com.example.mercadinho.domain.service.contractuser.DTO.UserDTO;
import com.example.mercadinho.domain.service.contractuser.UserService;
import com.example.mercadinho.domain.service.product.DTO.ProductDTO;
import com.example.mercadinho.infrastructure.repository.ProductRepository;
import com.example.mercadinho.infrastructure.repository.model.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService implements ProductFacade{

    private final ProductRepository productRepository;

    @Override
    public Mono<ProductEntity> createProduct(ProductRequest request){
        return productRepository.save(ProductEntity.builder()
                .name(request.name())
                .price(request.price())
                .quantity(request.quantity())
                .build())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"))
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
    public Mono<ProductEntity> findById(String idProduct){
        return productRepository.findById(idProduct)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found!!")));
    }

    @Override
    public Flux<ProductEntity> findAll(){
        return productRepository.findAll();
    }

}
