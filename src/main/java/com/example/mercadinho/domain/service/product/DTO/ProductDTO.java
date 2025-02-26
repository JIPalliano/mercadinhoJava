package com.example.mercadinho.domain.service.product.DTO;

import com.example.mercadinho.domain.service.contractuser.DTO.UserDTO;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ProductDTO {
    private String id;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private List<UserDTO> userInfo;
}
