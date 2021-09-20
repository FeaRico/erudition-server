package ru.mahach.eruditionserver.advice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.mahach.eruditionserver.exceptions.AnswerNotFoundException;
import ru.mahach.eruditionserver.exceptions.ItemNotFoundException;
import ru.mahach.eruditionserver.exceptions.QuestionNotFoundException;
import ru.mahach.eruditionserver.exceptions.base.QuestionException;

@RestControllerAdvice
public class BasicControllerExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler({ItemNotFoundException.class})
    public ResponseEntity<Object> itemNotFoundException(ItemNotFoundException ex, WebRequest request){
        String body = "Item controller error: " + ex.getMessage();
        return handleExceptionInternal(ex, body,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({QuestionNotFoundException.class})
    public ResponseEntity<Object> questionNotFoundException(QuestionNotFoundException ex, WebRequest request){
        String body = "Question controller error: " + ex.getMessage();
        return handleExceptionInternal(ex, body,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({AnswerNotFoundException.class})
    public ResponseEntity<Object> answerNotFoundException(AnswerNotFoundException ex, WebRequest request){
        String body = "Answer controller error: " + ex.getMessage();
        return handleExceptionInternal(ex, body,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
