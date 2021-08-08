package perobobbot.rest.controller;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import perobobbot.lang.Instants;
import perobobbot.lang.Platform;
import perobobbot.oauth.Token;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
@Component
public class OAuthSignIn {

    private static final Duration DELAY_BEFORE_REMOVE_PENDING_SIGN_IN = Duration.ofMinutes(5);

    private final @NonNull Instants instants;

    private final @NonNull Map<UUID, OAuthData> pendingSignIn = new HashMap<>();


    @Synchronized
    public @NonNull UUID addPendingSignIn(@NonNull Platform platform, @NonNull CompletionStage<Token> futureToken) {
        final var id = findAvailableUUID();
        final var data = new OAuthData(instants.now(), platform, futureToken);
        pendingSignIn.put(id, data);
        return id;
    }

    @Synchronized
    public @NonNull OAuthData getOAuthData(@NonNull UUID id) {
        final var data = pendingSignIn.get(id);
        if (data == null) {
            throw new IllegalStateException("No pending sign in for id "+id);
        }
        return data;
    }

    @Synchronized
    public void removeOAuthData(@NonNull UUID id) {
        pendingSignIn.remove(id);
    }


    @Synchronized
    @Scheduled(fixedDelay = 300_000)
    public void cleanUp() {
        final var now = instants.now();
        pendingSignIn.values()
                     .removeIf(c -> c.canBeDeleted(now));
    }


    private @NonNull UUID findAvailableUUID() {
        while (true) {
            final var id = UUID.randomUUID();
            if (!pendingSignIn.containsKey(id)) {
                return id;
            }
        }
    }


    @RequiredArgsConstructor
    @Getter
    public static class OAuthData {

        private final @NonNull Instant creationInstant;

        private final @NonNull Platform platform;

        private final @NonNull CompletionStage<Token> futureToken;


        public boolean canBeDeleted(@NonNull Instant now) {
            return creationInstant.plus(DELAY_BEFORE_REMOVE_PENDING_SIGN_IN).isBefore(now);
        }
    }
}
