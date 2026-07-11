package com.seunome.designpatterns.controller;

import com.seunome.designpatterns.domain.model.Pedido;
import com.seunome.designpatterns.service.PedidoFacade;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoFacade pedidoFacade;

    public PedidoController(PedidoFacade pedidoFacade) {
        this.pedidoFacade = pedidoFacade;
    }

    @PostMapping
    public Pedido criar(@RequestBody PedidoRequest req) {
        return pedidoFacade.criarPedido(
                req.cliente(), req.cep(), req.tipoFrete(), req.valorProdutos());
    }

    @GetMapping
    public Iterable<Pedido> listar() {
        return pedidoFacade.listar();
    }

    public record PedidoRequest(String cliente, String cep,
                                String tipoFrete, BigDecimal valorProdutos) {}
}
