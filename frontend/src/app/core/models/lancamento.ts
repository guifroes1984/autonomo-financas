export interface Lancamento {

    id: number;
    tipo: 'RECEITA' | 'DESPESA';
    descricao: string;
    valor: number;
    dataLancamento: string;

    categoria: {
        id: number;
        nome: string;
    };

    plataforma: {
        id: number;
        nome: string;
    } | null;

    observacao: string | null;
    dataCriacao: string;
    dataAtualizacao: string;

}
