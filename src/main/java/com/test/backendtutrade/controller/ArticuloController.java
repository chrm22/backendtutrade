package com.test.backendtutrade.controller;

import com.test.backendtutrade.dtos.ArticuloDTO;
import com.test.backendtutrade.dtos.ArticuloPorEstadoDTO;
import com.test.backendtutrade.dtos.ArticuloRegistroDTO;
import com.test.backendtutrade.service.ArticuloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ArticuloController {

    ArticuloService articuloService;

    @Autowired
    public ArticuloController(ArticuloService articuloService) {
        this.articuloService = articuloService;
    }

    @GetMapping("/articulos")
    public ResponseEntity<List<ArticuloDTO>> listarArticulosPublicos() {

        List<ArticuloDTO> articuloDTOS = articuloService.listarArticulosPublicos();

        return ResponseEntity.ok(articuloDTOS);
    }

    @GetMapping("/usuarios/{username}/articulos")
    public ResponseEntity<List<ArticuloDTO>> listarArticulosPublicosPorUsuario(
            @PathVariable String username) {

        List<ArticuloDTO> articuloDTOS = articuloService.listarArticulosPublicosPorUsuario(username);

        return ResponseEntity.ok(articuloDTOS);
    }

    @GetMapping("/articulos/filter")
    public ResponseEntity<List<ArticuloDTO>> filtrarArticulosPorEtiquetas(
            @RequestParam String etiquetas) {

        List<String> listaEtiquetas = Arrays.asList(etiquetas.split(" "));

        List<ArticuloDTO> articulos = articuloService.filtrarPorEtiquetas(listaEtiquetas);
        return ResponseEntity.ok(articulos);
    }

    @PostMapping("/articulos")
    public ResponseEntity<ArticuloDTO> registrarArticulo
            (@RequestBody ArticuloRegistroDTO articuloRegistroDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        ArticuloDTO articuloDTO = articuloService.registrarArticulo(username, articuloRegistroDTO);

        return ResponseEntity.ok(articuloDTO);
    }

    @GetMapping("/conteo/estado")
    public ResponseEntity<List<ArticuloPorEstadoDTO>> groupAndCountByEstado(){
        return ResponseEntity.ok(articuloService.groupAndCountByEstado());
    }

}