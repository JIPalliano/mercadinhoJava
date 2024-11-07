package com.example.mercadinho.service.shoppingcart;

import com.example.mercadinho.controller.response.ShoppingCartResponse;
import com.example.mercadinho.domain.repository.ProductRepository;
import com.example.mercadinho.domain.repository.ShoppingCartRepository;
import com.example.mercadinho.domain.repository.model.Product;
import com.example.mercadinho.domain.repository.model.entity.ShoppingCartEntity;
import com.example.mercadinho.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoppingCartService implements ShoppingCartFacade {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;

    @Override
    public ShoppingCartEntity create(String idProduct, Integer quantity) {
        return productRepository.findById(idProduct).map(product -> shoppingCartRepository.save(ShoppingCartEntity.builder()
                .products(List.of(Product.builder()
                        .id(idProduct)
                        .name(product.name())
                        .price(product.price())
                        .quantity(quantity)
                        .build()))
                .userId(UserService.getCurrentUser().getId())
                .date(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(LocalDateTime.now()))
                .build())).orElseThrow(() -> new RuntimeException("Product not found!!"));
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
    public void delete(String idShoppingCart) {
        shoppingCartRepository.deleteById(idShoppingCart);
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
                .orElseThrow();
    }
    @Override
    public List<ShoppingCartEntity> findAll() {
        return this.shoppingCartRepository.findAll();
    }

//    @Override
//    public ShoppingCartEntity findShoppingCartCookie(){
//        return null;//repository.findById(cookie.readCookie(request, "Carrinho-salvado")).orElse(null);
//    }

}
