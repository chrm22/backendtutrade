package com.test.backendtutrade.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ModificarRolesDTO {
    private List<String> rolesAsignar;
    private List<String> rolesRevocar;

}