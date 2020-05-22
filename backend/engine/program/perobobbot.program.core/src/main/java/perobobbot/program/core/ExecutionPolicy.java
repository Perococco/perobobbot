package perobobbot.program.core;

import com.google.common.collect.ImmutableMap;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import perobobbot.common.lang.Role;

import java.time.Duration;
import java.util.Optional;

@Value
@Builder
public class ExecutionPolicy {

    public static final ExecutionPolicy NONE = new ExecutionPolicy(Role.ANY_USER, Duration.ZERO, ImmutableMap.of());

    @NonNull
    private Role requiredRole;

    @NonNull
    private Duration globalCoolDown;

    @NonNull
    @Singular
    private ImmutableMap<Role,Duration> coolDowns;

    public static ExecutionPolicyBuilder builder() {
        return new ExecutionPolicyBuilder()
                .requiredRole(Role.ANY_USER)
                .globalCoolDown(Duration.ZERO)
                ;
    }

    @NonNull
    public Optional<Duration> maxUserCoolDown() {
        return coolDowns.values().stream().max(Duration::compareTo);
    }
}

