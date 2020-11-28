package perobobbot.twitch.chat.event;

import lombok.NonNull;

public abstract class TwitchChatEventAdapter<T> implements TwitchChatEventVisitor<T> {

    protected abstract T fallback(@NonNull TwitchChatEvent event);

    @NonNull
    @Override
    public T visit(@NonNull ReceivedMessage<?> receivedMessage) {
        return fallback(receivedMessage);
    }

    @NonNull
    @Override
    public T visit(@NonNull PostedMessage postedMessage) {
        return fallback(postedMessage);
    }

    @NonNull
    @Override
    public T visit(@NonNull Connection connection) {
        return fallback(connection);
    }

    @NonNull
    @Override
    public T visit(@NonNull Disconnection disconnection) {
        return fallback(disconnection);
    }

    @NonNull
    @Override
    public T visit(@NonNull Error error) {
        return fallback(error);
    }
}
