# Business Rules

## Objetivo

Este documento registra as principais regras de negócio do Autônomo Finanças.

As regras descritas aqui devem orientar a implementação do backend, frontend, banco de dados e testes automatizados.

---

## Usuário

### RN001 — Isolamento dos dados

Cada usuário só pode visualizar, editar ou excluir seus próprios dados.

Um usuário não pode acessar receitas, despesas, categorias ou origens pertencentes a outro usuário.

---

### RN002 — Identificação do usuário

Toda receita, despesa, categoria personalizada e origem personalizada deve estar associada a um usuário.

---

## Receitas

### RN003 — Valor da receita

O valor de uma receita é obrigatório e deve ser maior que zero.

---

### RN004 — Data da receita

Toda receita deve possuir uma data obrigatória.

A data representa o dia em que o ganho foi gerado, mesmo que o dinheiro seja recebido ou transferido posteriormente.

---

### RN005 — Origem da receita

Toda receita deve estar associada a uma origem.

Exemplos:

* iFood
* Uber
* 99
* DeliveryUp
* Particular
* Outro

---

### RN006 — Descrição da receita

A descrição da receita é opcional.

---

### RN007 — Registro diário por origem

No MVP, o usuário poderá registrar o valor total obtido em cada origem em determinado dia.

Não será necessário registrar cada entrega individualmente.

---

## Despesas

### RN008 — Valor da despesa

O valor de uma despesa é obrigatório e deve ser maior que zero.

---

### RN009 — Data da despesa

Toda despesa deve possuir uma data obrigatória.

---

### RN010 — Categoria da despesa

Toda despesa deve estar associada a uma categoria.

Exemplos:

* Combustível
* Manutenção
* Alimentação
* Estacionamento
* Pedágio
* Documentação
* Outros

---

### RN011 — Descrição da despesa

A descrição da despesa é opcional.

---

## Resultado financeiro

### RN012 — Cálculo do resultado

O resultado financeiro deve ser calculado automaticamente pela seguinte regra:

```text
Resultado = Total de receitas - Total de despesas
```

---

### RN013 — Resultado não armazenado

O resultado diário não deve ser informado manualmente pelo usuário.

Inicialmente, ele também não será armazenado em uma tabela própria, pois poderá ser calculado a partir das receitas e despesas.

---

### RN014 — Atualização automática

Sempre que uma receita ou despesa for cadastrada, alterada ou excluída, o resultado correspondente deve ser recalculado.

---

### RN015 — Resultado negativo

O sistema deve permitir resultado negativo.

Um resultado negativo representa que as despesas foram maiores que as receitas no período consultado.

---

## Dashboard

### RN016 — Resumo diário

O dashboard deve apresentar, no mínimo:

* total de receitas do dia;
* total de despesas do dia;
* resultado do dia.

---

### RN017 — Data padrão

Ao abrir o dashboard, o sistema deve apresentar inicialmente os dados da data atual.

---

### RN018 — Período sem movimentação

Quando não existirem receitas ou despesas em uma data, os totais devem ser apresentados como zero.

---

## Lançamentos

### RN019 — Edição

O usuário poderá editar suas próprias receitas e despesas.

Após a edição, os totais e o resultado devem ser atualizados.

---

### RN020 — Exclusão

O usuário poderá excluir suas próprias receitas e despesas.

Após a exclusão, os totais e o resultado devem ser atualizados.

---

## Categorias e origens

### RN021 — Categorias iniciais

O sistema poderá disponibilizar categorias padrão para facilitar o primeiro uso.

---

### RN022 — Origens iniciais

O sistema poderá disponibilizar origens padrão para facilitar o primeiro uso.

---

### RN023 — Personalização

O usuário poderá criar categorias de despesa e origens de receita personalizadas.

Essa funcionalidade deverá preservar o isolamento dos dados entre usuários.

---

## Valores monetários

### RN024 — Precisão monetária

Valores monetários devem utilizar duas casas decimais.

No backend e no banco de dados, não devem ser armazenados utilizando tipos de ponto flutuante, como `float` ou `double`.

---

## Datas

### RN025 — Data sem horário

Receitas e despesas do MVP serão associadas a uma data de referência.

O horário exato do lançamento não será obrigatório para o cálculo do resultado diário.

---

## Escopo do MVP

### RN026 — Contas financeiras

O MVP não controlará contas bancárias, carteiras digitais ou o local em que o dinheiro está disponível.

---

### RN027 — Recebimento e competência

No MVP, será considerada a data em que o ganho foi gerado.

A data de recebimento ou transferência do dinheiro não será controlada nesta versão.

---

### RN028 — Jornadas de trabalho

O MVP não controlará início, pausa ou encerramento da jornada de trabalho.

---

## Regra principal do produto

O sistema deve permitir que o usuário responda rapidamente:

> **Meu dia valeu a pena financeiramente?**
