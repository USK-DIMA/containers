package ru.uskov.dmitry.exception;

/**
 * Created by Dmitry on 22.04.2017.
 */
public class LoginAlreadyExistException extends Exception {

    public LoginAlreadyExistException() {
    }

    public LoginAlreadyExistException(String message) {
        super(message);
    }

    public LoginAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAlreadyExistException(Throwable cause) {
        super(cause);
    }

    public LoginAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}