package com.test.backendtutrade.repository;

import com.test.backendtutrade.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("select u from Usuario u inner join u.roles roles where u.id = ?1 and roles.nombre not like ?2")
    Optional<Usuario> findByIdAndRolesNombreIsNotLike(Long id, String role);

    Optional<Usuario> findByUsername(String username);

    @Query("select u from Usuario u inner join u.roles roles " +
            "where roles.nombre = ?1 " +
            "and u.estado not like 'eliminado' " +
            "and u.estado not like 'suspendido'")
    List<Usuario> findAllByRolesNombre(String nombre);

    @Query("select u from Usuario u inner join u.roles roles " +
            "where roles.nombre like ?1 " +
            "and roles.nombre not like ?2 " +
            "and u.estado not like 'eliminado' " +
            "and u.estado not like 'suspendido'")
    List<Usuario> findAllByRolesNombreNotLike(String rolAdmitido, String rolDenegado);

    @Query("SELECT u FROM Usuario u JOIN FETCH u.roles WHERE u.username = ?1")
    Optional<Usuario> findByUsernameWithRoles(String username);

    Boolean existsByUsername(String username);

    Boolean existsByInformacionUsuarioEmail(String email);

    Boolean existsByInformacionUsuarioDni(String dni);

    Boolean existsByInformacionUsuarioTelefono(String telefono);
}
