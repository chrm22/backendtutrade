package com.test.backendtutrade.repository;

import com.test.backendtutrade.entities.Etiqueta;
import com.test.backendtutrade.entities.EtiquetaId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EtiquetaRepository extends JpaRepository<Etiqueta, EtiquetaId> {
}