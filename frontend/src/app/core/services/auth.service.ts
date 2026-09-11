import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment.development';
import { HttpClient } from '@angular/common/http';
import { LoginRequest } from '../models/login-request';
import { Observable, tap } from 'rxjs';
import { LoginResponse } from '../models/login-response';
import { EsqueciSenhaRequest } from '../models/esqueci-senha-request';
import { RedefinirSenhaRequest } from '../models/redefinir-senha-request';

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  private readonly apiUrl = `${environment.apiUrl}/auth`;
  private readonly TOKEN_KEY = 'access_token';

  constructor(private readonly http: HttpClient) { }

  login(request: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(
      `${this.apiUrl}/login`, request
    )
      .pipe(
        tap(response => {
          localStorage.setItem(
            this.TOKEN_KEY,
            response.accessToken
          );
        })
      );
  }

  esqueciSenha(request: EsqueciSenhaRequest): Observable<void> {
    return this.http.post<void>(
      `${this.apiUrl}/esqueci-senha`,
      request
    );
  }

  redefinirSenha(request: RedefinirSenhaRequest): Observable<void> {
    return this.http.post<void>(
      `${this.apiUrl}/redefinir-senha`,
      request
    );
  }

  obterToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
  }

}
