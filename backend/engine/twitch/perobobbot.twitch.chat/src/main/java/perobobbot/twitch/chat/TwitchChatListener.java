package perobobbot.twitch.chat;

import perobobbot.twitch.chat.event.TwitchChatEvent;
import lombok.NonNull;

/**
 * @author perococco
 **/
public interface TwitchChatListener {

    void onTwitchChatEvent(@NonNull TwitchChatEvent event);

}
