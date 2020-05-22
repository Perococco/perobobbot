package perobobbot.twitch.chat.message.to;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.chat.advanced.DispatchContext;
import perobobbot.twitch.chat.Capability;
import perobobbot.twitch.chat.TwitchChatState;
import perobobbot.twitch.chat.message.IRCCommand;
import perobobbot.twitch.chat.message.from.CapAck;

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
    public @NonNull String payload(@NonNull DispatchContext dispatchContext) {
        return "CAP REQ :" + capabilities.stream()
                                         .map(Capability::getIrcValue)
                                         .collect(Collectors.joining(" "));
    }

    @Override
    protected Optional<CapAck> doIsMyAnswer(@NonNull CapAck twitchAnswer, @NonNull TwitchChatState state) {
        return Optional.of(twitchAnswer);
    }
}
