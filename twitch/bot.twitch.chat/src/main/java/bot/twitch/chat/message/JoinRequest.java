package bot.twitch.chat.message;

import bot.twitch.chat.Channel;
import lombok.NonNull;

/**
 * @author perococco
 **/
public class JoinRequest extends TwitchRequest<JoinAnswer> {

    @NonNull
    private final Channel channel;

    public JoinRequest(@NonNull Channel channel) {
        super(IRCCommand.JOIN,JoinAnswer.class);
        this.channel = channel;
    }

    @Override
    public @NonNull String payload() {
        return "JOIN #"+channel.name();
    }
}
