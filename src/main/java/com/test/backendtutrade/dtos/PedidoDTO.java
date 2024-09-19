package com.test.backendtutrade.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Getter
@Setter
public class PedidoDTO {
    private Integer id;
    private ArticuloResumenDTO articulo;
    private ArticuloResumenDTO articuloOfrecido;
    private LocalDate fechaCreacion;
    private String estado;

    public PedidoDTO(Integer id, ArticuloResumenDTO articulo, ArticuloResumenDTO articuloOfrecido,
                     Instant instanteCreacion, String estado) {
        this.id = id;
        this.articulo = articulo;
        this.articuloOfrecido = articuloOfrecido;
        this.fechaCreacion = instanteCreacion != null ? instanteCreacion.atZone(ZoneId.systemDefault()).toLocalDate() : null;
        this.estado = estado;
    }
}
