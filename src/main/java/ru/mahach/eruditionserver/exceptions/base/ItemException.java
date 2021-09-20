package ru.mahach.eruditionserver.exceptions.base;

public class ItemException extends RuntimeException{
    
    public ItemException(String message) {
        super(message);
    }

    public ItemException(String message, Throwable err) {
        super(message, err);
    }
}
