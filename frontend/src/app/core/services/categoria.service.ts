import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../../environments/environment.development';
import { Categoria } from '../models/categoria';
import { Observable } from 'rxjs';
import { CategoriaRequest } from '../models/categoria-request';

@Injectable({
  providedIn: 'root',
})
export class CategoriaService {

  private readonly http = inject(HttpClient);
  private readonly apiUrl = `${environment.apiUrl}/categorias`;

  listar(): Observable<Categoria[]> {
    return this.http.get<Categoria[]>(
      this.apiUrl
    );
  }

  criar(request: CategoriaRequest): Observable<Categoria> {
    return this.http.post<Categoria>(
      this.apiUrl, 
      request
    );
  }

  buscarPorId(id: number): Observable<Categoria> {
    return this.http.get<Categoria>(
      `${this.apiUrl}/${id}`
    );
  }

  atualizar(id: number,request: CategoriaRequest): Observable<Categoria> {
    return this.http.put<Categoria>(
      `${this.apiUrl}/${id}`, 
      request
    );
}

}
