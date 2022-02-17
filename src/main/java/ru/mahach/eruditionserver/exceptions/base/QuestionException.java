package ru.mahach.eruditionserver.exceptions.base;

public class QuestionException extends RuntimeException {
    public QuestionException(String message) {
        super(message);
    }

    public QuestionException(String message, Throwable err) {
        super(message, err);
    }
}
