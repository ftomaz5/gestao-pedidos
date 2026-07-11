package com.seunome.designpatterns.service.frete;

import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class FreteEconomicoStrategy implements FreteStrategy {
    @Override
    public boolean suporta(String tipo) {
        return "ECONOMICO".equalsIgnoreCase(tipo);
    }

    @Override
    public BigDecimal calcular(String uf, BigDecimal valorProdutos) {
        // regra simples: frete fixo, gratis acima de R$300
        if (valorProdutos.compareTo(new BigDecimal("300")) >= 0) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal("15.90");
    }
}
