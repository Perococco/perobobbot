package perococco.perobobbot.program.core;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.common.lang.User;
import perobobbot.program.core.ExecutionPolicy;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class ExecutionInfo {

    @NonNull
    @Getter
    private final String instructionName;

    @NonNull
    private final ExecutionPolicy policy;

    private Instant lastExecutionTime = Instant.MIN;

    private final Map<String, Instant> lastExecutionTimePerUserId = new HashMap<>();

    private final Duration maxUserCoolDown;

    public ExecutionInfo(@NonNull String instructionName, @NonNull ExecutionPolicy policy) {
        this.instructionName = instructionName;
        this.policy = policy;
        this.lastExecutionTime = lastExecutionTime;
        this.maxUserCoolDown = policy.maxUserCoolDown().orElse(Duration.ZERO);
    }

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

    private Duration findCoolDown(@NonNull User executor) {
        return policy.getCoolDowns()
                     .entrySet()
                     .stream()
                     .filter(e -> executor.canActAs(e.getKey()))
                     .map(e -> e.getValue())
                     .min(Duration::compareTo)
                     .orElse(Duration.ofSeconds(15));
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
        final Duration userCoolDown = findCoolDown(executor);
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
