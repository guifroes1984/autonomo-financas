# Architecture

## Objetivo

Este documento descreve a arquitetura inicial do projeto **Autônomo Finanças**.

Seu objetivo é orientar o desenvolvimento e manter um padrão técnico ao longo da evolução do sistema.

---

# Visão Geral

O projeto será desenvolvido utilizando uma arquitetura de **Monólito Modular**.

Nesta abordagem, toda a aplicação será executada em um único backend, porém organizada em módulos independentes.

Essa estratégia reduz a complexidade inicial e facilita futuras evoluções.

---

# Arquitetura da Solução

```text
Frontend (Angular)
        │
        │ HTTP / REST
        ▼
Backend (Spring Boot)
        │
        ▼
PostgreSQL
```

---

# Stack Tecnológica

## Backend

* Java 17
* Spring Boot 3
* Spring Security
* Spring Data JPA
* Flyway
* Bean Validation
* Maven

---

## Frontend

* Angular
* Bootstrap
* TypeScript

---

## Banco de Dados

* PostgreSQL

---

## Ferramentas

* Git
* GitHub
* Docker
* Swagger / OpenAPI

---

# Organização do Backend

A aplicação será organizada em módulos de negócio.

```text
com.autonomofinancas

├── auth
├── usuario
├── receita
├── despesa
├── categoria
├── dashboard
└── shared
```

Cada módulo será responsável apenas pelo seu domínio.

---

# Organização do Frontend

O frontend seguirá uma estrutura baseada em funcionalidades.

```text
src/app

├── auth
├── dashboard
├── receita
├── despesa
├── categoria
├── shared
└── core
```

---

# Persistência

O acesso ao banco será realizado utilizando:

* Spring Data JPA
* Hibernate
* PostgreSQL

Todas as alterações estruturais serão controladas pelo Flyway.

---

# API

O backend disponibilizará uma API REST.

Formato utilizado:

* JSON

Documentação:

* Swagger/OpenAPI

---

# Segurança

A autenticação será realizada utilizando:

* Spring Security
* JWT (JSON Web Token)

Cada usuário poderá acessar apenas seus próprios dados.

---

# Controle de Versão

Fluxo simplificado de Git:

```text
main
  │
  └── feature/*
```

Os commits seguirão o padrão Conventional Commits.

---

# Princípios Arquiteturais

Durante o desenvolvimento serão priorizados:

* Separação de responsabilidades.
* Baixo acoplamento.
* Alta coesão.
* Código limpo.
* Simplicidade.
* Evolução incremental.
* Testabilidade.

---

# Evolução da Arquitetura

O projeto inicia como um monólito modular.

Caso exista necessidade futura, a arquitetura permitirá evolução para microsserviços sem grandes impactos nas regras de negócio.

Essa decisão será reavaliada conforme o crescimento da aplicação.
