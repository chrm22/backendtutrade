package com.test.backendtutrade.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class EtiquetaId implements Serializable {
    @Serial
    private static final long serialVersionUID = 3459196617181678707L;
    @Column(name = "articulo_id", nullable = false)
    private Long articuloId;

    @Column(name = "nombre", nullable = false, length = 64)
    private String nombre;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EtiquetaId entity = (EtiquetaId) o;
        return Objects.equals(this.articuloId, entity.articuloId) &&
                Objects.equals(this.nombre, entity.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(articuloId, nombre);
    }

}