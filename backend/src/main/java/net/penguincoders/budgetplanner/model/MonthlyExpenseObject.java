package net.penguincoders.budgetplanner.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class MonthlyExpenseObject{

    private List<Expense> expenses;

    private double total;

    @Field("category_total")
    private Map<String,Double> categoryTotal;
}
