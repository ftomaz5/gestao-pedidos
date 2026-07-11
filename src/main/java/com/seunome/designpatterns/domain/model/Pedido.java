package com.seunome.designpatterns.domain.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cliente;
    private BigDecimal valorProdutos;
    private BigDecimal valorFrete;
    private BigDecimal valorTotal;

    @ManyToOne
    private Endereco endereco;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }
    public BigDecimal getValorProdutos() { return valorProdutos; }
    public void setValorProdutos(BigDecimal v) { this.valorProdutos = v; }
    public BigDecimal getValorFrete() { return valorFrete; }
    public void setValorFrete(BigDecimal v) { this.valorFrete = v; }
    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal v) { this.valorTotal = v; }
    public Endereco getEndereco() { return endereco; }
    public void setEndereco(Endereco e) { this.endereco = e; }
}
