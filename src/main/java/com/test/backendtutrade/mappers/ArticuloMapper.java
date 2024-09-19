package com.test.backendtutrade.mappers;

import com.test.backendtutrade.dtos.ArticuloDTO;
import com.test.backendtutrade.dtos.ArticuloRegistroDTO;
import com.test.backendtutrade.dtos.ImagenDTO;
import com.test.backendtutrade.dtos.UsuarioResumenDTO;
import com.test.backendtutrade.entities.Articulo;
import com.test.backendtutrade.entities.Etiqueta;
import com.test.backendtutrade.entities.Imagen;
import com.test.backendtutrade.entities.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ArticuloMapper {

    @Mapping(target = "nombre", source = "nombre")
    @Mapping(target = "descripcion", source = "descripcion")
    @Mapping(target = "publico", source = "publico")
    @Mapping(target = "etiquetas",
            expression = "java(convertRegistroDTOToSetEtiquetas(articuloRegistroDTO))")
    @Mapping(target = "imagenes",
            expression = "java(convertRegistroDTOToSetImagenes(articuloRegistroDTO))")
    Articulo articuloRegistroDTOToArticulo(ArticuloRegistroDTO articuloRegistroDTO);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    @Mapping(target = "descripcion", source = "descripcion")
    @Mapping(target = "publico", source = "publico")
    @Mapping(target = "estado", source = "estado")
    @Mapping(target = "etiquetas",
            expression = "java(convertToSetString(articulo))")
    @Mapping(target = "imagenes",
            expression = "java(convertToSetImagenesDTO(articulo))")
    @Mapping(target = "usuario",
            expression = "java(convertToUsuarioResumenDTO(articulo.getUsuario()))")
    ArticuloDTO articuloToArticuloDTO(Articulo articulo);


    @Named("convertDTOToSetEtiquetas")
    default Set<Etiqueta> convertDTOToSetEtiquetas(ArticuloDTO articuloDTO) {

        EtiquetaMapper etiquetaMapper = EtiquetaMapper.INSTANCE;

        Long articuloId = articuloDTO.getId();

        return articuloDTO.getEtiquetas()
                .stream()
                .map(etiquetaMapper::toEntity)
                .collect(Collectors.toSet());
    }

    @Named("convertRegistroDTOToSetEtiquetas")
    default Set<Etiqueta> convertRegistroDTOToSetEtiquetas(ArticuloRegistroDTO articuloRegistroDTO) {

        EtiquetaMapper etiquetaMapper = EtiquetaMapper.INSTANCE;

        return articuloRegistroDTO.getEtiquetas()
                .stream()
                .map(etiquetaMapper::toEntity)
                .collect(Collectors.toSet());
    }

    @Named("convertToSetString")
    default Set<String> convertToSetString(Articulo articulo) {
        return articulo.getEtiquetas()
                .stream()
                .map(Etiqueta::getNombre)
                .collect(Collectors.toSet());
    }

    @Named("convertDTOToSetImagenes")
    default Set<Imagen> convertDTOToSetImagenes(ArticuloDTO articuloDTO) {

        ImagenMapper imagenMapper = ImagenMapper.INSTANCE;

        return articuloDTO.getImagenes()
                .stream()
                .map(imagenMapper::toEntity)
                .collect(Collectors.toSet());
    }

    @Named("convertRegistroDTOToSetImagenes")
    default Set<Imagen> convertRegistroDTOToSetImagenes(ArticuloRegistroDTO articuloRegistroDTO) {

        ImagenMapper imagenMapper = ImagenMapper.INSTANCE;

        return articuloRegistroDTO.getImagenes()
                .stream()
                .map(imagenMapper::toEntity)
                .collect(Collectors.toSet());
    }

    @Named("convertToSetImagenesDTO")
    default Set<ImagenDTO> convertToSetImagenesDTO(Articulo articulo) {

        ImagenMapper imagenMapper = ImagenMapper.INSTANCE;

        return articulo.getImagenes()
                .stream()
                .map(imagenMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Named("convertToUsuarioResumenDTO")
    default UsuarioResumenDTO convertToUsuarioResumenDTO(Usuario usuario) {
        UsuarioMapper usuarioMapper = UsuarioMapper.INSTANCE;

        return usuarioMapper.usuarioToUsuarioResumenDTO(usuario);
    }

}
