package net.penguincoders.budgetplanner.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ValidationException;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandlers {

    private static final String AUTHENTICATION_EXCEPTION = "AUTHENTICATION_EXCEPTION";
    private static final String MESSAGE = "message";

    @ExceptionHandler
    public ResponseEntity<Object> handle(ValidationException ex) {
        return ResponseEntity.badRequest()
                .body(Map.of(
                        "code", AUTHENTICATION_EXCEPTION,
                        MESSAGE, ex.getMessage()
                ));
    }

    @ExceptionHandler
    public ResponseEntity<Object> handle(DuplicateUserNameException ex) {
        return ResponseEntity.badRequest()
                .body(Map.of(
                        "code", AUTHENTICATION_EXCEPTION,
                        MESSAGE, ex.getMessage()
                ));
    }

    @ExceptionHandler
    public ResponseEntity<Object> handle(DuplicateEmailException ex) {
        return ResponseEntity.badRequest()
                .body(Map.of(
                        "code", AUTHENTICATION_EXCEPTION,
                        MESSAGE, ex.getMessage()
                ));
    }
}