import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { PlataformaService } from '../../../core/services/plataforma.service';
import { ActivatedRoute, Router } from '@angular/router';
import { finalize } from 'rxjs';
import { PlataformaRequest } from '../../../core/models/plataforma-request';

@Component({
  selector: 'app-form-plataforma',
  imports: [ReactiveFormsModule],
  templateUrl: './form-plataforma.html',
  styleUrl: './form-plataforma.scss',
})
export class FormPlataforma implements OnInit {

  private readonly formBuilder = inject(FormBuilder);
  private readonly plataformaService = inject(PlataformaService);
  private readonly router = inject(Router);
  private readonly activatedRoute = inject(ActivatedRoute);

  readonly salvando = signal(false);
  readonly erro = signal<string | null>(null);
  readonly plataformaId = signal<number | null>(null);

  readonly modoEdicao = computed(() => this.plataformaId() !== null);

  readonly formulario = this.formBuilder.group({
    nome: ['', [Validators.required, Validators.maxLength(100)]]
  });

  ngOnInit(): void {
    const idParam =
      this.activatedRoute.snapshot.paramMap.get('id');

    if (!idParam) {
      return;
    }

    const id = Number(idParam);

    if (Number.isNaN(id)) {
      this.router.navigate(['/plataformas']);
      return;
    }

    this.plataformaId.set(id);
    this.carregarPlataforma(id);
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

    const request: PlataformaRequest = {
      nome: dados.nome!
    };

    const id = this.plataformaId();

    const requisicao = id !== null
      ? this.plataformaService.atualizar(id, request)
      : this.plataformaService.criar(request);

    this.salvando.set(true);

    requisicao
      .pipe(
        finalize(() => {
          this.salvando.set(false);
        })
      )
      .subscribe({
        next: () => {
          this.router.navigate(['/plataformas']);
        },

        error: erro => {
          if (erro.error?.mensagem) {
            this.erro.set(erro.error.mensagem);
            return;
          }

          this.erro.set(
            id !== null
              ? 'Não foi possível atualizar a plataforma.'
              : 'Não foi possível cadastrar a plataforma.'
          );

          console.error(
            id !== null
              ? 'Erro ao atualizar plataforma:'
              : 'Erro ao cadastrar plataforma:',
            erro
          );
        }
      });
  }

  cancelar(): void {
    this.router.navigate(['/plataformas']);
  }

  private carregarPlataforma(id: number): void {
    this.plataformaService.buscarPorId(id)
      .subscribe({
        next: plataforma => {
          this.formulario.patchValue({
            nome: plataforma.nome
          });
        },

        error: erro => {
          console.error(
            'Erro ao carregar plataforma:',
            erro
          );

          this.erro.set(
            'Não foi possível carregar a plataforma.'
          );
        }
      });
  }

}
