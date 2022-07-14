package net.penguincoders.budgetplanner.exception;

import java.io.Serial;

public class DuplicateEmailException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public DuplicateEmailException(String message) {
        super(message);
    }
}
