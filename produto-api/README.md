# Desafio - Sistema de Gerenciamento de Produtos e Clientes 🛒

## 🎯 Objetivo
Desenvolver um sistema de gerenciamento de produtos e clientes em Java, aplicando **Orientação a Objetos** e **Boas Práticas de Desenvolvimento**.

## 📝 Tarefas

### 1. "Mavenizar" o Projeto
Transforme o projeto em um projeto Maven para facilitar a gestão de dependências e o build do projeto.

#### Passos:
1. Crie um arquivo `pom.xml` na raiz do projeto.
2. Adicione as dependências necessárias, como `h2 connector` e `junit`.
3. Configure o plugin do Maven para compilar e testar o projeto.

### 2. Organizar o Projeto em Módulos
Organize o projeto em módulos para melhorar a estrutura e a manutenção do código. Utilize o critério de agrupamento de código que achar mais coerente. Discuta com o professor na monitoria como podemos organizar um projeto seguindo *package by layer* e *package by feature*.

### 3. Execute as Classes de Teste
Execute as classes de teste (`Testa*`). Todas têm erro, e você deve implementar corretamente o tratamento de exceção.

#### Passos:
1. Identifique os erros nas classes de teste (ou em outro ponto do projeto).
2. Implemente o tratamento de exceção adequado.
3. Garanta que todos os testes passem sem erros.

### 4. Crie uma Exceção de Negócio
Crie uma exceção de negócio e utilize-a quando uma entidade consultada por ID não for encontrada no banco de dados.

#### Passos:
1. Crie uma classe de exceção personalizada, por exemplo, `EntidadeNaoEncontradaException`.
2. Lance essa exceção nos métodos de consulta por ID quando a entidade não for encontrada.
3. Crie classes de teste para testar o lançamento correto da exceção.