import { Component, inject, output } from '@angular/core';
import { AuthService } from '../../core/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  imports: [],
  templateUrl: './header.html',
  styleUrl: './header.scss',
})
export class Header {

  readonly menuClick = output<void>();

  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);

  abrirMenu(): void {
    this.menuClick.emit();
  }

  sair(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

}
