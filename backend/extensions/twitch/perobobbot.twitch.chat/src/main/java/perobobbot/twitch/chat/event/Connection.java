package perobobbot.twitch.chat.event;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.twitch.chat.TwitchChatState;

@RequiredArgsConstructor
public class Connection implements TwitchChatEvent {

    @NonNull
    @Getter
    private final TwitchChatState state;

    @Override
    public <T> @NonNull T accept(@NonNull TwitchChatEventVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
