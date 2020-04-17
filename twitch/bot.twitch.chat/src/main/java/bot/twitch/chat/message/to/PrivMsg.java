package bot.twitch.chat.message.to;

import bot.twitch.chat.Channel;
import bot.twitch.chat.message.IRCCommand;
import lombok.NonNull;

/**
 * @author perococco
 **/
public class PrivMsg extends CommandToTwitch {

    @NonNull
    private final Channel channel;

    @NonNull
    private final String message;

    public PrivMsg(
            @NonNull Channel channel,
            @NonNull String message) {
        super(IRCCommand.PRIVMSG);
        this.channel = channel;
        this.message = message;
    }

    @Override
    public @NonNull String payload() {
        return "PRIVMSG #"+channel.name()+" :"+message;
    }
}
