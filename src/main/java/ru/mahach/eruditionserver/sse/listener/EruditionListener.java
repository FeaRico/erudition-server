package ru.mahach.eruditionserver.sse.listener;

import org.springframework.stereotype.Component;
import ru.mahach.eruditionserver.sse.event.base.EruditionEvent;

@Component
public interface EruditionListener {
    void handleEvent(EruditionEvent event);
}
