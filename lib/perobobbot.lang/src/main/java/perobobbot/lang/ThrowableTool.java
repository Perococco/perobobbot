package perobobbot.lang;

import lombok.NonNull;

import java.io.InterruptedIOException;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author perococco
 **/
public class ThrowableTool {

    public static boolean isCausedByAnInterruption(@NonNull Throwable throwable) {
        if (isInterruption(throwable)) {
            return true;
        }
        return isCausedByAnInterruption(throwable, new IdentityHashSet<>());
    }

    public static void interruptThreadIfCausedByInterruption(@NonNull Throwable throwable) {
        if (Thread.currentThread().isInterrupted()) {
            return;
        }
        if (isCausedByAnInterruption(throwable)) {
            Thread.currentThread().interrupt();
        }
    }

    private static boolean isCausedByAnInterruption(@NonNull Throwable throwable, @NonNull Set<Throwable> checked) {
        if (isInterruption(throwable)) {
            return true;
        }
        checked.add(throwable);
        return Stream.concat(
                Stream.of(throwable.getCause()),
                Stream.of(throwable.getSuppressed())
        )
                     .filter(Objects::nonNull)
                     .filter(e -> !checked.contains(e))
                     .anyMatch(t -> isCausedByAnInterruption(t, checked));
    }

    private static boolean isInterruption(@NonNull Throwable throwable) {
        return throwable instanceof InterruptedException || throwable instanceof InterruptedIOException;
    }

    public static @NonNull String formCauseMessageChain(@NonNull Throwable throwable) {
        return Stream.iterate(throwable, Objects::nonNull, Throwable::getCause)
                     .map(Throwable::getMessage)
                     .collect(Collectors.joining(" > "));
    }
}
