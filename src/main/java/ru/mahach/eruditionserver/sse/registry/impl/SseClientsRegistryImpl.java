package ru.mahach.eruditionserver.sse.registry.impl;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import ru.mahach.eruditionserver.sse.client.SseClient;
import ru.mahach.eruditionserver.sse.registry.SseClientsRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SseClientsRegistryImpl implements SseClientsRegistry {
    private final Map<String, SseClient> clients;

    public SseClientsRegistryImpl() {
        this.clients = new ConcurrentHashMap<>();
    }

    @Override
    public Map<String, SseClient> getClients() {
        return clients;
    }

    @Override
    public SseEmitter register(String clientId, Long time) {
        SseEmitter emitter = new SseEmitter(time);
        register(clientId, emitter);
        return emitter;
    }

    private void register(String clientId, SseEmitter sseEmitter) {
        SseClient client = new SseClient(clientId, sseEmitter);
        clients.put(clientId, client);
    }

    @Override
    public void unregister(String clientId) {
        SseClient client = clients.remove(clientId);
        if(client != null)
            client.getEmitter().complete();
    }
}
