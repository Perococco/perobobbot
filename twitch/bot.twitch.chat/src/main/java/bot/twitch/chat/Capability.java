package bot.twitch.chat;

import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

/**
 * @author perococco
 **/
@RequiredArgsConstructor
public enum Capability {
    COMMANDS("twitch.tv/commands"),
    TAGS("twitch.tv/tags"),
    MEMBERSHIP("twitch.tv/membership"),
    ;

    @NonNull
    @Getter
    private final String ircValue;

    public static Optional<Capability> find(@NonNull String ircValue) {
        for (Capability value : Holder.VALUES) {
            if (value.ircValue.equals(ircValue)) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }

    @NonNull
    public static ImmutableSet<Capability> AllCapabilities() {
        return Holder.VALUES;
    }

    private static class Holder {

        private static final ImmutableSet<Capability> VALUES = ImmutableSet.copyOf(values());
    }
}
