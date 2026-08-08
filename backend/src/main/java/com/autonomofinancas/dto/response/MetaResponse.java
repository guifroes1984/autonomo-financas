package com.autonomofinancas.dto.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

public class MetaResponse {

    @Schema(description = "Identificador da meta.", example = "1")
    private Long id;

    @Schema(description = "Valor da meta diária.", example = "200.00")
    private BigDecimal metaDiaria;

    @Schema(description = "Valor da meta mensal.", example = "5000.00")
    private BigDecimal metaMensal;

    @Schema(description = "Data de criação.", example = "2026-08-07T10:30:00Z")
    private OffsetDateTime dataCriacao;

    @Schema(description = "Data da última atualização.", example = "2026-08-07T10:30:00Z")
    private OffsetDateTime dataAtualizacao;

    public MetaResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMetaDiaria() {
        return metaDiaria;
    }

    public void setMetaDiaria(BigDecimal metaDiaria) {
        this.metaDiaria = metaDiaria;
    }

    public BigDecimal getMetaMensal() {
        return metaMensal;
    }

    public void setMetaMensal(BigDecimal metaMensal) {
        this.metaMensal = metaMensal;
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
