package com.test.backendtutrade.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "articulos")
public class Articulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "articulo_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonBackReference("usuarios_articulos")
    private Usuario usuario;

    @Column(name = "nombre", nullable = false, length = 64)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "publico", nullable = false)
    private Boolean publico = false;

    @ColumnDefault("disponible")
    @Column(name = "estado", nullable = false, length = 24)
    private String estado;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "articulo_id")
    private Set<Etiqueta> etiquetas = new LinkedHashSet<>();

    @OneToMany(mappedBy = "articulo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("articulo_imagenes")
    private Set<Imagen> imagenes = new LinkedHashSet<>();

    @OneToMany(mappedBy = "articulo")
    private Set<Pedido> articulosOfrecidos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "articuloOfrecido")
    private Set<Pedido> articulosPedidos = new LinkedHashSet<>();

}