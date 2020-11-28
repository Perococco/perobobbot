package perobobbot.twitch.chat;

import lombok.NonNull;

/**
 * @author perococco
 **/
public interface TwitchChatState {

    boolean connected();

    /**
     * empty if not defined
     */
    @NonNull
    String userName();

}
