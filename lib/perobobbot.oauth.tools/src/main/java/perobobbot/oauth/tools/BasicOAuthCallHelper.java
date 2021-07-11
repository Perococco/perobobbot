package perobobbot.oauth.tools;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import perobobbot.lang.fp.TryResult;
import perobobbot.oauth.BasicOAuthCall;

@RequiredArgsConstructor
@Log4j2
public class BasicOAuthCallHelper<T> implements OAuthCallHelper<T> {

    private final @NonNull BasicOAuthCall<T> oauthCall;

    private final @NonNull ApiTokenHelper apiTokenHelper;

    public T call() throws Throwable {
        boolean alreadyRefreshed = false;
        apiTokenHelper.initialize();

        do {
            final var result = performCall();
            final var error = result.failure().orElse(null);
            if (error == null) {
                return result.get();
            }

            final boolean retry;
            if (alreadyRefreshed) {
                LOG.error("Call not authorized and token has been refreshed -> fail");
                retry = false;
            } else {
                alreadyRefreshed = true;
                LOG.warn("Call not authorized. Refreshing token");
                retry = apiTokenHelper.refreshToken();
            }

            if (!retry) {
                apiTokenHelper.deleteToken();
                throw error;
            }
        } while (true);
    }


    private TryResult<Throwable, T> performCall() throws Throwable {
        final var token = apiTokenHelper.getToken().orElseThrow(() -> new IllegalArgumentException("No token set"));
        try {
            return TryResult.success(oauthCall.call(token));
        } catch (Throwable t) {
            if (isCausedByInvalidAccessToken(t)) {
                return TryResult.failure(t);
            }
            throw t;
        }
    }


    private boolean isCausedByInvalidAccessToken(@NonNull Throwable throwable) {
        if (throwable instanceof WebClientResponseException) {
            final var status = ((WebClientResponseException) throwable).getStatusCode();
            return switch (status) {
                case UNAUTHORIZED, BAD_REQUEST -> true;
                default -> false;
            };
        }
        return false;
    }

    public static @NonNull <T> T callWithTokenIdentifier(@NonNull BasicOAuthCall<T> oauthCall,
                                                          @NonNull ApiTokenHelper helper) throws Throwable {
        return new BasicOAuthCallHelper<>(oauthCall, helper).call();
    }


}
