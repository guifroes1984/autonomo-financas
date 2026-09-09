export interface RelatorioComparativo {
    inicioPeriodoAnterior: string;
    fimPeriodoAnterior: string;

    inicioPeriodoAtual: string;
    fimPeriodoAtual: string;

    receitasPeriodoAnterior: number;
    receitasPeriodoAtual: number;
    variacaoReceitas: number;

    despesasPeriodoAnterior: number;
    despesasPeriodoAtual: number;
    variacaoDespesas: number;

    saldoPeriodoAnterior: number;
    saldoPeriodoAtual: number;
    variacaoSaldo: number;
}
