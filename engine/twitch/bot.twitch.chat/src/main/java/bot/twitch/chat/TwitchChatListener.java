package bot.twitch.chat;

import bot.twitch.chat.event.TwitchChatEvent;
import lombok.NonNull;

/**
 * @author perococco
 **/
public interface TwitchChatListener {

    void onTwitchChatEvent(@NonNull TwitchChatEvent event);

}
