package com.test.backendtutrade.service;

import com.test.backendtutrade.dtos.ValoracionDTO;
import com.test.backendtutrade.dtos.ValoracionRegistroDTO;
import com.test.backendtutrade.entities.Usuario;
import com.test.backendtutrade.entities.Valoracion;
import com.test.backendtutrade.interfaces.IValoracionService;
import com.test.backendtutrade.mappers.ValoracionMapper;
import com.test.backendtutrade.repository.UsuarioRepository;
import com.test.backendtutrade.repository.ValoracionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ValoracionService implements IValoracionService {

    private final UsuarioRepository usuarioRepository;
    private final ValoracionRepository valoracionRepository;
    private final ValoracionMapper valoracionMapper;

    @Autowired
    public ValoracionService(UsuarioRepository usuarioRepository, ValoracionRepository valoracionRepository, ValoracionMapper valoracionMapper) {
        this.usuarioRepository = usuarioRepository;
        this.valoracionRepository = valoracionRepository;
        this.valoracionMapper = valoracionMapper;
    }


    @Override
    @Transactional
    public ValoracionDTO registrarValoracion(
            Long evaluadoId, String evaluadorUsername, ValoracionRegistroDTO valoracionDTO) {

        Usuario evaluador  = usuarioRepository.findByUsername(evaluadorUsername)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Usuario evaluado = usuarioRepository.findById(evaluadoId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (evaluado == evaluador)
            throw new RuntimeException("No se puede registrar la valoración");

        Valoracion valoracion = new Valoracion();

        valoracion.setUsuarioEvaluado(evaluado);
        valoracion.setUsuarioEvaluador(evaluador);
        valoracion.setCalificacion(valoracionDTO.calificacion());
        valoracion.setComentario(valoracionDTO.comentario());

        Valoracion valoracionResponse = valoracionRepository.save(valoracion);

        return valoracionMapper.valoracionToValoracionDTO(valoracionResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ValoracionDTO> listarValoracionesPorUsuario(String username) {
        return valoracionRepository.findAllByUsuarioEvaluadoUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ValoracionDTO> listarValoracionesPorUsuarioAsc(String username) {
        return valoracionRepository.
                findAllByUsuarioEvaluadoUsernameOrderByCalificacionAsc(username);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ValoracionDTO> listarValoracionesPorUsuarioDesc(String username) {
        return valoracionRepository.
                findAllByUsuarioEvaluadoUsernameOrderByCalificacionAsc(username);
    }
}
