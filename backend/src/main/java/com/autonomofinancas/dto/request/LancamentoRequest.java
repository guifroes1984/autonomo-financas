package com.autonomofinancas.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.autonomofinancas.entity.enums.TipoLancamento;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LancamentoRequest {

    @Schema(description = "Tipo do lançamento financeiro.", example = "DESPESA")
    @NotNull(message = "O tipo é obrigatório.")
    private TipoLancamento tipo;

    @Schema(description = "Descrição do lançamento.", example = "Abastecimento da motocicleta")
    @NotBlank(message = "A descrição é obrigatória.")
    @Size(max = 150, message = "A descrição deve ter no máximo 150 caracteres.")
    private String descricao;

    @Schema(description = "Valor do lançamento.", example = "85.50")
    @NotNull(message = "O valor é obrigatório.")
    @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero.")
    private BigDecimal valor;

    @Schema(description = "Data em que o lançamento ocorreu.", example = "2026-08-06")
    @NotNull(message = "A data do lançamento é obrigatória.")
    private LocalDate dataLancamento;

    @Schema(description = "Identificador da categoria.", example = "1")
    @NotNull(message = "A categoria é obrigatória.")
    private Long categoriaId;

    @Schema(description = "Identificador da plataforma. Obrigatório apenas para lançamentos do tipo RECEITA.", example = "2")
    private Long plataformaId;

    @Schema(description = "Observação opcional sobre o lançamento.", example = "Abastecimento realizado antes do turno da noite.")
    @Size(max = 500, message = "A observação deve ter no máximo 500 caracteres.")
    private String observacao;

    public LancamentoRequest() {
    }

    public TipoLancamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoLancamento tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public Long getPlataformaId() {
        return plataformaId;
    }

    public void setPlataformaId(Long plataformaId) {
        this.plataformaId = plataformaId;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

}
