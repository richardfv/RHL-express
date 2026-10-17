import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TrackingResponse } from '../models/envio.model';

@Injectable({
  providedIn: 'root'
})
export class TrackingService {
  private readonly http = inject(HttpClient);
  private readonly API_URL = 'http://localhost:8080/api/tracking';

  getTracking(codigo: string): Observable<TrackingResponse> {
    const cleanCode = codigo.trim().toUpperCase();
    return this.http.get<TrackingResponse>(`${this.API_URL}/${cleanCode}`);
  }
}
