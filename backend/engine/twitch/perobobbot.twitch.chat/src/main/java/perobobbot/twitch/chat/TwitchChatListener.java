package perobobbot.twitch.chat;

import lombok.NonNull;
import perobobbot.twitch.chat.event.TwitchChatEvent;

/**
 * @author perococco
 **/
public interface TwitchChatListener {

    void onTwitchChatEvent(@NonNull TwitchChatEvent event);

}
