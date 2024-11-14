package com.test.backendtutrade.controller;

import com.test.backendtutrade.dtos.*;
import com.test.backendtutrade.service.ArticuloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200", "18.222.240.43"})
@RestController
@RequestMapping("/api")
public class ArticuloController {

    ArticuloService articuloService;

    @Autowired
    public ArticuloController(ArticuloService articuloService) {
        this.articuloService = articuloService;
    }

    @GetMapping("/articulos/{id}")
    public ResponseEntity<ArticuloDTO> obtenerArticulo(@PathVariable Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        ArticuloDTO articulo = articuloService.obtenerArticulo(username, id);

        return ResponseEntity.ok(articulo);
    }

    @GetMapping("/articulos")
    public ResponseEntity<List<ArticuloDTO>> listarArticulosPublicos() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<ArticuloDTO> articuloDTOS = articuloService.listarArticulosPublicosExcluirUsuario(username);

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

    @GetMapping("/mis-articulos")
    public ResponseEntity<List<ArticuloDTO>> listarMisArticulos() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<ArticuloDTO> misArticulos = articuloService.listarMisArticulos(username);

        return ResponseEntity.ok(misArticulos);
    }

    @GetMapping("/mis-articulos/except/{id}")
    public ResponseEntity<List<ArticuloDTO>> listarMisArticulosExceptoOfrecidosA(@PathVariable Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<ArticuloDTO> misArticulos = articuloService.listarMisArticulosExceptoOfrecidosA(username, id);

        return ResponseEntity.ok(misArticulos);
    }

    @PutMapping("/mis-articulos")
    public ResponseEntity<ArticuloDTO> cambiarEstadoMiArticulo(@RequestBody EstadoArticuloDTO estadoArticuloDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        ArticuloDTO articuloDTO = articuloService.cambiarEstadoMiArticulo(username, estadoArticuloDTO);

        return ResponseEntity.ok(articuloDTO);
    }

    @PutMapping("/articulos/{id}/eliminar")
    public ResponseEntity<ArticuloDTO> eliminarArticulo(@PathVariable Long id) {

        ArticuloDTO articuloDTO = articuloService.eliminarArticuloAdmin(id);

        return ResponseEntity.ok(articuloDTO);
    }

    @PostMapping("/articulos")
    public ResponseEntity<ArticuloDTO> registrarArticulo
            (@RequestBody ArticuloRegistroDTO articuloRegistroDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        ArticuloDTO articuloDTO = articuloService.registrarArticulo(username, articuloRegistroDTO);

        return ResponseEntity.ok(articuloDTO);
    }

    @GetMapping("/articulos/conteo/estado")
    public ResponseEntity<List<ArticuloPorEstadoDTO>> groupAndCountByEstado(){
        return ResponseEntity.ok(articuloService.groupAndCountByEstado());
    }

    @GetMapping("/articulos/conteo/etiqueta")
    public ResponseEntity<List<ArticuloPorEtiquetaDTO>> groupAndCountByEtiqueta(){
        return ResponseEntity.ok(articuloService.groupAndCountByEtiqueta());
    }


}
