package com.test.backendtutrade.service;

import com.test.backendtutrade.dtos.*;
import com.test.backendtutrade.entities.*;
import com.test.backendtutrade.exceptions.ResourceNotFoundException;
import com.test.backendtutrade.interfaces.IArticuloService;
import com.test.backendtutrade.mappers.ArticuloMapper;
import com.test.backendtutrade.mappers.EtiquetaMapper;
import com.test.backendtutrade.mappers.ImagenMapper;
import com.test.backendtutrade.repository.ArticuloRepository;
import com.test.backendtutrade.repository.PedidoRepository;
import com.test.backendtutrade.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ArticuloService implements IArticuloService {

    private final ArticuloRepository articuloRepository;
    private final ArticuloMapper articuloMapper;
    private final UsuarioRepository usuarioRepository;
    private final PedidoRepository pedidoRepository;

    @Autowired
    public ArticuloService(ArticuloRepository articuloRepository, ArticuloMapper articuloMapper, UsuarioRepository usuarioRepository, PedidoRepository pedidoRepository) {
        this.articuloRepository = articuloRepository;
        this.articuloMapper = articuloMapper;
        this.usuarioRepository = usuarioRepository;
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ArticuloDTO obtenerArticulo(String username, Long id) {

        Articulo articulo = articuloRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Articulo no encontrado"));

        ArticuloDTO articuloResponse = articuloMapper.articuloToArticuloDTO(articulo);

        if (articulo.getPublico() ||
            articulo.getUsuario().getUsername().equals(username) ||
            this.esArticuloOfrecido(username, id)) {
            return articuloResponse;
        }
        else {
            throw new ResourceNotFoundException("Articulo no encontrado");
        }
    }

    @Override
    @Transactional
    public ArticuloDTO registrarArticulo(String username, ArticuloRegistroDTO articuloRegistroDTO) {

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Articulo articulo = articuloMapper.articuloRegistroDTOToArticulo(articuloRegistroDTO);
        articulo.setEstado("disponible");
        articulo.setUsuario(usuario);
        articulo.setImagenes(null);
        articulo.setEtiquetas(null);

        articuloRepository.save(articulo);

        articulo.setImagenes(this.convertRegistroDTOToSetImagenes(articuloRegistroDTO));
        articulo.setEtiquetas(this.convertRegistroDTOToSetEtiquetas(articuloRegistroDTO));

        articulo.getImagenes().forEach(imagen -> imagen.setArticulo(articulo));
        articulo.getEtiquetas().forEach(etiqueta -> etiqueta.setArticuloId(articulo.getId()));

        Articulo articuloFinal = articuloRepository.save(articulo);

        return articuloMapper.articuloToArticuloDTO(articuloFinal);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticuloDTO> listarArticulosPublicos() {
        return articuloRepository.findAllByPublicoIsTrue()
                .stream()
                .map(articuloMapper::articuloToArticuloDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticuloDTO> listarArticulosPublicosExcluirUsuario(String username) {
        return articuloRepository.findAllByPublicoIsTrueNotUsuario(username)
                .stream()
                .map(articuloMapper::articuloToArticuloDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticuloDTO> listarArticulosPublicosPorUsuario(String username) {
        return articuloRepository.findAllByUsuarioUsernameAndPublicoIsTrue(username)
                .stream()
                .map(articuloMapper::articuloToArticuloDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticuloDTO> filtrarPorEtiquetas(List<String> etiquetas) {
        int MAX_ETIQUETAS = 5;
        if (etiquetas.size() > MAX_ETIQUETAS) {
            throw new IllegalArgumentException("No se pueden usar más de " + MAX_ETIQUETAS + " etiquetas.");
        }

        return articuloRepository.findDistinctByEtiquetasNombreIn(etiquetas)
                .stream()
                .map(articuloMapper::articuloToArticuloDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticuloDTO> listarMisArticulos(String username) {
        return articuloRepository.findAllByUsuarioUsernameAndEstadoIsNotLike(username, "eliminado")
                .stream()
                .map(articuloMapper::articuloToArticuloDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticuloDTO> listarMisArticulosExceptoOfrecidosA(String username, Long id) {
        return articuloRepository.listarMisArticulosExceptoOfrecidosA(username, id)
                .stream()
                .map(articuloMapper::articuloToArticuloDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ArticuloDTO cambiarEstadoMiArticulo(String username, EstadoArticuloDTO estadoArticuloDTO) {

        Articulo articulo = articuloRepository.findById(estadoArticuloDTO.articuloId())
                .orElseThrow(() -> new ResourceNotFoundException("Articulo no encontrado"));

        if (!articulo.getUsuario().getUsername().equals(username))
            throw new RuntimeException("Acceso denegado");

        String estadoNuevo = estadoArticuloDTO.nuevoEstado();

        if (!(estadoNuevo.equals("disponible") || estadoNuevo.equals("intercambiado")))
            throw new RuntimeException("Estado inválido");

        articulo.setEstado(estadoNuevo);
        articulo.setPublico(estadoArticuloDTO.publico());

        Articulo articuloFinal = articuloRepository.save(articulo);

        return articuloMapper.articuloToArticuloDTO(articuloFinal);
    }

    @Override
    @Transactional
    public ArticuloDTO eliminarArticuloAdmin(Long id) {

        Articulo articulo = articuloRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Articulo no encontrado"));

        articulo.setEstado("eliminado");
        Articulo articuloFinal = articuloRepository.save(articulo);

        return articuloMapper.articuloToArticuloDTO(articuloFinal);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticuloPorEstadoDTO> groupAndCountByEstado() {
        return articuloRepository.groupAndCountByEstado();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticuloPorEtiquetaDTO> groupAndCountByEtiqueta() {
        Pageable topDiez = PageRequest.of(0, 10);

        return articuloRepository.groupAndCountByEtiqueta(topDiez);
    }

    private Set<Imagen> convertRegistroDTOToSetImagenes(ArticuloRegistroDTO articuloRegistroDTO) {
        ImagenMapper imagenMapper = ImagenMapper.INSTANCE;

        return articuloRegistroDTO.getImagenes()
                .stream()
                .map(imagenMapper::toEntity)
                .collect(Collectors.toSet());
    }

    private Set<Etiqueta> convertRegistroDTOToSetEtiquetas(ArticuloRegistroDTO articuloRegistroDTO) {

        EtiquetaMapper etiquetaMapper = EtiquetaMapper.INSTANCE;

        return articuloRegistroDTO.getEtiquetas()
                .stream()
                .map(etiquetaMapper::toEntity)
                .collect(Collectors.toSet());
    }

    private boolean esArticuloOfrecido(String username, Long id) {
        Set<Long> ids = pedidoRepository.findAllByArticuloUsuarioUsername(username)
                .stream()
                .map(pedido -> pedido.getArticuloOfrecido().getId())
                .collect(Collectors.toSet());

        return ids.contains(id);
    }

}
