package perobobbot.twitch.chat.event;

import lombok.NonNull;

import java.util.Optional;

public class ReceivedMessageExtractor extends TwitchChatEventAdapter<Optional<ReceivedMessage<?>>> {

    public static ReceivedMessageExtractor create() {
        return new ReceivedMessageExtractor();
    }

    private ReceivedMessageExtractor() {
    }

    @NonNull
    @Override
    public Optional<ReceivedMessage<?>> visit(@NonNull ReceivedMessage<?> receivedMessage) {
        return Optional.of(receivedMessage);
    }

    @Override
    protected Optional<ReceivedMessage<?>> fallback(@NonNull TwitchChatEvent event) {
        return Optional.empty();
    }
}
