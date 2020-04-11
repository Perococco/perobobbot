package bot.twitch.chat.message;

import bot.chat.advanced.Message;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @author perococco
 **/
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public abstract class TwitchMessage implements Message {

    @NonNull
    public abstract IRCCommand command();

}
