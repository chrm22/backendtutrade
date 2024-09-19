package com.test.backendtutrade.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticuloResumenDTO {
    private Long id;
    private String nombre;
    private String username;

    public ArticuloResumenDTO(Long id, String nombre, String username) {
        this.id = id;
        this.nombre = nombre;
        this.username = username;
    }
}
