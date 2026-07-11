package com.seunome.designpatterns.service.frete;

import java.math.BigDecimal;

public interface FreteStrategy {
    boolean suporta(String tipo);
    BigDecimal calcular(String uf, BigDecimal valorProdutos);
}
