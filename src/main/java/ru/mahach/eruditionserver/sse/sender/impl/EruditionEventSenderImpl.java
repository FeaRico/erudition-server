package ru.mahach.eruditionserver.sse.sender.impl;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import ru.mahach.eruditionserver.sse.event.base.EruditionEvent;
import ru.mahach.eruditionserver.sse.registry.SseClientsRegistry;
import ru.mahach.eruditionserver.sse.sender.EruditionEventSender;

import java.io.IOException;

@Component
public class EruditionEventSenderImpl implements EruditionEventSender {
    private final SseClientsRegistry clientsRegistry;

    public EruditionEventSenderImpl(SseClientsRegistry clientsRegistry) {
        this.clientsRegistry = clientsRegistry;
    }

    @Override
    public void send(EruditionEvent event) {
        clientsRegistry.getClients().values().forEach(client -> {
            SseEmitter emitter = client.getEmitter();
            try {
                emitter.send(SseEmitter.event()
                        .name(event.getEventType().getName())
                        .data(event, MediaType.APPLICATION_JSON));
            } catch (IOException e) {
                emitter.completeWithError(e);
            }
        });
    }
}
