package com.test.backendtutrade.mappers;

import com.test.backendtutrade.entities.Etiqueta;
import com.test.backendtutrade.entities.EtiquetaId;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EtiquetaMapper {

    EtiquetaMapper INSTANCE = Mappers.getMapper(EtiquetaMapper.class);

    default String toString(Etiqueta etiqueta) {
        if (etiqueta == null) {
            return null;
        }
        return etiqueta.getId().getNombre();
    }

    default Etiqueta toEntity(String nombreEtiqueta) {
        if (nombreEtiqueta == null) {
            return null;
        }

        EtiquetaId etiquetaId = new EtiquetaId();
        etiquetaId.setNombre(nombreEtiqueta);

        Etiqueta etiqueta = new Etiqueta();
        etiqueta.setId(etiquetaId);

        return etiqueta;
    }
}