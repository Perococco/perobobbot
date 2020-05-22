package perobobbot.twitch.chat.message.to;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.chat.advanced.DispatchContext;
import perobobbot.twitch.chat.Channel;
import perobobbot.twitch.chat.TwitchChatState;
import perobobbot.twitch.chat.message.IRCCommand;

import java.util.Optional;

/**
 * @author perococco
 **/
@Getter
public class Part extends SimpleRequestToTwitch<perobobbot.twitch.chat.message.from.Part> {

    @NonNull
    @Getter
    private final Channel channel;

    public Part(@NonNull Channel channel) {
        super(IRCCommand.PART, perobobbot.twitch.chat.message.from.Part.class);
        this.channel = channel;
    }

    @Override
    public @NonNull String payload(@NonNull DispatchContext dispatchContext) {
        return "PART #"+channel.getName();
    }

    @Override
    @NonNull
    protected Optional<perobobbot.twitch.chat.message.from.Part> doIsMyAnswer(@NonNull perobobbot.twitch.chat.message.from.Part twitchAnswer,
                                                                              @NonNull TwitchChatState state) {
        if (twitchAnswer.getChannel().equals(channel) && twitchAnswer.getUser().equals(state.userName())) {
            return Optional.of(twitchAnswer);
        }
        return Optional.empty();
    }
}
