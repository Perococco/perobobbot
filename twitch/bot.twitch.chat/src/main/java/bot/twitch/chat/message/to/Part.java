package bot.twitch.chat.message.to;

import bot.chat.advanced.DispatchContext;
import bot.twitch.chat.Channel;
import bot.twitch.chat.TwitchChatState;
import bot.twitch.chat.message.IRCCommand;
import lombok.Getter;
import lombok.NonNull;

import java.util.Optional;

/**
 * @author perococco
 **/
@Getter
public class Part extends SimpleRequestToTwitch<bot.twitch.chat.message.from.Part> {

    @NonNull
    @Getter
    private final Channel channel;

    public Part(@NonNull Channel channel) {
        super(IRCCommand.PART, bot.twitch.chat.message.from.Part.class);
        this.channel = channel;
    }

    @Override
    public @NonNull String payload(@NonNull DispatchContext dispatchContext) {
        return "PART #"+channel.name();
    }

    @Override
    @NonNull
    protected Optional<bot.twitch.chat.message.from.Part> doIsMyAnswer(@NonNull bot.twitch.chat.message.from.Part twitchAnswer,
            @NonNull TwitchChatState state) {
        if (twitchAnswer.channel().equals(channel) && twitchAnswer.user().equals(state.userName())) {
            return Optional.of(twitchAnswer);
        }
        return Optional.empty();
    }
}
