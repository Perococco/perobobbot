package perococco.bot.program.core;

import bot.common.lang.User;
import bot.program.core.ExecutionPolicy;
import lombok.*;

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

    public boolean canExecute(@NonNull User executor, @NonNull Instant now) {
        if (userPolicyFailed(executor)) {
            return false;
        }
        if (globalCoolDownPolicyFailed(now)) {
            return false;
        }
        if (userCoolDownPolicyFailed(executor.userId(),now)) {
            return false;
        }
        lastExecutionTime = now;
        setLastExecutionTime(executor.userId(),now);
        return true;
    }

    private boolean userPolicyFailed(@NonNull User executor) {
       return !executor.canActAs(policy.requiredRole());
    }

    private boolean globalCoolDownPolicyFailed(@NonNull Instant now) {
        final Duration durationSinceLastExecution = Duration.between(lastExecutionTime, now);
        return durationSinceLastExecution.compareTo(policy.globalCoolDown()) < 0;
    }

    private boolean userCoolDownPolicyFailed(@NonNull String userId, @NonNull Instant now) {
        final Instant lastExecutionTime = getLastExecutionTime(userId);
        if (lastExecutionTime == null) {
            return false;
        }
        final Duration durationSinceLastExecution = Duration.between(lastExecutionTime, now);
        return durationSinceLastExecution.compareTo(policy.userCoolDown()) < 0;
    }


    @Synchronized
    private Instant getLastExecutionTime(@NonNull String userId) {
        return lastExecutionTimePerUserId.get(userId);
    }

    @Synchronized
    private void setLastExecutionTime(@NonNull String userId, @NonNull Instant time) {
        lastExecutionTimePerUserId.put(userId,time);
    }

    @Synchronized
    public void cleanup() {
        final Instant nowMinusCooldown = Instant.now().minus(policy.userCoolDown());
        lastExecutionTimePerUserId.values().removeIf(nowMinusCooldown::isAfter);
    }
}
