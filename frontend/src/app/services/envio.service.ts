import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Envio, EnvioCrearRequest } from '../models/envio.model';

@Injectable({
  providedIn: 'root'
})
export class EnvioService {
  private readonly http = inject(HttpClient);
  private readonly API_URL = 'http://localhost:8080/api/envios';

  getEnvios(): Observable<Envio[]> {
    return this.http.get<Envio[]>(this.API_URL);
  }

  getEnvioById(id: number): Observable<Envio> {
    return this.http.get<Envio>(`${this.API_URL}/${id}`);
  }

  createEnvio(envio: EnvioCrearRequest): Observable<Envio> {
    return this.http.post<Envio>(this.API_URL, envio);
  }

  updateEstado(id: number, estado: string, comentario?: string): Observable<Envio> {
    const payload: { estado: string; comentario?: string } = { estado };
    if (comentario && comentario.trim().length > 0) {
      payload.comentario = comentario.trim();
    }
    return this.http.put<Envio>(`${this.API_URL}/${id}/estado`, payload);
  }

  deleteEnvio(id: number): Observable<void> {
    return this.http.delete<void>(`${this.API_URL}/${id}`);
  }
}
