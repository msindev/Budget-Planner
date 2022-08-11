import { Component, ViewChild } from '@angular/core';
import {
  ExpenseControllerService,
  MonthlyExpenseResponse,
} from 'src/openapi-generated';
import { AuthService } from '../services/auth.service';
import { tap } from 'rxjs/operators';
import { ChartData } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';

@Component({
  selector: 'app-tab1',
  templateUrl: 'tab1.page.html',
  styleUrls: ['tab1.page.scss'],
})
export class Tab1Page {
  monthlyExpense: MonthlyExpenseResponse;
  isLoading = true;

  categoryExpenseIcon = {
    food: { name: 'fast-food', color: 'danger' },
    transport: { name: 'car', color: 'secondary' },
    entertainment: { name: 'game-controller', color: 'success' },
    shopping: { name: 'cart', color: 'warning' },
    others: { name: 'wallet', color: 'medium' },
  };

  categories: string[] = [
    'Food',
    'Transport',
    'Entertainment',
    'Shopping',
    'Others',
  ];

  public barChartData: ChartData<'bar'> = {
    labels: this.categories,
    datasets: [{ data: [], label: '₹' }],
  };

  @ViewChild(BaseChartDirective) chart: BaseChartDirective | undefined;

  constructor(
    private expenseService: ExpenseControllerService,
    private authService: AuthService,
  ) {}

  ionViewWillEnter() {
    const month = (new Date().getMonth() + 1).toString();
    const year = new Date().getFullYear().toString();
    const username = this.authService.getUsername();

    this.expenseService
      .getExpenses(year, month, username)
      .pipe(tap(() => (this.isLoading = true)))
      .subscribe((expense) => {
        this.monthlyExpense = expense;
        this.prepareBarChartData();
        this.isLoading = false;
      });
  }

  prepareBarChartData() {
    const categoryExpense = this.monthlyExpense.categoryTotal;
    const data = [];
    for (const category of this.categories) {
      data.push(categoryExpense[category.toLowerCase()]);
    }
    this.barChartData.datasets[0].data = data;
  }
}
