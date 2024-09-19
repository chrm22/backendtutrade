package com.test.backendtutrade.mappers;

import com.test.backendtutrade.dtos.PedidoDTO;
import com.test.backendtutrade.entities.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    @Mapping(target = "fechaCreacion", source = "fechaHoraCreacion")
    @Mapping(target = "articulo.id", source = "articulo.id")
    @Mapping(target = "articulo.nombre", source = "articulo.nombre")
    @Mapping(target = "articulo.username", source = "articulo.usuario.username")
    @Mapping(target = "articuloOfrecido.id", source = "articuloOfrecido.id")
    @Mapping(target = "articuloOfrecido.nombre", source = "articuloOfrecido.nombre")
    @Mapping(target = "articuloOfrecido.username", source = "articuloOfrecido.usuario.username")
    @Mapping(target = "estado", source = "estado")
    PedidoDTO pedidoToPedidoDTO(Pedido pedido);

    default LocalDate mapInstantToLocalDate(Instant instant) {
        if (instant == null) {
            return null;
        }
        return instant.atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
