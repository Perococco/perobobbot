package perobobbot.twitch.chat.event;

import lombok.NonNull;
import perobobbot.twitch.chat.TwitchChatState;

public interface TwitchChatEvent {

    /**
     * @return the state of the chat after this event has been process (for instance, if this event add
     * a moderator to a channel, that will be reflected in the returned state).
     */
    @NonNull
    TwitchChatState getState();

    @NonNull
    <T> T accept(@NonNull TwitchChatEventVisitor<T> visitor);

    default boolean isPing() {
        return false;
    }
}
