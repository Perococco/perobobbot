package perococco.perobobbot.access;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.access.AccessRule;
import perobobbot.access.Policy;
import perobobbot.access.PolicyManager;
import perobobbot.access.UnknownPolicy;
import perobobbot.common.lang.SmartLock;
import perobobbot.common.lang.User;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class PerococcoPolicyManager implements PolicyManager {

    private final Map<UUID, AccessData> policies = new HashMap<>();

    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final SmartLock writeLock = new SmartLock(lock.writeLock());
    private final SmartLock readLock = new SmartLock(lock.writeLock());

    @Override
    public @NonNull Policy createPolicy(@NonNull AccessRule accessRule) {
        return writeLock.get(() -> {
            final UUID policyId = getAvailableUUID();
            final PerococcoPolicy policy = new PerococcoPolicy(this, policyId);
            policies.put(policyId, new AccessData(null, policyId, accessRule));
            return policy;
        });
    }

    public @NonNull Policy createChildPolicy(@NonNull UUID parentId, @NonNull AccessRule accessRule) {
        return writeLock.get(() -> {
            final AccessData parentData = getAccessData(parentId);
            final UUID policyId = getAvailableUUID();
            final PerococcoPolicy policy = new PerococcoPolicy(this, policyId);
            policies.put(policyId, new AccessData(parentData, policyId, accessRule));
            return policy;
        });
    }

    @Override
    public void cleanUp() {
        readLock.runLocked(() -> {
            policies.values().forEach(AccessData::cleanup);
        });
    }

    private @NonNull AccessData getAccessData(@NonNull UUID policyId) {
        final AccessData accessData = policies.get(policyId);
        if (accessData == null) {
            throw new UnknownPolicy(policyId);
        }
        return accessData;
    }

    private @NonNull UUID getAvailableUUID() {
        while (true) {
            final UUID policyId = UUID.randomUUID();
            if (!policies.containsKey(policyId)) {
                return policyId;
            }
        }
    }

    public void run(@NonNull UUID policyId, @NonNull User executor, @NonNull Instant executionTime, @NonNull Runnable runIfAllowed) {
        readLock.getLocked(() -> getAccessData(policyId))
                .run(executor, executionTime, runIfAllowed);
    }

    @RequiredArgsConstructor
    private static class AccessData {

        @Getter
        private final AccessData parent;

        @Getter
        private final @NonNull UUID policyId;

        @Getter
        private final @NonNull AccessRule rule;

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

        public AccessData(AccessData parent, @NonNull UUID policyId, @NonNull AccessRule rule) {
            this.parent = parent;
            this.policyId = policyId;
            this.rule = rule;
            this.maxUserCoolDown = rule.maxCooldownForRole().orElse(Duration.ZERO);
        }

        @Synchronized
        public void cleanup() {
            final Instant nowMinusCoolDown = Instant.now().minus(maxUserCoolDown);
            lastExecutionTimePerUserId.values().removeIf(nowMinusCoolDown::isAfter);
        }

        @Synchronized
        public void run(@NonNull User executor, @NonNull Instant executionTime, @NonNull Runnable runIfAllowed) {
            if (isAllowed(executor,executionTime)) {
                try {
                    runIfAllowed.run();
                } finally {
                    setExecuted(executor,executionTime);
                }
            }
        }

        private void setExecuted(@NonNull User executor,@NonNull Instant executionTime) {
            if (parent != null) {
                parent.setExecuted(executor,executionTime);
            }
            lastExecutionTime = executionTime;
            setLastExecutionTime(executor.getUserId(),executionTime);
        }

        private boolean isAllowed(@NonNull User executor, @NonNull Instant executionTime) {
            if (!parentIsAllowed(executor,executionTime)) {
                return false;
            }
            if (userPolicyFailed(executor)) {
                return false;
            }
//            if (globalCoolDownPolicyFailed(executionTime)) {
//                return false;
//            }
            return !userCoolDownPolicyFailed(executor, executionTime);
        }

        private boolean parentIsAllowed(@NonNull User executor, @NonNull Instant executionTime) {
            return parent == null || parent.isAllowed(executor,executionTime);
        }

        private boolean userPolicyFailed(@NonNull User executor) {
            return !executor.canActAs(rule.getRequiredRole());
        }

        private boolean globalCoolDownPolicyFailed(@NonNull Instant now) {
            final Duration durationSinceLastExecution = Duration.between(lastExecutionTime, now);
            return durationSinceLastExecution.compareTo(rule.getDefaultCooldown()) < 0;
        }

        private boolean userCoolDownPolicyFailed(@NonNull User executor, @NonNull Instant now) {
            final Instant lastExecutionTime = getLastExecutionTime(executor.getUserId());
            if (lastExecutionTime == null) {
                return false;
            }
            final Duration durationSinceLastExecution = Duration.between(lastExecutionTime, now);
            final Duration userCoolDown = rule.findForUserOrGlobalCoolDown(executor);

            return durationSinceLastExecution.compareTo(userCoolDown) < 0;
        }

        private Instant getLastExecutionTime(@NonNull String userId) {
            return lastExecutionTimePerUserId.get(userId);
        }

        private void setLastExecutionTime(@NonNull String userId, @NonNull Instant time) {
            lastExecutionTimePerUserId.put(userId, time);
        }


    }
}
