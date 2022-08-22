package net.penguincoders.budgetplanner.service;

import net.penguincoders.budgetplanner.dto.request.NewExpenseDto;
import net.penguincoders.budgetplanner.dto.response.MessageResponse;
import net.penguincoders.budgetplanner.dto.response.MonthlyExpenseResponse;
import net.penguincoders.budgetplanner.model.*;
import net.penguincoders.budgetplanner.repository.ExpenseRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.Comparator.comparing;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public void createExpenseDocument(String username) {
        expenseRepository.save(new ExpenseDocument(username));
    }

    public MessageResponse addExpense(NewExpenseDto newExpense, String username) {

        Expense expense = new Expense(new ObjectId(), newExpense.getName(), newExpense.getCategory(), newExpense.getAmount(), newExpense.getDate());
        if (newExpense.getDescription() != null) {
            expense.setDescription(newExpense.getDescription());
        }
        int year = newExpense.getDate().getYear();
        int month = newExpense.getDate().getMonthValue();

        ExpenseDocument expenseDocument = expenseRepository.findByUsername(username);
        if(expenseDocument == null) {
            return new MessageResponse("User Details Not Found in the Database.");
        }
        Map<Integer, MonthlyExpense> yearlyExpense = expenseDocument.getYearlyExpenses();

        if (yearlyExpense == null) { //If the entire document is empty, creating a first expense

            MonthlyExpense monthlyExpense = createMonthlyExpense(month, expense);

            Map<Integer, MonthlyExpense> yearlyExpenseMap = new HashMap<>();
            yearlyExpenseMap.put(year, monthlyExpense);

            expenseDocument.setYearlyExpenses(yearlyExpenseMap);
        } else {//There exists yearly expense object
            MonthlyExpense monthlyExpenseList = yearlyExpense.get(year);
            if (monthlyExpenseList == null) { // if there is no monthly expense list for this year, create one
                MonthlyExpense monthlyExpense = createMonthlyExpense(month, expense);

                yearlyExpense.put(year, monthlyExpense);
            } else {//Year exists
                // get the monthly expense list for that year
                Map<Integer, MonthlyExpenseObject> monthlyExpenseMap = monthlyExpenseList.getExpenses();
                List<Expense> expenseList;
                if (monthlyExpenseMap == null) {
                    MonthlyExpense monthlyExpense = createMonthlyExpense(month, expense);
                    yearlyExpense.put(year, monthlyExpense);
                } else {
                    if (monthlyExpenseMap.get(month) == null) {
                        MonthlyExpenseObject monthlyExpenseObject = createMonthlyExpenseObject(expense);
                        monthlyExpenseMap.put(month, monthlyExpenseObject);
                        yearlyExpense.put(year, new MonthlyExpense(monthlyExpenseMap));
                    } else {
                        double monthlyTotal = monthlyExpenseMap.get(month).getTotal();

                        expenseList = monthlyExpenseMap.get(month).getExpenses();
                        expenseList.add(expense);

                        Map<String, Double> categoryTotal = monthlyExpenseMap.get(month).getCategoryTotal();
                        double categoryTotalAmount = categoryTotal.get(expense.getCategory());
                        categoryTotal.put(expense.getCategory(), categoryTotalAmount + expense.getAmount());

                        monthlyExpenseMap.put(month, new MonthlyExpenseObject(expenseList, monthlyTotal + expense.getAmount(), categoryTotal));

                        yearlyExpense.put(year, new MonthlyExpense(monthlyExpenseMap));
                    }
                }
            }
            expenseDocument.setYearlyExpenses(yearlyExpense);
        }

        expenseRepository.save(expenseDocument);
        return new MessageResponse("Expense added successfully!");
    }

    private MonthlyExpense createMonthlyExpense(int month, Expense expense) {

        MonthlyExpenseObject monthlyExpenseObject = createMonthlyExpenseObject(expense);

        Map<Integer, MonthlyExpenseObject> monthlyExpenseMap = new HashMap<>();
        monthlyExpenseMap.put(month, monthlyExpenseObject);

        return new MonthlyExpense(monthlyExpenseMap);
    }

    private MonthlyExpenseObject createMonthlyExpenseObject(Expense expense) {
        List<Expense> expenseList = new ArrayList<>();
        expenseList.add(expense);

        Map<String, Double> categoryTotal = new HashMap<>();
        for (ExpenseCategory category : ExpenseCategory.values()) {
            categoryTotal.put(category.toString().toLowerCase(), 0.0);
        }
        categoryTotal.put(expense.getCategory(), expense.getAmount());

        return new MonthlyExpenseObject(expenseList, expense.getAmount(), categoryTotal);
    }

    public MonthlyExpenseResponse getMonthlyExpense(String year, String month, String username){
        MonthlyExpenseResponse monthlyExpenseResponse = new MonthlyExpenseResponse(new ArrayList<>(), 0.0, new HashMap<>());
        ExpenseDocument expenseDocument = expenseRepository.findByUsername(username);
        if(expenseDocument == null) {
            return monthlyExpenseResponse;
        }
        Map<Integer, MonthlyExpense> yearlyExpense = expenseDocument.getYearlyExpenses();
        if(yearlyExpense == null) {
            return monthlyExpenseResponse;
        }
        MonthlyExpense monthlyExpense = yearlyExpense.get(Integer.parseInt(year));
        if(monthlyExpense == null) {
            return monthlyExpenseResponse;
        }
        Map<Integer, MonthlyExpenseObject> monthlyExpenseMap = monthlyExpense.getExpenses();
        if(monthlyExpenseMap == null) {
            return monthlyExpenseResponse;
        }
        MonthlyExpenseObject monthlyExpenseObject = monthlyExpenseMap.get(Integer.parseInt(month));
        if(monthlyExpenseObject == null) {
            return monthlyExpenseResponse;
        }
        List<Expense> expenses = monthlyExpenseObject.getExpenses();
        expenses.sort(comparing(Expense::getDate).reversed());
        monthlyExpenseResponse.setExpenses(expenses);
        monthlyExpenseResponse.setTotal(monthlyExpenseObject.getTotal());
        monthlyExpenseResponse.setCategoryTotal(monthlyExpenseObject.getCategoryTotal());
        return monthlyExpenseResponse;
    }
}
