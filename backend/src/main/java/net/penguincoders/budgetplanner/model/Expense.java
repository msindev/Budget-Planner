package net.penguincoders.budgetplanner.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Getter
@Setter
@RequiredArgsConstructor
public class Expense {
    @Id
    private ObjectId id;

    @NonNull
    private String name;

    @NonNull
    private String category;

    @NonNull
    private double amount;

    @NonNull
    private LocalDate date;

    private String description;
}
