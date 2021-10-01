package perobobbot.server.sse;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import perobobbot.security.com.BotUser;

@RestController
@RequiredArgsConstructor
public class EventStream {

    private final Hub hub;

    @GetMapping("/events/sse")
    public ResponseBodyEmitter handleSse(@AuthenticationPrincipal BotUser principal,
                                         @RequestHeader(name = "Last-Event-ID", required = false) String lastEventId) {
        final SseEmitter emitter;
        if (lastEventId == null) {
            emitter = hub.createEmitterForNewConnection(principal);
        } else {
            emitter = hub.createEmitterForReconnection(principal,lastEventId);
        }
        return emitter;
    }

}

