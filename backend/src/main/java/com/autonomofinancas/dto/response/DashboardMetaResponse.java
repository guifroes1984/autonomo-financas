package com.autonomofinancas.dto.response;

import java.math.BigDecimal;

public class DashboardMetaResponse {

    private BigDecimal metaDiaria;

    private BigDecimal saldoHoje;

    private BigDecimal percentualMetaDiaria;

    private BigDecimal valorRestanteMetaDiaria;

    private Boolean metaDiariaAlcancada;

    private BigDecimal metaMensal;

    private BigDecimal saldoMes;

    private BigDecimal percentualMetaMensal;

    private BigDecimal valorRestanteMetaMensal;

    private Boolean metaMensalAtingida;

    public DashboardMetaResponse() {
    }

    public DashboardMetaResponse(BigDecimal metaDiaria, BigDecimal saldoHoje, BigDecimal percentualMetaDiaria,
            BigDecimal valorRestanteMetaDiaria, Boolean metaDiariaAlcancada, BigDecimal metaMensal, BigDecimal saldoMes,
            BigDecimal percentualMetaMensal, BigDecimal valorRestanteMetaMensal, Boolean metaMensalAtingida) {
        this.metaDiaria = metaDiaria;
        this.saldoHoje = saldoHoje;
        this.percentualMetaDiaria = percentualMetaDiaria;
        this.valorRestanteMetaDiaria = valorRestanteMetaDiaria;
        this.metaDiariaAlcancada = metaDiariaAlcancada;
        this.metaMensal = metaMensal;
        this.saldoMes = saldoMes;
        this.percentualMetaMensal = percentualMetaMensal;
        this.valorRestanteMetaMensal = valorRestanteMetaMensal;
        this.metaMensalAtingida = metaMensalAtingida;
    }

    public BigDecimal getMetaDiaria() {
        return metaDiaria;
    }

    public void setMetaDiaria(BigDecimal metaDiaria) {
        this.metaDiaria = metaDiaria;
    }

    public BigDecimal getSaldoHoje() {
        return saldoHoje;
    }

    public void setSaldoHoje(BigDecimal saldoHoje) {
        this.saldoHoje = saldoHoje;
    }

    public BigDecimal getPercentualMetaDiaria() {
        return percentualMetaDiaria;
    }

    public void setPercentualMetaDiaria(BigDecimal percentualMetaDiaria) {
        this.percentualMetaDiaria = percentualMetaDiaria;
    }

    public BigDecimal getValorRestanteMetaDiaria() {
        return valorRestanteMetaDiaria;
    }

    public void setValorRestanteMetaDiaria(BigDecimal valorRestanteMetaDiaria) {
        this.valorRestanteMetaDiaria = valorRestanteMetaDiaria;
    }

    public Boolean getMetaDiariaAlcancada() {
        return metaDiariaAlcancada;
    }

    public void setMetaDiariaAlcancada(Boolean metaDiariaAlcancada) {
        this.metaDiariaAlcancada = metaDiariaAlcancada;
    }

    public BigDecimal getMetaMensal() {
        return metaMensal;
    }

    public void setMetaMensal(BigDecimal metaMensal) {
        this.metaMensal = metaMensal;
    }

    public BigDecimal getSaldoMes() {
        return saldoMes;
    }

    public void setSaldoMes(BigDecimal saldoMes) {
        this.saldoMes = saldoMes;
    }

    public BigDecimal getPercentualMetaMensal() {
        return percentualMetaMensal;
    }

    public void setPercentualMetaMensal(BigDecimal percentualMetaMensal) {
        this.percentualMetaMensal = percentualMetaMensal;
    }

    public BigDecimal getValorRestanteMetaMensal() {
        return valorRestanteMetaMensal;
    }

    public void setValorRestanteMetaMensal(BigDecimal valorRestanteMetaMensal) {
        this.valorRestanteMetaMensal = valorRestanteMetaMensal;
    }

    public Boolean getMetaMensalAtingida() {
        return metaMensalAtingida;
    }

    public void setMetaMensalAtingida(Boolean metaMensalAtingida) {
        this.metaMensalAtingida = metaMensalAtingida;
    }

}
