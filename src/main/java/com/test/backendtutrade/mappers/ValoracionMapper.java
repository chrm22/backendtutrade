package com.test.backendtutrade.mappers;

import com.test.backendtutrade.dtos.ValoracionDTO;
import com.test.backendtutrade.entities.Valoracion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Mapper(componentModel = "spring")
public interface ValoracionMapper {

    @Mapping(target = "fechaCreacion", source = "fechaHoraCreacion")
    @Mapping(target = "username", source = "usuarioEvaluador.username")
    ValoracionDTO valoracionToValoracionDTO(Valoracion valoracion);

    default LocalDate mapInstantToLocalDate(Instant instant) {
        if (instant == null) {
            return null;
        }
        return instant.atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
