import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../../environments/environment.development';
import { Observable } from 'rxjs';
import { DashboardResumo } from '../models/dashboard-resumo';
import { DashboardMeta } from '../models/dashboard-meta';
import { DashboardReceitaPlataforma } from '../models/dashboard-receita-plataforma';
import { DashboardDespesaCategoria } from '../models/dashboard-despesa-categoria';
import { DashboardEvolucaoDiaria } from '../models/dashboard-evolucao-diaria';
import { DashboardIndicadores } from '../models/dashboard-indicadores';

@Injectable({
  providedIn: 'root',
})
export class DashboardService {

  private readonly http = inject(HttpClient);
  private readonly apiUrl = `${environment.apiUrl}/dashboard`;

  obterResumo(): Observable<DashboardResumo> {
    return this.http.get<DashboardResumo>(
      `${this.apiUrl}/resumo`
    );
  }

  obterMetas(): Observable<DashboardMeta> {
    return this.http.get<DashboardMeta>(
      `${this.apiUrl}/metas`
    );
  }

  obterReceitasPorPlataforma(): Observable<DashboardReceitaPlataforma[]> {
    return this.http.get<DashboardReceitaPlataforma[]>(
      `${this.apiUrl}/receitas-plataformas`
    );
  }

  obterDespesasPorCategoria(): Observable<DashboardDespesaCategoria[]> {
    return this.http.get<DashboardDespesaCategoria[]>(
      `${this.apiUrl}/despesas-categorias`
    );
  }

  obterEvolucaoDiaria(inicio: string, fim: string): Observable<DashboardEvolucaoDiaria[]> {
    return this.http.get<DashboardEvolucaoDiaria[]>(
      `${this.apiUrl}/evolucao-diaria`,
      {
        params: {
          inicio,
          fim
        }
      }
    );
  }

  obterIndicadores(inicio: string, fim: string): Observable<DashboardIndicadores> {
    return this.http.get<DashboardIndicadores>(
      `${this.apiUrl}/indicadores`,
      {
        params: {
          inicio,
          fim
        }
      }
    );
  }

}
