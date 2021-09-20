package ru.mahach.eruditionserver.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mahach.eruditionserver.exceptions.QuestionNotFoundException;
import ru.mahach.eruditionserver.exceptions.base.QuestionException;
import ru.mahach.eruditionserver.models.Question;
import ru.mahach.eruditionserver.services.QuestionService;

import java.util.Collection;

@RestController
@RequestMapping(value = "api/v1/question")
@CrossOrigin(value = "http://localhost:4200", allowCredentials = "true")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public ResponseEntity<Question> createQuestion(@RequestBody Question question){
        return ResponseEntity.ok(questionService.createQuestion(question)
                .orElseThrow(() -> new QuestionException("Failed to create question")));
    }

    @PutMapping
    public ResponseEntity<Question> updateQuestion(@RequestBody Question question){
        return ResponseEntity.ok(questionService.updateQuestion(question)
                .orElseThrow(() -> new QuestionException("Failed to update question")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Question> deleteQuestion(@PathVariable Long id){
        return ResponseEntity.ok(questionService.deleteQuestionById(id)
                .orElseThrow(() -> new QuestionException("Failed to delete question")));
    }

    @GetMapping("/all")
    public ResponseEntity<Collection<Question>> allQuestion(){
        Collection<Question> result = questionService.questionFindAll();
        if(result.isEmpty()){
            throw new QuestionNotFoundException();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Question> questionById(@PathVariable Long id){
        return ResponseEntity.ok(questionService.questionFindById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id)));
    }
}
