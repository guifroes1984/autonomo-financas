package com.autonomofinancas.dto.request;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class MetaRequest {

    @Schema(description = "Valor da meta diária.", example = "200.00")
    @NotNull(message = "A meta diária é obrigatória.")
    @DecimalMin(value = "0.01", message = "A meta diária deve ser maior que zero.")
    @JsonProperty("metaDiaria")
    private BigDecimal metaDiaria;

    @Schema(description = "Valor da meta mensal.", example = "5000.00")
    @NotNull(message = "A meta mensal é obrigatória.")
    @DecimalMin(value = "0.01", message = "A meta mensal deve ser maior que zero.")
    @JsonProperty("metaMensal")
    private BigDecimal metaMensal;

    public MetaRequest() {
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

}
