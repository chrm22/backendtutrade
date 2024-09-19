package com.test.backendtutrade.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "roles", uniqueConstraints = {
        @UniqueConstraint(name = "roles_ak_1", columnNames = {"nombre"})
})
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rol_id", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 32)
    private String nombre;

    @ManyToMany(mappedBy = "roles")
    @JsonBackReference("usuarios_roles")
    private Set<Usuario> usuarios = new LinkedHashSet<>();

}