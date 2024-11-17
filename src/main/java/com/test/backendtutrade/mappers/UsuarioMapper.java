package com.test.backendtutrade.mappers;

import com.test.backendtutrade.dtos.*;
import com.test.backendtutrade.entities.Articulo;
import com.test.backendtutrade.entities.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "informacionUsuario.nombre")
    @Mapping(target = "apellido", source = "informacionUsuario.apellido")
    @Mapping(target = "dni", source = "informacionUsuario.dni")
    @Mapping(target = "fechaNacimiento", source = "informacionUsuario.fechaNacimiento")
    @Mapping(target = "email", source = "informacionUsuario.email")
    @Mapping(target = "telefono", source = "informacionUsuario.telefono")
    @Mapping(target = "ciudad", source = "informacionUsuario.ciudad")
    @Mapping(target = "pais", source = "informacionUsuario.pais")
    @Mapping(target = "cantidadIntercambios", source = "informacionUsuario.cantidadIntercambios")
    @Mapping(target = "calificacionPromedio", source = "informacionUsuario.calificacionPromedio")
    @Mapping(target = "verificado", source = "informacionUsuario.verificado")
    @Mapping(target = "fechaCreacion", source = "fechaHoraCreacion")
    UsuarioDTO usuarioToUsuarioDTO(Usuario usuario);

    @Mapping(target = "username", source = "username")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "urlFotoPerfil", source = "urlFotoPerfil")
    @Mapping(target = "informacionUsuario.nombre", source = "nombre")
    @Mapping(target = "informacionUsuario.apellido", source = "apellido")
    @Mapping(target = "informacionUsuario.dni", source = "dni")
    @Mapping(target = "informacionUsuario.fechaNacimiento", source = "fechaNacimiento")
    @Mapping(target = "informacionUsuario.email", source = "email")
    @Mapping(target = "informacionUsuario.telefono", source = "telefono")
    @Mapping(target = "informacionUsuario.ciudad", source = "ciudad")
    @Mapping(target = "informacionUsuario.pais", source = "pais")
    Usuario usuarioRegistroDTOToUsuario(UsuarioRegistroDTO usuarioRegistroDTO);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(target = "nombreCompleto",
            expression = "java(concatenateNombreApellido" +
                    "(usuario.getInformacionUsuario().getNombre(), " +
                    "usuario.getInformacionUsuario().getApellido()))")
    @Mapping(target = "localidad",
            expression = "java(concatenateCiudadPais" +
                    "(usuario.getInformacionUsuario().getCiudad(), " +
                    "usuario.getInformacionUsuario().getPais()))")
    @Mapping(source = "informacionUsuario.cantidadIntercambios", target = "cantidadIntercambios")
    @Mapping(source = "informacionUsuario.calificacionPromedio", target = "calificacionPromedio")
    @Mapping(target = "articulos",
            expression = "java(convertToArticulosDto(usuario.getArticulos()))")
    UsuarioPerfilDTO usuarioToUsuarioPerfilDTO(Usuario usuario);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(target = "nombreCompleto",
            expression = "java(concatenateNombreApellido" +
                    "(usuario.getInformacionUsuario().getNombre(), " +
                    "usuario.getInformacionUsuario().getApellido()))")
    @Mapping(target = "localidad",
            expression = "java(concatenateCiudadPais" +
                    "(usuario.getInformacionUsuario().getCiudad(), " +
                    "usuario.getInformacionUsuario().getPais()))")
    UsuarioResumenDTO usuarioToUsuarioResumenDTO(Usuario usuario);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "informacionUsuario.nombre", source = "nombre")
    @Mapping(target = "informacionUsuario.apellido", source = "apellido")
    @Mapping(target = "informacionUsuario.dni", source = "dni")
    @Mapping(target = "informacionUsuario.fechaNacimiento", source = "fechaNacimiento")
    @Mapping(target = "informacionUsuario.email", source = "email")
    @Mapping(target = "informacionUsuario.telefono", source = "telefono")
    @Mapping(target = "informacionUsuario.ciudad", source = "ciudad")
    @Mapping(target = "informacionUsuario.pais", source = "pais")
    void updateUsuarioFromDTO(UsuarioDTO usuarioDTO, @MappingTarget Usuario usuario);

    default LocalDate mapInstantToLocalDate(Instant instant) {
        if (instant == null) {
            return null;
        }
        return instant.atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @Named("concatenateNombreApellido")
    default String concatenateNombreApellido(String nombre, String apellido) {
        return (nombre != null ? nombre : "") + " " + (apellido != null ? apellido : "");
    }

    @Named("concatenateCiudadPais")
    default String concatenateCiudadPais(String ciudad, String pais) {
        return (ciudad != null ? ciudad : "") + ", " + (pais != null ? pais : "");
    }

    @Named("convertToArticulosDto")
    default List<ArticuloDTO> convertToArticulosDto(Set<Articulo> articulos) {
        ArticuloMapper articuloMapper = Mappers.getMapper(ArticuloMapper.class);

        return articulos.stream()
                .map(articuloMapper::articuloToArticuloDTO)
                .collect(Collectors.toList());
    }


}
