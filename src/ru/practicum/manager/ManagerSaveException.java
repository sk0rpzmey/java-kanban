package ru.practicum.manager;

public class ManagerSaveException extends RuntimeException{
    public ManagerSaveException () {
        super();
    }

    public ManagerSaveException (String message) {
        super(message);
    }

    public ManagerSaveException (String message, Throwable e) {
        super(message, e);
    }
}
