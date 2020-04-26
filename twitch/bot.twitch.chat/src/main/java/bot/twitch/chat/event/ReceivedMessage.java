package bot.twitch.chat.event;

import bot.twitch.chat.TwitchChatState;
import bot.twitch.chat.message.from.MessageFromTwitch;
import bot.twitch.chat.message.from.PingFromTwitch;
import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.Optional;

@RequiredArgsConstructor
public class ReceivedMessage<M extends MessageFromTwitch> implements TwitchChatEvent {

    @NonNull
    @Getter
    private final TwitchChatState state;

    @NonNull
    @Getter
    private final Instant receptionTime;

    @NonNull
    @Getter
    private final M message;

    @Override
    public String toString() {
        return "ReceivedMessages{" +
               "message=" + message +
               '}';
    }

    @NonNull
    public <U extends MessageFromTwitch> Optional<ReceivedMessage<U>> castMessageTo(@NonNull Class<U> messageType) {
        if (messageType.isInstance(message)) {
            return Optional.of(new ReceivedMessage<>(state,receptionTime,messageType.cast(messageType)));
        }
        return Optional.empty();
    }

    @Override
    public <T> @NonNull T accept(@NonNull TwitchChatEventVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean isPing() {
        return message instanceof PingFromTwitch;
    }
}
