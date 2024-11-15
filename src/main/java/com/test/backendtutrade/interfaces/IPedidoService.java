package com.test.backendtutrade.interfaces;

import com.test.backendtutrade.dtos.EstadoPedidoDTO;
import com.test.backendtutrade.dtos.PedidoDTO;
import com.test.backendtutrade.dtos.PedidoRegistroDTO;

import java.util.List;

public interface IPedidoService {

    PedidoDTO registrarPedido(Long articuloId, String username, PedidoRegistroDTO pedido);

    PedidoDTO buscarPedido(String username, Long id);

    PedidoDTO aceptarRechazarPedido(String username, EstadoPedidoDTO estadoPedidoDTO);

    void cancelarPedido(String username, EstadoPedidoDTO estadoPedidoDTO);

    List<PedidoDTO> listarPedidosRecibidos(String username);

    List<PedidoDTO> listarPedidosRecibidosPorArticuloId(String username, Long id);

    List<PedidoDTO> listarPedidosRealizados(String username);

    List<PedidoDTO> listarPedidosRealizadosPorArticuloId(String username, Long id);
}
