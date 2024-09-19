package com.test.backendtutrade.repository;

import com.test.backendtutrade.dtos.ArticuloPorEstadoDTO;
import com.test.backendtutrade.entities.Articulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticuloRepository extends JpaRepository<Articulo, Long> {

    List<Articulo> findAllByPublicoIsTrue();

    List<Articulo> findAllByUsuarioUsernameAndPublicoIsTrue(String username);

    @Query("select distinct a " +
            "from Articulo a " +
            "left join a.etiquetas etiquetas " +
            "where etiquetas.id.nombre in ?1")
    List<Articulo> findDistinctByEtiquetasNombreIn(List<String> etiquetas);

    @Query("select new com.test.backendtutrade.dtos.ArticuloPorEstadoDTO(a.estado, count(a)) " +
            "from Articulo a " +
            "group by a.estado")
    List<ArticuloPorEstadoDTO> groupAndCountByEstado();
}
