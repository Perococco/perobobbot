package bot.twitch.chat.event;

import bot.twitch.chat.TwitchChatState;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

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
