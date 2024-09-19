package com.test.backendtutrade.interfaces;

import com.test.backendtutrade.dtos.ValoracionDTO;
import com.test.backendtutrade.dtos.ValoracionRegistroDTO;

import java.util.List;

public interface IValoracionService {
    ValoracionDTO registrarValoracion(
            Long evaluadoId, String evaluadorUsername, ValoracionRegistroDTO valoracionDTO);

    List<ValoracionDTO> listarValoracionesPorUsuario(String username);

    List<ValoracionDTO> listarValoracionesPorUsuarioAsc(String username);

    List<ValoracionDTO> listarValoracionesPorUsuarioDesc(String username);
}
