package bot.twitch.chat.message.to;

import bot.twitch.chat.Channel;
import bot.twitch.chat.TwitchChatState;
import bot.twitch.chat.message.IRCCommand;
import lombok.NonNull;

import java.util.Optional;

/**
 * @author perococco
 **/
public class Join extends SimpleRequestToTwitch<bot.twitch.chat.message.from.Join> {

    @NonNull
    private final Channel channel;

    public Join(@NonNull Channel channel) {
        super(IRCCommand.JOIN, bot.twitch.chat.message.from.Join.class);
        this.channel = channel;
    }

    @Override
    public @NonNull String payload() {
        return "JOIN #"+channel.name();
    }

    @Override
    protected Optional<bot.twitch.chat.message.from.Join> doIsMyAnswer(@NonNull bot.twitch.chat.message.from.Join twitchAnswer,
            @NonNull TwitchChatState state) {
         if (twitchAnswer.channel().equals(channel) && twitchAnswer.user().equals(state.userNickName())) {
             return Optional.of(twitchAnswer);
         }
         return Optional.empty();
    }
}
