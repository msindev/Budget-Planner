import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { IonicModule } from '@ionic/angular';
import { Tab3Page } from './tab3.page';

import { ChangePasswordModule } from '../components/change-password/change-password.module';
import { MonthlyBudgetModule } from '../components/monthly-budget/monthly-budget.module';
import { Tab3PageRoutingModule } from './tab3-routing.module';

@NgModule({
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    RouterModule.forChild([{ path: '', component: Tab3Page }]),
    Tab3PageRoutingModule,
    ChangePasswordModule,
    MonthlyBudgetModule,
  ],
  declarations: [Tab3Page],
})
export class Tab3PageModule {}
