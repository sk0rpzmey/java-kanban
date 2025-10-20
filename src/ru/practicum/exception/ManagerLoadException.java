package ru.practicum.exception;

public class ManagerLoadException extends RuntimeException {
    public ManagerLoadException() {
        super();
    }

    public ManagerLoadException(String message) {
        super(message);
    }

    public ManagerLoadException(String message, Throwable e) {
        super(message, e);
    }
}
