import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../../environments/environment.development';
import { CriarUsuarioRequest } from '../models/criar-usuario-request';
import { Observable } from 'rxjs';
import { UsuarioResponse } from '../models/usuario-response';

@Injectable({
  providedIn: 'root',
})
export class UsuarioService {

  private readonly http = inject(HttpClient);
  private readonly apiUrl = `${environment.apiUrl}/usuarios`;

  criar(request: CriarUsuarioRequest): Observable<UsuarioResponse> {
    return this.http.post<UsuarioResponse>(
      this.apiUrl,
      request
    );
  }

}
