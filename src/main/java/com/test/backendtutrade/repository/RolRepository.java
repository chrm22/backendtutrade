package com.test.backendtutrade.repository;

import com.test.backendtutrade.entities.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol, Integer> {

    Rol findByNombre(String nombre);
}