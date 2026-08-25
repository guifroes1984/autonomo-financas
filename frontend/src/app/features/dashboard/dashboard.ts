import { Component, inject, OnInit } from '@angular/core';
import { DashboardService } from '../../core/services/dashboard.service';
import { DashboardResumo } from '../../core/models/dashboard-resumo';
import { CurrencyPipe, DecimalPipe } from '@angular/common';
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

  resumo: DashboardResumo | null = null;
  meta: DashboardMeta | null = null;

  ngOnInit(): void {
    this.carregarResumo();
    this.carregarMetas();
  }

  private carregarResumo(): void {
    this.dashboardService.obterResumo()
      .subscribe({
        next: response => {
          this.resumo = response;
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
          this.meta = response;
        },
        error: erro => {
          console.error('Erro ao carregar metas do dashboard:', erro);
        }
      });
  }

}
