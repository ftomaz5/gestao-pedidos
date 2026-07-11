# 🎯 API de Gestão de Pedidos — Design Patterns na Prática

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
git clone https://github.com/SEU_USUARIO/gestao-pedidos.git
cd gestao-pedidos
mvn spring-boot:run
```

> Requisitos: **JDK 17+** e **Maven 3.9+** instalados.
> Se preferir usar o Maven Wrapper (`./mvnw`), gere-o uma vez com
> `mvn -N wrapper:wrapper` e faça o commit dos arquivos gerados.

- Swagger UI: http://localhost:8080/swagger-ui.html
- Console H2: http://localhost:8080/h2-console (JDBC URL: `jdbc:h2:mem:pedidos`, user `sa`)

## 📫 Exemplo de uso

Criar um pedido:

```bash
curl -X POST http://localhost:8080/pedidos \
  -H "Content-Type: application/json" \
  -d '{"cliente":"Maria","cep":"01001000","tipoFrete":"RAPIDO","valorProdutos":150.00}'
```

Listar pedidos:

```bash
curl http://localhost:8080/pedidos
```

## 🧪 Testes

```bash
mvn test
```

Os testes cobrem as estratégias de frete (padrão Strategy), incluindo as regras
de negócio e o tratamento de tipo de frete inválido.

## 🔧 Como evoluir

- Adicione uma nova estratégia de frete implementando `FreteStrategy` — o Spring
  injeta automaticamente, sem alterar o `FreteService` (Open/Closed Principle).
- Troque o H2 por PostgreSQL alterando apenas o `application.yml`.
- Acrescente o padrão **Observer** para notificar por e-mail a cada pedido criado.

## 📂 Estrutura

```
gestao-pedidos/
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
    └── test/java/com/seunome/designpatterns/service/frete/FreteServiceTest.java
```

## 📚 Referências

- Refactoring.guru — Design Patterns
- *Design Patterns: Elements of Reusable OO Software* (GoF)
- Documentação oficial do Spring Framework

---

> 💡 Antes de publicar, substitua `SEU_USUARIO` pela sua conta do GitHub e,
> se quiser, troque o `groupId`/pacote `com.seunome` pelo seu domínio.
