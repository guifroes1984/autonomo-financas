package com.autonomofinancas.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

import com.autonomofinancas.entity.enums.TipoLancamento;

public class LancamentoResponse {

    private Long id;

    private TipoLancamento tipo;

    private String descricao;

    private BigDecimal valor;

    private LocalDate dataLancamento;

    private CategoriaSimplificadaResponse categoria;

    private PlataformaSimplificadaResponse plataforma;

    private String observacao;

    private OffsetDateTime dataCriacao;

    private OffsetDateTime dataAtualizacao;

    public LancamentoResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public CategoriaSimplificadaResponse getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaSimplificadaResponse categoria) {
        this.categoria = categoria;
    }

    public PlataformaSimplificadaResponse getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(PlataformaSimplificadaResponse plataforma) {
        this.plataforma = plataforma;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public OffsetDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(OffsetDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public OffsetDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(OffsetDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

}
