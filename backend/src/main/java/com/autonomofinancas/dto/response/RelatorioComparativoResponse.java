package com.autonomofinancas.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RelatorioComparativoResponse {

    private LocalDate inicioPeriodoAnterior;
    private LocalDate fimPeriodoAnterior;

    private LocalDate inicioPeriodoAtual;
    private LocalDate fimPeriodoAtual;

    private BigDecimal receitasPeriodoAnterior;
    private BigDecimal receitasPeriodoAtual;
    private BigDecimal variacaoReceitas;

    private BigDecimal despesasPeriodoAnterior;
    private BigDecimal despesasPeriodoAtual;
    private BigDecimal variacaoDespesas;

    private BigDecimal saldoPeriodoAnterior;
    private BigDecimal saldoPeriodoAtual;
    private BigDecimal variacaoSaldo;

    public RelatorioComparativoResponse() {
    }

    public RelatorioComparativoResponse(LocalDate inicioPeriodoAnterior, LocalDate fimPeriodoAnterior,
            LocalDate inicioPeriodoAtual, LocalDate fimPeriodoAtual, BigDecimal receitasPeriodoAnterior,
            BigDecimal receitasPeriodoAtual, BigDecimal variacaoReceitas, BigDecimal despesasPeriodoAnterior,
            BigDecimal despesasPeriodoAtual, BigDecimal variacaoDespesas, BigDecimal saldoPeriodoAnterior,
            BigDecimal saldoPeriodoAtual, BigDecimal variacaoSaldo) {
        this.inicioPeriodoAnterior = inicioPeriodoAnterior;
        this.fimPeriodoAnterior = fimPeriodoAnterior;
        this.inicioPeriodoAtual = inicioPeriodoAtual;
        this.fimPeriodoAtual = fimPeriodoAtual;
        this.receitasPeriodoAnterior = receitasPeriodoAnterior;
        this.receitasPeriodoAtual = receitasPeriodoAtual;
        this.variacaoReceitas = variacaoReceitas;
        this.despesasPeriodoAnterior = despesasPeriodoAnterior;
        this.despesasPeriodoAtual = despesasPeriodoAtual;
        this.variacaoDespesas = variacaoDespesas;
        this.saldoPeriodoAnterior = saldoPeriodoAnterior;
        this.saldoPeriodoAtual = saldoPeriodoAtual;
        this.variacaoSaldo = variacaoSaldo;
    }

    public LocalDate getInicioPeriodoAnterior() {
        return inicioPeriodoAnterior;
    }

    public void setInicioPeriodoAnterior(LocalDate inicioPeriodoAnterior) {
        this.inicioPeriodoAnterior = inicioPeriodoAnterior;
    }

    public LocalDate getFimPeriodoAnterior() {
        return fimPeriodoAnterior;
    }

    public void setFimPeriodoAnterior(LocalDate fimPeriodoAnterior) {
        this.fimPeriodoAnterior = fimPeriodoAnterior;
    }

    public LocalDate getInicioPeriodoAtual() {
        return inicioPeriodoAtual;
    }

    public void setInicioPeriodoAtual(LocalDate inicioPeriodoAtual) {
        this.inicioPeriodoAtual = inicioPeriodoAtual;
    }

    public LocalDate getFimPeriodoAtual() {
        return fimPeriodoAtual;
    }

    public void setFimPeriodoAtual(LocalDate fimPeriodoAtual) {
        this.fimPeriodoAtual = fimPeriodoAtual;
    }

    public BigDecimal getReceitasPeriodoAnterior() {
        return receitasPeriodoAnterior;
    }

    public void setReceitasPeriodoAnterior(BigDecimal receitasPeriodoAnterior) {
        this.receitasPeriodoAnterior = receitasPeriodoAnterior;
    }

    public BigDecimal getReceitasPeriodoAtual() {
        return receitasPeriodoAtual;
    }

    public void setReceitasPeriodoAtual(BigDecimal receitasPeriodoAtual) {
        this.receitasPeriodoAtual = receitasPeriodoAtual;
    }

    public BigDecimal getVariacaoReceitas() {
        return variacaoReceitas;
    }

    public void setVariacaoReceitas(BigDecimal variacaoReceitas) {
        this.variacaoReceitas = variacaoReceitas;
    }

    public BigDecimal getDespesasPeriodoAnterior() {
        return despesasPeriodoAnterior;
    }

    public void setDespesasPeriodoAnterior(BigDecimal despesasPeriodoAnterior) {
        this.despesasPeriodoAnterior = despesasPeriodoAnterior;
    }

    public BigDecimal getDespesasPeriodoAtual() {
        return despesasPeriodoAtual;
    }

    public void setDespesasPeriodoAtual(BigDecimal despesasPeriodoAtual) {
        this.despesasPeriodoAtual = despesasPeriodoAtual;
    }

    public BigDecimal getVariacaoDespesas() {
        return variacaoDespesas;
    }

    public void setVariacaoDespesas(BigDecimal variacaoDespesas) {
        this.variacaoDespesas = variacaoDespesas;
    }

    public BigDecimal getSaldoPeriodoAnterior() {
        return saldoPeriodoAnterior;
    }

    public void setSaldoPeriodoAnterior(BigDecimal saldoPeriodoAnterior) {
        this.saldoPeriodoAnterior = saldoPeriodoAnterior;
    }

    public BigDecimal getSaldoPeriodoAtual() {
        return saldoPeriodoAtual;
    }

    public void setSaldoPeriodoAtual(BigDecimal saldoPeriodoAtual) {
        this.saldoPeriodoAtual = saldoPeriodoAtual;
    }

    public BigDecimal getVariacaoSaldo() {
        return variacaoSaldo;
    }

    public void setVariacaoSaldo(BigDecimal variacaoSaldo) {
        this.variacaoSaldo = variacaoSaldo;
    }

}
