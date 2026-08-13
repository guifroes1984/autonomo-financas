package com.autonomofinancas.dto.response;

import java.math.BigDecimal;

public class RelatorioPlataformaResponse {

    private String plataforma;
    private BigDecimal total;
    private BigDecimal percentual;

    public RelatorioPlataformaResponse() {
    }

    public RelatorioPlataformaResponse(
            String plataforma,
            BigDecimal total,
            BigDecimal percentual) {

        this.plataforma = plataforma;
        this.total = total;
        this.percentual = percentual;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getPercentual() {
        return percentual;
    }

    public void setPercentual(BigDecimal percentual) {
        this.percentual = percentual;
    }

}
