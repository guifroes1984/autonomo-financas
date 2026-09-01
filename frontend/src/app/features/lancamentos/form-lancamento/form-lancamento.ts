import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { finalize } from 'rxjs';

import { CategoriaService } from '../../../core/services/categoria.service';
import { PlataformaService } from '../../../core/services/plataforma.service';
import { LancamentoService } from '../../../core/services/lancamento.service';

import { Categoria } from '../../../core/models/categoria';
import { Plataforma } from '../../../core/models/plataforma';
import { LancamentoRequest } from '../../../core/models/lancamento-request';
import { Moeda } from '../../../shared/directives/moeda';

@Component({
  selector: 'app-form-lancamento',
  imports: [
    ReactiveFormsModule, 
    Moeda
  ],
  templateUrl: './form-lancamento.html',
  styleUrl: './form-lancamento.scss',
})
export class FormLancamento implements OnInit {

  private readonly formBuilder = inject(FormBuilder);
  private readonly categoriaService = inject(CategoriaService);
  private readonly plataformaService = inject(PlataformaService);
  private readonly lancamentoService = inject(LancamentoService);
  private readonly router = inject(Router);

  readonly categorias = signal<Categoria[]>([]);
  readonly plataformas = signal<Plataforma[]>([]);
  readonly tipoSelecionado = signal<'RECEITA' | 'DESPESA'>('DESPESA');

  readonly salvando = signal(false);

  readonly formulario = this.formBuilder.group({
    tipo: ['DESPESA', Validators.required],
    descricao: ['', [Validators.required, Validators.maxLength(150)]],
    valor: [
      null as number | null,
      [Validators.required, Validators.min(0.01)]
    ],
    dataLancamento: ['', Validators.required],
    categoriaId: [null as number | null, Validators.required],
    plataformaId: [null as number | null],
    observacao: ['']
  });

  readonly categoriasFiltradas = computed(() =>
    this.categorias().filter(
      categoria =>
        categoria.ativa &&
        categoria.tipo === this.tipoSelecionado()
    )
  );

  ngOnInit(): void {
    this.carregarCategorias();
    this.carregarPlataformas();

    this.formulario.controls.tipo.valueChanges.subscribe(tipo => {
      if (tipo === 'RECEITA' || tipo === 'DESPESA') {
        this.tipoSelecionado.set(tipo);

        this.formulario.controls.categoriaId.setValue(null);

        if (tipo === 'DESPESA') {
          this.formulario.controls.plataformaId.setValue(null);
        }
      }
    });
  }

  private carregarCategorias(): void {
    this.categoriaService.listar().subscribe({
      next: response => {
        this.categorias.set(response);
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
    this.plataformaService.listar().subscribe({
      next: response => {
        this.plataformas.set(
          response.filter(
            plataforma => plataforma.ativo
          )
        );
      },
      error: erro => {
        console.error(
          'Erro ao carregar plataformas:',
          erro
        );
      }
    });
  }

  salvar(): void {

    if (this.salvando()) {
      return;
    }

    if (this.formulario.invalid) {
      this.formulario.markAllAsTouched();
      return;
    }

    const dados = this.formulario.getRawValue();

    const request: LancamentoRequest = {
      tipo: dados.tipo as 'RECEITA' | 'DESPESA',
      descricao: dados.descricao!,
      valor: dados.valor!,
      dataLancamento: dados.dataLancamento!,
      categoriaId: dados.categoriaId!,
      plataformaId:
        dados.tipo === 'RECEITA'
          ? dados.plataformaId
          : null,
      observacao: dados.observacao || null
    };

    this.salvando.set(true);

    this.lancamentoService.criar(request)
      .pipe(
        finalize(() => {
          this.salvando.set(false);
        })
      )
      .subscribe({
        next: () => {
          this.router.navigate(['/lancamentos']);
        },
        error: erro => {
          console.error(
            'Erro ao cadastrar lançamento:',
            erro
          );
        }
      });
  }

  cancelar(): void {
    this.router.navigate(['/lancamentos']);
  }

}