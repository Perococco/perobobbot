package perobobbot.server.sse.transformer;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import perobobbot.lang.ApplicationEvent;
import perobobbot.server.sse.SSEventAccess;

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
