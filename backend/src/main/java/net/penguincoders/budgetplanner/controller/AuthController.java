package net.penguincoders.budgetplanner.controller;

import net.penguincoders.budgetplanner.dto.request.LoginRequestDto;
import net.penguincoders.budgetplanner.dto.request.SignupRequestDto;
import net.penguincoders.budgetplanner.dto.response.JwtResponse;
import net.penguincoders.budgetplanner.dto.response.MessageResponse;
import net.penguincoders.budgetplanner.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.ValidationException;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequestDto loginRequest, BindingResult errors) {
        if (errors.hasErrors()) {
            throw new ValidationException((Throwable) errors);
        }
        JwtResponse jwtResponse = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequestDto signUpRequest, BindingResult errors) {
        if (errors.hasErrors()) {
            throw new ValidationException((Throwable) errors);
        }
        MessageResponse messageResponse = authService.registerUser(signUpRequest);
        return ResponseEntity.ok(messageResponse);
    }
}