import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { MonthlyBudgetComponent } from './monthly-budget.component';

@NgModule({
  declarations: [MonthlyBudgetComponent],
  imports: [CommonModule, ReactiveFormsModule, IonicModule],
  exports: [MonthlyBudgetComponent],
})
export class MonthlyBudgetModule {}
