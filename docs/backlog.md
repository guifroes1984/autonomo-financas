# Product Backlog

## Objetivo

Este documento organiza as funcionalidades planejadas para o Autônomo Finanças.

O backlog será atualizado conforme novas necessidades forem identificadas durante o desenvolvimento e o uso do sistema.

---

# MVP

## EPIC 01 — Usuários e autenticação

### US001 — Cadastro de usuário

Como profissional autônomo,
quero criar uma conta,
para acessar e manter meus dados financeiros de forma segura.

### Critérios de aceitação

* O usuário deve informar nome, e-mail e senha.
* O e-mail deve ser único.
* A senha não deve ser armazenada em texto puro.
* O cadastro deve validar os campos obrigatórios.

---

### US002 — Login

Como usuário cadastrado,
quero realizar login,
para acessar meus dados financeiros.

### Critérios de aceitação

* O usuário deve informar e-mail e senha.
* O sistema deve rejeitar credenciais inválidas.
* Após autenticação, o sistema deve fornecer um token de acesso.
* Apenas usuários autenticados poderão acessar os dados financeiros.

---

## EPIC 02 — Receitas

### US003 — Cadastrar receita

Como profissional autônomo,
quero registrar uma receita,
para controlar quanto ganhei em determinado dia.

### Critérios de aceitação

* O valor deve ser obrigatório e maior que zero.
* A data deve ser obrigatória.
* A origem da receita deve ser obrigatória.
* A descrição deve ser opcional.
* A receita deve ser associada ao usuário autenticado.

---

### US004 — Consultar receitas

Como usuário,
quero consultar minhas receitas,
para acompanhar meus ganhos.

### Critérios de aceitação

* O usuário só poderá visualizar suas próprias receitas.
* A consulta deverá permitir filtro por período.
* O sistema deverá exibir valor, data, origem e descrição.

---

### US005 — Editar receita

Como usuário,
quero editar uma receita,
para corrigir informações registradas incorretamente.

### Critérios de aceitação

* Apenas o proprietário poderá editar a receita.
* As mesmas validações do cadastro deverão ser aplicadas.
* O resultado financeiro deverá refletir a alteração.

---

### US006 — Excluir receita

Como usuário,
quero excluir uma receita,
para remover um lançamento incorreto.

### Critérios de aceitação

* Apenas o proprietário poderá excluir a receita.
* O sistema deverá solicitar confirmação antes da exclusão no frontend.
* O resultado financeiro deverá ser atualizado após a exclusão.

---

## EPIC 03 — Despesas

### US007 — Cadastrar despesa

Como profissional autônomo,
quero registrar uma despesa,
para saber quanto gastei para trabalhar.

### Critérios de aceitação

* O valor deve ser obrigatório e maior que zero.
* A data deve ser obrigatória.
* A categoria deve ser obrigatória.
* A descrição deve ser opcional.
* A despesa deve ser associada ao usuário autenticado.

---

### US008 — Consultar despesas

Como usuário,
quero consultar minhas despesas,
para acompanhar meus gastos.

### Critérios de aceitação

* O usuário só poderá visualizar suas próprias despesas.
* A consulta deverá permitir filtro por período.
* O sistema deverá exibir valor, data, categoria e descrição.

---

### US009 — Editar despesa

Como usuário,
quero editar uma despesa,
para corrigir informações registradas incorretamente.

### Critérios de aceitação

* Apenas o proprietário poderá editar a despesa.
* As mesmas validações do cadastro deverão ser aplicadas.
* O resultado financeiro deverá refletir a alteração.

---

### US010 — Excluir despesa

Como usuário,
quero excluir uma despesa,
para remover um lançamento incorreto.

### Critérios de aceitação

* Apenas o proprietário poderá excluir a despesa.
* O sistema deverá solicitar confirmação antes da exclusão no frontend.
* O resultado financeiro deverá ser atualizado após a exclusão.

---

## EPIC 04 — Categorias e origens

### US011 — Consultar categorias de despesa

Como usuário,
quero visualizar categorias de despesa,
para classificar meus gastos.

### Critérios de aceitação

* O sistema deverá disponibilizar categorias iniciais.
* O usuário deverá visualizar categorias padrão e próprias.
* Categorias de outros usuários não poderão ser visualizadas.

---

### US012 — Criar categoria de despesa

Como usuário,
quero criar uma categoria personalizada,
para classificar despesas específicas da minha rotina.

### Critérios de aceitação

* O nome deve ser obrigatório.
* A categoria deve ser associada ao usuário.
* O usuário não poderá criar categorias duplicadas com o mesmo nome em sua conta.

---

### US013 — Consultar origens de receita

Como usuário,
quero visualizar origens de receita,
para identificar de onde vieram meus ganhos.

### Critérios de aceitação

* O sistema deverá disponibilizar origens iniciais.
* O usuário deverá visualizar origens padrão e próprias.
* Origens de outros usuários não poderão ser visualizadas.

---

### US014 — Criar origem de receita

Como usuário,
quero criar uma origem personalizada,
para registrar ganhos que não pertencem às opções padrão.

### Critérios de aceitação

* O nome deve ser obrigatório.
* A origem deve ser associada ao usuário.
* O usuário não poderá criar origens duplicadas com o mesmo nome em sua conta.

---

## EPIC 05 — Dashboard

### US015 — Visualizar resultado diário

Como profissional autônomo,
quero visualizar o resumo financeiro do dia,
para saber se valeu a pena trabalhar.

### Critérios de aceitação

O dashboard deverá apresentar:

* total de receitas;
* total de despesas;
* resultado do dia.

O resultado deverá ser calculado por:

```text
Resultado = Receitas - Despesas
```

Quando não houver movimentações, os totais deverão ser apresentados como zero.

---

### US016 — Consultar resultado de outra data

Como usuário,
quero selecionar uma data,
para analisar o resultado de dias anteriores.

### Critérios de aceitação

* A data atual deverá ser selecionada por padrão.
* O usuário poderá consultar outra data.
* Os totais deverão ser recalculados conforme a data selecionada.

---

## EPIC 06 — Histórico

### US017 — Consultar histórico de lançamentos

Como usuário,
quero consultar minhas receitas e despesas anteriores,
para acompanhar minha movimentação financeira.

### Critérios de aceitação

* O histórico deverá permitir filtro por período.
* O usuário só poderá acessar seus próprios lançamentos.
* Receitas e despesas deverão ser claramente diferenciadas.
* Os lançamentos deverão ser ordenados por data.

---

# Backlog futuro

As funcionalidades abaixo não fazem parte do MVP.

## Gestão financeira

* Controle de contas bancárias
* Controle de carteiras digitais
* Transferências entre contas
* Controle de Pix
* Conciliação financeira
* Controle de recebimentos pendentes

## Trabalho e produtividade

* Registro de início e fim da jornada
* Controle de pausas
* Cálculo de ganho por hora
* Cálculo de ganho por quilômetro
* Comparação entre aplicativos
* Identificação dos horários mais rentáveis

## Veículos

* Cadastro de veículos
* Controle de abastecimento
* Controle de quilometragem
* Histórico de manutenção
* Manutenção preventiva
* Custos com documentação
* Seguro
* Depreciação estimada

## Planejamento financeiro

* Metas diárias
* Metas semanais
* Metas mensais
* Reserva para manutenção
* Reserva para impostos
* Projeções de resultado

## Relatórios

* Relatório semanal
* Relatório mensal
* Comparação entre períodos
* Receitas por origem
* Despesas por categoria
* Exportação em PDF
* Exportação em planilha

## Integrações

* Open Finance
* Integração com aplicativos de entrega
* Importação de extratos
* Leitura automática de comprovantes
* Integração com serviços de mapas

## Inteligência e automação

* Alertas financeiros
* Recomendações de economia
* Análise de desempenho
* Inteligência Artificial
* Previsões de receitas e despesas

## Plataformas

* Aplicativo Android
* Aplicativo iOS
* Notificações push
* Funcionamento offline

---

# Priorização

O backlog seguirá a seguinte classificação:

## MVP

Funcionalidades essenciais para responder:

> **Meu dia valeu a pena financeiramente?**

## Próximas versões

Funcionalidades que melhoram a experiência, mas não são necessárias para validar o produto.

## Futuro

Funcionalidades avançadas, integrações e diferenciais comerciais.

---

# Critério para novas funcionalidades

Antes de adicionar uma nova funcionalidade ao MVP, deverá ser respondida a seguinte pergunta:

> Esta funcionalidade é necessária para que o usuário entenda o resultado financeiro do seu dia?

Caso a resposta seja não, a funcionalidade deverá permanecer no backlog futuro.
