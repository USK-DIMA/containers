package ru.uskov.dmitry.exception;

/**
 * Created by Dmitry on 15.06.2017.
 */
public class IncorrectNewUserException extends Exception {
    public IncorrectNewUserException() {
    }

    public IncorrectNewUserException(String message) {
        super(message);
    }

    public IncorrectNewUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectNewUserException(Throwable cause) {
        super(cause);
    }

    public IncorrectNewUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}