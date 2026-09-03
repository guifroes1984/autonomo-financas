import { HttpErrorResponse, HttpInterceptorFn, HttpResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { catchError, throwError } from 'rxjs';
import { Router } from '@angular/router';

export const authInterceptor: HttpInterceptorFn = (req, next) => {

  const authService = inject(AuthService);
  const router = inject(Router);

  const token = authService.obterToken();

  if (!token) {
    return next(req);
  }

  const requestAutenticada = req.clone({
    setHeaders: {
      Authorization: `Bearer ${token}`
    }
  });

  return next(requestAutenticada).pipe(
    catchError((erro: HttpErrorResponse) => {

      if (erro.status === 401) {
        authService.logout();
        router.navigate(['/login']);
      }

      return throwError(() => erro);
    })
  );
};
