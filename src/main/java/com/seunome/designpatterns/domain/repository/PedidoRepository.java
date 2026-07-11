package com.seunome.designpatterns.domain.repository;

import com.seunome.designpatterns.domain.model.Pedido;
import org.springframework.data.repository.CrudRepository;

public interface PedidoRepository extends CrudRepository<Pedido, Long> {
}
