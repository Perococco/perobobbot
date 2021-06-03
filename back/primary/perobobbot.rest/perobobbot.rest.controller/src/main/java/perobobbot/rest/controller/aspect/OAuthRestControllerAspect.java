package perobobbot.rest.controller.aspect;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import perobobbot.lang.CastTool;
import perobobbot.lang.Caster;
import perobobbot.oauth.LoginTokenIdentifier;
import perobobbot.oauth.Markers;
import perobobbot.oauth.OAuthContextHolder;
import perobobbot.oauth.TokenIdentifier;

import java.util.Optional;

/**
 * Aspect that add the login of the identified user to the OAuthContext
 */
@Aspect
@Component
@Log4j2
public class OAuthRestControllerAspect {

    private final static Caster<UserDetails> USER_DETAILS_CASTER = CastTool.caster(UserDetails.class);

    /**
     * Wrap all calls to all RestControllers
     * @param joinPoint the wrapped point cut
     * @return the result of the rest call
     * @throws Throwable any error thrown by the rest call
     */
    @Around(value = "perobobbot.rest.controller.aspect.RestPointArchitecture.allRestCalls()")
    public Object setTokenIdentifier(ProceedingJoinPoint joinPoint) throws Throwable {
        this.initializeOAuthContextWithAuthenticatedUserLogin();
        try {
            return joinPoint.proceed();
        } finally {
            OAuthContextHolder.remove();
        }
    }

    private void initializeOAuthContextWithAuthenticatedUserLogin() {
        final var identifier = retrieveIdentifier().orElse(null);
        if (identifier != null) {
            LOG.info(Markers.OAUTH_MARKER,"Got token identifier {}",identifier);
            OAuthContextHolder.getContext().setTokenIdentifier(identifier);
        } else {
            LOG.info(Markers.OAUTH_MARKER,"No token identifier set");
        }
    }

    private @NonNull Optional<TokenIdentifier> retrieveIdentifier() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                       .map(Authentication::getPrincipal)
                       .flatMap(USER_DETAILS_CASTER)
                       .map(UserDetails::getUsername)
                       .map(LoginTokenIdentifier::new);

    }
}
