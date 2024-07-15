# Desafio - Programa de Bolsas Backend (Spring Boot) da Compass e AWS

## Introdução

Este repositório contém o código fonte para o Desafio 2 do Programa de Bolsas Backend (Spring Boot) da Compass e AWS, que visa desenvolver uma aplicação backend para auxiliar desabrigados em enchentes. A aplicação controla entradas e saídas de doações, mostra a duração do estoque do abrigo e dos centros de distribuição, e a quantidade de itens recebidos pelos centros.

## Funcionalidades

### Registro de Doações

- Cadastro, leitura, edição e exclusão de itens doados.
- Direcionamento de lotes de itens para centros de distribuição específicos.
- Itens obrigatórios:
  - **Roupas**: Descrição, Gênero (M/F), Tamanho (Infantil/PP/P/M/G/GG)
  - **Produtos de higiene**: Descrição, Sabonete, Escova de dentes, Pasta de dentes, Absorventes
  - **Alimentos**: Descrição, Quantidade, Unidade de medida, Validade
- Limite de armazenamento de 1000 itens por tipo em cada centro de distribuição.
- Cadastro de doações via arquivo CSV.

### Registro de Abrigos

- Cadastro, leitura, edição e exclusão de abrigos aptos a receber doações.
- Armazenamento de nome, endereço, responsável, telefone, email de contato, capacidade e ocupação (%) de cada abrigo.
- Validação de dados inseridos.
- Listagem da quantidade de cada item recebido pelo abrigo na busca.

### Ordem de Pedido

- Listagem das necessidades dos abrigos (item e atributos).
- Indicação dos centros de distribuição com os itens solicitados.
- Ordenação da listagem por centro que dispõe da quantidade solicitada.
- Envio de ordem de pedido para um ou mais centros de distribuição pelo abrigo.

### Checkout de Itens

- Lista de pedidos para aceitar ou recusar em cada centro de distribuição.
- Dedução da quantidade enviada de cada item do total disponível no centro após a aceitação do pedido.
- Armazenamento da quantidade de itens enviados para cada abrigo.
- Informação do motivo da recusa em caso de recusa do pedido.

### Transferência de Doações

- Transferência de itens entre centros de distribuição.
- Limite de 1000 itens de cada tipo em cada centro de distribuição.
- Limite de 200 itens de cada tipo em cada abrigo.

## Requisitos de Sistema

- **Tecnologias**: Java 17
- **Framework**: Sem framework Spring Boot neste momento
- **Banco de dados**: De livre escolha
- **Boas práticas**: O código deve seguir as boas práticas de desenvolvimento de software.
- **Versionamento**: Código fonte no repositório do GitHub.

## Visão Geral da Aplicação

A aplicação é uma solução backend para auxiliar na gestão de doações e na organização da ajuda humanitária para desabrigados em situações de enchentes. Ela permite o registro de doações, abrigos e pedidos, facilitando a logística de distribuição de itens e o acompanhamento do estoque.

---
