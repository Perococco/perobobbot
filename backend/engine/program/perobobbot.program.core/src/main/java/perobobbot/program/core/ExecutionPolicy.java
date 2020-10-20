package perobobbot.program.core;

import com.google.common.collect.ImmutableMap;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import perobobbot.common.lang.Role;
import perobobbot.common.lang.User;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;

@Value
@Builder
public class ExecutionPolicy {

    public static final ExecutionPolicy ANY_WITH_10s_GLOBAL_COOLDOWN = new ExecutionPolicy(Role.ANY_USER, Duration.ofSeconds(10), ImmutableMap.of());
    public static final ExecutionPolicy ADMINISTRATOR_NO_COOLDOWN = new ExecutionPolicy(Role.ADMINISTRATOR, Duration.ofSeconds(0), ImmutableMap.of());

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

    public static ExecutionPolicy withGlobalCooldown(@NonNull Duration duration) {
        return ExecutionPolicy.builder().globalCoolDown(duration).build();
    }

    @NonNull
    public Optional<Duration> maxCooldownForRole() {
        return coolDowns.values().stream().max(Duration::compareTo);
    }

    @NonNull
    public Optional<Duration> findCoolDownFor(@NonNull User executor) {
        return Role.rolesFromHighestToLowest()
                   .stream()
                   .filter(executor::canActAs)
                   .map(coolDowns::get)
                   .filter(Objects::nonNull)
                   .findFirst();
    }

    /**
     * @param user the user to retrieve the cooldown from
     * @return the cooldown for the provided user according to his role or the global cooldown if none is sets
     */
    @NonNull
    public Duration findForUserOrGlobalCoolDown(@NonNull User user) {
        return findCoolDownFor(user).orElse(globalCoolDown);
    }


}

