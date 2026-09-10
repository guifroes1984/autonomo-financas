import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../../environments/environment.development';
import { Observable } from 'rxjs';
import { UsuarioPerfil } from '../models/usuario-perfil';
import { AtualizarPerfilRequest } from '../models/atualizar-perfil-request';
import { AlterarSenhaRequest } from '../models/alterar-senha-request';

@Injectable({
  providedIn: 'root',
})
export class PerfilService {

  private readonly http = inject(HttpClient);
  private readonly apiUrl = `${environment.apiUrl}/usuarios/me`;

  buscar(): Observable<UsuarioPerfil> {
    return this.http.get<UsuarioPerfil>(this.apiUrl);
  }

  atualizar(request: AtualizarPerfilRequest): Observable<UsuarioPerfil> {
    return this.http.put<UsuarioPerfil>(
      this.apiUrl, 
      request
    );
  }

  alterarSenha(request: AlterarSenhaRequest): Observable<void> {
    return this.http.patch<void>(
      `${this.apiUrl}/senha`, 
      request
    );
  }

}
