import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { ChangePasswordComponent } from './change-password.component';

@NgModule({
  declarations: [ChangePasswordComponent],
  imports: [CommonModule, ReactiveFormsModule, IonicModule],
  exports: [ChangePasswordComponent],
})
export class ChangePasswordModule {}
