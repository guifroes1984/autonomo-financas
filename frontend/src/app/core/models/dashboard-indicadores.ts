import { IndicadorDia } from "./indicador-dia";

export interface DashboardIndicadores {
    melhorDia: IndicadorDia | null;
    piorDia: IndicadorDia | null;

    mediaDiaria: number;
    diasTrabalhados: number;

    melhorPlataforma: string | null;
    maiorCategoriaDespesa: string | null;
}
