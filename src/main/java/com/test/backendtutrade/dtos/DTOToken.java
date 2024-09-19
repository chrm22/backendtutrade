package com.test.backendtutrade.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DTOToken {
    String jwtToken;
    Long id;
    String authorities;
}