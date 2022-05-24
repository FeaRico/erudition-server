package ru.mahach.eruditionserver.controllers;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import ru.mahach.eruditionserver.exceptions.AnswerNotFoundException;
import ru.mahach.eruditionserver.exceptions.base.AnswerException;
import ru.mahach.eruditionserver.models.dto.AnswerDto;
import ru.mahach.eruditionserver.services.AnswerService;
import ru.mahach.eruditionserver.sse.event.answer.AnswerCreatedEvent;
import ru.mahach.eruditionserver.sse.registry.SseClientsRegistry;
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
    private final SseClientsRegistry sseClientsRegistry;
    private final ApplicationEventPublisher eventPublisher;

    public AnswerController(AnswerService answerService, SseClientsRegistry sseClientsRegistry, ApplicationEventPublisher eventPublisher) {
        this.answerService = answerService;
        this.sseClientsRegistry = sseClientsRegistry;
        this.eventPublisher = eventPublisher;
    }

    @PostMapping
    @Validated(Marker.Create.class)
    public ResponseEntity<AnswerDto> createAnswer(@RequestBody @Valid AnswerDto answer) {
        AnswerDto createdAnswer = answerService.createAnswer(answer)
                .orElseThrow(() -> new AnswerException("Failed to create answer"));
        eventPublisher.publishEvent(new AnswerCreatedEvent(createdAnswer.getId(), System.currentTimeMillis()));
        return ResponseEntity.ok(createdAnswer);
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
    public ResponseEntity<List<AnswerDto>> answersByQuestionId(@PathVariable @Min(1) Long questionId) {
        return ResponseEntity.ok(answerService.getAnswersByQuestionId(questionId));
    }

    @GetMapping("/sse/subscribe")
    public SseEmitter subscribeSse(@RequestHeader String uuid) {
        return sseClientsRegistry.register(uuid, -1L);
    }

    @GetMapping("/sse/unsubscribe")
    public void unsubscribeSse(@RequestHeader String uuid) {
        sseClientsRegistry.unregister(uuid);
    }
}
