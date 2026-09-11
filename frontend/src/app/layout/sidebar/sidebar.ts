import { Component, input, output } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-sidebar',
  imports: [
    RouterLink,
    RouterLinkActive
  ],
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.scss',
})
export class Sidebar {

  readonly recolhida = input(false);

  readonly itemSelecionado = output<void>();

  selecionarItem(): void {
    this.itemSelecionado.emit();
  }

}
