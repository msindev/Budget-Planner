import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { AuthPageRoutingModule } from './auth-routing.module';

import { LoginModule } from 'src/app/components/login/login.module';
import { SignupModule } from 'src/app/components/signup/signup.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    AuthPageRoutingModule,
    LoginModule,
    SignupModule,
  ],
})
export class AuthPageModule {}
