# 🎯 API de Gestão de Pedidos — Design Patterns na Prática

![CI](https://github.com/ftomaz5/gestao-pedidos/actions/workflows/ci.yml/badge.svg)
![Java](https://img.shields.io/badge/Java-17-007396?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.2-6DB33F?logo=springboot&logoColor=white)
![License](https://img.shields.io/badge/license-MIT-green)

API REST em **Spring Boot** que demonstra Padrões de Projeto (GoF) aplicados de
forma real, desenvolvida como desafio do bootcamp *"Design Patterns com Java: Dos
Clássicos (GoF) ao Spring Framework"* (DIO).

## 🧩 Padrões implementados

| Padrão | Onde | Descrição |
|--------|------|-----------|
| **Singleton** | Beans do Spring (`@Service`, `@Repository`, `@RestController`) | O container gerencia instâncias únicas por padrão |
| **Strategy** | `FreteStrategy` + implementações | Cálculo de frete intercambiável em runtime |
| **Facade** | `PedidoFacade` | Esconde a orquestração (ViaCEP + frete + persistência) |
| **Repository** | Spring Data JPA | Abstrai o acesso a dados |

## 🛠️ Tecnologias

- Java 17
- Spring Boot 3.3.2 (Web, Data JPA)
- Spring Cloud OpenFeign (consumo da API ViaCEP)
- Banco H2 em memória
- SpringDoc / Swagger UI
- JUnit 5

## 🚀 Como executar

```bash
git clone https://github.com/ftomaz5/gestao-pedidos.git
cd gestao-pedidos
mvn spring-boot:run
```

> Requisitos: **JDK 17+** e **Maven 3.9+** instalados.
> Se preferir usar o Maven Wrapper (`./mvnw`), gere-o uma vez com
> `mvn -N wrapper:wrapper` e faça o commit dos arquivos gerados.

- Swagger UI: http://localhost:8080/swagger-ui.html
- Console H2: http://localhost:8080/h2-console (JDBC URL: `jdbc:h2:mem:pedidos`, user `sa`)

## 🔧 Configuração e variáveis de ambiente

Toda a configuração vive em [`application.yml`](src/main/resources/application.yml).
Para rodar contra **PostgreSQL** em vez do H2 em memória, sobreponha via variáveis
de ambiente (Spring lê `SPRING_*` automaticamente) — sem tocar no código:

| Variável | Descrição | Padrão (H2) |
|----------|-----------|-------------|
| `SERVER_PORT` | Porta HTTP da aplicação | `8080` |
| `SPRING_DATASOURCE_URL` | JDBC URL do banco | `jdbc:h2:mem:pedidos` |
| `SPRING_DATASOURCE_USERNAME` | Usuário do banco | `sa` |
| `SPRING_DATASOURCE_PASSWORD` | Senha do banco | *(vazio)* |
| `SPRING_JPA_HIBERNATE_DDL_AUTO` | Estratégia de schema | `update` |

O cliente **ViaCEP** é externo e não exige credenciais (`https://viacep.com.br/ws`).

## 📫 Exemplos de uso (cURL)

### Criar um pedido — frete `RAPIDO`

```bash
curl -X POST http://localhost:8080/pedidos \
  -H "Content-Type: application/json" \
  -d '{"cliente":"Maria","cep":"01001000","tipoFrete":"RAPIDO","valorProdutos":150.00}'
```

Resposta (o endereço é resolvido pelo ViaCEP e o frete pela Strategy):

```json
{
  "id": 1,
  "cliente": "Maria",
  "valorProdutos": 150.00,
  "valorFrete": 29.90,
  "valorTotal": 179.90,
  "endereco": { "cep": "01001-000", "localidade": "São Paulo", "uf": "SP" }
}
```

### Criar um pedido — frete `ECONOMICO` (grátis acima de R$300)

```bash
curl -X POST http://localhost:8080/pedidos \
  -H "Content-Type: application/json" \
  -d '{"cliente":"João","cep":"30140071","tipoFrete":"ECONOMICO","valorProdutos":350.00}'
```

### Listar pedidos

```bash
curl http://localhost:8080/pedidos
```

> Tipos de frete válidos: `RAPIDO` e `ECONOMICO`. Um tipo desconhecido retorna
> erro de validação (`IllegalArgumentException`).

## 🧪 Testes e cobertura

```bash
mvn -B verify          # compila, roda os testes e gera o relatório de cobertura
```

A suíte cobre as três camadas de padrões de projeto:

| Teste | Foco |
|-------|------|
| `FreteServiceTest` / `FreteStrategyEdgeTest` | **Strategy** — regras e bordas do frete |
| `PedidoFacadeTest` (Mockito) | **Facade** — cache de endereço, ViaCEP e persistência |
| `PedidoControllerTest` (`@WebMvcTest`) | Camada web — serialização de request/response |
| `GestaoPedidosApplicationTests` | Smoke test do contexto Spring (JPA + Feign) |

O relatório de cobertura (JaCoCo) é gerado em `target/site/jacoco/index.html` e
publicado como artefato no CI.

## 🔧 Como evoluir

- Adicione uma nova estratégia de frete implementando `FreteStrategy` — o Spring
  injeta automaticamente, sem alterar o `FreteService` (Open/Closed Principle).
- Troque o H2 por PostgreSQL alterando apenas o `application.yml`.
- Acrescente o padrão **Observer** para notificar por e-mail a cada pedido criado.

## 📂 Estrutura

```
gestao-pedidos/
├── .github/workflows/       # ci.yml (build+testes+cobertura) e release.yml
├── pom.xml
├── README.md
└── src/
    ├── main/
    │   ├── java/com/seunome/designpatterns/
    │   │   ├── GestaoPedidosApplication.java
    │   │   ├── client/ViaCepClient.java
    │   │   ├── controller/PedidoController.java
    │   │   ├── domain/model/{Pedido,Endereco}.java
    │   │   ├── domain/repository/{PedidoRepository,EnderecoRepository}.java
    │   │   └── service/
    │   │       ├── PedidoFacade.java
    │   │       └── frete/{FreteStrategy,FreteService,FreteRapidoStrategy,FreteEconomicoStrategy}.java
    │   └── resources/application.yml
    └── test/java/com/seunome/designpatterns/
        ├── GestaoPedidosApplicationTests.java
        ├── controller/PedidoControllerTest.java
        └── service/
            ├── PedidoFacadeTest.java
            └── frete/{FreteServiceTest,FreteStrategyEdgeTest}.java
```

## 📚 Referências

- Refactoring.guru — Design Patterns
- *Design Patterns: Elements of Reusable OO Software* (GoF)
- Documentação oficial do Spring Framework

---

> 💡 Antes de publicar, substitua `SEU_USUARIO` pela sua conta do GitHub e,
> se quiser, troque o `groupId`/pacote `com.seunome` pelo seu domínio.
