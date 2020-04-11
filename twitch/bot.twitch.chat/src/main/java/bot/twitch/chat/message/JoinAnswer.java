package bot.twitch.chat.message;

import bot.twitch.chat.Channel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

/**
 * @author perococco
 **/
@Getter
@EqualsAndHashCode(callSuper = true)
public class JoinAnswer extends TwitchAnswer {

    @NonNull
    private final String user;

    @NonNull
    private final Channel channel;

    public JoinAnswer(@NonNull String rawMessage, @NonNull String user, @NonNull Channel channel) {
        super(IRCCommand.JOIN,rawMessage);
        this.user = user;
        this.channel = channel;
    }

}
