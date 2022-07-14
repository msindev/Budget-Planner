package net.penguincoders.budgetplanner.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SignupRequestDto {

    @NotBlank(message = "Username cannot be empty")
    @Size(max = 20, message = "Username must be less than 20 characters")
    private String username;

    @NotBlank(message = "Email cannot be empty")
    @Size(max = 50)
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(max = 120)
    private String password;
}
