import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { UsuarioService } from '../../../core/services/usuario.service';
import { finalize } from 'rxjs';
import { CriarUsuarioRequest } from '../../../core/models/criar-usuario-request';

@Component({
  selector: 'app-cadastro',
  imports: [
    ReactiveFormsModule,
    RouterLink
  ],
  templateUrl: './cadastro.html',
  styleUrl: './cadastro.scss',
})
export class Cadastro {

  private readonly formBuilder = inject(FormBuilder);
  private readonly usuarioService = inject(UsuarioService);
  private readonly router = inject(Router);

  readonly salvando = signal(false);
  readonly erro = signal<string | null>(null);
  readonly sucesso = signal<string | null>(null);

  readonly formulario = this.formBuilder.group({
    nome: ['', [Validators.required, Validators.maxLength(120)]],
    email: ['', [Validators.required, Validators.email]],
    senha: ['', [Validators.required, Validators.minLength(6)]],
    confirmarSenha: ['', [Validators.required]]
  });

  cadastrar(): void {
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

    if (dados.senha !== dados.confirmarSenha) {
      this.erro.set(
        'As senhas informadas não são iguais.'
      );

      return;
    }

    const request: CriarUsuarioRequest = {
      nome: dados.nome!,
      email: dados.email!,
      senha: dados.senha!
    };

    this.salvando.set(true);

    this.usuarioService.criar(request)
      .pipe(
        finalize(() => {
          this.salvando.set(false);
        })
      )
      .subscribe({
        next: () => {
          this.sucesso.set(
            'Conta criada com sucesso.'
          );

          setTimeout(() => {
            this.router.navigate(['/login']);
          }, 1200);
        },

        error: erro => {
          if (erro.status === 409) {
            this.erro.set(
              'Já existe uma conta cadastrada com este e-mail.'
            );

            return;
          }

          if (erro.error?.mensagem) {
            this.erro.set(
              erro.error.mensagem
            );

            return;
          }

          this.erro.set(
            'Não foi possível criar sua conta. Tente novamente.'
          );

          console.error(
            'Erro ao cadastrar usuário:',
            erro
          );
        }
      });
  }

}
