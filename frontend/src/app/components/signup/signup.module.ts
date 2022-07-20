import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SignupComponent } from './signup.component';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { IonicModule } from '@ionic/angular';

@NgModule({
  declarations: [SignupComponent],
  imports: [CommonModule, IonicModule, ReactiveFormsModule, RouterModule],
  exports: [SignupComponent],
})
export class SignupModule {}
