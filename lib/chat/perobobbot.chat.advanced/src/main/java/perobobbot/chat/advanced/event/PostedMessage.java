package perobobbot.chat.advanced.event;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.chat.advanced.AdvancedDispatchContext;
import perobobbot.chat.advanced.Message;

import java.time.Instant;

/**
 * Event sent when a message is posted to the chat
 * @param <M>
 */
@RequiredArgsConstructor
@Getter
public class PostedMessage<M> implements AdvancedChatEvent<M> {

    @NonNull
    private final Instant dispatchingTime;

    @NonNull
    @Getter
    private final Message postedMessage;

    @NonNull
    @Override
    public <T> T accept(@NonNull AdvancedChatEventVisitor<M,T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        final String payload = postedMessage.payload(AdvancedDispatchContext.NIL);
        final int idx = payload.indexOf("PASS");
        final String value;
        if (idx >= 0) {
            value = payload.substring(0,idx+4)+" ******";
        } else {
            value = payload;
        }
        return "PostedMessage{" +value+"}";
    }

}
