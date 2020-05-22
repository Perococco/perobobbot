package perobobbot.twitch.chat;

import lombok.NonNull;
import lombok.Value;
import perobobbot.common.lang.Secret;

/**
 * @author perococco
 **/
@Value
public class TwitchChatOAuth {

    public static TwitchChatOAuth create(@NonNull String nick, @NonNull String secret) {
        return new TwitchChatOAuth(nick, new Secret(secret));
    }

    @NonNull
    private final String nick;

    @NonNull
    private final Secret secret;

}
