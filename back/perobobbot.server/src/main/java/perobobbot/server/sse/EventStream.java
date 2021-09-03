package perobobbot.server.sse;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
public class EventStream {

    private final Hub hub;

    @GetMapping("/events/ssee")
    public SseEmitter handleSse(@RequestHeader(name = "Last-Event-ID", required = false) String lastEventId) {
        final SseEmitter emitter;
        if (lastEventId == null) {
            emitter = hub.createEmitterForNewConnection();
        } else {
            emitter = hub.createEmitterForReconnection(lastEventId);
        }
        return emitter;
    }

}

