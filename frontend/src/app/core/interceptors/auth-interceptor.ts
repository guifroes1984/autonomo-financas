import { inject } from '@angular/core';
import {
  HttpErrorResponse,
  HttpInterceptorFn
} from '@angular/common/http';
import { Router } from '@angular/router';
import {
  catchError,
  finalize,
  Observable,
  shareReplay,
  switchMap,
  throwError
} from 'rxjs';

import { AuthService } from '../services/auth.service';
import { RefreshTokenResponse } from '../models/refresh-token-response';

let refreshEmAndamento$: Observable<RefreshTokenResponse> | null = null;

export const authInterceptor: HttpInterceptorFn = (req, next) => {

  const authService = inject(AuthService);
  const router = inject(Router);

  const token = authService.obterToken();

  const rotaDeAutenticacao =
    req.url.includes('/auth/login') ||
    req.url.includes('/auth/refresh') ||
    req.url.includes('/auth/logout') ||
    req.url.includes('/auth/esqueci-senha') ||
    req.url.includes('/auth/redefinir-senha');

  const requestAutenticada =
    token && !rotaDeAutenticacao
      ? req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      })
      : req;

  return next(requestAutenticada).pipe(

    catchError((erro: HttpErrorResponse) => {

      if (
        erro.status !== 401 ||
        rotaDeAutenticacao
      ) {
        return throwError(() => erro);
      }

      const refreshToken =
        authService.obterRefreshToken();

      if (!refreshToken) {
        authService.limparTokens();
        router.navigate(['/login']);

        return throwError(() => erro);
      }

      if (!refreshEmAndamento$) {

        refreshEmAndamento$ =
          authService.refreshToken().pipe(
            shareReplay(1),
            finalize(() => {
              refreshEmAndamento$ = null;
            })
          );
      }

      return refreshEmAndamento$.pipe(

        switchMap(response => {

          const requestRenovada = req.clone({
            setHeaders: {
              Authorization:
                `Bearer ${response.accessToken}`
            }
          });

          return next(requestRenovada);
        }),

        catchError(erroRefresh => {

          authService.limparTokens();

          router.navigate(['/login']);

          return throwError(() => erroRefresh);
        })
      );
    })
  );
};