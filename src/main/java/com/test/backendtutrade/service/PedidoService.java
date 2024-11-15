package com.test.backendtutrade.service;

import com.test.backendtutrade.dtos.EstadoPedidoDTO;
import com.test.backendtutrade.dtos.PedidoDTO;
import com.test.backendtutrade.dtos.PedidoRegistroDTO;
import com.test.backendtutrade.entities.Articulo;
import com.test.backendtutrade.entities.Pedido;
import com.test.backendtutrade.interfaces.IPedidoService;
import com.test.backendtutrade.mappers.PedidoMapper;
import com.test.backendtutrade.repository.ArticuloRepository;
import com.test.backendtutrade.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService implements IPedidoService {

    private final PedidoMapper pedidoMapper;
    private final ArticuloRepository articuloRepository;
    private final PedidoRepository pedidoRepository;

    @Autowired
    public PedidoService(PedidoMapper pedidoMapper, ArticuloRepository articuloRepository, PedidoRepository pedidoRepository) {
        this.pedidoMapper = pedidoMapper;
        this.articuloRepository = articuloRepository;
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    @Transactional
    public PedidoDTO registrarPedido(Long articuloId, String username, PedidoRegistroDTO requestDTO) {

        Articulo articulo = articuloRepository.findById(articuloId)
                .orElseThrow(() -> new RuntimeException("Articulo no encontrado"));

        Articulo articuloOfrecido = articuloRepository.findById(requestDTO.articuloOfrecidoId())
                .orElseThrow(() -> new RuntimeException("Articulo a ofrecer no encontrado"));

        if (!articuloOfrecido.getUsuario().getUsername().equals(username))
            throw new RuntimeException("Acceso denegado");

        Pedido pedido = new Pedido();

        pedido.setArticulo(articulo);
        pedido.setArticuloOfrecido(articuloOfrecido);
        pedido.setEstado("pendiente");

        Pedido pedidoResponse = pedidoRepository.save(pedido);

        return pedidoMapper.pedidoToPedidoDTO(pedidoResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public PedidoDTO buscarPedido(String username, Long id) {

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        if (!(pedido.getArticulo().getUsuario().getUsername().equals(username) ||
                pedido.getArticuloOfrecido().getUsuario().getUsername().equals(username)))
            throw new RuntimeException("Acceso denegado");

        if (!pedido.getEstado().equals("pendiente"))
            throw new RuntimeException("Pedido ya no disponible");

        return pedidoMapper.pedidoToPedidoDTO(pedido);
    }

    @Override
    @Transactional
    public PedidoDTO aceptarRechazarPedido(String username, EstadoPedidoDTO estadoPedidoDTO) {

        Pedido pedido = pedidoRepository.findById(estadoPedidoDTO.pedidoId())
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        if (!pedido.getArticulo().getUsuario().getUsername().equals(username))
            throw new RuntimeException("Acceso denegado");

        if (!pedido.getEstado().equals("pendiente"))
            throw new RuntimeException("Pedido ya no disponible");

        if (estadoPedidoDTO.opcion().equals("aceptar"))
            pedido.setEstado("aceptado");
        else if (estadoPedidoDTO.opcion().equals("rechazar"))
            pedido.setEstado("rechazado");
        else throw new RuntimeException("Opción inválida");

        Pedido pedidoResponse = pedidoRepository.save(pedido);

        return pedidoMapper.pedidoToPedidoDTO(pedidoResponse);
    }

    @Override
    @Transactional
    public void cancelarPedido(String username, EstadoPedidoDTO estadoPedidoDTO) {

        Pedido pedido = pedidoRepository.findById(estadoPedidoDTO.pedidoId())
                .orElseThrow(() -> new RuntimeException("Pedido Ano encontrado"));

        if (!pedido.getArticuloOfrecido().getUsuario().getUsername().equals(username))
            throw new RuntimeException("Acceso denegado");

        if (!pedido.getEstado().equals("pendiente"))
            throw new RuntimeException("Pedido ya no disponible");

        if (estadoPedidoDTO.opcion().equals("cancelar"))
            pedidoRepository.delete(pedido);
//            pedido.setEstado("cancelado");
        else throw new RuntimeException("Opción inválida");

//        Pedido pedidoResponse = pedidoRepository.save(pedido);
//        return pedidoMapper.pedidoToPedidoDTO(pedidoResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoDTO> listarPedidosRecibidos(String username) {
        return pedidoRepository.findAllByArticuloUsuarioUsername(username)
                .stream()
                .map(pedidoMapper::pedidoToPedidoDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoDTO> listarPedidosRecibidosPorArticuloId(String username, Long id) {

        Articulo articulo = articuloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Articulo no encontrado"));

        if (!articulo.getUsuario().getUsername().equals(username))
            throw new RuntimeException("Acceso denegado");

        return pedidoRepository.findAllByArticuloId(id)
                .stream()
                .map(pedidoMapper::pedidoToPedidoDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoDTO> listarPedidosRealizados(String username) {
        return pedidoRepository.findAllByArticuloOfrecidoUsuarioUsername(username)
                .stream()
                .map(pedidoMapper::pedidoToPedidoDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoDTO> listarPedidosRealizadosPorArticuloId(String username, Long id) {

        Articulo articulo = articuloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Articulo no encontrado"));

        if (!articulo.getUsuario().getUsername().equals(username))
            throw new RuntimeException("Acceso denegado");

        return pedidoRepository.findAllByArticuloOfrecidoId(id)
                .stream()
                .map(pedidoMapper::pedidoToPedidoDTO)
                .collect(Collectors.toList());
    }
}
