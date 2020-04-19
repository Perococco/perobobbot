package bot.chat.advanced.event;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@RequiredArgsConstructor
public class ReceivedMessages<M> implements AdvancedChatEvent<M> {

    @NonNull
    @Getter
    private final Instant receptionTime;

    @NonNull
    @Getter
    private final ImmutableList<M> messages;

    @Override
    public void accept(@NonNull AdvancedChatEventVisitor<M> visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "ReceivedMessage{" + messages + "}";
    }
}
