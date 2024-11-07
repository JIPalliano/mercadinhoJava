package com.example.mercadinho.controller.admin;


import com.example.mercadinho.controller.request.ProductRequest;
import com.example.mercadinho.domain.repository.model.entity.ProductEntity;
import com.example.mercadinho.service.product.ProductFacade;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping(path="/v1/admin/merchandiser")
public class AdminProductController {

    private final ProductFacade productFacade;

    @PutMapping(path="{id-product}" )
    @RolesAllowed("ADMIN")
    public ProductEntity updateProduct(@PathVariable("id-product") String idProduct, @RequestBody ProductRequest request){
        return this.productFacade.updateProduct(idProduct, request);
    }

    @PostMapping
    @RolesAllowed("ADMIN")
    public ProductEntity createProduct(@RequestBody ProductRequest request) {
        return this.productFacade.createProduct(request);
    }

    @DeleteMapping(path="{id-product}")
    @RolesAllowed("ADMIN")
    public void deleteProduct(@PathVariable("id-product") String idProduct){
        this.productFacade.deleteProduct(idProduct);
    }

}
