package com.test.backendtutrade.interfaces;

import com.test.backendtutrade.dtos.UsuarioDTO;
import com.test.backendtutrade.dtos.UsuarioRegistroDTO;

import java.util.List;

public interface IUsuarioService {

    UsuarioDTO registrarUsuario(UsuarioRegistroDTO usuarioRegistroDTO);
    UsuarioDTO actualizarUsuario(Long id, UsuarioDTO usuarioDTO);
    void eliminarUsuario(Long id);
    List<UsuarioDTO> listarUsuarios();
}
