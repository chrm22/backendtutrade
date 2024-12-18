package com.test.backendtutrade.interfaces;

import com.test.backendtutrade.dtos.*;

import java.util.List;

public interface IUsuarioService {

    UsuarioDTO obtenerUsuarioPorId(Long id);

    UsuarioDTO registrarUsuario(UsuarioRegistroDTO usuarioRegistroDTO);

    UsuarioDTO actualizarUsuario(String username, UsuarioDTO usuarioDTO);

    UsuarioPerfilDTO obtenerPerfilUsuario(String username);

    void eliminarUsuario(String username);

    // Metodo de eliminar para administrados
    void eliminarUsuarioAdmin(Long id);

    UsuarioDTO suspenderUsuario(Long id);

    UsuarioRolesDTO modificarRolesUsuario(Long id, ModificarRolesDTO modificarRolesDTO);

    List<UsuarioDTO> listarUsuarios();

    List<UsuarioResumenDTO> listarUsuariosResumidos();

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByDni(String dni);

    Boolean existsByTelefono(String telefono);
}
