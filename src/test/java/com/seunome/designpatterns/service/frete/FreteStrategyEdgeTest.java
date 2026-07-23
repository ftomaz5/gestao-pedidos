package com.seunome.designpatterns.service.frete;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testes de borda das estratégias de frete (limites e case-insensitivity).
 */
class FreteStrategyEdgeTest {

    private final FreteEconomicoStrategy economico = new FreteEconomicoStrategy();
    private final FreteRapidoStrategy rapido = new FreteRapidoStrategy();

    @Test
    void economicoEhGratisExatamenteEm300() {
        BigDecimal frete = economico.calcular("SP", new BigDecimal("300"));
        assertEquals(0, BigDecimal.ZERO.compareTo(frete));
    }

    @Test
    void economicoCobraFixoLogoAbaixoDe300() {
        BigDecimal frete = economico.calcular("SP", new BigDecimal("299.99"));
        assertEquals(0, new BigDecimal("15.90").compareTo(frete));
    }

    @Test
    void rapidoUsaBaseEmSP() {
        BigDecimal frete = rapido.calcular("SP", new BigDecimal("100"));
        assertEquals(0, new BigDecimal("29.90").compareTo(frete));
    }

    @Test
    void suportaEhCaseInsensitiveEExclusivo() {
        assertTrue(economico.suporta("economico"));
        assertTrue(rapido.suporta("Rapido"));
        assertFalse(economico.suporta("RAPIDO"));
        assertFalse(rapido.suporta("ECONOMICO"));
    }
}
