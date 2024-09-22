package com.test.backendtutrade.repository;

import com.test.backendtutrade.entities.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Integer> {

    @Query("select r from Rol r where r.nombre = ?1")
    Rol findByNombre(String nombre);

    @Query("select r from Rol r where r.nombre = ?1")
    Optional<Rol> findByNombreOpcional(String nombre);
}