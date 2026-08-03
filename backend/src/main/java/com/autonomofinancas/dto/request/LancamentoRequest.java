package com.autonomofinancas.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.autonomofinancas.entity.enums.TipoLancamento;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LancamentoRequest {

    @NotNull(message = "O tipo é obrigatório.")
    private TipoLancamento tipo;

    @NotBlank(message = "A descrição é obrigatória.")
    @Size(max = 150, message = "A descrição deve ter no máximo 150 caracteres.")
    private String descricao;

    @NotNull(message = "O valor é obrigatório.")
    @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero.")
    private BigDecimal valor;

    @NotNull(message = "A data do lançamento é obrigatória.")
    private LocalDate dataLancamento;

    @NotNull(message = "A categoria é obrigatória.")
    private Long categoriaId;

    private Long plataformaId;

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
