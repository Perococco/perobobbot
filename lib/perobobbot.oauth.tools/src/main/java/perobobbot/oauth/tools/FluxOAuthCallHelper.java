package perobobbot.oauth.tools;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.oauth.FluxOAuthCall;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class FluxOAuthCallHelper<T> implements OAuthCallHelper<Flux<T>>  {

    private final @NonNull FluxOAuthCall<T> oauthCall;

    private final @NonNull ApiTokenHelper apiTokenHelper;

    public Flux<T> call() throws Throwable {
        return Flux.defer(this::callBasic);
    }

    private @NonNull Flux<T> callBasic() {
        try {
            final var result = BasicOAuthCallHelper.callWithTokenIdentifier(oauthCall.toBasic(), apiTokenHelper);
            return Flux.fromIterable(result);
        } catch (Throwable throwable) {
            return Flux.error(throwable);
        }

    }

}
