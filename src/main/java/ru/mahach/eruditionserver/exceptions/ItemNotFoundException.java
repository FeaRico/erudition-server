package ru.mahach.eruditionserver.exceptions;

import ru.mahach.eruditionserver.exceptions.base.ItemException;

public class ItemNotFoundException extends ItemException {
    public ItemNotFoundException(String message) {
        super(message);
    }

    public ItemNotFoundException(String message, Throwable err) {
        super(message, err);
    }

    public ItemNotFoundException() {
        this("Item not found");
    }

    public ItemNotFoundException(Long id) {
        this("Item with id = " + id + " not found");
    }
}
