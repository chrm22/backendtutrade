package com.test.backendtutrade.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticuloPorEstadoDTO {
    private String estado;
    private Long cantidad;
}
