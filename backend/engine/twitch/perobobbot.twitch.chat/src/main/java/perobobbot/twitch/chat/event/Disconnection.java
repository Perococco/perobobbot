package perobobbot.twitch.chat.event;

import perobobbot.twitch.chat.TwitchChatState;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Disconnection implements TwitchChatEvent {

    @NonNull
    @Getter
    private final TwitchChatState state;

    @Override
    public <T> @NonNull T accept(@NonNull TwitchChatEventVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
