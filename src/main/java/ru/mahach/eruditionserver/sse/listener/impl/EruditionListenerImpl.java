package ru.mahach.eruditionserver.sse.listener.impl;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.mahach.eruditionserver.sse.event.base.EruditionEvent;
import ru.mahach.eruditionserver.sse.listener.EruditionListener;
import ru.mahach.eruditionserver.sse.sender.EruditionEventSender;

@Component
public class EruditionListenerImpl implements EruditionListener {
    private final EruditionEventSender eventSender;

    public EruditionListenerImpl(EruditionEventSender eventSender) {
        this.eventSender = eventSender;
    }

    @Override
    @EventListener(EruditionEvent.class)
    public void handleEvent(EruditionEvent event) {
        eventSender.send(event);
    }
}
