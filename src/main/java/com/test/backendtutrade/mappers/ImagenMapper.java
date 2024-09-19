package com.test.backendtutrade.mappers;

import com.test.backendtutrade.dtos.ImagenDTO;
import com.test.backendtutrade.entities.Imagen;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ImagenMapper {

    ImagenMapper INSTANCE = Mappers.getMapper(ImagenMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "nroImagen", source = "nroImagen")
    @Mapping(target = "url", source = "url")
    @Mapping(target = "descripcion", source = "descripcion")
    ImagenDTO toDto(Imagen imagen);

    default Imagen toEntity(ImagenDTO imagenDTO) {
        if (imagenDTO == null) {
            return null;
        }

        Imagen imagen = new Imagen();
        imagen.setDescripcion(imagenDTO.getDescripcion());
        imagen.setUrl(imagenDTO.getUrl());
        imagen.setNroImagen(imagenDTO.getNroImagen());

        return imagen;
    }


}
