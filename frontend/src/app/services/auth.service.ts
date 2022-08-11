import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private router: Router) {}

  getToken(): string {
    return localStorage.getItem('access-token');
  }

  getUsername(): string {
    const username = localStorage.getItem('username');
    return username !== null ? username : '';
  }

  get isLoggedIn(): boolean {
    const currentTime = Math.floor(new Date().getTime() / 1000);
    const authToken = localStorage.getItem('access-token');
    const expiresAt = localStorage.getItem('expires-at');
    if (authToken != null && expiresAt != null && currentTime < +expiresAt) {
      return true;
    }
    return false;
  }

  logout() {
    const removeToken = localStorage.removeItem('access-token');
    localStorage.removeItem('username');
    localStorage.removeItem('expires-at');
    if (removeToken == null) {
      this.router.navigate(['auth/login']);
    }
  }
}
