package com.seunome.designpatterns.service.frete;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes do padrao Strategy aplicado ao calculo de frete.
 */
class FreteServiceTest {

    private final FreteService freteService = new FreteService(
            List.of(new FreteEconomicoStrategy(), new FreteRapidoStrategy()));

    @Test
    void economicoGratisAcimaDe300() {
        BigDecimal frete = freteService.calcular("ECONOMICO", "SP", new BigDecimal("350"));
        assertEquals(0, BigDecimal.ZERO.compareTo(frete));
    }

    @Test
    void economicoCobraFixoAbaixoDe300() {
        BigDecimal frete = freteService.calcular("ECONOMICO", "SP", new BigDecimal("100"));
        assertEquals(0, new BigDecimal("15.90").compareTo(frete));
    }

    @Test
    void rapidoMaisCaroForaDeSP() {
        BigDecimal sp = freteService.calcular("RAPIDO", "SP", new BigDecimal("100"));
        BigDecimal rj = freteService.calcular("RAPIDO", "RJ", new BigDecimal("100"));
        assertTrue(rj.compareTo(sp) > 0);
    }

    @Test
    void tipoInvalidoLancaExcecao() {
        assertThrows(IllegalArgumentException.class,
                () -> freteService.calcular("EXPRESSO", "SP", new BigDecimal("100")));
    }
}
