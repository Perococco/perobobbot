package perobobbot.lang;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.util.Comparator;

@RequiredArgsConstructor
public enum Role {
    THE_BOSS(4),
    ADMINISTRATOR(3),
    TRUSTED_USER(2),
    STANDARD_USER(1),
    ANY_USER(0),
    ;
    @Getter
    private final int level;

    @NonNull
    public static ImmutableList<Role> rolesFromHighestToLowest() {
        return Holder.ROLE_FROM_HIGHEST_TO_LOWEST;
    }

    public boolean isBetterThan(@NonNull Role other) {
        return other.level < this.level;
    }

    public boolean isNotBetterThan(@NonNull Role other) {
        return this.level <= other.level;
    }

    @NonNull
    public RoleCoolDown cooldown(@NonNull Duration duration) {
        return new RoleCoolDown(this,duration);
    }

    @NonNull
    public RoleCoolDown noCooldown() {
        return cooldown(Duration.ZERO);
    }



    @NonNull
    public static final Comparator<Role> HIGHER_LEVEL_ROLE_COMPARATOR = Comparator.comparingInt(Role::getLevel).reversed();

    private static final class Holder {


        private static final ImmutableSet<Role> ROLES = ImmutableSet.copyOf(values());
        private static final ImmutableList<Role> ROLE_FROM_HIGHEST_TO_LOWEST;

        static {
            ROLE_FROM_HIGHEST_TO_LOWEST = ROLES.stream()
                                               .sorted(HIGHER_LEVEL_ROLE_COMPARATOR)
                                               .collect(ImmutableList.toImmutableList());
        }

    }
}
