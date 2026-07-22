package com.seunome.designpatterns.controller;

import com.seunome.designpatterns.domain.model.Pedido;
import com.seunome.designpatterns.service.PedidoFacade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Testes da camada web (PedidoController) isolando a fachada com @MockBean.
 */
@WebMvcTest(PedidoController.class)
class PedidoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PedidoFacade pedidoFacade;

    @Test
    void criaPedidoRetornaValoresCalculados() throws Exception {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setCliente("Ana");
        pedido.setValorProdutos(new BigDecimal("100.00"));
        pedido.setValorFrete(new BigDecimal("15.90"));
        pedido.setValorTotal(new BigDecimal("115.90"));

        when(pedidoFacade.criarPedido(anyString(), anyString(), anyString(), any()))
                .thenReturn(pedido);

        String body = """
                {"cliente":"Ana","cep":"01000-000","tipoFrete":"ECONOMICO","valorProdutos":100.00}
                """;

        mockMvc.perform(post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cliente").value("Ana"))
                .andExpect(jsonPath("$.valorTotal").value(115.90));
    }

    @Test
    void listaPedidos() throws Exception {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setCliente("Bruno");
        when(pedidoFacade.listar()).thenReturn(List.of(pedido));

        mockMvc.perform(get("/pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cliente").value("Bruno"));
    }
}
