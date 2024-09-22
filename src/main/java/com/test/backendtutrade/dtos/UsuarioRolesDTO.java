package com.test.backendtutrade.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UsuarioRolesDTO {
    private Long idUsuario;
    private List<String> roles;

}