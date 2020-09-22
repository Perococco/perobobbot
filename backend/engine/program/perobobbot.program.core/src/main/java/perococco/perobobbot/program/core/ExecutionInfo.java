package perococco.perobobbot.program.core;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.common.lang.Role;
import perobobbot.common.lang.User;
import perobobbot.program.core.ExecutionPolicy;

import java.security.Policy;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static perobobbot.program.core.ExecutionPolicy.DEFAULT_COOLDOWN;

/**
 * Information regarding an instruction.
 * It contains the policy of this instruction as well as information
 * needed to apply it (like the last time the instruction has been executed
 * and by whom...)
 */
@RequiredArgsConstructor
public class ExecutionInfo {

    @NonNull
    @Getter
    private final String instructionName;

    @NonNull
    private final ExecutionPolicy policy;

    /**
     * The instant of the last execution (whoever executed it)
     */
    private Instant lastExecutionTime = Instant.MIN;

    /**
     * The last execution per user
     */
    private final Map<String, Instant> lastExecutionTimePerUserId = new HashMap<>();

    /**
     * The maximal cooldown amongst all the user's cooldown, or ZERO if no user
     * has a cooldown
     */
    private final Duration maxUserCoolDown;

    public ExecutionInfo(@NonNull String instructionName, @NonNull ExecutionPolicy policy) {
        this.instructionName = instructionName;
        this.policy = policy;
        this.maxUserCoolDown = policy.maxPerRoleCooldown().orElse(Duration.ZERO);
    }

    /**
     * @param executor the use executing the instruction
     * @param now      the instant of the execution
     * @return true if all execution policy passed, false otherwise
     */
    public boolean canExecute(@NonNull User executor, @NonNull Instant now) {
        if (userPolicyFailed(executor)) {
            return false;
        }
        if (globalCoolDownPolicyFailed(now)) {
            return false;
        }
        if (userCoolDownPolicyFailed(executor, now)) {
            return false;
        }
        lastExecutionTime = now;
        setLastExecutionTime(executor.getUserId(), now);
        return true;
    }

    @NonNull
    private Optional<Duration> findCoolDownFor(@NonNull User executor) {
        return Role.rolesFromHighestToLowest()
                   .stream()
                   .filter(executor::canActAs)
                   .map(policy::findCoolDown)
                   .flatMap(Optional::stream)
                   .findFirst();
    }

    private boolean userPolicyFailed(@NonNull User executor) {
        return !executor.canActAs(policy.getRequiredRole());
    }

    private boolean globalCoolDownPolicyFailed(@NonNull Instant now) {
        final Duration durationSinceLastExecution = Duration.between(lastExecutionTime, now);
        return durationSinceLastExecution.compareTo(policy.getGlobalCoolDown()) < 0;
    }

    private boolean userCoolDownPolicyFailed(@NonNull User executor, @NonNull Instant now) {
        final Instant lastExecutionTime = getLastExecutionTime(executor.getUserId());
        if (lastExecutionTime == null) {
            return false;
        }
        final Duration durationSinceLastExecution = Duration.between(lastExecutionTime, now);
        final Duration userCoolDown = findCoolDownFor(executor).orElse(policy.getGlobalCoolDown());

        return durationSinceLastExecution.compareTo(userCoolDown) < 0;
    }


    @Synchronized
    private Instant getLastExecutionTime(@NonNull String userId) {
        return lastExecutionTimePerUserId.get(userId);
    }

    @Synchronized
    private void setLastExecutionTime(@NonNull String userId, @NonNull Instant time) {
        lastExecutionTimePerUserId.put(userId, time);
    }

    @Synchronized
    public void cleanup() {
        final Instant nowMinusCoolDown = Instant.now().minus(maxUserCoolDown);
        lastExecutionTimePerUserId.values().removeIf(nowMinusCoolDown::isAfter);
    }
}
