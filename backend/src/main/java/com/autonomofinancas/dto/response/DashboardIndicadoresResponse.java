package com.autonomofinancas.dto.response;

import java.math.BigDecimal;

public class DashboardIndicadoresResponse {

    private IndicadorDiaResponse melhorDia;
    private IndicadorDiaResponse piorDia;

    private BigDecimal mediaDiaria;
    private Long diasTrabalhados;

    private String melhorPlataforma;
    private String maiorCategoriaDespesa;

    public DashboardIndicadoresResponse() {
    }

    public DashboardIndicadoresResponse(IndicadorDiaResponse melhorDia, IndicadorDiaResponse piorDia,
            BigDecimal mediaDiaria, Long diasTrabalhados, String melhorPlataforma, String maiorCategoriaDespesa) {
        this.melhorDia = melhorDia;
        this.piorDia = piorDia;
        this.mediaDiaria = mediaDiaria;
        this.diasTrabalhados = diasTrabalhados;
        this.melhorPlataforma = melhorPlataforma;
        this.maiorCategoriaDespesa = maiorCategoriaDespesa;
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

    public String getMelhorPlataforma() {
        return melhorPlataforma;
    }

    public void setMelhorPlataforma(String melhorPlataforma) {
        this.melhorPlataforma = melhorPlataforma;
    }

    public String getMaiorCategoriaDespesa() {
        return maiorCategoriaDespesa;
    }

    public void setMaiorCategoriaDespesa(String maiorCategoriaDespesa) {
        this.maiorCategoriaDespesa = maiorCategoriaDespesa;
    }

}
