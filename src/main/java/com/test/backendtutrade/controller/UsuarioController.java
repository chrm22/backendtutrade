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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    //asdadsa
    @Autowired
    private AuthenticationManager authenticationManager;
    //asdasdad
    @Autowired
    UserDetailsService userDetailsService;
    //adsadsda
    @Autowired
    private JwtUtilService jwtUtilService;

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Esta parte faltaaaa
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

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {

        UsuarioDTO usuarioDTOActualizado = usuarioService.actualizarUsuario(id, usuarioDTO);

        return ResponseEntity.ok(usuarioDTOActualizado);
    }

    @DeleteMapping("/logico/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {

        usuarioService.eliminarUsuario(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/fisico/{id}")
    public void delete(@PathVariable("id") Long id){
        usuarioService.eliminarUsuarioFisico(id);
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
