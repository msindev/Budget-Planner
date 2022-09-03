package net.penguincoders.budgetplanner.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MonthlyBudgetDto {
    @NotNull
    private double budget;
}
