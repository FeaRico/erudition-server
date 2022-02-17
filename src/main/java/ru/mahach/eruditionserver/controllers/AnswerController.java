package ru.mahach.eruditionserver.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mahach.eruditionserver.exceptions.AnswerNotFoundException;
import ru.mahach.eruditionserver.exceptions.base.AnswerException;
import ru.mahach.eruditionserver.models.dto.AnswerDto;
import ru.mahach.eruditionserver.services.AnswerService;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/answers")
@CrossOrigin(value = "http://localhost:4200", allowCredentials = "true")
public class AnswerController {
    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping
    public ResponseEntity<AnswerDto> createAnswer(@RequestBody AnswerDto answer) {
        return ResponseEntity.ok(answerService.create(answer)
                .orElseThrow(() -> new AnswerException("Failed to create answer")));
    }

    @PutMapping
    public ResponseEntity<AnswerDto> updateAnswer(@RequestBody AnswerDto answer) {
        return ResponseEntity.ok(answerService.update(answer)
                .orElseThrow(() -> new AnswerException("Failed to update answer with id = " + answer.getId())));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<AnswerDto> deleteAnswer(@PathVariable Long id) {
        return ResponseEntity.ok(answerService.deleteById(id)
                .orElseThrow(() -> new AnswerException("Failed to delete answer with id = " + id)));
    }

    @GetMapping
    public ResponseEntity<List<AnswerDto>> allAnswers() {
        List<AnswerDto> result = answerService.getAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("{id}")
    public ResponseEntity<AnswerDto> answerById(@PathVariable Long id) {
        return ResponseEntity.ok(answerService.getById(id)
                .orElseThrow(() -> new AnswerNotFoundException(id)));
    }
}
