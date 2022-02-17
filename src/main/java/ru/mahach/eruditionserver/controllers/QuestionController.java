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
    public ResponseEntity<QuestionDto> createQuestion(@RequestBody QuestionDto question){
        return ResponseEntity.ok(questionService.create(question)
                .orElseThrow(() -> new QuestionException("Failed to create question")));
    }

    @PutMapping
    public ResponseEntity<QuestionDto> updateQuestion(@RequestBody QuestionDto question){
        return ResponseEntity.ok(questionService.update(question)
                .orElseThrow(() -> new QuestionException("Failed to update question")));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<QuestionDto> deleteQuestion(@PathVariable Long id){
        return ResponseEntity.ok(questionService.deleteById(id)
                .orElseThrow(() -> new QuestionException("Failed to delete question")));
    }

    @GetMapping
    public ResponseEntity<List<QuestionDto>> allQuestion(){
        List<QuestionDto> result = questionService.getAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("{id}")
    public ResponseEntity<QuestionDto> questionById(@PathVariable Long id){
        return ResponseEntity.ok(questionService.getById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id)));
    }
}
