package ru.mahach.eruditionserver.sse.event.question;

import ru.mahach.eruditionserver.sse.event.base.EruditionEvent;
import ru.mahach.eruditionserver.sse.type.EventType;

public class QuestionCreatedEvent implements EruditionEvent {
    private final Long questionId;
    private final Long createdTime;

    public QuestionCreatedEvent(Long questionId, Long createdTime) {
        this.questionId = questionId;
        this.createdTime = createdTime;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public Long getCreatedTime() {
        return createdTime;
    }

    @Override
    public EventType getEventType() {
        return EventType.QUESTION;
    }
}
