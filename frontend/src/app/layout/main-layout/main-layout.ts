import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

import { Header } from '../header/header';
import { Sidebar } from '../sidebar/sidebar';

@Component({
  selector: 'app-main-layout',
  imports: [
    RouterOutlet,
    Header,
    Sidebar
  ],
  templateUrl: './main-layout.html',
  styleUrl: './main-layout.scss'
})
export class MainLayout {

  menuAberto = false;
  sidebarRecolhida = false;

  alterarMenu(): void {
    if (window.innerWidth < 768) {
      this.menuAberto = !this.menuAberto;
      return;
    }

    this.sidebarRecolhida = !this.sidebarRecolhida;
  }

  fecharMenu(): void {
    this.menuAberto = false;
  }

}