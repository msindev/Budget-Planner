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
    let username = localStorage.getItem('username');
    return username !== null ? username : '';
  }

  get isLoggedIn(): boolean {
    let currentTime = Math.floor(new Date().getTime() / 1000);
    let authToken = localStorage.getItem('access-token');
    let expiresAt = localStorage.getItem('expires-at');
    if (authToken != null && expiresAt != null && currentTime < +expiresAt) {
      return true;
    }
    return false;
  }

  logout() {
    let removeToken = localStorage.removeItem('access-token');
    localStorage.removeItem('username');
    localStorage.removeItem('expires-at');
    if (removeToken == null) {
      this.router.navigate(['auth/login']);
    }
  }
}
