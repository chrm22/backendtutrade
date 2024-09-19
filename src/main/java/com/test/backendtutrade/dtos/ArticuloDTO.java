package com.test.backendtutrade.dtos;

import lombok.Value;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.test.backendtutrade.entities.Articulo}
 */
@Value
public class ArticuloDTO implements Serializable {
    Long id;
    UsuarioResumenDTO usuario;
    String nombre;
    String descripcion;
    Boolean publico;
    String estado;
    Set<String> etiquetas;
    Set<ImagenDTO> imagenes;
}