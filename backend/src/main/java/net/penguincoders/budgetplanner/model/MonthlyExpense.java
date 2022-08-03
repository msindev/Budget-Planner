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
public class MonthlyExpense {

    @Field("month")
    private Map<Integer,List<Expense>> expenses;
}
