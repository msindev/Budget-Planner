package net.penguincoders.budgetplanner.service;

import net.penguincoders.budgetplanner.dto.request.NewExpenseDto;
import net.penguincoders.budgetplanner.dto.response.MessageResponse;
import net.penguincoders.budgetplanner.model.Expense;
import net.penguincoders.budgetplanner.model.ExpenseDocument;
import net.penguincoders.budgetplanner.model.MonthlyExpense;
import net.penguincoders.budgetplanner.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public void createExpenseDocument(String username){
        expenseRepository.save(new ExpenseDocument(username));
    }

    public MessageResponse addExpense(NewExpenseDto newExpense, String username) {

        Expense expense = new Expense(newExpense.getName(), newExpense.getCategory(), newExpense.getAmount(), newExpense.getDate());
        int year = newExpense.getDate().getYear();
        int month = newExpense.getDate().getMonthValue();

        ExpenseDocument expenseDocument = expenseRepository.findByUsername(username);
        Map<Integer, MonthlyExpense> yearlyExpense = expenseDocument.getYearlyExpenses();

        if(yearlyExpense == null){ //If the entire document is empty, creating a first expense

            MonthlyExpense monthlyExpense = createMonthlyExpense(month,expense);

            Map<Integer, MonthlyExpense> yearlyExpenseMap = new HashMap<>();
            yearlyExpenseMap.put(year, monthlyExpense);

            expenseDocument.setYearlyExpenses(yearlyExpenseMap);
        }
        else {//There exists yearly expense object
            MonthlyExpense monthlyExpenseList = yearlyExpense.get(year);
            if (monthlyExpenseList == null) { // if there is no monthly expense list for this year, create one
                MonthlyExpense monthlyExpense = createMonthlyExpense(month, expense);

                yearlyExpense.put(year, monthlyExpense);
            } else {//Year exists
                Map<Integer, List<Expense>> monthlyExpenseMap = monthlyExpenseList.getExpenses();// get the monthly expense list for that year
                List<Expense> expenseList;
                if (monthlyExpenseMap == null) {//if no expenses for that month
                    expenseList = new ArrayList<>();
                    expenseList.add(expense);

                    monthlyExpenseMap = new HashMap<>();
                } else {
                    expenseList = monthlyExpenseMap.get(month);
                    if (expenseList == null) {
                        expenseList = new ArrayList<>();
                    }
                    expenseList.add(expense);
                }
                monthlyExpenseMap.put(month, expenseList);
                yearlyExpense.put(year, new MonthlyExpense(monthlyExpenseMap));
            }
            expenseDocument.setYearlyExpenses(yearlyExpense);
        }

        expenseRepository.save(expenseDocument);
        return new MessageResponse("Expense added successfully!");
    }

    private MonthlyExpense createMonthlyExpense(int month,Expense expense) {
        List<Expense> expenseList = new ArrayList<>();
        expenseList.add(expense);

        Map<Integer, List<Expense>> monthlyExpenseMap = new HashMap<>();
        monthlyExpenseMap.put(month, expenseList);
        return new MonthlyExpense(monthlyExpenseMap);
    }
}
