package net.penguincoders.budgetplanner.dao;

import net.penguincoders.budgetplanner.dto.request.NewExpenseDto;
import net.penguincoders.budgetplanner.dto.response.MonthlyExpenseResponse;

public interface ExpenseDao {
    String addExpense(String username, NewExpenseDto expense);
    MonthlyExpenseResponse getMonthlyExpense(String username, int year, int month);
    String setMonthlyBudget(String username, int year, int month,double amount);
    double getMonthlyBudget(String username, int year, int month) throws NullPointerException;
}
