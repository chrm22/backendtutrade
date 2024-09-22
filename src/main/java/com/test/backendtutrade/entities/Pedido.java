package com.test.backendtutrade.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "pedidos_articulos", uniqueConstraints = {
        @UniqueConstraint(name = "pedidos_articulos_ak_1", columnNames = {"articulo_id", "articulo_ofrecido_id"})
})
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pedido_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "articulo_id", nullable = false)
    private Articulo articulo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "articulo_ofrecido_id", nullable = false)
    private Articulo articuloOfrecido;

    @CreationTimestamp
    @Column(name = "fecha_hora_creacion", nullable = false)
    private Instant fechaHoraCreacion;

    @ColumnDefault("'pendiente'")
    @Column(name = "estado", nullable = false, length = 24)
    private String estado;

}