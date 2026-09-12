import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { DashboardService } from '../../core/services/dashboard.service';
import { DashboardResumo } from '../../core/models/dashboard-resumo';
import { CurrencyPipe } from '@angular/common';
import { DashboardMeta } from '../../core/models/dashboard-meta';
import { MetaCard } from '../../shared/components/meta-card/meta-card';
import { DashboardReceitaPlataforma } from '../../core/models/dashboard-receita-plataforma';
import { DashboardDespesaCategoria } from '../../core/models/dashboard-despesa-categoria';
import { DashboardEvolucaoDiaria } from '../../core/models/dashboard-evolucao-diaria';
import { BaseChartDirective } from 'ng2-charts';
import { CategoryScale, Chart, ChartConfiguration, ChartData, Legend, LinearScale, LineController, LineElement, PointElement, Tooltip } from 'chart.js';
import { DashboardIndicadores } from '../../core/models/dashboard-indicadores';

Chart.register(
  CategoryScale,
  LinearScale,
  LineController,
  LineElement,
  PointElement,
  Tooltip,
  Legend
);

@Component({
  selector: 'app-dashboard',
  imports: [
    CurrencyPipe,
    MetaCard,
    BaseChartDirective
  ],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.scss',
})
export class Dashboard implements OnInit {

  private readonly dashboardService = inject(DashboardService);

  readonly resumo = signal<DashboardResumo | null>(null);
  readonly meta = signal<DashboardMeta | null>(null);
  readonly receitasPorPlataforma = signal<DashboardReceitaPlataforma[]>([]);
  readonly despesasPorCategoria = signal<DashboardDespesaCategoria[]>([]);
  readonly evolucaoDiaria = signal<DashboardEvolucaoDiaria[]>([]);
  readonly indicadores = signal<DashboardIndicadores | null>(null);

  readonly tipoGrafico: 'line' = 'line';

  readonly dadosGrafico = computed<ChartData<'line'>>(() => {
    const dados = this.evolucaoDiaria();

    return {
      labels: dados.map(item => {
        const [, mes, dia] = item.data.split('-');

        return `${dia}/${mes}`;
      }),

      datasets: [
        {
          label: 'Receitas',
          data: dados.map(item => item.totalReceitas),
          borderColor: '#198754',
          backgroundColor: '#198754',
          pointBackgroundColor: '#198754',
          pointBorderColor: '#ffffff',
          pointBorderWidth: 2,
          pointRadius: 4,
          pointHoverRadius: 6,
          borderWidth: 2,
          tension: 0.35
        },
        {
          label: 'Despesas',
          data: dados.map(item => item.totalDespesas),
          borderColor: '#dc3545',
          backgroundColor: '#dc3545',
          pointBackgroundColor: '#dc3545',
          pointBorderColor: '#ffffff',
          pointBorderWidth: 2,
          pointRadius: 4,
          pointHoverRadius: 6,
          borderWidth: 2,
          tension: 0.35
        },
        {
          label: 'Saldo',
          data: dados.map(item => item.saldo),
          borderColor: '#0d6efd',
          backgroundColor: '#0d6efd',
          pointBackgroundColor: '#0d6efd',
          pointBorderColor: '#ffffff',
          pointBorderWidth: 2,
          pointRadius: 4,
          pointHoverRadius: 6,
          borderWidth: 2,
          tension: 0.35
        }
      ]
    };
  });

  readonly opcoesGrafico: ChartConfiguration<'line'>['options'] = {
    responsive: true,
    maintainAspectRatio: false,

    interaction: {
      mode: 'index',
      intersect: false
    },

    plugins: {
      legend: {
        position: 'bottom',

        labels: {
          usePointStyle: true,
          pointStyle: 'circle',
          padding: 20
        }
      },

      tooltip: {
        callbacks: {
          label: context => {
            const valor = Number(context.raw ?? 0);

            const valorFormatado = valor.toLocaleString('pt-BR', {
              style: 'currency',
              currency: 'BRL'
            });

            return `${context.dataset.label}: ${valorFormatado}`;
          }
        }
      }
    },

    scales: {
      x: {
        grid: {
          display: false
        },

        ticks: {
          color: '#868e96'
        }
      },

      y: {
        beginAtZero: true,

        grid: {
          color: '#f1f3f5'
        },

        ticks: {
          color: '#868e96',

          callback: value => {
            const numero = Number(value);

            return numero.toLocaleString('pt-BR', {
              style: 'currency',
              currency: 'BRL',
              maximumFractionDigits: 0
            });
          }
        }
      }
    }
  };

  ngOnInit(): void {
    this.carregarResumo();
    this.carregarMetas();
    this.carregarReceitasPorPlataforma();
    this.carregarDespesasPorCategoria();
    this.carregarEvolucaoDiaria();
    this.carregarIndicadores();
  }

  private carregarReceitasPorPlataforma(): void {
    this.dashboardService.obterReceitasPorPlataforma()
      .subscribe({
        next: response => {
          this.receitasPorPlataforma.set(response);
        },

        error: erro => {
          console.error(
            'Erro ao carregar receitas por plataforma:',
            erro
          );
        }
      });
  }

  private carregarDespesasPorCategoria(): void {
    this.dashboardService.obterDespesasPorCategoria()
      .subscribe({
        next: response => {
          this.despesasPorCategoria.set(response);
        },

        error: erro => {
          console.error(
            'Erro ao carregar despesas por categoria:',
            erro
          );
        }
      });
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

  private carregarEvolucaoDiaria(): void {
    const hoje = new Date();

    const inicio = new Date(
      hoje.getFullYear(),
      hoje.getMonth(),
      1
    );

    const inicioFormatado = this.formatarData(inicio);
    const fimFormatado = this.formatarData(hoje);

    this.dashboardService
      .obterEvolucaoDiaria(
        inicioFormatado,
        fimFormatado
      )
      .subscribe({
        next: response => {
          this.evolucaoDiaria.set(response);
        },

        error: erro => {
          console.error(
            'Erro ao carregar evolução diária:',
            erro
          );
        }
      });
  }

  private carregarIndicadores(): void {
    const hoje = new Date();

    const inicio = new Date(
      hoje.getFullYear(),
      hoje.getMonth(),
      1
    );

    const inicioFormatado = this.formatarData(inicio);
    const fimFormatado = this.formatarData(hoje);

    this.dashboardService
      .obterIndicadores(
        inicioFormatado,
        fimFormatado
      )
      .subscribe({
        next: response => {
          this.indicadores.set(response);
        },

        error: erro => {
          console.error(
            'Erro ao carregar indicadores do dashboard:',
            erro
          );
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

  private formatarData(data: Date): string {
    const ano = data.getFullYear();

    const mes = String(
      data.getMonth() + 1
    ).padStart(2, '0');

    const dia = String(
      data.getDate()
    ).padStart(2, '0');

    return `${ano}-${mes}-${dia}`;
  }

  formatarDataExibicao(data: string): string {
    const [ano, mes, dia] = data.split('-');

    return `${dia}/${mes}/${ano}`;
  }

}
