package net.penguincoders.budgetplanner.exception;

import java.io.Serial;

public class DuplicateUserNameException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public DuplicateUserNameException(String message) {
        super(message);
    }
}
