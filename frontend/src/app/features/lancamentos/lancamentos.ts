import { Component, inject, OnInit, signal } from '@angular/core';
import { LancamentoService } from '../../core/services/lancamento.service';
import { Lancamento } from '../../core/models/lancamento';
import { CurrencyPipe, DatePipe } from '@angular/common';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { CategoriaService } from '../../core/services/categoria.service';
import { PlataformaService } from '../../core/services/plataforma.service';
import { Categoria } from '../../core/models/categoria';
import { Plataforma } from '../../core/models/plataforma';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-lancamentos',
  imports: [
    CurrencyPipe,
    DatePipe,
    ReactiveFormsModule, 
    RouterLink
  ],
  templateUrl: './lancamentos.html',
  styleUrl: './lancamentos.scss',
})
export class Lancamentos implements OnInit {

  private readonly lancamentoService = inject(LancamentoService);
  private readonly categoriaService = inject(CategoriaService);
  private readonly plataformaService = inject(PlataformaService);
  private readonly formBuilder = inject(FormBuilder);

  readonly lancamentos = signal<Lancamento[]>([]);
  readonly categorias = signal<Categoria[]>([]);
  readonly plataformas = signal<Plataforma[]>([]);

  readonly paginaAtual = signal(0);
  readonly totalPaginas = signal(0);
  readonly totalElementos = signal(0);

  readonly tamanhoPagina = 5;

  readonly formularioFiltro = this.formBuilder.group({
    descricao: [''],
    tipo: [''], 
    categoriaId: [null as number | null],
    plataformaId: [null as number | null],
    inicio: [''],
    fim: ['']
  });

  ngOnInit(): void {
    this.carregarLancamentos();
    this.carregarCategorias();
    this.carregarPlataformas();
  }

  private carregarLancamentos(): void {
    const filtro = this.formularioFiltro.getRawValue();

    this.lancamentoService.listar({
      descricao: filtro.descricao || undefined,
      tipo: filtro.tipo
        ? filtro.tipo as 'RECEITA' | 'DESPESA'
        : undefined,

      categoriaId: filtro.categoriaId ?? undefined,
      plataformaId: filtro.plataformaId ?? undefined,

      inicio: filtro.inicio || undefined,
      fim: filtro.fim || undefined,

      page: this.paginaAtual(),
      size: this.tamanhoPagina,
      sort: ['dataLancamento,desc']
    })
      .subscribe({
        next: response => {
          this.lancamentos.set(response.content);
          this.totalPaginas.set(response.totalPages);
          this.totalElementos.set(response.totalElements);
        },
        error: erro => {
          console.error(
            'Erro ao carregar lançamentos:',
            erro
          );
        }
      });
  }

  private carregarCategorias(): void {
    this.categoriaService.listar()
      .subscribe({
        next: response => {
          this.categorias.set(
            response.filter(categoria => categoria.ativa)
          );
        },
        error: erro => {
          console.error(
            'Erro ao carregar categorias:',
            erro
          );
        }
      });
  }

  private carregarPlataformas(): void {
    this.plataformaService.listar()
      .subscribe({
        next: response => {
          this.plataformas.set(
            response.filter(plataforma => plataforma.ativo)
          );
        }, 
        error: erro => {
          console.error(
            'Erro ao carregar plataformas.', 
            erro
          );
        }
      });
  }

  filtrar(): void {
    this.paginaAtual.set(0);
    this.carregarLancamentos();
  }

  limparFiltros(): void {
    this.formularioFiltro.reset({
      descricao: '',
      tipo: '', 
      categoriaId: null,
      plataformaId: null,
      inicio: '',
      fim: ''
    });

    this.paginaAtual.set(0);
    this.carregarLancamentos();
  }

  paginaAnterior(): void {

    if (this.paginaAtual() > 0) {

      this.paginaAtual.update(
        pagina => pagina - 1
      );

      this.carregarLancamentos();
    }
  }

  proximaPagina(): void {

    if (
      this.paginaAtual() <
      this.totalPaginas() - 1
    ) {

      this.paginaAtual.update(
        pagina => pagina + 1
      );

      this.carregarLancamentos();
    }
  }

}
