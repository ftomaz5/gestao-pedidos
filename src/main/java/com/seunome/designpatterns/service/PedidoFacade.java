package com.seunome.designpatterns.service;

import com.seunome.designpatterns.client.ViaCepClient;
import com.seunome.designpatterns.domain.model.Endereco;
import com.seunome.designpatterns.domain.model.Pedido;
import com.seunome.designpatterns.domain.repository.EnderecoRepository;
import com.seunome.designpatterns.domain.repository.PedidoRepository;
import com.seunome.designpatterns.service.frete.FreteService;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class PedidoFacade {

    private final ViaCepClient viaCepClient;
    private final EnderecoRepository enderecoRepository;
    private final PedidoRepository pedidoRepository;
    private final FreteService freteService;

    public PedidoFacade(ViaCepClient viaCepClient,
                        EnderecoRepository enderecoRepository,
                        PedidoRepository pedidoRepository,
                        FreteService freteService) {
        this.viaCepClient = viaCepClient;
        this.enderecoRepository = enderecoRepository;
        this.pedidoRepository = pedidoRepository;
        this.freteService = freteService;
    }

    public Pedido criarPedido(String cliente, String cep, String tipoFrete, BigDecimal valorProdutos) {
        // 1. Busca o endereco (cache local ou ViaCEP)
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
            Endereco novo = viaCepClient.buscarPorCep(cep);
            enderecoRepository.save(novo);
            return novo;
        });

        // 2. Calcula o frete via Strategy
        BigDecimal frete = freteService.calcular(tipoFrete, endereco.getUf(), valorProdutos);

        // 3. Monta e persiste o pedido
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setEndereco(endereco);
        pedido.setValorProdutos(valorProdutos);
        pedido.setValorFrete(frete);
        pedido.setValorTotal(valorProdutos.add(frete));
        return pedidoRepository.save(pedido);
    }

    public Iterable<Pedido> listar() {
        return pedidoRepository.findAll();
    }
}
