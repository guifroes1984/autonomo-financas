import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { RedefinirSenhaRequest } from '../../../core/models/redefinir-senha-request';
import { finalize } from 'rxjs';

@Component({
  selector: 'app-redefinir-senha',
  imports: [
    ReactiveFormsModule,
    RouterLink
  ],
  templateUrl: './redefinir-senha.html',
  styleUrl: './redefinir-senha.scss',
})
export class RedefinirSenha {

  private readonly formBuilder = inject(FormBuilder);
  private readonly authService = inject(AuthService);
  private readonly route = inject(ActivatedRoute);

  readonly redefinindo = signal(false);
  readonly erro = signal<string | null>(null);
  readonly sucesso = signal(false);

  readonly token = signal<string | null>(
    this.route.snapshot.queryParamMap.get('token')
  );

  readonly formulario = this.formBuilder.group({
    novaSenha: ['', [Validators.required, Validators.minLength(6)]],
    confirmarSenha: ['', [Validators.required]]
  });

  redefinirSenha(): void {
    if (this.redefinindo()) {
      return;
    }

    this.erro.set(null);

    const token = this.token();

    if (!token) {
      this.erro.set(
        'O link de recuperação é inválido ou está incompleto.'
      );

      return;
    }

    if (this.formulario.invalid) {
      this.formulario.markAllAsTouched();
      return;
    }

    const dados = this.formulario.getRawValue();

    if (dados.novaSenha !== dados.confirmarSenha) {
      this.erro.set(
        'As senhas informadas não são iguais.'
      );

      return;
    }

    const request: RedefinirSenhaRequest = {
      token,
      novaSenha: dados.novaSenha!
    };

    this.redefinindo.set(true);

    this.authService.redefinirSenha(request)
      .pipe(
        finalize(() => {
          this.redefinindo.set(false);
        })
      )
      .subscribe({
        next: () => {
          this.sucesso.set(true);
          this.formulario.reset();
        },

        error: erro => {
          if (erro.error?.mensagem) {
            this.erro.set(erro.error.mensagem);
            return;
          }

          this.erro.set(
            'Não foi possível redefinir sua senha. O link pode ter expirado.'
          );

          console.error(
            'Erro ao redefinir senha:',
            erro
          );
        }
      });
  }

}
