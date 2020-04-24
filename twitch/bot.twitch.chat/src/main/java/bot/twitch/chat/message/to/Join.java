package bot.twitch.chat.message.to;

import bot.twitch.chat.Channel;
import bot.twitch.chat.TwitchChatState;
import bot.twitch.chat.message.IRCCommand;
import bot.twitch.chat.message.from.UserState;
import lombok.NonNull;

import java.util.Optional;

/**
 * @author perococco
 **/
public class Join extends SimpleRequestToTwitch<UserState> {

    @NonNull
    private final Channel channel;

    public Join(@NonNull Channel channel) {
        super(IRCCommand.JOIN, UserState.class);
        this.channel = channel;
    }

    @Override
    @NonNull
    public String payload() {
        return "JOIN #"+channel.name();
    }

    @Override
    @NonNull
    protected Optional<UserState> doIsMyAnswer(@NonNull UserState twitchAnswer, @NonNull TwitchChatState state) {
         if (twitchAnswer.channel().equals(channel)) {
             return Optional.of(twitchAnswer);
         }
         return Optional.empty();
    }
}
