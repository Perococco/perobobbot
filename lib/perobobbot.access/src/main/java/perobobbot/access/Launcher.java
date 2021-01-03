package perobobbot.access;

import lombok.NonNull;

import java.time.Instant;

public interface Launcher {

    boolean launch(@NonNull AccessRule accessRule,
                   @NonNull Instant executionTime,
                   @NonNull Runnable runnable);
}
