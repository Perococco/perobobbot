package perobobbot.common.lang;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.Duration;

@Value
public class RoleCooldown {

    @NonNull Role role;

    @NonNull Duration duration;

    public static @NonNull RoleCooldownBuilder applyTo(@NonNull Role role) {
        return new RoleCooldownBuilder(role);
    }


    @RequiredArgsConstructor
    public static class RoleCooldownBuilder {
        private final @NonNull Role role;

        public @NonNull RoleCooldown aCDof(@NonNull Duration duration) {
            return new RoleCooldown(role,duration);
        }

        public @NonNull RoleCooldown aCDof(long coolDownInSeconds) {
            return aCDof(Duration.ofSeconds(coolDownInSeconds));
        }
    }
}
