import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { CategoriaService } from '../../../core/services/categoria.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CategoriaRequest } from '../../../core/models/categoria-request';
import { finalize } from 'rxjs';

@Component({
  selector: 'app-form-categoria',
  imports: [ReactiveFormsModule],
  templateUrl: './form-categoria.html',
  styleUrl: './form-categoria.scss',
})
export class FormCategoria implements OnInit {

  private readonly formBuilder = inject(FormBuilder);
  private readonly categoriaService = inject(CategoriaService);
  private readonly router = inject(Router);
  private readonly activatedRoute = inject(ActivatedRoute);

  readonly categoriaId = signal<number | null>(null);

  readonly modoEdicao = computed(
    () => this.categoriaId() !== null
  );

  readonly salvando = signal(false);

  readonly erro = signal<string | null>(null);

  readonly formulario = this.formBuilder.group({
    nome: [
      '',
      [
        Validators.required,
        Validators.maxLength(100)
      ]
    ],
    tipo: [
      'DESPESA',
      Validators.required
    ],
    cor: [
      '#0d6efd',
      Validators.required
    ]
  });

  ngOnInit(): void {
    const idParam =
      this.activatedRoute.snapshot.paramMap.get('id');

    if (!idParam) {
      return;
    }

    const id = Number(idParam);

    if (Number.isNaN(id)) {
      this.router.navigate(['/categorias']);
      return;
    }

    this.categoriaId.set(id);

    this.carregarCategoria(id);
  }

  private carregarCategoria(id: number): void {
    this.categoriaService.buscarPorId(id)
      .subscribe({
        next: categoria => {
          this.formulario.patchValue({
            nome: categoria.nome,
            tipo: categoria.tipo,
            cor: categoria.cor
          });
        },
        error: erro => {
          console.error(
            'Erro ao carregar categoria:',
            erro
          );

          this.erro.set(
            'Não foi possível carregar a categoria.'
          );
        }
      });
  }

  salvar(): void {
    if (this.salvando()) {
      return;
    }

    this.erro.set(null);

    if (this.formulario.invalid) {
      this.formulario.markAllAsTouched();
      return;
    }

    const dados = this.formulario.getRawValue();

    const request: CategoriaRequest = {
      nome: dados.nome!,
      tipo: dados.tipo as 'RECEITA' | 'DESPESA',
      cor: dados.cor!
    };

    const id = this.categoriaId();

    const requisicao = id !== null
      ? this.categoriaService.atualizar(
        id,
        request
      )
      : this.categoriaService.criar(
        request
      );

    this.salvando.set(true);

    requisicao
      .pipe(
        finalize(() => {
          this.salvando.set(false);
        })
      )
      .subscribe({
        next: () => {
          this.router.navigate(['/categorias']);
        },

        error: erro => {
          if (erro.error?.mensagem) {
            this.erro.set(
              erro.error.mensagem
            );
            return;
          }

          this.erro.set(
            'Não foi possível salvar a categoria. Tente novamente.'
          );

          console.error(
            id !== null
              ? 'Erro ao atualizar categoria:'
              : 'Erro ao cadastrar categoria:',
            erro
          );
        }
      });
  }

  cancelar(): void {
    this.router.navigate(['/categorias']);
  }
}
