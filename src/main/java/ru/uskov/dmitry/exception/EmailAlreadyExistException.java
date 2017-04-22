package ru.uskov.dmitry.exception;

/**
 * Created by Dmitry on 22.04.2017.
 */
public class EmailAlreadyExistException extends Exception {

    public EmailAlreadyExistException() {
    }

    public EmailAlreadyExistException(String message) {
        super(message);
    }

    public EmailAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailAlreadyExistException(Throwable cause) {
        super(cause);
    }

    public EmailAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}