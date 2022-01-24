package ru.mahach.eruditionserver.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mahach.eruditionserver.exceptions.AnswerNotFoundException;
import ru.mahach.eruditionserver.exceptions.base.AnswerException;
import ru.mahach.eruditionserver.models.entity.AnswerEntity;
import ru.mahach.eruditionserver.services.AnswerService;

import java.util.Collection;

@RestController
@RequestMapping(value = "api/v1/answers")
@CrossOrigin(value = "http://localhost:4200", allowCredentials = "true")
public class AnswerController {

    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping
    public ResponseEntity<AnswerEntity> createAnswer(@RequestBody AnswerEntity answer){
        return ResponseEntity.ok(answerService.createAnswer(answer)
                .orElseThrow(() -> new AnswerException("Failed to create answer")));
    }

    @PutMapping
    public ResponseEntity<AnswerEntity> updateAnswer(@RequestBody AnswerEntity answer){
        return ResponseEntity.ok(answerService.updateAnswer(answer)
                .orElseThrow(() -> new AnswerException("Failed to update answer")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AnswerEntity> deleteAnswer(@PathVariable Long id){
        return ResponseEntity.ok(answerService.deleteAnswerById(id)
                .orElseThrow(() -> new AnswerException("Failed to delete answer")));
    }

    @GetMapping("/")
    public ResponseEntity<Collection<AnswerEntity>> allAnswer(){
        Collection<AnswerEntity> result = answerService.answerFindAll();
        if(result.isEmpty()){
            throw new AnswerNotFoundException();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnswerEntity> answerById(@PathVariable Long id){
        return ResponseEntity.ok(answerService.answerFindById(id)
                .orElseThrow(() -> new AnswerNotFoundException(id)));
    }
}
