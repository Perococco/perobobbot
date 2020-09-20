package perobobbot.program.core;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
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

    public static final Duration DEFAULT_COOLDOWN = Duration.ofMinutes(1);


    /**
     * The role required for the execution
     */
    @NonNull Role requiredRole;

    /**
     * The global cooldown of the execution
     */
    @NonNull Duration globalCoolDown;

    /**
     * the cooldown for each role
     */
    @NonNull
    @Singular
    ImmutableMap<Role,Duration> coolDowns;

    public static ExecutionPolicyBuilder builder() {
        return new ExecutionPolicyBuilder()
                .requiredRole(Role.ANY_USER)
                .globalCoolDown(Duration.ZERO)
                ;
    }

    @NonNull
    public Optional<Duration> findCoolDown(@NonNull Role role) {
        return Optional.ofNullable(coolDowns.get(role));
    }

    @NonNull
    public Optional<Duration> maxPerRoleCooldown() {
        return coolDowns.values().stream().max(Duration::compareTo);
    }
}

