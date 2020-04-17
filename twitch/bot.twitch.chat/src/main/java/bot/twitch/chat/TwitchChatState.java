package bot.twitch.chat;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * @author perococco
 **/
public interface TwitchChatState {

    boolean connected();

    /**
     * empty if not defined
     */
    @NonNull
    String userNickName();

}
