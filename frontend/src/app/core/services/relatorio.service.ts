import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../../environments/environment.development';
import { Observable } from 'rxjs';
import { RelatorioResumo } from '../models/relatorio-resumo';
import { ReceitaPlataforma } from '../models/receita-plataforma';
import { DespesaCategoria } from '../models/despesa-categoria';
import { RelatorioComparativo } from '../models/relatorio-comparativo';

@Injectable({
  providedIn: 'root',
})
export class RelatorioService {

  private readonly http = inject(HttpClient);

  private readonly apiUrl = `${environment.apiUrl}/relatorios`;

  resumo(inicio: string, fim: string): Observable<RelatorioResumo> {
    const params = new HttpParams()
      .set('inicio', inicio)
      .set('fim', fim);

    return this.http.get<RelatorioResumo>(
      `${this.apiUrl}/resumo`,
      { params }
    );
  }

  receitasPorPlataforma(inicio: string, fim: string): Observable<ReceitaPlataforma[]> {
    const params = new HttpParams()
      .set('inicio', inicio)
      .set('fim', fim);

    return this.http.get<ReceitaPlataforma[]>(
      `${this.apiUrl}/receitas-por-plataforma`,
      { params }
    );
  }

  despesasPorCategoria(inicio: string, fim: string): Observable<DespesaCategoria[]> {
    const params = new HttpParams()
      .set('inicio', inicio)
      .set('fim', fim);

    return this.http.get<DespesaCategoria[]>(
      `${this.apiUrl}/despesas-por-categoria`,
      { params }
    );
  }

  comparativo(inicio: string, fim: string): Observable<RelatorioComparativo> {
    const params = new HttpParams()
      .set('inicio', inicio)
      .set('fim', fim);

    return this.http.get<RelatorioComparativo>(
      `${this.apiUrl}/comparativo`,
      { params }
    );
  }

  exportarPdf(
    inicio: string,
    fim: string
  ): Observable<Blob> {

    const params = new HttpParams()
      .set('inicio', inicio)
      .set('fim', fim);

    return this.http.get(
      `${this.apiUrl}/pdf`,
      {
        params,
        responseType: 'blob'
      }
    );
  }

}
