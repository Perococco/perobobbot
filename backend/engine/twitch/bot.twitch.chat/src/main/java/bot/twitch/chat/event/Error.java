package bot.twitch.chat.event;

import bot.twitch.chat.TwitchChatState;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

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
