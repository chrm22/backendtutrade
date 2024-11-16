package com.test.backendtutrade.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "usuarios", uniqueConstraints = {
        @UniqueConstraint(name = "usuarios_ak_username", columnNames = {"username"})
})
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false, length = 24)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "url_foto_perfil")
    private String urlFotoPerfil;

    @CreationTimestamp
    @Column(name = "fecha_hora_creacion", nullable = false)
    private Instant fechaHoraCreacion;

    @ColumnDefault("'activo'")
    @Column(name = "estado", nullable = false, length = 24)
    private String estado;

    @OneToMany(mappedBy = "usuario")
    @JsonManagedReference("usuarios_articulos")
    private Set<Articulo> articulos = new LinkedHashSet<>();

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("usuario_informacion")
    private InfoUsuario informacionUsuario;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "roles_usuarios",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id"))
    @JsonManagedReference("usuarios_roles")
    private Set<Rol> roles = new LinkedHashSet<>();

//    @OneToMany(mappedBy = "usuarioEvaluado")
//    private Set<Valoracion> valoracionesRecibidas = new LinkedHashSet<>();
//
//    @OneToMany(mappedBy = "usuarioEvaluador")
//    private Set<Valoracion> valoracionesRealizadas = new LinkedHashSet<>();

    public boolean isEnabled() {
        return (estado.equals("activo"));
    }

}