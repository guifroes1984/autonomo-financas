import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../../environments/environment.development';
import { Observable } from 'rxjs';
import { Plataforma } from '../models/plataforma';
import { PlataformaRequest } from '../models/plataforma-request';

@Injectable({
  providedIn: 'root',
})
export class PlataformaService {

  private readonly http = inject(HttpClient);
  private readonly apiUrl = `${environment.apiUrl}/plataformas`;

  listar(): Observable<Plataforma[]> {
    return this.http.get<Plataforma[]>(
      this.apiUrl
    );
  }

  criar(request: PlataformaRequest): Observable<Plataforma> {
    return this.http.post<Plataforma>(
      this.apiUrl,
      request
    );
  }

  buscarPorId(id: number): Observable<Plataforma> {
    return this.http.get<Plataforma>(
      `${this.apiUrl}/${id}`
    );
  }

  atualizar(id: number, request: PlataformaRequest): Observable<Plataforma> {
    return this.http.put<Plataforma>(
      `${this.apiUrl}/${id}`,
      request
    );
  }

  desativar(id: number): Observable<void> {
    return this.http.patch<void>(
      `${this.apiUrl}/${id}/desativar`,
      null
    );
  }

  ativar(id: number): Observable<void> {
    return this.http.patch<void>(
      `${this.apiUrl}/${id}/ativar`,
      null
    );
  }

}
