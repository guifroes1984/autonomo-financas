import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../../environments/environment.development';
import { Observable } from 'rxjs';
import { DashboardResumo } from '../models/dashboard-resumo';
import { DashboardMeta } from '../models/dashboard-meta';

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

}
