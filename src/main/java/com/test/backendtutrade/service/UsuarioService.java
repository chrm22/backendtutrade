package com.test.backendtutrade.service;

import com.test.backendtutrade.dtos.*;
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
import java.util.Set;
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

    @Override
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

    @Override
    @Transactional
    public UsuarioDTO actualizarUsuario(String username, UsuarioDTO usuarioDTO) {

        Usuario usuarioExistente = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException
                        ("Usuario '" + username + "' no encontrado"));

        usuarioDTO.setId(usuarioExistente.getId());

        usuarioMapper.updateUsuarioFromDTO(usuarioDTO, usuarioExistente);

        Usuario usuarioActualizado = usuarioRepository.save(usuarioExistente);

        return usuarioMapper.usuarioToUsuarioDTO(usuarioActualizado);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioPerfilDTO obtenerPerfilUsuario(String username) {

        if (username.equals("admin"))
            throw new RuntimeException("Usuario administrador");

        Usuario usuarioExistente = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        return usuarioMapper.usuarioToUsuarioPerfilDTO(usuarioExistente);
    }

    @Override
    @Transactional
    public void eliminarUsuario(String username) {

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException
                        ("Usuario '" + username + "' no encontrado"));

        usuario.setEstado("eliminado");

        usuario.getRoles().clear();

        usuario.getArticulos().forEach(articulo -> articulo.setEstado("eliminado"));

        usuarioRepository.save(usuario);



    }

    @Override
    @Transactional
    public void eliminarUsuarioAdmin(Long id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException
                        (String.format("Usuario con id %d no encontrado", id)));

        if (usuario.getEstado().equals("eliminado"))
            throw new RuntimeException("Usuario ya se encuentra eliminado");

        usuario.setEstado("eliminado");

        usuario.getRoles().clear();

        usuario.getArticulos().forEach(articulo -> articulo.setEstado("eliminado"));

        usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public UsuarioDTO suspenderUsuario(Long id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException
                        (String.format("Usuario con id %d no encontrado", id)));

        if (usuario.getEstado().equals("eliminado"))
            throw new RuntimeException("Usuario se encuentra eliminado");

        usuario.setEstado("suspendido");

        usuario.getArticulos().forEach(articulo -> articulo.setEstado("no_disponible"));

        Usuario usuarioFinal = usuarioRepository.save(usuario);

        return usuarioMapper.usuarioToUsuarioDTO(usuarioFinal);
    }

    @Override
    @Transactional
    public UsuarioRolesDTO modificarRolesUsuario(Long id, ModificarRolesDTO modificarRolesDTO) {
        // Buscar el usuario por id
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));

        Set<Rol> rolesActuales = usuario.getRoles();

        // Roles a asignar
        if (modificarRolesDTO.getRolesAsignar() != null) {
            for (String nombreRol : modificarRolesDTO.getRolesAsignar()) {
                Rol rol = rolRepository.findByNombreOpcional(nombreRol)
                        .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado: " + nombreRol));

                if (!rolesActuales.contains(rol)) {
                    usuario.getRoles().add(rol);
                }
            }
        }

        // Roles a revocar
        if (modificarRolesDTO.getRolesRevocar() != null) {
            for (String nombreRol : modificarRolesDTO.getRolesRevocar()) {
                Rol rol = rolRepository.findByNombreOpcional(nombreRol)
                        .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado: " + nombreRol));

                if (rol.getNombre().equals("ADMIN") && rolesActuales.contains(rol)) {
                    throw new IllegalArgumentException("No se puede revocar el rol de administrador.");
                }

                usuario.getRoles().remove(rol);
            }
        }

        usuarioRepository.save(usuario);

        List<String> nombresRoles = usuario.getRoles().stream()
                .map(Rol::getNombre)
                .collect(Collectors.toList());

        return new UsuarioRolesDTO(usuario.getId(), nombresRoles);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepository.findAllByRolesNombre("ROL_USUARIO")
                .stream()
                .map(usuarioMapper::usuarioToUsuarioDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResumenDTO> listarUsuariosResumidos() {
        return usuarioRepository.findAllByRolesNombreNotLike("ROL_USUARIO", "ROL_ADMIN")
                .stream()
                .map(usuarioMapper::usuarioToUsuarioResumenDTO)
                .collect(Collectors.toList());
    }
}
