package perobobbot.oauth;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.fp.Function1;

import java.net.URI;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
public class UserOAuthInfo<T> {

    /**
     * The URI to GET to start the user OAuth process
     */
    @Getter
    private final @NonNull URI oauthURI;

    /**
     * A completion stage that will contain the user token
     * if the OAuth process succeeded
     */
    @Getter
    private final @NonNull CompletionStage<T> futureToken;

    public <U> @NonNull UserOAuthInfo<U> then(@NonNull Function1<? super T, ? extends U> mapper) {
        return new UserOAuthInfo<U>(
                oauthURI,
                futureToken.thenApply(mapper)
        );
    }

    public @NonNull T get() throws ExecutionException, InterruptedException {
        return this.futureToken.toCompletableFuture().get();
    }
}
