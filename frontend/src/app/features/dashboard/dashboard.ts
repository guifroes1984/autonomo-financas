import { Component, inject, OnInit, signal } from '@angular/core';
import { DashboardService } from '../../core/services/dashboard.service';
import { DashboardResumo } from '../../core/models/dashboard-resumo';
import { CurrencyPipe } from '@angular/common';
import { DashboardMeta } from '../../core/models/dashboard-meta';
import { MetaCard } from '../../shared/components/meta-card/meta-card';

@Component({
  selector: 'app-dashboard',
  imports: [
    CurrencyPipe, 
    MetaCard
  ],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.scss',
})
export class Dashboard implements OnInit {

  private readonly dashboardService = inject(DashboardService);

  readonly resumo = signal<DashboardResumo | null>(null);
  readonly meta = signal<DashboardMeta | null>(null);

  ngOnInit(): void {
    this.carregarResumo();
    this.carregarMetas();
  }

  private carregarResumo(): void {
    this.dashboardService.obterResumo()
      .subscribe({
        next: response => {
          this.resumo.set(response);
        },
        error: erro => {
          console.error('Erro ao carregar resumo do dashboard:', erro);
        }
      });
  }

  private carregarMetas(): void {
    this.dashboardService.obterMetas()
      .subscribe({
        next: response => {
          this.meta.set(response);
        },
        error: erro => {
          console.error('Erro ao carregar metas do dashboard:', erro);
        }
      });
  }

}
