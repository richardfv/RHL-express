import { Injectable, signal, computed, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { LoginRequest, LoginResponse } from '../models/envio.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly http = inject(HttpClient);
  private readonly router = inject(Router);
  private readonly API_URL = 'http://localhost:8080/api/auth';

  private readonly tokenSignal = signal<string | null>(localStorage.getItem('rhl_token'));
  private readonly userSignal = signal<string | null>(localStorage.getItem('rhl_user'));
  private readonly roleSignal = signal<string | null>(localStorage.getItem('rhl_role'));

  readonly token = computed(() => this.tokenSignal());
  readonly currentUser = computed(() => this.userSignal());
  readonly userRole = computed(() => this.roleSignal());
  readonly isAuthenticated = computed(() => !!this.tokenSignal());

  login(credentials: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.API_URL}/login`, credentials).pipe(
      tap((res) => {
        if (res && res.token) {
          localStorage.setItem('rhl_token', res.token);
          localStorage.setItem('rhl_user', res.username);
          localStorage.setItem('rhl_role', res.rol);
          this.tokenSignal.set(res.token);
          this.userSignal.set(res.username);
          this.roleSignal.set(res.rol);
        }
      })
    );
  }

  logout(): void {
    localStorage.removeItem('rhl_token');
    localStorage.removeItem('rhl_user');
    localStorage.removeItem('rhl_role');
    this.tokenSignal.set(null);
    this.userSignal.set(null);
    this.roleSignal.set(null);
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return this.tokenSignal();
  }
}
