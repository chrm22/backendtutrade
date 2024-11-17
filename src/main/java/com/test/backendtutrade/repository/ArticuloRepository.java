package com.test.backendtutrade.repository;

import com.test.backendtutrade.dtos.ArticuloPorEstadoDTO;
import com.test.backendtutrade.dtos.ArticuloPorEtiquetaDTO;
import com.test.backendtutrade.entities.Articulo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticuloRepository extends JpaRepository<Articulo, Long> {

    @Query("select a from Articulo a " +
            "where a.publico = true " +
            "and a.usuario.username not like ?1 " +
            "and a.estado not like 'eliminado' " +
            "and a.estado not like 'intercambiado' " +
            "and a.estado not like 'no_disponible'")
    List<Articulo> findAllByPublicoIsTrueNotUsuario(String username);

    @Query("select a from Articulo a " +
            "where a.publico = true " +
            "and a.estado not like 'eliminado' " +
            "and a.estado not like 'intercambiado' " +
            "and a.estado not like 'no_disponible'")
    List<Articulo> findAllByPublicoIsTrue();

    @Query("select a from Articulo a " +
            "where a.usuario.username = ?1 " +
            "and a.publico = true " +
            "and a.estado not like 'eliminado' " +
            "and a.estado not like 'no_disponible'")
    List<Articulo> findAllByUsuarioUsernameAndPublicoIsTrue(String username);

    @Query("select a from Articulo a where a.usuario.username = ?1 and a.estado not like ?2")
    List<Articulo> findAllByUsuarioUsernameAndEstadoIsNotLike(String username, String estado);

    @Query("select a from Articulo a " +
            "left join a.articulosPedidos p " +
            "where a.usuario.username = ?1 " +
            "and a.estado not like 'eliminado' " +
            "and (p.id is null or p.articulo.id != ?2)")
    List<Articulo> listarMisArticulosExceptoOfrecidosA(String username, Long id);

    @Query("select distinct a " +
            "from Articulo a " +
            "left join a.etiquetas etiquetas " +
            "where etiquetas.id.nombre in ?1 " +
            "and a.publico = true " +
            "and a.estado not like 'eliminado' " +
            "and a.estado not like 'intercambiado' " +
            "and a.estado not like 'no_disponible'")
    List<Articulo> findDistinctByEtiquetasNombreIn(List<String> etiquetas);

    @Query("select new com.test.backendtutrade.dtos.ArticuloPorEstadoDTO(a.estado, count(a)) " +
            "from Articulo a " +
            "group by a.estado")
    List<ArticuloPorEstadoDTO> groupAndCountByEstado();

    @Query("select new com.test.backendtutrade.dtos.ArticuloPorEtiquetaDTO(e.id.nombre, count(a)) " +
            "from Articulo a join a.etiquetas e " +
            "group by e.id.nombre " +
            "order by count(a) desc")
    List<ArticuloPorEtiquetaDTO> groupAndCountByEtiqueta(Pageable pageable);
}
