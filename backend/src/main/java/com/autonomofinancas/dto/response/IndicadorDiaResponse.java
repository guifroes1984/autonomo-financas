package com.autonomofinancas.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public class IndicadorDiaResponse {

    private LocalDate data;
    private BigDecimal saldo;

    public IndicadorDiaResponse() {
    }

    public IndicadorDiaResponse(LocalDate data, BigDecimal saldo) {
        this.data = data;
        this.saldo = saldo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

}
