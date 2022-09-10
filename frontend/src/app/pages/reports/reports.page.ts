import { Component } from '@angular/core';
import { Color, ScaleType } from '@swimlane/ngx-charts';
import { forkJoin } from 'rxjs';
import { tap } from 'rxjs/operators';
import { ExpenseControllerService } from '../../../openapi-generated';
import { AuthService } from '../../services/auth.service';

interface ICardChart {
  name: string;
  value: number;
}
@Component({
  selector: 'app-reports',
  templateUrl: './reports.page.html',
  styleUrls: ['./reports.page.scss'],
})
export class ReportsPageComponent {
  cardChartView: [number, number] = [400, 250];
  total: number;
  monthlyBudget: number;
  categoryTotal: { [key: string]: number };
  cardChartValues: ICardChart[] = [];
  isCardChartLoading: boolean;
  colorScheme: Color = {
    name: 'cardColorScheme',
    selectable: true,
    group: ScaleType.Ordinal,
    domain: ['#FFA500', '#EE4B2B', '#219819'],
  };

  constructor(
    private expenseService: ExpenseControllerService,
    private authService: AuthService,
  ) {}

  ionViewWillEnter(): void {
    const month = new Date().getMonth() + 1;
    const year = new Date().getFullYear();
    const username = this.authService.getUsername();
    this.isCardChartLoading = true;
    this.cardChartValues = [];
    const categoryWiseExpensesObs = this.expenseService
      .getExpenses(year, month, username)
      .pipe(
        tap((res) => {
          (this.total = res.total), (this.categoryTotal = res.categoryTotal);
        }),
      );
    const monthlyBudgetObs = this.expenseService
      .getMonthlyBudget(year, month, username)
      .pipe(
        tap((res) => {
          this.monthlyBudget = res.monthlyBudget;
        }),
      );
    forkJoin([categoryWiseExpensesObs, monthlyBudgetObs]).subscribe(() =>
      this.initCardChart(),
    );
  }

  initCardChart(): void {
    const needsTotal =
      this.categoryTotal['food'] +
      this.categoryTotal['home'] +
      this.categoryTotal['transport'];
    const wantsTotal =
      this.categoryTotal['entertainment'] +
      this.categoryTotal['shopping'] +
      this.categoryTotal['others'];
    const investmentsTotal = this.categoryTotal['investments'];
    this.cardChartValues.push({
      name: 'Needs : ' + this.getCategoryTotalPercentage(needsTotal) + '%',
      value: needsTotal,
    });
    this.cardChartValues.push({
      name: 'Wants: ' + this.getCategoryTotalPercentage(wantsTotal) + '%',
      value: wantsTotal,
    });
    this.cardChartValues.push({
      name:
        'Investments: ' +
        this.getCategoryTotalPercentage(investmentsTotal) +
        '%',
      value: investmentsTotal,
    });
    this.isCardChartLoading = false;
  }

  getCategoryTotalPercentage(expense: number): string {
    return ((expense / this.monthlyBudget) * 100).toFixed(1);
  }
}
