package bot.twitch.chat.message.to;

import bot.common.lang.fp.TryResult;
import bot.twitch.chat.TwitchChatState;
import bot.twitch.chat.message.IRCCommand;
import bot.twitch.chat.message.from.MessageFromTwitch;
import bot.twitch.chat.message.from.PingFromTwitch;
import bot.twitch.chat.message.from.PongFromTwitch;
import lombok.NonNull;

import java.util.Optional;

public class Ping extends SimpleRequestToTwitch<PongFromTwitch> {

    public Ping() {
        super(IRCCommand.PING, PongFromTwitch.class);
    }

    @Override
    public String commandInPayload() {
        return "PING";
    }

    @Override
    public @NonNull String payload() {
        return "PING :tmi.twitch.tv";
    }

    @Override
    protected @NonNull Optional<PongFromTwitch> doIsMyAnswer(@NonNull PongFromTwitch twitchAnswer, @NonNull TwitchChatState state) {
        return Optional.of(twitchAnswer);
    }
}
