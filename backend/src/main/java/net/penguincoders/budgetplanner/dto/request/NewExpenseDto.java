package net.penguincoders.budgetplanner.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class NewExpenseDto {

    @NotBlank
    private String name;

    @NotBlank
    private String category;

    @NotNull
    private double amount;

    @NotNull
    private LocalDate date;

    private String description;
}
