package ru.mahach.eruditionserver.exceptions;

import ru.mahach.eruditionserver.exceptions.base.QuestionException;

public class QuestionNotFoundException extends QuestionException {

    public QuestionNotFoundException(String message) {
        super(message);
    }

    public QuestionNotFoundException(String message, Throwable err) {
        super(message, err);
    }

    public QuestionNotFoundException(){
        this("Question not found");
    }

    public QuestionNotFoundException(Long id){
        this("Question with " + id + " id not found");
    }

}
