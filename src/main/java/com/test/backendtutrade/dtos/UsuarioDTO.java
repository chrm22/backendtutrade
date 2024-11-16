package com.test.backendtutrade.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class UsuarioDTO implements Serializable {
    private Long id;
    private String username;
    private String urlFotoPerfil;
    private String nombre;
    private String apellido;
    private String dni;
    private LocalDate fechaNacimiento;
    private String email;
    private String telefono;
    private String ciudad;
    private String pais;
    private LocalDate fechaCreacion;
    private Integer cantidadIntercambios;
    private BigDecimal calificacionPromedio;
    private Boolean verificado;

}
