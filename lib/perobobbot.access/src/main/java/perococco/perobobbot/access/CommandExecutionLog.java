package perococco.perobobbot.access;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.access.AccessRule;
import perobobbot.access.Launcher;
import perobobbot.lang.ChatUser;
import perobobbot.lang.SmartLock;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class CommandExecutionLog {

    private final SmartLock lock = SmartLock.reentrant();
    /**
     * The instant of the last execution (whoever executed it)
     */
    private Instant lastExecutionTime = Instant.MIN;

    /**
     * The last execution per user
     */
    private final Map<String, Instant> lastExecutionTimePerUserId = new HashMap<>();

    private @NonNull Duration maxCoolDownTime = Duration.ofDays(1);

    public @NonNull Launcher createLauncher(@NonNull ChatUser chatUser) {
        return (runnable, accessRule, executionTime) -> {
            final var il = new InnerLauncher(chatUser, accessRule, executionTime, runnable);
            return lock.getLocked(il::launch);
        };
    }

    public boolean cleanUp() {
        return lock.getLocked(() -> {
            final Instant nowMinusCoolDown = Instant.now().minus(maxCoolDownTime);
            if (lastExecutionTime.isBefore(nowMinusCoolDown)) {
                this.lastExecutionTimePerUserId.clear();
                return true;
            }
            this.lastExecutionTimePerUserId.values().removeIf(i -> i.isBefore(nowMinusCoolDown));
            return this.lastExecutionTimePerUserId.isEmpty();
        });
    }

    private void setLastExecutionTime(@NonNull String userId, @NonNull Instant time) {
        lastExecutionTime = time;
        lastExecutionTimePerUserId.put(userId, time);
    }

    @RequiredArgsConstructor
    private class InnerLauncher {

        private final @NonNull ChatUser chatUser;
        private final @NonNull AccessRule accessRule;
        private final @NonNull Instant executionTime;
        private final @NonNull Runnable action;

        private boolean launch() {
            this.updateMaxCoolDown();
            if (isUserAllowedToLaunchAction()) {
                try {
                    launchAction();
                } finally {
                    setExecuted();
                }
                return true;
            }
            return false;
        }

        private void updateMaxCoolDown() {
            maxCoolDownTime = accessRule.maxCoolDownForRole().orElse(Duration.ZERO);
        }

        private boolean isUserAllowedToLaunchAction() {
            return userCanLaunchTheCommand() && commandNotOnCoolDown();
        }

        private void launchAction() {
            action.run();
        }

        private void setExecuted() {
            setLastExecutionTime(chatUser.getUserId(), executionTime);
        }

        private boolean userCanLaunchTheCommand() {
            return !chatUser.canActAs(accessRule.getRequiredRole());
        }

        private boolean commandNotOnCoolDown() {
            final Instant lastExecutionTime = getLastExecutionTime(chatUser.getUserId());
            if (lastExecutionTime == null) {
                return false;
            }
            final Duration durationSinceLastExecution = Duration.between(lastExecutionTime, executionTime);
            final Duration userCoolDown = accessRule.findForUserOrGlobalCoolDown(chatUser);

            return durationSinceLastExecution.compareTo(userCoolDown) < 0;
        }

        private Instant getLastExecutionTime(@NonNull String userId) {
            return lastExecutionTimePerUserId.get(userId);
        }


    }
}
