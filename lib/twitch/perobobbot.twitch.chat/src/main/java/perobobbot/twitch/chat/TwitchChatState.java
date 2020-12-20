package perobobbot.twitch.chat;

import lombok.NonNull;
import perobobbot.lang.Bot;
import perobobbot.lang.Platform;

/**
 * @author perococco
 **/
public interface TwitchChatState {

    boolean isConnected();

    /**
     * The bot used for the connection
     */
    @NonNull
    Bot getBot();

    default @NonNull String getNickOfConnectedUser() {
        return getBot().getCredentialsNick(Platform.TWITCH);
    }

}
