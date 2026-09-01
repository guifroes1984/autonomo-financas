export interface Categoria {
    id: number;
    nome: string;
    tipo: 'RECEITA' | 'DESPESA';
    cor: string;
    ativa: boolean;
    dataCriacao: string;
    dataAtualizacao: string
}
