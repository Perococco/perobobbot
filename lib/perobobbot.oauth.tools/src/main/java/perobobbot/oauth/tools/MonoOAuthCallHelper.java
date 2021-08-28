package perobobbot.oauth.tools;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.oauth.MonoOAuthCall;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class MonoOAuthCallHelper<T> implements OAuthCallHelper<Mono<T>> {

    private final @NonNull MonoOAuthCall<T> oauthCall;

    private final @NonNull ApiTokenHelper apiTokenHelper;

    public @NonNull Mono<T> call() {
        return Mono.defer(this::callBasic);
    }

    private @NonNull Mono<T> callBasic() {
        try {
            final var result = BasicOAuthCallHelper.callWithTokenIdentifier(oauthCall.toBasic(), apiTokenHelper);
            return Mono.just(result);
        } catch (Throwable throwable) {
            return Mono.error(throwable);
        }

    }

}
