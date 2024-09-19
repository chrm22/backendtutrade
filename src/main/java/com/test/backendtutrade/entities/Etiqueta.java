package com.test.backendtutrade.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "etiquetas_articulos")
public class Etiqueta {
    @EmbeddedId
    private EtiquetaId id;

    public String getNombre() {
        return id.getNombre();
    }

    public void setNombre(String nombre) {
        id.setNombre(nombre);
    }

    public Long getArticuloId() {
        return id.getArticuloId();
    }

    public void setArticuloId(Long articuloId) {
        id.setArticuloId(articuloId);
    }
}