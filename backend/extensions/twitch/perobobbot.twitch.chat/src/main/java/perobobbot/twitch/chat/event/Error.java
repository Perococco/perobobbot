package perobobbot.twitch.chat.event;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.twitch.chat.TwitchChatState;

@RequiredArgsConstructor
public class Error implements TwitchChatEvent {

    @NonNull
    @Getter
    private final TwitchChatState state;

    @NonNull
    @Getter
    private final Throwable error;

    @Override
    public String toString() {
        return "Error{" +
                "error=" + error.getMessage() +
                '}';
    }

    @Override
    public <T> @NonNull T accept(@NonNull TwitchChatEventVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
