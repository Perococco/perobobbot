package perobobbot.twitch.chat;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.Secret;

/**
 * @author perococco
 **/
@Value
public class TwitchChatOAuth {

    public static TwitchChatOAuth create(@NonNull String nick, @NonNull String secret) {
        return new TwitchChatOAuth(nick, new Secret(secret));
    }

    @NonNull String nick;

    @NonNull Secret secret;

}
