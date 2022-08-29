package net.penguincoders.budgetplanner.service;

import net.penguincoders.budgetplanner.dto.request.ChangePasswordRequestDto;
import net.penguincoders.budgetplanner.dto.request.LoginRequestDto;
import net.penguincoders.budgetplanner.dto.request.SignupRequestDto;
import net.penguincoders.budgetplanner.dto.response.JwtResponse;
import net.penguincoders.budgetplanner.dto.response.MessageResponse;
import net.penguincoders.budgetplanner.model.User;
import net.penguincoders.budgetplanner.repository.UserRepository;
import net.penguincoders.budgetplanner.security.jwt.JwtUtils;
import net.penguincoders.budgetplanner.security.service.UserDetailsImpl;
import net.penguincoders.budgetplanner.validation.AuthValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AuthValidation authValidation;

    @Autowired
    ExpenseService expenseService;

    public JwtResponse authenticateUser(LoginRequestDto loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail());
    }

    public MessageResponse registerUser(SignupRequestDto signUpRequest) {
        authValidation.registerValidation(signUpRequest);
        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        userRepository.save(user);
        expenseService.createExpenseDocument(user.getUsername());
        return new MessageResponse("User registered successfully!");
    }

    public MessageResponse changePassword(ChangePasswordRequestDto changePasswordRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            String oldPassword = user.get().getPassword();
            if (encoder.matches(changePasswordRequest.getOldPassword(), oldPassword)) {
                user.get().setPassword(encoder.encode(changePasswordRequest.getNewPassword()));
                userRepository.save(user.get());
                return new MessageResponse("Password changed successfully!");
            } else {
                return new MessageResponse("Wrong Password Entered, please try again!");
            }
        }
        return new MessageResponse("User not found!");
    }
}
