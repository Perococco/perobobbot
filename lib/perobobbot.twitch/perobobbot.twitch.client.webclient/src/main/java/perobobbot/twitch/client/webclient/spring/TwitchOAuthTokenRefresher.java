package perobobbot.twitch.client.webclient.spring;

import lombok.NonNull;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import perobobbot.lang.Platform;
import perobobbot.lang.fp.Try0;
import perobobbot.lang.fp.TryResult;
import perobobbot.oauth.tools.OAuthTokenHelper;

/**
 * Setup the OAuthContext with correct Token if it exists.
 * After the aspect has been applied, the {@link perobobbot.oauth.OAuthContext}
 * contains the {@link perobobbot.oauth.CallRequirements} and the User/Client token
 * associated with this <code>CallRequirements</code> and the <code>tokenIdentifier</code>
 * as well.
 * <p>
 * If no token could be found, none will be set in the context.
 */
@Aspect
@Component
@Order(2)
public class TwitchOAuthTokenRefresher {

    public static final int MAX_NB_TRIES = 2;

    private final @NonNull OAuthTokenHelper oAuthTokenHelper;

    public TwitchOAuthTokenRefresher(@NonNull OAuthTokenHelper.Factory factory) {
        this.oAuthTokenHelper = factory.create(Platform.TWITCH, new TwitchOAuthAnnotationProvider().createCallRequirementFactory());
    }

    @Around(value = "perobobbot.twitch.client.webclient.spring.TwitchApiArchitectures.allCallsToTwitchApi()")
    public Object prepareToken(ProceedingJoinPoint joinPoint) throws Throwable {
        int nbTries = MAX_NB_TRIES;

        while (true) {
            final var tryResult = this.callMethod(joinPoint);
            nbTries--;

            final var result = tryResult.success().orElse(null);
            final var error = tryResult.failure().orElse(null);
            if (error == null) {
                return result;
            }

            final boolean noMoreTryAvailable = nbTries <= 0;
            final boolean causedByInvalidAccessToken = isCausedByInvalidAccessToken(error);

            if (noMoreTryAvailable && causedByInvalidAccessToken) {
                oAuthTokenHelper.removeTokenFromDb();
            }

            if (noMoreTryAvailable || !causedByInvalidAccessToken) {
                throw error;
            } else {
                final var couldRefreshToken = oAuthTokenHelper.refreshToken();
                if (!couldRefreshToken) {
                    oAuthTokenHelper.removeTokenFromDb();
                    throw error;
                }
            }
        }

    }

    private @NonNull TryResult<Throwable,Object> callMethod(@NonNull ProceedingJoinPoint proceedingJoinPoint) {
        return Try0.of(proceedingJoinPoint::proceed).fSafe();
    }

    private boolean isCausedByInvalidAccessToken(@NonNull Throwable throwable) {
        if (throwable instanceof WebClientResponseException) {
            return switch (((WebClientResponseException)throwable).getStatusCode()) {
                case UNAUTHORIZED, BAD_REQUEST -> true;
                default -> false;
            };
        }
        return false;
    }



}
