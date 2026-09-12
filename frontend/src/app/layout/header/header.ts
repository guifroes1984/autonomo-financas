import { Component, inject, OnInit, output, signal } from '@angular/core';
import { AuthService } from '../../core/services/auth.service';
import { Router } from '@angular/router';
import { PerfilService } from '../../core/services/perfil.service';
import { UsuarioPerfil } from '../../core/models/usuario-perfil';

@Component({
  selector: 'app-header',
  imports: [],
  templateUrl: './header.html',
  styleUrl: './header.scss',
})
export class Header implements OnInit {

  readonly menuClick = output<void>();

  readonly menuUsuarioAberto = signal(false);
  readonly perfil = signal<UsuarioPerfil | null>(null);

  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);
  private readonly perfilService = inject(PerfilService);

  ngOnInit(): void {
    this.carregarPerfil();
  }

  abrirMenu(): void {
    this.menuClick.emit();
  }

  alterarMenuUsuario(): void {
    this.menuUsuarioAberto.update(aberto => !aberto);
  }

  abrirPerfil(): void {
    this.menuUsuarioAberto.set(false);
    this.router.navigate(['/perfil']);
  }

  sair(): void {
    this.authService.logout()
      .subscribe({
        next: () => {
          this.router.navigate(['/login']);
        },

        error: erro => {
          console.error(
            'Erro ao realizar logout:',
            erro
          );

          this.authService.limparTokens();
          this.router.navigate(['/login']);
        }
      });
  }

  private carregarPerfil(): void {
    this.perfilService.buscar()
      .subscribe({
        next: response => {
          this.perfil.set(response);
        },

        error: erro => {
          console.error(
            'Erro ao carregar perfil no header:',
            erro
          );
        }
      });
  }

}
