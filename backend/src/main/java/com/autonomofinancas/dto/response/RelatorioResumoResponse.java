package com.autonomofinancas.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RelatorioResumoResponse {

    private LocalDate inicio;
    private LocalDate fim;

    private BigDecimal totalReceitas;
    private BigDecimal totalDespesas;
    private BigDecimal saldo;

    private BigDecimal mediaDiaria;
    private Long diasTrabalhados;

    private IndicadorDiaResponse melhorDia;
    private IndicadorDiaResponse piorDia;

    public RelatorioResumoResponse() {
    }

    public RelatorioResumoResponse(
            LocalDate inicio,
            LocalDate fim,
            BigDecimal totalReceitas,
            BigDecimal totalDespesas,
            BigDecimal saldo,
            BigDecimal mediaDiaria,
            Long diasTrabalhados,
            IndicadorDiaResponse melhorDia,
            IndicadorDiaResponse piorDia) {

        this.inicio = inicio;
        this.fim = fim;
        this.totalReceitas = totalReceitas;
        this.totalDespesas = totalDespesas;
        this.saldo = saldo;
        this.mediaDiaria = mediaDiaria;
        this.diasTrabalhados = diasTrabalhados;
        this.melhorDia = melhorDia;
        this.piorDia = piorDia;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getFim() {
        return fim;
    }

    public void setFim(LocalDate fim) {
        this.fim = fim;
    }

    public BigDecimal getTotalReceitas() {
        return totalReceitas;
    }

    public void setTotalReceitas(BigDecimal totalReceitas) {
        this.totalReceitas = totalReceitas;
    }

    public BigDecimal getTotalDespesas() {
        return totalDespesas;
    }

    public void setTotalDespesas(BigDecimal totalDespesas) {
        this.totalDespesas = totalDespesas;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public BigDecimal getMediaDiaria() {
        return mediaDiaria;
    }

    public void setMediaDiaria(BigDecimal mediaDiaria) {
        this.mediaDiaria = mediaDiaria;
    }

    public Long getDiasTrabalhados() {
        return diasTrabalhados;
    }

    public void setDiasTrabalhados(Long diasTrabalhados) {
        this.diasTrabalhados = diasTrabalhados;
    }

    public IndicadorDiaResponse getMelhorDia() {
        return melhorDia;
    }

    public void setMelhorDia(IndicadorDiaResponse melhorDia) {
        this.melhorDia = melhorDia;
    }

    public IndicadorDiaResponse getPiorDia() {
        return piorDia;
    }

    public void setPiorDia(IndicadorDiaResponse piorDia) {
        this.piorDia = piorDia;
    }

}
