package com.example.mercadinho.domain.service.contractuser.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private String id;
    private String username;
    private String email;
}
