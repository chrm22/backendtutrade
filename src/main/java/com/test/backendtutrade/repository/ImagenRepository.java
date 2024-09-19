package com.test.backendtutrade.repository;

import com.test.backendtutrade.entities.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagenRepository extends JpaRepository<Imagen, Long> {

    List<Imagen> findAllByArticuloIdOrderByNroImagenAsc(Long id);
}