package perobobbot.server.sse;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import perobobbot.data.service.EventService;
import perobobbot.data.service.UserService;
import perobobbot.security.com.BotUser;

@RestController
@RequiredArgsConstructor
public class EventStream {

    private final Hub hub;

    private final @EventService  @NonNull UserService userService;

    @GetMapping("/events/sse")
    public ResponseBodyEmitter handleSse(@AuthenticationPrincipal BotUser principal,
                                         @RequestHeader(name = "Last-Event-ID", required = false) String lastEventId) {
        final var user = userService.getUser(principal.getUsername());
        System.out.println("User : "+user.getLogin()+ " "+user.getUsername());
        final SseEmitter emitter;
        if (lastEventId == null) {
            emitter = hub.createEmitterForNewConnection(user);
        } else {
            emitter = hub.createEmitterForReconnection(user,lastEventId);
        }
        return emitter;
    }

}

