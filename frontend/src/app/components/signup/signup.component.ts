import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthControllerService } from 'src/openapi-generated';
import { HotToastService } from '@ngneat/hot-toast';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss'],
})
export class SignupComponent implements OnInit {
  signupForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private toast: HotToastService,
    private authService: AuthControllerService,
    private router: Router,
  ) {}

  ngOnInit() {
    this.signupForm = this.fb.group({
      username: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
    });
  }

  signup() {
    const signupData = this.signupForm.value;
    this.authService
      .registerUser(signupData)
      .pipe(
        this.toast.observe({
          success: 'Registered successfully',
          loading: 'Registering...',
          error: ({ message }) =>
            `There was an error signing you in: ${message} `,
        }),
      )
      .subscribe(() => {
        this.router.navigate(['/tabs/home']);
      });
  }
}
