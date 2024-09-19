package com.test.backendtutrade.repository;

import com.test.backendtutrade.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByInformacionUsuarioEmail(String email);

    Optional<Usuario> findByUsername(String username);

    List<Usuario> findAllByRolesNombre(String nombre);

    List<Usuario> findAllByEstadoIsNotLike(String estado);

    @Query("SELECT u FROM Usuario u JOIN FETCH u.roles WHERE u.username = ?1")
    Optional<Usuario> findByUsernameWithRoles(String username);
}
