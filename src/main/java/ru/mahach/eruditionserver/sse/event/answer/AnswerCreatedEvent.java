package ru.mahach.eruditionserver.sse.event.answer;

import ru.mahach.eruditionserver.sse.event.base.EruditionEvent;
import ru.mahach.eruditionserver.sse.type.EventType;

public class AnswerCreatedEvent implements EruditionEvent {
    private Long answerId;
    private Long createTime;

    public AnswerCreatedEvent(Long answerId, Long createTime) {
        this.answerId = answerId;
        this.createTime = createTime;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    @Override
    public EventType getEventType() {
        return EventType.ANSWER;
    }
}
