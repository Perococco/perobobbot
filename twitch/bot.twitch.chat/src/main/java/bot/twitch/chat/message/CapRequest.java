package bot.twitch.chat.message;

import bot.twitch.chat.Capabilities;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;

import java.util.stream.Collectors;

/**
 * @author perococco
 **/
public class CapRequest extends TwitchRequest<CapAnswer> {

    @NonNull
    private final ImmutableSet<Capabilities> capabilities;

    public CapRequest(@NonNull ImmutableSet<Capabilities> capabilities) {
        super(IRCCommand.CAP, CapAnswer.class);
        this.capabilities = capabilities;
    }

    @Override
    public @NonNull String payload() {
        return capabilities.stream()
                    .map(Capabilities::ircValue)
                    .collect(Collectors.joining(" ","CAP REQ :",""));
    }
}
