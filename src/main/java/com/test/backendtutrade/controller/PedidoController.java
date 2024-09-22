package com.test.backendtutrade.controller;

import com.test.backendtutrade.dtos.EstadoPedidoDTO;
import com.test.backendtutrade.dtos.PedidoDTO;
import com.test.backendtutrade.dtos.PedidoRegistroDTO;
import com.test.backendtutrade.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200", "18.222.240.43"})
@RestController
@RequestMapping("/api")
public class PedidoController {

    private final PedidoService pedidoService;

    @Autowired
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping("/articulos/{articuloId}")
    public ResponseEntity<PedidoDTO> registrarPedido(@PathVariable Long articuloId,
                                                     @RequestBody PedidoRegistroDTO pedidoRegistroDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        PedidoDTO pedidoResponse = pedidoService.registrarPedido(articuloId, username, pedidoRegistroDTO);

        return ResponseEntity.ok(pedidoResponse);
    }

    @PutMapping("/pedidos/recibidos")
    public ResponseEntity<PedidoDTO> aceptarRechazarPedido(@RequestBody EstadoPedidoDTO estadoPedidoDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        PedidoDTO pedidoResponse = pedidoService.aceptarRechazarPedido(username, estadoPedidoDTO);

        return ResponseEntity.ok(pedidoResponse);
    }

    @PutMapping("/pedidos/realizados")
    public ResponseEntity<PedidoDTO> cancelarPedido(@RequestBody EstadoPedidoDTO estadoPedidoDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        PedidoDTO pedidoResponse = pedidoService.cancelarPedido(username, estadoPedidoDTO);

        return ResponseEntity.ok(pedidoResponse);
    }

    @GetMapping("/pedidos/recibidos")
    public ResponseEntity<List<PedidoDTO>> listarMisPedidosRecibidos() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<PedidoDTO> pedidosResponse = pedidoService.listarPedidosRecibidos(username);

        return ResponseEntity.ok(pedidosResponse);
    }

    @GetMapping("/pedidos/recibidos/{articuloId}")
    public ResponseEntity<List<PedidoDTO>> listarMisPedidosRecibidosPorArticulo(@PathVariable Long articuloId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<PedidoDTO> pedidosResponse = pedidoService.listarPedidosRecibidosPorArticuloId(username, articuloId);

        return ResponseEntity.ok(pedidosResponse);
    }

    @GetMapping("/pedidos/realizados")
    public ResponseEntity<List<PedidoDTO>> listarMisPedidosRealizados() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<PedidoDTO> pedidosResponse = pedidoService.listarPedidosRealizados(username);

        return ResponseEntity.ok(pedidosResponse);
    }

    @GetMapping("/pedidos/realizados/{articuloId}")
    public ResponseEntity<List<PedidoDTO>> listarMisPedidosRealizadosPorArticulo(@PathVariable Long articuloId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<PedidoDTO> pedidosResponse = pedidoService.listarPedidosRealizadosPorArticuloId(username, articuloId);

        return ResponseEntity.ok(pedidosResponse);
    }

}
