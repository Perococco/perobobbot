package bot.twitch.chat.message.to;

import bot.chat.advanced.DispatchContext;
import bot.common.lang.fp.Function1;
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
    private final Function1<? super DispatchContext, ? extends String> messageBuilder;

    public PrivMsg(
            @NonNull Channel channel,
            @NonNull Function1<? super DispatchContext, ? extends String> messageBuilder) {
        super(IRCCommand.PRIVMSG);
        this.channel = channel;
        this.messageBuilder = messageBuilder;
    }

    @Override
    public @NonNull String payload(@NonNull DispatchContext dispatchContext) {
        final String message = messageBuilder.apply(dispatchContext);
        PrivMsgValidator.validate(message);
        return "PRIVMSG #"+channel.name()+" :"+message;
    }
}
