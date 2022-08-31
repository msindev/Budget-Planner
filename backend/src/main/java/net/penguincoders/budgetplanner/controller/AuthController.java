package net.penguincoders.budgetplanner.controller;

import net.penguincoders.budgetplanner.dto.request.ChangePasswordRequestDto;
import net.penguincoders.budgetplanner.dto.request.LoginRequestDto;
import net.penguincoders.budgetplanner.dto.request.SignupRequestDto;
import net.penguincoders.budgetplanner.dto.response.JwtResponse;
import net.penguincoders.budgetplanner.dto.response.MessageResponse;
import net.penguincoders.budgetplanner.exception.ValidationException;
import net.penguincoders.budgetplanner.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequestDto loginRequest, BindingResult errors) {
        if (errors.hasErrors()) {
            throw new ValidationException(errors);
        }
        JwtResponse jwtResponse = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequestDto signUpRequest, BindingResult errors) {
        if (errors.hasErrors()) {
            throw new ValidationException(errors);
        }
        MessageResponse messageResponse = authService.registerUser(signUpRequest);
        return ResponseEntity.ok(messageResponse);
    }

    @PostMapping("/change-password")
    public ResponseEntity<MessageResponse> changePassword(@Valid @RequestBody ChangePasswordRequestDto changePasswordRequest, BindingResult errors) {
        if (errors.hasErrors()) {
            throw new ValidationException(errors);
        }
        MessageResponse messageResponse = authService.changePassword(changePasswordRequest);
        if(messageResponse.getMessage().equals("Password changed successfully!")) {
            return ResponseEntity.ok(messageResponse);
        }
        return new ResponseEntity<>(messageResponse,HttpStatus.BAD_REQUEST);
    }
}