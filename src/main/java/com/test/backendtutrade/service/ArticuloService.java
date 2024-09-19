package com.test.backendtutrade.service;

import com.test.backendtutrade.dtos.ArticuloDTO;
import com.test.backendtutrade.dtos.ArticuloPorEstadoDTO;
import com.test.backendtutrade.dtos.ArticuloRegistroDTO;
import com.test.backendtutrade.entities.*;
import com.test.backendtutrade.exceptions.ResourceNotFoundException;
import com.test.backendtutrade.interfaces.IArticuloService;
import com.test.backendtutrade.mappers.ArticuloMapper;
import com.test.backendtutrade.mappers.EtiquetaMapper;
import com.test.backendtutrade.mappers.ImagenMapper;
import com.test.backendtutrade.repository.ArticuloRepository;
import com.test.backendtutrade.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ArticuloService implements IArticuloService {

    private final int MAX_ETIQUETAS = 5;
    private final ArticuloRepository articuloRepository;
    private final ArticuloMapper articuloMapper;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public ArticuloService(ArticuloRepository articuloRepository, ArticuloMapper articuloMapper, UsuarioRepository usuarioRepository) {
        this.articuloRepository = articuloRepository;
        this.articuloMapper = articuloMapper;
        this.usuarioRepository = usuarioRepository;
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
    public List<ArticuloDTO> listarArticulosPublicosPorUsuario(String username) {
        return articuloRepository.findAllByUsuarioUsernameAndPublicoIsTrue(username)
                .stream()
                .map(articuloMapper::articuloToArticuloDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticuloDTO> filtrarPorEtiquetas(List<String> etiquetas) {
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
    public List<ArticuloPorEstadoDTO> groupAndCountByEstado() {
        return articuloRepository.groupAndCountByEstado();
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

}
