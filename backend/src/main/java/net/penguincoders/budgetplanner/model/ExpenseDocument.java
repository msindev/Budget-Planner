package net.penguincoders.budgetplanner.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

@Getter
@Setter
@RequiredArgsConstructor
@Document(collection = "expenses")
public class ExpenseDocument {

    @Id
    private ObjectId id;

    @NonNull
    private String username;

    @Field("year")
    private Map<Integer, MonthlyExpense> yearlyExpenses;
}

