import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../../environments/environment.development';
import { Observable } from 'rxjs';
import { MetaRequest } from '../models/meta-request';
import { Meta } from '../models/meta';

@Injectable({
  providedIn: 'root',
})
export class MetaService {

  private readonly http = inject(HttpClient);

  private readonly apiUrl =`${environment.apiUrl}/meta`;

  buscar(): Observable<Meta> {
    return this.http.get<Meta>(this.apiUrl);
  }

  criar(request: MetaRequest): Observable<Meta> {
    return this.http.post<Meta>(
      this.apiUrl, 
      request
    );
  }

  atualizar(request: MetaRequest): Observable<Meta> {
    return this.http.put<Meta>(
      this.apiUrl, 
      request
    );
  }

}
