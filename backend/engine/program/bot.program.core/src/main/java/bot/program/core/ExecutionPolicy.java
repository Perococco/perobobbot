package bot.program.core;

import bot.common.lang.UserRole;
import lombok.NonNull;
import lombok.Value;

import java.time.Duration;

@Value
public class ExecutionPolicy {

    public static final ExecutionPolicy NONE = new ExecutionPolicy(UserRole.ANY_USER,Duration.ZERO,Duration.ZERO);

    @NonNull
    private final UserRole requiredRole;

    @NonNull
    private final Duration globalCoolDown;

    @NonNull
    private final Duration userCoolDown;

}

