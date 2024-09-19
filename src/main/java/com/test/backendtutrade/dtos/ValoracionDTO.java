package com.test.backendtutrade.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Getter
@Setter
public class ValoracionDTO {
    private String username;
    private Integer calificacion;
    private String comentario;
    private LocalDate fechaCreacion;

    public ValoracionDTO(String username, Integer calificacion, String comentario, Instant instanteCreacion) {
        this.username = username;
        this.calificacion = calificacion;
        this.comentario = comentario;
        this.fechaCreacion = instanteCreacion != null ? instanteCreacion.atZone(ZoneId.systemDefault()).toLocalDate() : null;
    }
}
