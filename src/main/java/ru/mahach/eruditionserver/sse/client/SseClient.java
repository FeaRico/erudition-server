package ru.mahach.eruditionserver.sse.client;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public class SseClient {
    private String clientId;
    private SseEmitter emitter;

    public SseClient(String clientId, SseEmitter emitter) {
        this.clientId = clientId;
        this.emitter = emitter;
    }

    public String getClientId() {
        return clientId;
    }

    public SseEmitter getEmitter() {
        return emitter;
    }
}
