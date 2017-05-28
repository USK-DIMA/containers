package ru.uskov.dmitry.exception;

/**
 * Created by Dmitry on 24.04.2017.
 */
public class ConfirmPasswordException extends Exception {

    public ConfirmPasswordException() {
    }

    public ConfirmPasswordException(String message) {
        super(message);
    }

    public ConfirmPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfirmPasswordException(Throwable cause) {
        super(cause);
    }

    public ConfirmPasswordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}