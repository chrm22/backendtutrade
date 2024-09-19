package com.test.backendtutrade.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "valoraciones", uniqueConstraints = {
        @UniqueConstraint(name = "valoraciones_ak_1", columnNames = {"usuario_evaluado_id", "usuario_evaluador_id"})
})
public class Valoracion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "valoracion_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_evaluado_id", nullable = false)
    private Usuario usuarioEvaluado;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_evaluador_id", nullable = false)
    private Usuario usuarioEvaluador;

    @Column(name = "calificacion", nullable = false)
    private Integer calificacion;

    @Column(name = "comentario")
    private String comentario;

    @CreationTimestamp
    @Column(name = "fecha_hora_creacion", nullable = false)
    private Instant fechaHoraCreacion;

}