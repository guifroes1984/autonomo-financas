import { Component, inject, OnInit, signal } from '@angular/core';

import { MetaService } from '../../core/services/meta.service';
import { Meta } from '../../core/models/meta';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { finalize } from 'rxjs';
import { MetaRequest } from '../../core/models/meta-request';

@Component({
  selector: 'app-metas',
  imports: [ReactiveFormsModule],
  templateUrl: './metas.html',
  styleUrl: './metas.scss',
})
export class Metas implements OnInit {

  private readonly metaService = inject(MetaService);
  private readonly formBuilder = inject(FormBuilder);

  readonly meta = signal<Meta | null>(null);
  readonly carregando = signal(true);
  readonly erro = signal<string | null>(null);
  readonly salvando = signal(false);
  readonly sucesso = signal<string | null>(null);

  readonly formulario = this.formBuilder.group({
    metaDiaria: [null as number | null, [Validators.required, Validators.min(0.01)]],
    metaMensal: [null as number | null, [Validators.required, Validators.min(0.01)]]
  });

  ngOnInit(): void {
    this.carregarMeta();
  }

  salvar(): void {
    if (this.salvando()) {
      return;
    }

    this.erro.set(null);
    this.sucesso.set(null);

    if (this.formulario.invalid) {
      this.formulario.markAllAsTouched();
      return;
    }

    const dados = this.formulario.getRawValue();

    const request: MetaRequest = {
      metaDiaria: dados.metaDiaria!,
      metaMensal: dados.metaMensal!
    };

    const possuiMeta = this.meta() !== null;

    const requisicao = possuiMeta
      ? this.metaService.atualizar(request)
      : this.metaService.criar(request);

    this.salvando.set(true);

    requisicao
      .pipe(
        finalize(() => {
          this.salvando.set(false);
        })
      )
      .subscribe({
        next: response => {
          this.meta.set(response);

          this.formulario.patchValue({
            metaDiaria: response.metaDiaria,
            metaMensal: response.metaMensal
          });

          this.sucesso.set(
            possuiMeta
              ? 'Metas atualizadas com sucesso.'
              : 'Metas cadastradas com sucesso.'
          );

          setTimeout(() => {
            this.sucesso.set(null);
          }, 3000);
        },

        error: erro => {
          if (erro.error?.mensagem) {
            this.erro.set(erro.error.mensagem);
            return;
          }

          this.erro.set(
            possuiMeta
              ? 'Não foi possível atualizar as metas. Tente novamente.'
              : 'Não foi possível cadastrar as metas. Tente novamente.'
          );

          console.error(
            possuiMeta
              ? 'Erro ao atualizar metas:'
              : 'Erro ao cadastrar metas:',
            erro
          );
        }
      });
  }

  private carregarMeta(): void {
    this.carregando.set(true);
    this.erro.set(null);

    this.metaService.buscar()
      .subscribe({
        next: response => {
          this.meta.set(response);

          this.formulario.patchValue({
            metaDiaria: response.metaDiaria,
            metaMensal: response.metaMensal
          });

          this.carregando.set(false);
        },

        error: erro => {
          this.carregando.set(false);

          if (erro.status === 404) {
            this.meta.set(null);
            return;
          }

          console.error(
            'Erro ao carregar meta:',
            erro
          );

          this.erro.set(
            'Não foi possível carregar as metas. Tente novamente.'
          );
        }
      });
  }
}