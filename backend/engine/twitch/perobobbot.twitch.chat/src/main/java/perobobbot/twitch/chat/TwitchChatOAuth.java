package perobobbot.twitch.chat;

import perobobbot.common.lang.Secret;
import lombok.NonNull;
import lombok.Value;

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
