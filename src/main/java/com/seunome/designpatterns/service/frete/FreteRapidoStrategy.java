package com.seunome.designpatterns.service.frete;

import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class FreteRapidoStrategy implements FreteStrategy {
    @Override
    public boolean suporta(String tipo) {
        return "RAPIDO".equalsIgnoreCase(tipo);
    }

    @Override
    public BigDecimal calcular(String uf, BigDecimal valorProdutos) {
        // regra simples: mais caro para UFs fora de SP
        BigDecimal base = new BigDecimal("29.90");
        return "SP".equalsIgnoreCase(uf) ? base : base.add(new BigDecimal("20"));
    }
}
