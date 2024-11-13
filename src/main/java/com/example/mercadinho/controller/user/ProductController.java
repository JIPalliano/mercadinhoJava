package com.example.mercadinho.controller.user;



import com.example.mercadinho.service.product.ProductFacade;
import com.example.mercadinho.domain.repository.model.entity.ProductEntity;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path="/v1/merchandiser")
public class ProductController {

    private final ProductFacade facade;

//    @DeleteMapping(path="{id-product}")
//    public void deleteProductShoppingCart(@PathVariable("id-product") String idProduct) {
//        this.facade.deleteProductShoppingCart(idProduct);
//    }

    @GetMapping(path="{id-product}")
    public ProductEntity findById(@PathVariable("id-product") ProductEntity idProduct) {
        return this.facade.findById(idProduct);
    }

    @GetMapping
    public List<ProductEntity> findAll() {
        return this.facade.findAll();
    }


}
