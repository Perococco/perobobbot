package perobobbot.chat.advanced.event;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

/**
 * Event sent when a message is received from the chat
 * @param <M>
 */
@RequiredArgsConstructor
public class ReceivedMessage<M> implements AdvancedChatEvent<M> {

    @NonNull
    @Getter
    private final Instant receptionTime;

    @NonNull
    @Getter
    private final M message;

    @NonNull
    @Override
    public <T> T accept(@NonNull AdvancedChatEventVisitor<M,T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "ReceivedMessage{" + message + "}";
    }
}
