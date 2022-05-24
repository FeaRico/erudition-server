package ru.mahach.eruditionserver.sse.event.base;

import ru.mahach.eruditionserver.sse.type.EventType;

public interface EruditionEvent {
    EventType getEventType();
}
