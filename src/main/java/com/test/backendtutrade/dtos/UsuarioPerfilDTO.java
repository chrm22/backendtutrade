package com.test.backendtutrade.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class UsuarioPerfilDTO {
    Long id;
    String username;
    String nombreCompleto;
    String localidad;
    Integer cantidadIntercambios;
    BigDecimal calificacionPromedio;
    List<ArticuloDTO> articulos;
}
