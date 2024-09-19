package com.test.backendtutrade.dtos;

import lombok.Value;

import java.util.Set;

@Value
public class ArticuloRegistroDTO {
    String nombre;
    String descripcion;
    Boolean publico;
    Set<String> etiquetas;
    Set<ImagenDTO> imagenes;
}
