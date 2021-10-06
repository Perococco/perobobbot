package perobobbot.server.sse.transformer;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import perobobbot.lang.MessageContext;
import perobobbot.server.sse.SSEventAccess;

@Component
public class MessageTransformer extends BaseEventTransformer<MessageContext>  {

    public MessageTransformer(@NonNull DefaultPayloadConstructor payloadConstructor) {
        super(payloadConstructor, MessageContext.class);
    }

    @Override
    protected @NonNull String getEventName() {
        return "chat-message";
    }

    @Override
    protected @NonNull SSEventAccess getAuthorizedLogins(@NonNull MessageContext event) {
        //TODO find login that joined the channel from which the event comes from
        return SSEventAccess.PERMIT_ALL;
    }
}
