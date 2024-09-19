package com.test.backendtutrade.controller;

import com.test.backendtutrade.dtos.ValoracionDTO;
import com.test.backendtutrade.dtos.ValoracionRegistroDTO;
import com.test.backendtutrade.service.ValoracionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/valoraciones")
public class ValoracionController {

    private final ValoracionService valoracionService;

    public ValoracionController(ValoracionService valoracionService) {
        this.valoracionService = valoracionService;
    }

    @PostMapping("/{evaluadoId}")
    public ResponseEntity<ValoracionDTO> registrarValoracion(@PathVariable Long evaluadoId,
                                                             @RequestBody ValoracionRegistroDTO valoracionDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String evaluadorUsername = authentication.getName();

        ValoracionDTO valoracionResponseDTO =
                valoracionService.registrarValoracion(evaluadoId, evaluadorUsername, valoracionDTO);

        return ResponseEntity.ok(valoracionResponseDTO);
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<ValoracionDTO>> obtenerValoraciones(
            @PathVariable String username,
            @RequestParam(required = false) String order) {

        if (order != null) {
            if (order.equals("asc"))
                return ResponseEntity.ok(valoracionService.listarValoracionesPorUsuarioAsc(username));

            if (order.equals("desc"))
                return ResponseEntity.ok(valoracionService.listarValoracionesPorUsuarioDesc(username));
        }

        return ResponseEntity.ok(valoracionService.listarValoracionesPorUsuario(username));
    }
}
