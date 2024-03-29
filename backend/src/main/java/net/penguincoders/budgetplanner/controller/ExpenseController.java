package net.penguincoders.budgetplanner.controller;

import net.penguincoders.budgetplanner.dto.request.MonthlyBudgetDto;
import net.penguincoders.budgetplanner.dto.request.NewExpenseDto;
import net.penguincoders.budgetplanner.dto.response.MessageResponse;
import net.penguincoders.budgetplanner.dto.response.MonthlyBudgetResponse;
import net.penguincoders.budgetplanner.dto.response.MonthlyExpenseResponse;
import net.penguincoders.budgetplanner.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping()
    public ResponseEntity<MessageResponse> addExpense(@Valid @RequestBody NewExpenseDto newExpenseDto, @RequestParam String username, BindingResult errors) {
        if (errors.hasErrors()) {
            throw new ValidationException((Throwable) errors);
        }
        MessageResponse messageResponse = expenseService.addExpense(newExpenseDto, username);
        return ResponseEntity.ok(messageResponse);
    }

    @GetMapping()
    public ResponseEntity<MonthlyExpenseResponse> getExpenses(@RequestParam int year, @RequestParam int month, @RequestParam String username) {
        MonthlyExpenseResponse monthlyExpenseResponse = expenseService.getMonthlyExpense(year, month, username);
        return ResponseEntity.ok(monthlyExpenseResponse);
    }

    @PostMapping("/budget")
    public ResponseEntity<MessageResponse> setMonthlyBudget(@RequestParam int year, @RequestParam int month, @RequestParam String username, @Valid @RequestBody MonthlyBudgetDto monthlyBudgetDto) {
        MessageResponse messageResponse = expenseService.setMonthlyBudget(monthlyBudgetDto, year, month, username);
        return ResponseEntity.ok(messageResponse);
    }

    @GetMapping("/budget")
    public ResponseEntity<MonthlyBudgetResponse> getMonthlyBudget(@RequestParam int year, @RequestParam int month, @RequestParam String username){
        MonthlyBudgetResponse monthlyBudgetResponse = expenseService.getMonthlyBudget(year, month, username);
        return ResponseEntity.ok(monthlyBudgetResponse);
    }

}
