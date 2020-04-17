package bot.twitch.chat;

import bot.common.lang.Secret;
import lombok.NonNull;
import lombok.Value;

/**
 * @author perococco
 **/
@Value
public class TwitchChatOAuth {

    @NonNull
    private final String nick;

    @NonNull
    private final Secret secret;

}
