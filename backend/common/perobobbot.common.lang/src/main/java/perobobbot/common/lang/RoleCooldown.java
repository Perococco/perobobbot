package perobobbot.common.lang;

import lombok.NonNull;
import lombok.Value;

import java.time.Duration;

@Value
public class RoleCooldown {

    @NonNull Role role;

    @NonNull Duration duration;
}
