import { Component, inject, OnInit, signal } from '@angular/core';
import { PlataformaService } from '../../core/services/plataforma.service';
import { Plataforma } from '../../core/models/plataforma';
import { RouterLink } from '@angular/router';
import { ModalConfirmacao } from '../../shared/components/modal-confirmacao/modal-confirmacao';
import { finalize } from 'rxjs';

@Component({
  selector: 'app-plataformas',
  imports: [
    RouterLink,
    ModalConfirmacao
  ],
  templateUrl: './plataformas.html',
  styleUrl: './plataformas.scss',
})
export class Plataformas implements OnInit {

  private readonly plataformaService = inject(PlataformaService);

  readonly plataformas = signal<Plataforma[]>([]);
  readonly plataformaParaDesativar = signal<Plataforma | null>(null);
  readonly plataformaParaAtivar = signal<Plataforma | null>(null);

  readonly desativando = signal(false);
  readonly ativando = signal(false);

  ngOnInit(): void {
    this.carregarPlataformas();
  }

  solicitarDesativacao(plataforma: Plataforma): void {
    this.plataformaParaDesativar.set(plataforma);
  }

  cancelarDesativacao(): void {
    this.plataformaParaDesativar.set(null);
  }

  solicitarAtivacao(plataforma: Plataforma): void {
    this.plataformaParaAtivar.set(plataforma);
  }

  cancelarAtivacao(): void {
    this.plataformaParaAtivar.set(null);
  }

  desativarPlataforma(): void {
    const plataforma = this.plataformaParaDesativar();

    if (!plataforma || this.desativando()) {
      return;
    }

    this.desativando.set(true);

    this.plataformaService
      .desativar(plataforma.id)
      .pipe(
        finalize(() => {
          this.desativando.set(false);
        })
      )
      .subscribe({
        next: () => {
          this.plataformaParaDesativar.set(null);
          this.carregarPlataformas();
        },

        error: erro => {
          console.error(
            'Erro ao desativar plataforma:',
            erro
          );
        }
      });
  }

  ativarPlataforma(): void {
    const plataforma = this.plataformaParaAtivar();

    if (!plataforma || this.ativando()) {
      return;
    }

    this.ativando.set(true);

    this.plataformaService
      .ativar(plataforma.id)
      .pipe(
        finalize(() => {
          this.ativando.set(false);
        })
      )
      .subscribe({
        next: () => {
          this.plataformaParaAtivar.set(null);
          this.carregarPlataformas();
        },

        error: erro => {
          console.error(
            'Erro ao ativar plataforma:',
            erro
          );
        }
      });
  }

  private carregarPlataformas(): void {
    this.plataformaService.listar()
      .subscribe({
        next: response => {
          this.plataformas.set(response);
        },
        error: erro => {
          console.error(
            'Erro ao carregar plataformas:',
            erro
          );
        }
      });
  }

}
