package com.test.backendtutrade.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UsuarioResumenDTO {
    Long id;
    String username;
    String nombreCompleto;
    String localidad;
}
