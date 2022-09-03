package net.penguincoders.budgetplanner.service;

import net.penguincoders.budgetplanner.dao.ExpenseDao;
import net.penguincoders.budgetplanner.dto.request.MonthlyBudgetDto;
import net.penguincoders.budgetplanner.dto.request.NewExpenseDto;
import net.penguincoders.budgetplanner.dto.response.MessageResponse;
import net.penguincoders.budgetplanner.dto.response.MonthlyBudgetResponse;
import net.penguincoders.budgetplanner.dto.response.MonthlyExpenseResponse;
import net.penguincoders.budgetplanner.model.*;
import net.penguincoders.budgetplanner.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ExpenseDao expenseDao;

    public void createExpenseDocument(String username) {
        expenseRepository.save(new ExpenseDocument(username));
    }

    public MessageResponse addExpense(NewExpenseDto newExpense, String username) {
        String response = expenseDao.addExpense(username, newExpense);
        return new MessageResponse(response);
    }

    public MonthlyExpenseResponse getMonthlyExpense(int year, int month, String username){
        return expenseDao.getMonthlyExpense(username, year, month);
    }

    public MessageResponse setMonthlyBudget(MonthlyBudgetDto budget, int year, int month, String username){
        return new MessageResponse(expenseDao.setMonthlyBudget(username,year, month, budget.getBudget()));

    }

    public MonthlyBudgetResponse getMonthlyBudget(int year, int month, String username) {
        double monthlyBudget = expenseDao.getMonthlyBudget(username,year, month);
        return new MonthlyBudgetResponse(monthlyBudget);
    }
}
