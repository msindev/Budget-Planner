import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private router: Router) {}

  getToken(): string {
    return localStorage.getItem('budget-planner-access-token');
  }

  get isLoggedIn(): boolean {
    let authToken = localStorage.getItem('budget-planner-access-token');
    return authToken !== null ? true : false;
  }

  logout() {
    let removeToken = localStorage.removeItem('budget-planner-access-token');
    if (removeToken == null) {
      this.router.navigate(['auth/login']);
    }
  }
}
