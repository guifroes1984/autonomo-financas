import { Component, inject, OnInit, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { RelatorioService } from '../../core/services/relatorio.service';
import { RelatorioResumo } from '../../core/models/relatorio-resumo';
import { CurrencyPipe, DatePipe } from '@angular/common';
import { ReceitaPlataforma } from '../../core/models/receita-plataforma';
import { DespesaCategoria } from '../../core/models/despesa-categoria';
import { RelatorioComparativo } from '../../core/models/relatorio-comparativo';
import { finalize, forkJoin } from 'rxjs';

@Component({
  selector: 'app-relatorios',
  imports: [
    ReactiveFormsModule,
    CurrencyPipe,
    DatePipe
  ],
  templateUrl: './relatorios.html',
  styleUrl: './relatorios.scss',
})
export class Relatorios implements OnInit {

  private readonly formBuilder = inject(FormBuilder);
  private readonly relatorioService = inject(RelatorioService);

  readonly resumo = signal<RelatorioResumo | null>(null);
  readonly carregando = signal(false);
  readonly erro = signal<string | null>(null);
  readonly receitasPorPlataforma = signal<ReceitaPlataforma[]>([]);
  readonly despesasPorCategoria = signal<DespesaCategoria[]>([]);
  readonly comparativo = signal<RelatorioComparativo | null>(null);
  readonly exportandoPdf = signal(false);

  readonly formulario = this.formBuilder.group({
    inicio: ['', Validators.required],
    fim: ['', Validators.required]
  });

  ngOnInit(): void {
    this.definirPeriodoInicial();
  }

  exportarPdf(): void {
    this.erro.set(null);

    if (this.formulario.invalid) {
      this.formulario.markAllAsTouched();
      return;
    }

    const {
      inicio,
      fim
    } = this.formulario.getRawValue();

    if (!inicio || !fim) {
      return;
    }

    if (inicio > fim) {
      this.erro.set(
        'A data inicial não pode ser posterior à data final.'
      );

      return;
    }

    this.exportandoPdf.set(true);

    this.relatorioService
      .exportarPdf(inicio, fim)
      .pipe(
        finalize(() => {
          this.exportandoPdf.set(false);
        })
      )
      .subscribe({
        next: arquivo => {
          const url = URL.createObjectURL(arquivo);

          const link = document.createElement('a');

          link.href = url;
          link.download =
            `relatorio-${inicio}-a-${fim}.pdf`;

          document.body.appendChild(link);

          link.click();

          document.body.removeChild(link);

          URL.revokeObjectURL(url);
        },

        error: erro => {
          console.error(
            'Erro ao exportar relatório em PDF:',
            erro
          );

          this.erro.set(
            'Não foi possível exportar o relatório em PDF.'
          );
        }
      });
  }

  gerarRelatorio(): void {
    this.erro.set(null);

    if (this.formulario.invalid) {
      this.formulario.markAllAsTouched();
      return;
    }

    const {
      inicio,
      fim
    } = this.formulario.getRawValue();

    if (!inicio || !fim) {
      return;
    }

    if (inicio > fim) {
      this.erro.set(
        'A data inicial não pode ser posterior à data final.'
      );

      return;
    }

    // Limpa os dados do relatório anterior
    this.resumo.set(null);
    this.receitasPorPlataforma.set([]);
    this.despesasPorCategoria.set([]);
    this.comparativo.set(null);

    this.carregando.set(true);

    forkJoin({
      resumo: this.relatorioService.resumo(
        inicio,
        fim
      ),

      receitasPorPlataforma:
        this.relatorioService.receitasPorPlataforma(
          inicio,
          fim
        ),

      despesasPorCategoria:
        this.relatorioService.despesasPorCategoria(
          inicio,
          fim
        ),

      comparativo:
        this.relatorioService.comparativo(
          inicio,
          fim
        )
    })
      .pipe(
        finalize(() => {
          this.carregando.set(false);
        })
      )
      .subscribe({
        next: resultado => {
          this.resumo.set(
            resultado.resumo
          );

          this.receitasPorPlataforma.set(
            resultado.receitasPorPlataforma
          );

          this.despesasPorCategoria.set(
            resultado.despesasPorCategoria
          );

          this.comparativo.set(
            resultado.comparativo
          );
        },

        error: erro => {
          console.error(
            'Erro ao gerar relatório:',
            erro
          );

          this.erro.set(
            erro.error?.mensagem ??
            'Não foi possível gerar o relatório.'
          );
        }
      });
  }

  private definirPeriodoInicial(): void {
    const hoje = new Date();

    const primeiroDia = new Date(
      hoje.getFullYear(),
      hoje.getMonth(),
      1
    );

    this.formulario.patchValue({
      inicio: this.formatarData(primeiroDia),
      fim: this.formatarData(hoje)
    });
  }

  private formatarData(data: Date): string {
    const ano = data.getFullYear();
    const mes = String(data.getMonth() + 1).padStart(2, '0');
    const dia = String(data.getDate()).padStart(2, '0');

    return `${ano}-${mes}-${dia}`;
  }

}
