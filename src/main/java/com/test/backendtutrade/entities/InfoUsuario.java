package com.test.backendtutrade.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "informacion_usuarios", uniqueConstraints = {
        @UniqueConstraint(name = "informacion_usuarios_ak_dni", columnNames = {"dni"}),
        @UniqueConstraint(name = "informacion_usuarios_ak_email", columnNames = {"email"}),
        @UniqueConstraint(name = "informacion_usuarios_ak_telefono", columnNames = {"telefono"})
})
public class InfoUsuario {
    @Id
    @Column(name = "usuario_id", nullable = false)
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonBackReference("usuario_informacion")
    private Usuario usuario;

    @Column(name = "nombre", nullable = false, length = 48)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 48)
    private String apellido;

    @Column(name = "dni", nullable = false, length = 8)
    private String dni;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "telefono", nullable = false, length = 9)
    private String telefono;

    @Column(name = "ciudad", nullable = false, length = 40)
    private String ciudad;

    @Column(name = "pais", nullable = false, length = 40)
    private String pais;

    @ColumnDefault("0")
    @Column(name = "cantidad_intercambios", nullable = false)
    private Integer cantidadIntercambios;

    @Column(name = "calificacion_promedio", precision = 3, scale = 2)
    private BigDecimal calificacionPromedio;

    @ColumnDefault("false")
    @Column(name = "verificado", nullable = false)
    private Boolean verificado = false;

}