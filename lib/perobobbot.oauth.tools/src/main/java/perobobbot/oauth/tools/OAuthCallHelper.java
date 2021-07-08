package perobobbot.oauth.tools;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import perobobbot.oauth.OAuthCall;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class OAuthCallHelper {

    public static final int MAX_NB_TRIES = 2;

    private final @NonNull OAuthCall oauthCall;

    private final @NonNull ApiTokenHelper apiTokenHelper;

    private Object call() throws Throwable {
        int nbTries = 0;

        apiTokenHelper.initialize();

        while (true) {
            nbTries++;
            final Throwable error;
            try {
                final var token = apiTokenHelper.getToken().orElseThrow(() -> new IllegalArgumentException("No token set"));
                return oauthCall.call(token);
            } catch (Throwable throwable) {
                if (!isCausedByInvalidAccessToken(throwable)) {
                    throw throwable;
                }
                error = throwable;
            }
            final boolean noMoreTryAvailable = nbTries >= MAX_NB_TRIES;
            if (noMoreTryAvailable) {
                apiTokenHelper.deleteToken();
                throw error;
            }

            final var couldRefreshToken = apiTokenHelper.refreshToken();
            if (!couldRefreshToken) {
                apiTokenHelper.deleteToken();
                throw error;
            }
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

    public static @NonNull Object callWithTokenIdentifier(@NonNull OAuthCall oauthCall,
                                                          @NonNull ApiTokenHelper helper) throws Throwable {
        return new OAuthCallHelper(oauthCall, helper).call();
    }


}
