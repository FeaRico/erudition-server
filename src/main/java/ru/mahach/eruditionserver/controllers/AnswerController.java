package ru.mahach.eruditionserver.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.mahach.eruditionserver.exceptions.AnswerNotFoundException;
import ru.mahach.eruditionserver.exceptions.base.AnswerException;
import ru.mahach.eruditionserver.models.dto.AnswerDto;
import ru.mahach.eruditionserver.services.AnswerService;
import ru.mahach.eruditionserver.validation.Marker;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/answers")
@CrossOrigin(value = "http://localhost:4200", allowCredentials = "true")
@Validated
public class AnswerController {
    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping
    @Validated(Marker.Create.class)
    public ResponseEntity<AnswerDto> createAnswer(@RequestBody @Valid AnswerDto answer) {
        return ResponseEntity.ok(answerService.createAnswer(answer)
                .orElseThrow(() -> new AnswerException("Failed to create answer")));
    }

    @PutMapping
    @Validated(Marker.Update.class)
    public ResponseEntity<AnswerDto> updateAnswer(@RequestBody @Valid AnswerDto answer) {
        return ResponseEntity.ok(answerService.updateAnswer(answer)
                .orElseThrow(() -> new AnswerException("Failed to update answer with id = " + answer.getId())));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<AnswerDto> deleteAnswer(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(answerService.deleteAnswerById(id)
                .orElseThrow(() -> new AnswerException("Failed to delete answer with id = " + id)));
    }

    @GetMapping
    public ResponseEntity<List<AnswerDto>> allAnswers() {
        List<AnswerDto> result = answerService.getAllAnswers();
        return ResponseEntity.ok(result);
    }

    @GetMapping("{id}")
    public ResponseEntity<AnswerDto> answerById(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(answerService.getAnswerById(id)
                .orElseThrow(() -> new AnswerNotFoundException(id)));
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity<List<AnswerDto>> answersByQuestionId(@PathVariable @Min(1) Long questionId){
        return ResponseEntity.ok(answerService.getAnswersByQuestionId(questionId));
    }
}
