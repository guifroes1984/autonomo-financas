export interface LancamentoFiltro {

    tipo?: 'RECEITA' | 'DESPESA';
    categoriaId?: number;
    plataformaId?: number;
    inicio?: string;
    fim?: string;
    descricao?: string;

    page?: number;
    size?: number;
    sort?: string[];

}
