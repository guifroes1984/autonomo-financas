import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../../environments/environment.development';
import { Observable } from 'rxjs';
import { Plataforma } from '../models/plataforma';

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

}
