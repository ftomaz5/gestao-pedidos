package com.seunome.designpatterns;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Smoke test: garante que o contexto Spring sobe (beans, JPA/H2 e Feign) sem erros.
 */
@SpringBootTest
class GestaoPedidosApplicationTests {

    @Test
    void contextLoads() {
        // Se o contexto falhar ao carregar, este teste falha automaticamente.
    }
}
