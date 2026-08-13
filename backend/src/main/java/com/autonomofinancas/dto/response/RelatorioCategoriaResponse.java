package com.autonomofinancas.dto.response;

import java.math.BigDecimal;

public class RelatorioCategoriaResponse {

    private String categoria;
    private BigDecimal total;
    private BigDecimal percentual;

    public RelatorioCategoriaResponse() {
    }

    public RelatorioCategoriaResponse(
            String categoria,
            BigDecimal total,
            BigDecimal percentual) {

        this.categoria = categoria;
        this.total = total;
        this.percentual = percentual;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
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
