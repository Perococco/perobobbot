package perobobbot.twitch.chat.event;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.twitch.chat.TwitchChatState;
import perobobbot.twitch.chat.message.from.MessageFromTwitch;
import perobobbot.twitch.chat.message.from.PingFromTwitch;

import java.time.Instant;

@RequiredArgsConstructor
@Getter
public class ReceivedMessage<M extends MessageFromTwitch> implements TwitchChatEvent {

    /**
     * The state of the Twitch chat when the message was received
     */
    @NonNull
    private final TwitchChatState state;

    /**
     * The time when the message was received
     */
    @NonNull
    private final Instant receptionTime;

    /**
     * The received message
     */
    @NonNull
    private final M message;

    @Override
    public String toString() {
        return "ReceivedMessages{" +
               "message=" + message +
               '}';
    }

    @Override
    public <T> @NonNull T accept(@NonNull TwitchChatEventVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean isPing() {
        return message instanceof PingFromTwitch;
    }

    @NonNull
    public String getRawMessage() {
        return message.getIrcParsing().getRawMessage();
    }
}
