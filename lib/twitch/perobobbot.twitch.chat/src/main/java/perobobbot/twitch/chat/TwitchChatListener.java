package perobobbot.twitch.chat;

import lombok.NonNull;
import perobobbot.lang.MessageListener;
import perobobbot.twitch.chat.event.TwitchChatEvent;
import perococco.perobobbot.twitch.chat.PrivMsgFromTwitchListenerWrapper;

/**
 * @author perococco
 **/
public interface TwitchChatListener {

    void onTwitchChatEvent(@NonNull TwitchChatEvent event);

    static @NonNull TwitchChatListener wrap(@NonNull MessageListener messageListener) {
        return new PrivMsgFromTwitchListenerWrapper(m -> m.toMessage().ifPresent(messageListener::onMessage));
    }

}
