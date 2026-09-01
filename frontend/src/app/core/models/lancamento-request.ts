export interface LancamentoRequest {

    tipo: 'RECEITA' | 'DESPESA';
    descricao: string;
    valor: number;
    dataLancamento: string;
    categoriaId: number;
    plataformaId?: number | null;
    observacao?: string | null;

}
