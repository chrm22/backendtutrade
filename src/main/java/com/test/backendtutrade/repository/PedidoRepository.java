package com.test.backendtutrade.repository;

import com.test.backendtutrade.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findAllByArticuloUsuarioUsername(String username);

    List<Pedido> findAllByArticuloOfrecidoUsuarioUsername(String username);

    List<Pedido> findAllByArticuloId(Long id);

    List<Pedido> findAllByArticuloOfrecidoId(Long id);
}