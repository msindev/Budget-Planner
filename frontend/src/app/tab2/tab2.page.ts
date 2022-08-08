import { DatePipe } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { HotToastService } from '@ngneat/hot-toast';
import { ExpenseControllerService } from 'src/openapi-generated';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-tab2',
  templateUrl: 'tab2.page.html',
  styleUrls: ['tab2.page.scss'],
})
export class Tab2Page {
  expenseForm: FormGroup;
  constructor(
    private fb: FormBuilder,
    private expenseService: ExpenseControllerService,
    private authService: AuthService,
    private toast: HotToastService,
    private router: Router,
    private datePipe: DatePipe,
  ) {
    this.expenseForm = this.fb.group({
      name: ['', Validators.required],
      amount: ['', Validators.required],
      date: ['', Validators.required],
      category: ['', Validators.required],
      description: [''],
    });
  }

  onSubmit() {
    console.log(this.expenseForm.value);
    const username = this.authService.getUsername();
    let expense = { ...this.expenseForm.value };
    expense.date = this.datePipe.transform(expense.date, 'yyyy-MM-dd');
    this.expenseService
      .addExpense(username, expense)
      .pipe(
        this.toast.observe({
          success: 'Expense Saved successfully',
          loading: 'Saving Expense...',
          error: () => `There was an error saving your expense `,
        }),
      )
      .subscribe(() => {
        this.expenseForm.reset();
        this.router.navigate(['/tabs/home']);
      });
  }
}
