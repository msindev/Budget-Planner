import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { HotToastService } from '@ngneat/hot-toast';
import { AuthControllerService } from 'src/openapi-generated';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private toast: HotToastService,
    private authService: AuthControllerService,
    private router: Router,
  ) {}

  ngOnInit() {
    this.loginForm = this.fb.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(6)]],
    });
  }

  login() {
    const loginData = this.loginForm.value;
    this.authService
      .authenticateUser(loginData)
      .pipe(
        this.toast.observe({
          success: 'Logged in successfully',
          loading: 'Logging in...',
          error: ({ message }) =>
            `There was an error logging you in: ${message} `,
        }),
      )
      .subscribe(() => {
        this.router.navigate(['/tabs/home']);
      });
  }
}
