package com.test.backendtutrade.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "imagenes_articulos")
public class Imagen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "imagen_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "articulo_id", nullable = false)
    @JsonBackReference("articulo_imagenes")
    private Articulo articulo;

    @Column(name = "nro_imagen_articulo", nullable = false)
    private Integer nroImagen;

    @Column(name = "url_imagen", nullable = false)
    private String url;

    @Column(name = "descripcion")
    private String descripcion;

}