package com.example.mercadinho.service.shoppingcart;

import com.example.mercadinho.domain.repository.ProductRepository;
import com.example.mercadinho.domain.repository.ShoppingCartRepository;
import com.example.mercadinho.domain.repository.model.Product;
import com.example.mercadinho.domain.repository.model.ShoppingCartEntity;
import com.example.mercadinho.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ShoppingCartService implements ShoppingCartFacade{

    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;
    private final UserService userService;

    @Override
    public ShoppingCartEntity create(String idProduct, Integer quantity) {
        return productRepository.findById(idProduct).map(product -> shoppingCartRepository.save(ShoppingCartEntity.builder()
                .products(List.of(Product.builder()
                                .id(idProduct)
                                .quantity(quantity)
                        .build()))
                .userId(UserService.getCurrentUser().getId())
                .date(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(LocalDateTime.now()))
                .build())).orElseThrow(()-> new RuntimeException("Produto não encontrado!!"));
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
                                products.add(Product.builder()
                                        .id(idProduct)
                                        .quantity(quantity)
                                        .build());
                            }
                        }
                );
    }


    @Override
    public void delete(String idShoppingCart){
        shoppingCartRepository.deleteById(idShoppingCart);
    }

    @Override
    public ShoppingCartEntity find(){
        return shoppingCartRepository.findByUserId(Objects.requireNonNull(UserService.getCurrentUser()).getId()).orElseThrow();
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
