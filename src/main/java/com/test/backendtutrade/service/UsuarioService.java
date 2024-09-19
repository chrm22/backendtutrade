package com.test.backendtutrade.service;

import com.test.backendtutrade.dtos.UsuarioDTO;
import com.test.backendtutrade.dtos.UsuarioPerfilDTO;
import com.test.backendtutrade.dtos.UsuarioRegistroDTO;
import com.test.backendtutrade.dtos.UsuarioResumenDTO;
import com.test.backendtutrade.entities.InfoUsuario;
import com.test.backendtutrade.entities.Rol;
import com.test.backendtutrade.entities.Usuario;
import com.test.backendtutrade.exceptions.ResourceNotFoundException;
import com.test.backendtutrade.interfaces.IUsuarioService;
import com.test.backendtutrade.mappers.UsuarioMapper;
import com.test.backendtutrade.repository.RolRepository;
import com.test.backendtutrade.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements IUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, RolRepository rolRepository, UsuarioMapper usuarioMapper, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.usuarioMapper = usuarioMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UsuarioDTO registrarUsuario(UsuarioRegistroDTO usuarioRegistroDTO) {
        usuarioRegistroDTO = new UsuarioRegistroDTO(
                usuarioRegistroDTO.getUsername(),
                passwordEncoder.encode(usuarioRegistroDTO.getPassword()),
                usuarioRegistroDTO.getNombre(),
                usuarioRegistroDTO.getApellido(),
                usuarioRegistroDTO.getDni(),
                usuarioRegistroDTO.getFechaNacimiento(),
                usuarioRegistroDTO.getEmail(),
                usuarioRegistroDTO.getTelefono(),
                usuarioRegistroDTO.getCiudad(),
                usuarioRegistroDTO.getPais()
        );

        // Mapeo UsuarioDTO a Usuario
        Usuario usuario = usuarioMapper.usuarioRegistroDTOToUsuario(usuarioRegistroDTO);

        InfoUsuario infoUsuario = usuario.getInformacionUsuario();
        if (infoUsuario != null) {
            infoUsuario.setUsuario(usuario);
            infoUsuario.setCantidadIntercambios(0);
            infoUsuario.setVerificado(false);
        }

        Rol rolUsuarioRegular = rolRepository.findByNombre("ROL_USUARIO");

        usuario.getRoles().add(rolUsuarioRegular);
        usuario.setEstado("activo");

        usuarioRepository.save(usuario);

        return usuarioMapper.usuarioToUsuarioDTO(usuario);
    }

    @Transactional
    public UsuarioDTO actualizarUsuario(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException
                        (String.format("Usuario con id %d no encontrado", id)));

        usuarioDTO.setId(id);

        usuarioMapper.updateUsuarioFromDTO(usuarioDTO, usuarioExistente);

        Usuario usuarioActualizado = usuarioRepository.save(usuarioExistente);

        return usuarioMapper.usuarioToUsuarioDTO(usuarioActualizado);
    }

    @Transactional(readOnly = true)
    public UsuarioPerfilDTO obtenerPerfilUsuario(String username) {
        Usuario usuarioExistente = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        return usuarioMapper.usuarioToUsuarioPerfilDTO(usuarioExistente);
    }

    @Transactional
    public void eliminarUsuario(Long id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException
                        (String.format("Usuario con id %d no encontrado", id)));

        usuario.setEstado("eliminado");
        usuarioRepository.save(usuario);

    }

    @Transactional
    public void eliminarUsuarioFisico(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepository.findAllByRolesNombre("ROL_USUARIO")
                .stream()
                .map(usuarioMapper::usuarioToUsuarioDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UsuarioResumenDTO> listarUsuariosResumidos() {
        return usuarioRepository.findAllByRolesNombre("ROL_USUARIO")
                .stream()
                .map(usuarioMapper::usuarioToUsuarioResumenDTO)
                .collect(Collectors.toList());
    }
}
