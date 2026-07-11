package com.seunome.designpatterns.service.frete;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class FreteService {

    private final List<FreteStrategy> estrategias;

    public FreteService(List<FreteStrategy> estrategias) {
        this.estrategias = estrategias;
    }

    public BigDecimal calcular(String tipoFrete, String uf, BigDecimal valorProdutos) {
        return estrategias.stream()
                .filter(e -> e.suporta(tipoFrete))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Tipo de frete invalido: " + tipoFrete))
                .calcular(uf, valorProdutos);
    }
}
