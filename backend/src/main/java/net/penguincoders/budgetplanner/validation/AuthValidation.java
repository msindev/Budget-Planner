package net.penguincoders.budgetplanner.validation;

import net.penguincoders.budgetplanner.dto.request.SignupRequestDto;
import net.penguincoders.budgetplanner.exception.DuplicateEmailException;
import net.penguincoders.budgetplanner.exception.DuplicateUserNameException;
import net.penguincoders.budgetplanner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthValidation {

    @Autowired
    private UserRepository userRepository;

    public void registerValidation(SignupRequestDto signupRequest) {
        if (Boolean.TRUE.equals(userRepository.existsByUsername(signupRequest.getUsername()))) {
            throw new DuplicateUserNameException("Error: Username is already taken!");
        }

        if (Boolean.TRUE.equals(userRepository.existsByEmail(signupRequest.getEmail()))) {
            throw new DuplicateEmailException("Error: Email is already in use!");
        }
    }
}
