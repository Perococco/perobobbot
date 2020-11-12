package perobobbot.chat.core.event;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@RequiredArgsConstructor
public class ReceivedMessage implements ChatEvent {

    @NonNull
    @Getter
    private final Instant receptionTime;

    @NonNull
    @Getter
    private final String message;

    @Override
    public void accept(@NonNull ChatEventVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "ReceivedMessage{'" + message + "'}";
    }
}
