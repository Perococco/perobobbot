package perobobbot.twitch.chat;

import lombok.NonNull;
import perobobbot.lang.Bot;
import perobobbot.lang.ConnectionInfo;
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
    ConnectionInfo getConnectionInfo();

    default @NonNull String getNickOfConnectedUser() {
        return getConnectionInfo().getCredential().getNick();
    }

}
