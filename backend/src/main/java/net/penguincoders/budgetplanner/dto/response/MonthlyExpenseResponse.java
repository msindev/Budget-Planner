package net.penguincoders.budgetplanner.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.penguincoders.budgetplanner.model.Expense;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
public class MonthlyExpenseResponse {
    private List<Expense> expenses;

    private double total;

    private Map<String,Double> categoryTotal;
}
