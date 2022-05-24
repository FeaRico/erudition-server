package ru.mahach.eruditionserver.sse.sender;

import ru.mahach.eruditionserver.sse.event.base.EruditionEvent;

@FunctionalInterface
public interface EruditionEventSender {
    void send(EruditionEvent event);
}
