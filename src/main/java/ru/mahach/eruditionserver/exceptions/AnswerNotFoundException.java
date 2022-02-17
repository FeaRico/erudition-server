package ru.mahach.eruditionserver.exceptions;

import ru.mahach.eruditionserver.exceptions.base.AnswerException;

public class AnswerNotFoundException extends AnswerException {
    public AnswerNotFoundException(String message) {
        super(message);
    }

    public AnswerNotFoundException(String message, Throwable err) {
        super(message, err);
    }

    public AnswerNotFoundException() {
        this("Answer not found");
    }

    public AnswerNotFoundException(Long id) {
        this("Answer with id = " + id + "not found");
    }
}
