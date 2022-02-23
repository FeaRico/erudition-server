package ru.mahach.eruditionserver.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mahach.eruditionserver.exceptions.QuestionNotFoundException;
import ru.mahach.eruditionserver.exceptions.base.QuestionException;
import ru.mahach.eruditionserver.models.dto.QuestionDto;
import ru.mahach.eruditionserver.services.QuestionService;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/questions")
@CrossOrigin(value = "http://localhost:4200", allowCredentials = "true")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public ResponseEntity<QuestionDto> createQuestion(@RequestBody QuestionDto question) {
        return ResponseEntity.ok(questionService.createQuestion(question)
                .orElseThrow(() -> new QuestionException("Failed to create question")));
    }

    @PutMapping
    public ResponseEntity<QuestionDto> updateQuestion(@RequestBody QuestionDto question) {
        return ResponseEntity.ok(questionService.updateQuestion(question)
                .orElseThrow(() -> new QuestionException("Failed to update question with id = " +
                        question.getId())));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<QuestionDto> deleteQuestion(@PathVariable Long id) {
        return ResponseEntity.ok(questionService.deleteQuestionById(id)
                .orElseThrow(() ->
                        new QuestionException("Failed to delete question with id = " + id)));
    }

    @GetMapping
    public ResponseEntity<List<QuestionDto>> allQuestions() {
        List<QuestionDto> result = questionService.getAllQuestions();
        return ResponseEntity.ok(result);
    }

    @GetMapping("{id}")
    public ResponseEntity<QuestionDto> questionById(@PathVariable Long id) {
        return ResponseEntity.ok(questionService.getQuestionById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id)));
    }
}
