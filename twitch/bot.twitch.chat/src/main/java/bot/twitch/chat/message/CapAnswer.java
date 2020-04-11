package bot.twitch.chat.message;

import bot.twitch.chat.Capabilities;
import com.google.common.collect.ImmutableSet;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

/**
 * @author perococco
 **/
@EqualsAndHashCode(callSuper = true)
public class CapAnswer extends TwitchAnswer {

    @Getter
    private final ImmutableSet<Capabilities> acknowledgeCapabilities;

    public CapAnswer(@NonNull String payload, @NonNull ImmutableSet<Capabilities> acknowledgeCapabilities) {
        super(IRCCommand.CAP, payload);
        this.acknowledgeCapabilities = acknowledgeCapabilities;
    }
}
