import { RelatorioDia } from "./relatorio-dia";

export interface RelatorioResumo {
    inicio: string;
    fim: string;
    totalReceitas: number;
    totalDespesas: number;
    saldo: number;
    mediaDiaria: number;
    diasTrabalhados: number;
    melhorDia: RelatorioDia;
    piorDia: RelatorioDia;
}
