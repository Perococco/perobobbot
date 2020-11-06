package perobobbot.twitch.chat.event;

import lombok.NonNull;

public interface TwitchChatEventVisitor<T> {

    @NonNull
    T visit(@NonNull ReceivedMessage<?> receivedMessage);

    @NonNull
    T visit(@NonNull PostedMessage postedMessage);

    @NonNull
    T visit(@NonNull Connection connection);

    @NonNull
    T visit(@NonNull Disconnection disconnection);

    @NonNull
    T visit(@NonNull Error error);
}
