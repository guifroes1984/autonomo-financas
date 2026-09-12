import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment.development';
import { HttpClient } from '@angular/common/http';
import { LoginRequest } from '../models/login-request';
import { Observable, tap } from 'rxjs';
import { LoginResponse } from '../models/login-response';
import { EsqueciSenhaRequest } from '../models/esqueci-senha-request';
import { RedefinirSenhaRequest } from '../models/redefinir-senha-request';
import { RefreshTokenResponse } from '../models/refresh-token-response';

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  private readonly apiUrl = `${environment.apiUrl}/auth`;

  private readonly ACCESS_TOKEN_KEY = 'access_token';
  private readonly REFRESH_TOKEN_KEY = 'refresh_token';

  constructor(private readonly http: HttpClient) { }

  login(request: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(
      `${this.apiUrl}/login`,
      request
    )
      .pipe(
        tap(response => {
          this.salvarTokens(
            response.accessToken,
            response.refreshToken
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

  refreshToken(): Observable<RefreshTokenResponse> {
    const refreshToken = this.obterRefreshToken();

    return this.http.post<RefreshTokenResponse>(
      `${this.apiUrl}/refresh`,
      {
        refreshToken
      }
    )
      .pipe(
        tap(response => {
          localStorage.setItem(
            this.ACCESS_TOKEN_KEY,
            response.accessToken
          );
        })
      );
  }

  obterToken(): string | null {
    return localStorage.getItem(
      this.ACCESS_TOKEN_KEY
    );
  }

  obterRefreshToken(): string | null {
    return localStorage.getItem(
      this.REFRESH_TOKEN_KEY
    );
  }

  logout(): Observable<void> {
    const refreshToken = this.obterRefreshToken();

    return this.http.post<void>(
      `${this.apiUrl}/logout`,
      {
        refreshToken
      }
    )
      .pipe(
        tap(() => {
          this.limparTokens();
        })
      );
  }

  limparTokens(): void {
    localStorage.removeItem(
      this.ACCESS_TOKEN_KEY
    );

    localStorage.removeItem(
      this.REFRESH_TOKEN_KEY
    );
  }

  private salvarTokens(
    accessToken: string,
    refreshToken: string
  ): void {

    localStorage.setItem(
      this.ACCESS_TOKEN_KEY,
      accessToken
    );

    localStorage.setItem(
      this.REFRESH_TOKEN_KEY,
      refreshToken
    );
  }

}