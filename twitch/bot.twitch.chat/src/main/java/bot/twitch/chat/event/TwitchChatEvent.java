package bot.twitch.chat.event;

import bot.twitch.chat.TwitchChatState;
import lombok.NonNull;

public interface TwitchChatEvent {

    /**
     * @return the state of the chat after this event has been process (for instance, if this event add
     * a moderator to a channel, that will be reflected in the returned state).
     */
    @NonNull
    TwitchChatState state();

    @NonNull
    <T> T accept(@NonNull TwitchChatEventVisitor<T> visitor);

    default boolean isPing() {
        return false;
    }
}
