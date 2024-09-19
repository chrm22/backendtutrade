package com.test.backendtutrade.repository;

import com.test.backendtutrade.dtos.ValoracionDTO;
import com.test.backendtutrade.entities.Valoracion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ValoracionRepository extends JpaRepository<Valoracion, Long> {

    @Query("select new com.test.backendtutrade.dtos.ValoracionDTO" +
            "(v.usuarioEvaluador.username, v.calificacion, v.comentario, v.fechaHoraCreacion) " +
            "from Valoracion v " +
            "where v.usuarioEvaluado.username = ?1 ")
    List<ValoracionDTO> findAllByUsuarioEvaluadoUsername(String username);

    @Query("select new com.test.backendtutrade.dtos.ValoracionDTO" +
            "(v.usuarioEvaluador.username, v.calificacion, v.comentario, v.fechaHoraCreacion) " +
            "from Valoracion v " +
            "where v.usuarioEvaluado.username = ?1 " +
            "order by v.calificacion asc")
    List<ValoracionDTO> findAllByUsuarioEvaluadoUsernameOrderByCalificacionAsc(String username);

    @Query("select new com.test.backendtutrade.dtos.ValoracionDTO" +
            "(v.usuarioEvaluador.username, v.calificacion, v.comentario, v.fechaHoraCreacion) " +
            "from Valoracion v " +
            "where v.usuarioEvaluado.username = ?1 " +
            "order by v.calificacion desc")
    List<ValoracionDTO> findAllByUsuarioEvaluadoUsernameOrderByCalificacionDesc(String username);


}