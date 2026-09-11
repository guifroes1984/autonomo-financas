import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { EsqueciSenhaRequest } from '../../../core/models/esqueci-senha-request';
import { finalize } from 'rxjs';

@Component({
  selector: 'app-esqueci-senha',
  imports: [
    ReactiveFormsModule,
    RouterLink
  ],
  templateUrl: './esqueci-senha.html',
  styleUrl: './esqueci-senha.scss',
})
export class EsqueciSenha {

  private readonly formBuilder = inject(FormBuilder);
  private readonly authService = inject(AuthService);

  readonly enviando = signal(false);
  readonly erro = signal<string | null>(null);
  readonly sucesso = signal(false);

  readonly formulario = this.formBuilder.group({
    email: ['', [Validators.required, Validators.email]]
  });

  solicitarRecuperacao(): void {
    if (this.enviando()) {
      return;
    }

    this.erro.set(null);

    if (this.formulario.invalid) {
      this.formulario.markAllAsTouched();
      return;
    }

    const dados = this.formulario.getRawValue();

    const request: EsqueciSenhaRequest = {
      email: dados.email!
    };

    this.enviando.set(true);

    this.authService.esqueciSenha(request)
      .pipe(
        finalize(() => {
          this.enviando.set(false);
        })
      )
      .subscribe({
        next: () => {
          this.sucesso.set(true);
        },

        error: erro => {
          if (erro.error?.mensagem) {
            this.erro.set(erro.error.mensagem);
            return;
          }

          this.erro.set(
            'Não foi possível solicitar a recuperação de senha. Tente novamente.'
          );

          console.error(
            'Erro ao solicitar recuperação de senha:',
            erro
          );
        }
      });
  }

}
