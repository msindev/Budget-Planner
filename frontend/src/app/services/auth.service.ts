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
    let authToken = localStorage.getItem('access-token');
    return authToken !== null ? true : false;
  }

  logout() {
    let removeToken = localStorage.removeItem('access-token');
    localStorage.removeItem('username');
    if (removeToken == null) {
      this.router.navigate(['auth/login']);
    }
  }
}
