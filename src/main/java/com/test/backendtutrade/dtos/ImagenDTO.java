package com.test.backendtutrade.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.test.backendtutrade.entities.Imagen}
 */

@Getter
@Setter
@AllArgsConstructor
public class ImagenDTO implements Serializable {
    private Long id;
    private Integer nroImagen;
    private String url;
    private String descripcion;
}