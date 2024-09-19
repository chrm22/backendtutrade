package com.test.backendtutrade.dtos;

import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.test.backendtutrade.entities.Usuario}
 */
@Value
public class UsuarioRegistroDTO implements Serializable {
    String username;
    String password;
    String nombre;
    String apellido;
    String dni;
    LocalDate fechaNacimiento;
    String email;
    String telefono;
    String ciudad;
    String pais;
}