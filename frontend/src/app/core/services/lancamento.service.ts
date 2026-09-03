import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../../environments/environment.development';
import { LancamentoFiltro } from '../models/lancamento-filtro';
import { Observable } from 'rxjs';
import { PaginaResponse } from '../models/pagina-response';
import { Lancamento } from '../models/lancamento';
import { LancamentoRequest } from '../models/lancamento-request';

@Injectable({
  providedIn: 'root',
})
export class LancamentoService {

  private readonly http = inject(HttpClient);
  private readonly apiUrl = `${environment.apiUrl}/lancamentos`;

  listar(filtro: LancamentoFiltro = {}
  ): Observable<PaginaResponse<Lancamento>> {

    let params = new HttpParams();

    if (filtro.tipo) {
      params = params.set('tipo', filtro.tipo);
    }

    if (filtro.categoriaId != null) {
      params = params.set(
        'categoriaId', 
        filtro.categoriaId.toString()
      );
    }

    if (filtro.plataformaId != null) {
      params = params.set(
        'plataformaId', 
        filtro.plataformaId
      );
    }

    if (filtro.inicio) {
      params = params.set('inicio', filtro.inicio);
    }

    if (filtro.fim) {
      params = params.set('fim', filtro.fim);
    }

    if (filtro.descricao) {
      params = params.set(
        'descricao', 
        filtro.descricao
      );
    }

    if (filtro.page != null) {
      params = params.set(
        'page', 
        filtro.page.toString()
      );
    }

    if (filtro.size != null) {
      params = params.set(
        'size', 
        filtro.size.toString()
      );
    }

    if (filtro.sort?.length) {
      filtro.sort.forEach(item => {
        params = params.append('sort', item)
      });
    }

    return this.http.get<PaginaResponse<Lancamento>>(
      this.apiUrl, 
      { params }
    );
  }

  criar(request: LancamentoRequest): Observable<Lancamento> {
    return this.http.post<Lancamento>(this.apiUrl, request);
  }

  buscarPorId(id: number): Observable<Lancamento> {
    return this.http.get<Lancamento>(
      `${this.apiUrl}/${id}`
    );
  }

  atualizar(id: number, request: LancamentoRequest): Observable<Lancamento> {
    return this.http.put<Lancamento>(
      `${this.apiUrl}/${id}`, 
      request
    );
  }

}
