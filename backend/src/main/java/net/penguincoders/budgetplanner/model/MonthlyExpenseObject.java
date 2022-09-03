package net.penguincoders.budgetplanner.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class MonthlyExpenseObject{

    private List<Expense> expenses;

    private Double total;

    @Field("category_total")
    private Map<String,Double> categoryTotal;

    @Field("monthly_budget")
    private Double monthlyBudget;
}
