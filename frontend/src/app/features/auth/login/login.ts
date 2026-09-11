import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../../core/services/auth.service';
import { Router, RouterLink } from '@angular/router';
import { finalize } from 'rxjs';

@Component({
  selector: 'app-login',
  imports: [
    ReactiveFormsModule, 
    RouterLink
  ],
  templateUrl: './login.html',
  styleUrl: './login.scss'
})
export class Login {

  private readonly formBuilder = inject(FormBuilder);
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);

  readonly carregando = signal(false);
  readonly erro = signal<string | null>(null);

  readonly formulario = this.formBuilder.group({
    email: ['', [Validators.required, Validators.email]],
    senha: ['', [Validators.required]]
  });

  enviar(): void {
    if (this.carregando()) {
      return;
    }

    this.erro.set(null);

    if (this.formulario.invalid) {
      this.formulario.markAllAsTouched();
      return;
    }

    const request = {
      email: this.formulario.controls.email.value!,
      senha: this.formulario.controls.senha.value!
    };

    this.carregando.set(true);

    this.authService.login(request)
      .pipe(
        finalize(() => {
          this.carregando.set(false);
        })
      )
      .subscribe({
        next: () => {
          this.router.navigate(['/dashboard']);
        },

        error: erro => {
          if (erro.status === 401) {
            this.erro.set(
              'E-mail ou senha inválidos.'
            );
            return;
          }

          this.erro.set(
            'Não foi possível realizar o login. Tente novamente.'
          );

          console.error(
            'Erro ao realizar login:',
            erro
          );
        }
      });
  }

}