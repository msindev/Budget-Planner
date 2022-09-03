import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HotToastService } from '@ngneat/hot-toast';
import { Observable } from 'rxjs';
import {
  ExpenseControllerService,
  MonthlyBudgetResponse,
} from '../../../openapi-generated';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-monthly-budget',
  templateUrl: './monthly-budget.component.html',
  styleUrls: ['./monthly-budget.component.scss'],
})
export class MonthlyBudgetComponent implements OnInit {
  monthlyBudgetObs$: Observable<MonthlyBudgetResponse>;
  isEditing = false;
  budgetForm: FormGroup;
  month: number;
  year: number;
  username: string;
  constructor(
    private expenseService: ExpenseControllerService,
    private authService: AuthService,
    private fb: FormBuilder,
    private toast: HotToastService,
  ) {
    this.month = new Date().getMonth() + 1;
    this.year = new Date().getFullYear();
    this.username = this.authService.getUsername();
  }

  ngOnInit() {
    this.budgetForm = this.fb.group({
      budget: ['', [Validators.required]],
    });
    this.getBudget();
  }

  getBudget() {
    this.monthlyBudgetObs$ = this.expenseService.getMonthlyBudget(
      this.year,
      this.month,
      this.username,
    );
  }

  setBudget() {
    const data = this.budgetForm.value;
    console.log(data);
    this.expenseService
      .setMonthlyBudget(this.year, this.month, this.username, data)
      .pipe(
        this.toast.observe({
          success: 'Budget Set Successfully',
          loading: 'Setting Budget...',
          error: `There was an error setting the budget`,
        }),
      )
      .subscribe(() => {
        this.isEditing = false;
        this.getBudget();
      });
  }
}
