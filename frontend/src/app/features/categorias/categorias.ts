import { Component, inject, OnInit, signal } from '@angular/core';
import { CategoriaService } from '../../core/services/categoria.service';
import { Categoria } from '../../core/models/categoria';
import { RouterLink } from '@angular/router';
import { ModalConfirmacao } from '../../shared/components/modal-confirmacao/modal-confirmacao';
import { finalize } from 'rxjs';

@Component({
  selector: 'app-categorias',
  imports: [
    RouterLink,
    ModalConfirmacao
  ],
  templateUrl: './categorias.html',
  styleUrl: './categorias.scss',
})
export class Categorias implements OnInit {

  private readonly categoriaService = inject(CategoriaService);

  readonly categorias = signal<Categoria[]>([]);
  readonly categoriaParaDesativar = signal<Categoria | null>(null);
  readonly desativando = signal(false);
  readonly categoriaParaAtivar = signal<Categoria | null>(null);
  readonly ativando = signal(false);

  ngOnInit(): void {
    this.carregarCategorias();
  }

  confirmarDesativacao(categoria: Categoria): void {
    this.categoriaParaDesativar.set(categoria);
  }

  confirmarAtivacao(categoria: Categoria): void {
    this.categoriaParaAtivar.set(categoria);
  }

  cancelarDesativacao(): void {
    if (this.desativando()) {
      return;
    }

    this.categoriaParaDesativar.set(null);
  }

  cancelarAtivacao(): void {
    if (this.ativando()) {
      return;
    }

    this.categoriaParaAtivar.set(null);
  }

  desativarCategoria(): void {
    const categoria = this.categoriaParaDesativar();

    if (!categoria || this.desativando()) {
      return;
    }

    this.desativando.set(true);

    this.categoriaService.desativar(categoria.id)
      .pipe(
        finalize(() => {
          this.desativando.set(false);
        })
      )
      .subscribe({
        next: () => {
          this.categoriaParaDesativar.set(null);
          this.carregarCategorias();
        },
        error: erro => {
          console.error(
            'Erro ao desativar categoria:',
            erro
          );
        }
      });
  }

  ativarCategoria(): void {
    const categoria = this.categoriaParaAtivar();

    if (!categoria || this.ativando()) {
      return;
    }

    this.ativando.set(true);

    this.categoriaService.ativar(categoria.id)
      .pipe(
        finalize(() => {
          this.ativando.set(false);
        })
      )
      .subscribe({
        next: () => {
          this.categoriaParaAtivar.set(null);
          this.carregarCategorias();
        },
        error: erro => {
          console.error(
            'Erro ao ativar categoria:',
            erro
          );
        }
      });
  }

  private carregarCategorias(): void {
    this.categoriaService.listar()
      .subscribe({
        next: response => {
          this.categorias.set(response);
        },
        error: erro => {
          console.error(
            'Erro ao carregar categorias:',
            erro
          );
        }
      });
  }

}
