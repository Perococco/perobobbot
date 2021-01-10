package perobobbot.access;

import lombok.NonNull;

import java.time.Instant;

/**
 * Launch a runnable if some rule are satisfied
 */
public interface Launcher {

    /**
     * @param action the action to launch
     * @param accessRule the access rules that must be satisfied to launch the action
     * @param executionTime the time of the execution (this is generally {@link Instant#now} except for tests)
     * @return true if the action has been launched
     */
    boolean launch(@NonNull Runnable action,
                   @NonNull AccessRule accessRule,
                   @NonNull Instant executionTime);
}
