package com.test.backendtutrade.controller;

import com.test.backendtutrade.dtos.*;
import com.test.backendtutrade.security.JwtUtilService;
import com.test.backendtutrade.security.SecurityUser;
import com.test.backendtutrade.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    private JwtUtilService jwtUtilService;

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<DTOToken> authenticate(@RequestBody UsuarioLoginDTO user) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(),
                        user.getPassword()));

        SecurityUser securityUser = (SecurityUser) this.userDetailsService.loadUserByUsername(
                user.getUsername());

        String jwt = jwtUtilService.generateToken(securityUser);

        Long id = securityUser.getUsuario().getId();

        String authoritiesString = securityUser.getUsuario().getRoles().stream()
                .map(n -> String.valueOf(n.getNombre()))
                .collect(Collectors.joining(";", "", ""));

        return new ResponseEntity<>(new DTOToken(jwt, id, authoritiesString), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioDTO> registrarUsuario(@RequestBody UsuarioRegistroDTO usuarioRegistroDTO) {

        UsuarioDTO usuarioDTO = usuarioService.registrarUsuario(usuarioRegistroDTO);

        return ResponseEntity.ok(usuarioDTO);
    }

    @PutMapping
    public ResponseEntity<UsuarioDTO> actualizarUsuario(@RequestBody UsuarioDTO usuarioDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        UsuarioDTO usuarioDTOActualizado = usuarioService.actualizarUsuario(username, usuarioDTO);

        return ResponseEntity.ok(usuarioDTOActualizado);
    }

    @PutMapping("/eliminar")
    public ResponseEntity<Void> eliminarUsuario() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        usuarioService.eliminarUsuario(username);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarUsuarioAdmin(@PathVariable Long id) {
        usuarioService.eliminarUsuarioAdmin(id);
        return ResponseEntity.noContent().build();
    }



    @PutMapping("/suspender/{id}")
    public ResponseEntity<UsuarioDTO> suspenderUsuario(@PathVariable Long id) {

        UsuarioDTO usuarioDTO = usuarioService.suspenderUsuario(id);

        return ResponseEntity.ok(usuarioDTO);
    }

    @PutMapping("/roles/{id}")
    public ResponseEntity<UsuarioRolesDTO> modificarRolesUsuario(
            @PathVariable Long id,
            @RequestBody ModificarRolesDTO modificarRolesDTO) {

        if ((modificarRolesDTO.getRolesAsignar() == null || modificarRolesDTO.getRolesAsignar().isEmpty()) &&
                (modificarRolesDTO.getRolesRevocar() == null || modificarRolesDTO.getRolesRevocar().isEmpty())) {
            return ResponseEntity.badRequest().build();
        }

        if (modificarRolesDTO.getRolesAsignar() != null && modificarRolesDTO.getRolesRevocar() != null) {
            Set<String> interseccion = new HashSet<>(modificarRolesDTO.getRolesAsignar());
            interseccion.retainAll(modificarRolesDTO.getRolesRevocar());
            if (!interseccion.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
        }

        try {
            UsuarioRolesDTO usuarioRolesDTO = usuarioService.modificarRolesUsuario(id, modificarRolesDTO);
            return ResponseEntity.ok(usuarioRolesDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<UsuarioPerfilDTO> obtenerPerfilPorUsername(@PathVariable String username) {

        UsuarioPerfilDTO usuarioPerfilDTO = usuarioService.obtenerPerfilUsuario(username);

        return ResponseEntity.ok(usuarioPerfilDTO);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {

        List<UsuarioDTO> usuariosDTOS = usuarioService.listarUsuarios();

        return ResponseEntity.ok(usuariosDTOS);
    }

    @GetMapping("/resumidos")
    public ResponseEntity<List<UsuarioResumenDTO>> listarUsuariosResumidos() {

        List<UsuarioResumenDTO> usuariosResumenDTOS = usuarioService.listarUsuariosResumidos();

        return ResponseEntity.ok(usuariosResumenDTOS);
    }


}
