package perobobbot.twitch.chat;

import lombok.NonNull;
import perobobbot.lang.ChatConnectionInfo;

/**
 * @author perococco
 **/
public interface TwitchChatState {

    boolean isConnected();

    /**
     * The bot used for the connection
     */
    @NonNull
    ChatConnectionInfo getChatConnectionInfo();

    default @NonNull String getNickOfConnectedUser() {
        return getChatConnectionInfo().getNick();
    }

}
