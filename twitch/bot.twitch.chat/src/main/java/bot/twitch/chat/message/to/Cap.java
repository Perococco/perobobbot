package bot.twitch.chat.message.to;

import bot.twitch.chat.Capability;
import bot.twitch.chat.TwitchChatState;
import bot.twitch.chat.message.IRCCommand;
import bot.twitch.chat.message.from.CapAck;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author perococco
 **/
public final class Cap extends SimpleRequestToTwitch<CapAck> {

    @NonNull
    private final ImmutableSet<Capability> capabilities;

    public Cap(@NonNull Capability... capabilities) {
        this(ImmutableSet.copyOf(capabilities));
    }

    public Cap(@NonNull ImmutableSet<Capability> capabilities) {
        super(IRCCommand.CAP, CapAck.class);
        this.capabilities = capabilities;
    }

    @Override
    public @NonNull String payload() {
        return "CAP REQ :" + capabilities.stream()
                                         .map(Capability::ircValue)
                                         .collect(Collectors.joining(" "));
    }

    @Override
    protected Optional<CapAck> doIsMyAnswer(@NonNull CapAck twitchAnswer, @NonNull TwitchChatState state) {
        return Optional.of(twitchAnswer);
    }
}
