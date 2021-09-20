package ru.mahach.eruditionserver.exceptions.base;

public class AnswerException extends RuntimeException{

    public AnswerException(String message){
        super(message);
    }

    public AnswerException(String message, Throwable err){
        super(message, err);
    }
}
