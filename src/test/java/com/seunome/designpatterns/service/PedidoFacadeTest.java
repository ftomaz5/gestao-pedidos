package com.seunome.designpatterns.service;

import com.seunome.designpatterns.client.ViaCepClient;
import com.seunome.designpatterns.domain.model.Endereco;
import com.seunome.designpatterns.domain.model.Pedido;
import com.seunome.designpatterns.domain.repository.EnderecoRepository;
import com.seunome.designpatterns.domain.repository.PedidoRepository;
import com.seunome.designpatterns.service.frete.FreteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Testes do padrão Facade (PedidoFacade) — orquestra endereço (cache/ViaCEP),
 * cálculo de frete (Strategy) e persistência do pedido.
 */
@ExtendWith(MockitoExtension.class)
class PedidoFacadeTest {

    @Mock
    ViaCepClient viaCepClient;
    @Mock
    EnderecoRepository enderecoRepository;
    @Mock
    PedidoRepository pedidoRepository;
    @Mock
    FreteService freteService;

    @InjectMocks
    PedidoFacade facade;

    private Endereco enderecoSP() {
        Endereco e = new Endereco();
        e.setCep("01000-000");
        e.setUf("SP");
        e.setLocalidade("São Paulo");
        return e;
    }

    @Test
    void buscaEnderecoNoViaCepQuandoNaoEstaEmCache() {
        when(enderecoRepository.findById("01000-000")).thenReturn(Optional.empty());
        when(viaCepClient.buscarPorCep("01000-000")).thenReturn(enderecoSP());
        when(freteService.calcular(anyString(), anyString(), any()))
                .thenReturn(new BigDecimal("15.90"));
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(inv -> inv.getArgument(0));

        Pedido pedido = facade.criarPedido("Ana", "01000-000", "ECONOMICO", new BigDecimal("100.00"));

        verify(viaCepClient).buscarPorCep("01000-000");
        verify(enderecoRepository).save(any(Endereco.class));
        assertEquals(0, new BigDecimal("15.90").compareTo(pedido.getValorFrete()));
        assertEquals(0, new BigDecimal("115.90").compareTo(pedido.getValorTotal()));
        assertEquals("Ana", pedido.getCliente());
    }

    @Test
    void usaEnderecoEmCacheSemChamarViaCep() {
        when(enderecoRepository.findById("01000-000")).thenReturn(Optional.of(enderecoSP()));
        when(freteService.calcular(anyString(), anyString(), any())).thenReturn(BigDecimal.ZERO);
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(inv -> inv.getArgument(0));

        Pedido pedido = facade.criarPedido("Bruno", "01000-000", "ECONOMICO", new BigDecimal("350.00"));

        verify(viaCepClient, never()).buscarPorCep(anyString());
        verify(enderecoRepository, never()).save(any());
        assertEquals(0, new BigDecimal("350.00").compareTo(pedido.getValorTotal()));
    }

    @Test
    void listarDelegaAoRepositorio() {
        when(pedidoRepository.findAll()).thenReturn(List.of(new Pedido(), new Pedido()));

        Iterable<Pedido> resultado = facade.listar();

        assertEquals(2, ((Collection<?>) resultado).size());
    }
}
