package perobobbot.twitch.chat;

import lombok.NonNull;

/**
 * @author perococco
 **/
public interface TwitchChatState {

    boolean isConnected();

    /**
     * empty if not defined
     */
    @NonNull
    String getUserId();

}
