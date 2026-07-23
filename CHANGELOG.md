# Changelog

Todas as mudanças relevantes deste projeto são documentadas aqui.
O formato segue [Keep a Changelog](https://keepachangelog.com/pt-BR/1.1.0/)
e o projeto adota [Versionamento Semântico](https://semver.org/lang/pt-BR/).

## [Não lançado]

### Adicionado
- **CI/CD** com GitHub Actions: `mvn -B verify` (build + testes + cobertura JaCoCo).
- Workflow de release por tag semver (`vX.Y.Z`) que anexa o JAR à GitHub Release.
- Plugin JaCoCo para relatório de cobertura de testes.
- Testes: `PedidoFacadeTest` (Mockito), `PedidoControllerTest` (`@WebMvcTest`),
  `GestaoPedidosApplicationTests` (contexto) e `FreteStrategyEdgeTest` (bordas).
- Arquivo `LICENSE` (MIT), guia de contribuição, template de PR, Dependabot e
  validação de título de PR (Conventional Commits).
- Documentação: exemplos cURL com respostas, tabela de variáveis de ambiente e mapa de testes.

## [1.0.0]

### Adicionado
- API REST de gestão de pedidos aplicando Design Patterns (Singleton, Strategy, Facade, Repository).
- Integração com ViaCEP via OpenFeign e persistência com Spring Data JPA (H2).
