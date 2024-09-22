package com.test.backendtutrade.interfaces;

import com.test.backendtutrade.dtos.*;

import java.util.List;

public interface IArticuloService {

    ArticuloDTO registrarArticulo(String username, ArticuloRegistroDTO articuloRegistroDTO);

    List<ArticuloDTO> listarArticulosPublicos();

    List<ArticuloDTO> listarArticulosPublicosPorUsuario(String username);

    List<ArticuloDTO> filtrarPorEtiquetas(List<String> etiquetas);

    List<ArticuloDTO> listarMisArticulos(String username);

    ArticuloDTO cambiarEstadoMiArticulo(String username, EstadoArticuloDTO estadoArticuloDTO);

    ArticuloDTO eliminarArticuloAdmin(Long id);

    List<ArticuloPorEstadoDTO> groupAndCountByEstado();

    List<ArticuloPorEtiquetaDTO> groupAndCountByEtiqueta();
}
