package perobobbot.server.sse.transformer;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import perobobbot.lang.ApplicationEvent;
import perobobbot.lang.IdentityInfo;
import perobobbot.lang.MessageContext;
import perobobbot.lang.Todo;
import perobobbot.security.com.BotUser;
import perobobbot.server.sse.SSEvent;
import perobobbot.server.sse.SSEventAccess;

import java.util.Optional;

@Component
public class ApplicationEventTransformer extends BaseEventTransformer<ApplicationEvent> {


    public ApplicationEventTransformer(@NonNull DefaultPayloadConstructor payloadConstructor) {
        super(payloadConstructor, ApplicationEvent.class);
    }

    @Override
    protected @NonNull String getEventName() {
        return "application-event";
    }

    @Override
    protected @NonNull SSEventAccess getAuthorizedLogins(@NonNull ApplicationEvent event) {
        return SSEventAccess.PERMIT_ALL;
    }
}
