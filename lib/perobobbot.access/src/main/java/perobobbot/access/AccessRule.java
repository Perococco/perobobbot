package perobobbot.access;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import perobobbot.lang.Role;
import perobobbot.lang.RoleCoolDown;
import perobobbot.lang.User;

import java.time.Duration;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Defines the rule to access an action
 */
@Value
public class AccessRule {

    /**
     * The role required for the execution
     */
    @NonNull
    Role requiredRole;

    /**
     * The default cool-down for the role without specific cool-down
     */
    @NonNull
    Duration defaultCoolDown;

    /**
     * the cool-down for each role
     */
    @NonNull
    @Singular
    ImmutableMap<Role,Duration> coolDowns;

    @NonNull
    public static AccessRule create(@NonNull Role requiredRole, @NonNull RoleCoolDown... roleCoolDowns) {
        return create(requiredRole,Duration.ZERO,roleCoolDowns);
    }

    @NonNull
    public static AccessRule create(@NonNull Role requiredRole, @NonNull Duration defaultCooldown, @NonNull RoleCoolDown... roleCoolDowns) {
        return new AccessRule(
                requiredRole,
                defaultCooldown,
                Arrays.stream(roleCoolDowns).collect(Collectors.collectingAndThen(Collectors.toMap(RoleCoolDown::getRole, RoleCoolDown::getDuration), ImmutableMap::copyOf))
        );
    }

    @NonNull
    public Optional<Duration> maxCoolDownForRole() {
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
        return findCoolDownFor(user).orElse(defaultCoolDown);
    }



}
