import { Component, inject, OnInit, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { PerfilService } from '../../core/services/perfil.service';
import { UsuarioPerfil } from '../../core/models/usuario-perfil';
import { finalize } from 'rxjs';
import { AtualizarPerfilRequest } from '../../core/models/atualizar-perfil-request';
import { AlterarSenhaRequest } from '../../core/models/alterar-senha-request';

@Component({
  selector: 'app-perfil',
  imports: [ReactiveFormsModule],
  templateUrl: './perfil.html',
  styleUrl: './perfil.scss',
})
export class Perfil implements OnInit {

  private readonly perfilService = inject(PerfilService);
  private readonly formBuilder = inject(FormBuilder);

  readonly perfil = signal<UsuarioPerfil | null>(null);
  readonly carregando = signal(true);
  readonly erro = signal<string | null>(null);
  readonly salvando = signal(false);
  readonly sucesso = signal<string | null>(null);
  readonly alterandoSenha = signal(false);
  readonly sucessoSenha = signal<string | null>(null);
  readonly erroSenha = signal<string | null>(null);

  readonly formulario = this.formBuilder.group({
    nome: ['', [Validators.required, Validators.maxLength(120)]],
    email: ['', [Validators.required, Validators.email]]
  });

  readonly formularioSenha = this.formBuilder.group({
    senhaAtual: ['', [Validators.required]],
    novaSenha: ['', [Validators.required, Validators.minLength(6)]]
  });

  ngOnInit(): void {
    this.carregarPerfil();
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

    const request: AtualizarPerfilRequest = {
      nome: dados.nome!,
      email: dados.email!
    };

    this.salvando.set(true);

    this.perfilService.atualizar(request)
      .pipe(
        finalize(() => {
          this.salvando.set(false);
        })
      )
      .subscribe({
        next: response => {
          this.perfil.set(response);

          this.formulario.patchValue({
            nome: response.nome,
            email: response.email
          });

          this.formulario.markAsPristine();
          
          this.sucesso.set(
            'Perfil atualizado com sucesso.'
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
            'Não foi possível atualizar o perfil. Tente novamente.'
          );

          console.error(
            'Erro ao atualizar perfil:',
            erro
          );
        }
      });
  }

  alterarSenha(): void {
    if (this.alterandoSenha()) {
      return;
    }

    this.erroSenha.set(null);
    this.sucessoSenha.set(null);

    if (this.formularioSenha.invalid) {
      this.formularioSenha.markAllAsTouched();
      return;
    }

    const dados = this.formularioSenha.getRawValue();

    const request: AlterarSenhaRequest = {
      senhaAtual: dados.senhaAtual!,
      novaSenha: dados.novaSenha!
    };

    this.alterandoSenha.set(true);

    this.perfilService.alterarSenha(request)
      .pipe(
        finalize(() => {
          this.alterandoSenha.set(false);
        })
      )
      .subscribe({
        next: () => {
          this.formularioSenha.reset();

          this.sucessoSenha.set(
            'Senha alterada com sucesso.'
          );

          setTimeout(() => {
            this.sucessoSenha.set(null);
          }, 3000);
        },

        error: erro => {
          if (erro.error?.mensagem) {
            this.erroSenha.set(erro.error.mensagem);
            return;
          }

          this.erroSenha.set(
            'Não foi possível alterar a senha. Tente novamente.'
          );

          console.error(
            'Erro ao alterar senha:',
            erro
          );
        }
      });
  }

  private carregarPerfil(): void {
    this.carregando.set(true);
    this.erro.set(null);

    this.perfilService.buscar()
      .subscribe({
        next: response => {
          this.perfil.set(response);

          this.formulario.patchValue({
            nome: response.nome,
            email: response.email
          });

          this.carregando.set(false);
        },

        error: erro => {
          this.carregando.set(false);

          console.error(
            'Erro ao carregar perfil:',
            erro
          );

          if (erro.error?.mensagem) {
            this.erro.set(erro.error.mensagem);
            return;
          }

          this.erro.set(
            'Não foi possível carregar o perfil. Tente novamente.'
          );
        }
      });
  }

}
