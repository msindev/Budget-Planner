package net.penguincoders.budgetplanner.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ChangePasswordRequestDto {

    @NotBlank
    private String oldPassword;

    @NotBlank
    private String newPassword;
}
