import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { ProfilePageComponent } from './profile.page';

import { ChangePasswordModule } from '../../components/change-password/change-password.module';
import { MonthlyBudgetModule } from '../../components/monthly-budget/monthly-budget.module';
import { ProfilePageRoutingModule } from './profile-routing.module';

@NgModule({
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    ProfilePageRoutingModule,
    ChangePasswordModule,
    MonthlyBudgetModule,
  ],
  declarations: [ProfilePageComponent],
})
export class ProfilePageModule {}
