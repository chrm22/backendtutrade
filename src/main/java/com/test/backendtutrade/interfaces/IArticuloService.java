package com.test.backendtutrade.interfaces;

import com.test.backendtutrade.dtos.ArticuloDTO;
import com.test.backendtutrade.dtos.ArticuloPorEstadoDTO;
import com.test.backendtutrade.dtos.ArticuloRegistroDTO;

import java.util.List;

public interface IArticuloService {

    ArticuloDTO registrarArticulo(String username, ArticuloRegistroDTO articuloRegistroDTO);

    List<ArticuloDTO> listarArticulosPublicos();

    List<ArticuloDTO> listarArticulosPublicosPorUsuario(String username);

    List<ArticuloDTO> filtrarPorEtiquetas(List<String> etiquetas);

    List<ArticuloPorEstadoDTO> groupAndCountByEstado();
}
