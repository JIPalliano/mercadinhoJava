package com.example.mercadinho.controller.user;



import com.example.mercadinho.service.product.ProductFacade;
import com.example.mercadinho.domain.repository.model.entity.ProductEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path="/v1/merchandiser")
@Tag(name="a")
public class ProductController {

    private final ProductFacade facade;

    @Operation
    @GetMapping(path="{id-product}")
    public ProductEntity findById(@PathVariable("id-product") ProductEntity idProduct) {
        return this.facade.findById(idProduct);
    }

    @GetMapping
    public List<ProductEntity> findAll() {
        return this.facade.findAll();
    }


}
