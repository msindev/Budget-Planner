import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { HotToastService } from '@ngneat/hot-toast';
import { EMPTY } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthControllerService } from '../../../openapi-generated';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss'],
})
export class ChangePasswordComponent implements OnInit {
  @Output() modalEvent = new EventEmitter<boolean>();
  changePasswordForm: FormGroup;
  errorMessage = '';
  constructor(
    private fb: FormBuilder,
    private toast: HotToastService,
    private authService: AuthControllerService,
    private router: Router,
  ) {}

  ngOnInit() {
    this.changePasswordForm = this.fb.group({
      oldPassword: ['', [Validators.required, Validators.minLength(6)]],
      newPassword: ['', [Validators.required, Validators.minLength(6)]],
    });
  }

  changePassword() {
    const changePasswordRequestData = this.changePasswordForm.value;
    this.authService
      .changePassword(changePasswordRequestData)
      .pipe(
        this.toast.observe({
          success: 'Password Changed successfully',
          loading: 'Changing Password...',
          error: 'There was an error trying to change your password.',
        }),
        catchError((error: unknown) => {
          this.errorMessage = error['error']['message'];
          this.changePasswordForm.reset();
          return EMPTY;
        }),
      )
      .subscribe(() => {
        this.modalEvent.emit(true);
      });
  }

  cancel() {
    this.modalEvent.emit(false);
  }
}
