import { Component, inject } from '@angular/core';
import {FormBuilder, ReactiveFormsModule, Validators} from '@angular/forms';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-login',
  imports: [
    ReactiveFormsModule],
  templateUrl: './login.html',
  styleUrl: './login.scss'
})
export class Login {

  private readonly formBuilder = inject(FormBuilder);
  private readonly authService = inject(AuthService);

  readonly formulario = this.formBuilder.group({
    email: ['', [Validators.required, Validators.email]],
    senha: ['', Validators.required]
  });

  enviar(): void {
    if (this.formulario.invalid) {
      this.formulario.markAllAsTouched();
      return;
  }

  const request = {
    email: this.formulario.controls.email.value!, 
    senha: this.formulario.controls.senha.value!
  };

  this.authService.login(request)
    .subscribe({
      next: response => {
        console.log('Login realizado:', response);
      }, 
      error: erro => {
        console.error('Erro no login:', erro);
      }
    });
  }
}