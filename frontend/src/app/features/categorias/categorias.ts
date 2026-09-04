import { Component, inject, OnInit, signal } from '@angular/core';
import { CategoriaService } from '../../core/services/categoria.service';
import { Categoria } from '../../core/models/categoria';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-categorias',
  imports: [RouterLink],
  templateUrl: './categorias.html',
  styleUrl: './categorias.scss',
})
export class Categorias implements OnInit {

  private readonly categoriaService = inject(CategoriaService);

  readonly categorias = signal<Categoria[]>([]);

  ngOnInit(): void {
    this.carregarCategorias();
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
