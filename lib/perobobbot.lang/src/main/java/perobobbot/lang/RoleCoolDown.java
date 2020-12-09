package perobobbot.lang;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.Duration;

@Value
public class RoleCoolDown {

    @NonNull Role role;

    @NonNull Duration duration;

    public static @NonNull RoleCooldownBuilder applyTo(@NonNull Role role) {
        return new RoleCooldownBuilder(role);
    }


    @RequiredArgsConstructor
    public static class RoleCooldownBuilder {
        private final @NonNull Role role;

        public @NonNull RoleCoolDown aCDof(@NonNull Duration duration) {
            return new RoleCoolDown(role,duration);
        }

        public @NonNull RoleCoolDown aCDof(long coolDownInSeconds) {
            return aCDof(Duration.ofSeconds(coolDownInSeconds));
        }
    }
}
