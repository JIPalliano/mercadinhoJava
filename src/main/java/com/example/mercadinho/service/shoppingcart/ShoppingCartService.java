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
    public ShoppingCartEntity create(String idProduct, Integer quantity) {
        return productRepository.findById(idProduct).map(product -> shoppingCartRepository.save(ShoppingCartEntity.builder()
                .products(List.of(Product.builder()
                        .id(idProduct)
                        .name(product.name())
                        .price(product.price())
                        .quantity(quantity > 0? quantity : 1)
                        .build()))
                .userId(UserService.getCurrentUser().getId())
                .date(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(LocalDateTime.now()))
                .build())).orElseThrow(() -> new RuntimeException("Product not found!"));
    }

    @Override
    public ShoppingCartEntity addProduct(String idProduct, Integer quantity) {
        return shoppingCartRepository.findByUserId(UserService.getCurrentUser().getId())
                .map(shoppingCartEntity -> {
                    updateProductQuantity(shoppingCartEntity.getProducts(), idProduct, quantity);
                    return shoppingCartRepository.save(shoppingCartEntity);
                })
                .orElseGet(() -> create(idProduct, quantity));
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
                                products.add(findProduct(idProduct, quantity));
                            }else{
                                throw new RuntimeException("Product cant be added!");
                            }
                        }
                );
    }

    private Product findProduct( String idProduct, Integer quantity) {
        return productRepository.findById(idProduct).map(productEntity -> Product.builder()
                .id(idProduct)
                .name(productEntity.name())
                .price(productEntity.price())
                .quantity(quantity)
                .build()).orElseThrow(() -> new RuntimeException("Product not found!"));
    }


    @Override
    public void delete() {
        shoppingCartRepository.findByUserId(UserService.getCurrentUser().getId())
                .ifPresentOrElse(
                        shoppingCart -> shoppingCartRepository.deleteById(shoppingCart.getId()),
                        ()-> {throw new RuntimeException("ShoppingCart not found!");}
                );
    }

    @Override
    public ShoppingCartResponse find() {
        return shoppingCartRepository.findByUserId(UserService.getCurrentUser().getId())
                .map(shoppingCartEntity -> {
                    int totalQuantity = shoppingCartEntity.getProducts().stream()
                            .mapToInt(Product::getQuantity)
                            .sum();

                    BigDecimal totalPrice = shoppingCartEntity.getProducts().stream()
                            .map(product -> product.getPrice().multiply(BigDecimal.valueOf(product.getQuantity())))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    return ShoppingCartResponse.builder()
                            .id(shoppingCartEntity.getId())
                            .products(shoppingCartEntity.getProducts())
                            .totalPrice(totalPrice)
                            .quantity(totalQuantity)
                            .userId(shoppingCartEntity.getUserId())
                            .date(shoppingCartEntity.getDate())
                            .build();
                })
                .orElseThrow(()-> new RuntimeException("ShoppingCart not found!"));
    }
    @Override
    public List<ShoppingCartEntity> findAll() {
        return Optional.of(shoppingCartRepository.findAll())
                .filter(carts -> !carts.isEmpty())
                .orElseThrow(() -> new RuntimeException("ShoppingCart not found!"));
    }

}
