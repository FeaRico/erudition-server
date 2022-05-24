package ru.mahach.eruditionserver.sse.registry;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import ru.mahach.eruditionserver.sse.client.SseClient;

import java.util.Map;

public interface SseClientsRegistry {
    Map<String, SseClient> getClients();

    SseEmitter register(String clientId, Long time);

    void unregister(String clientId);
}
