package perobobbot.rest.controller.aspect;

import lombok.NonNull;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import perobobbot.lang.CastTool;
import perobobbot.lang.Caster;
import perobobbot.oauth.LoginTokenIdentifier;
import perobobbot.oauth.OAuthContextHolder;

import java.util.Optional;

@Aspect
public class OAuthTokenIdentifierSetter {

    private final static Caster<UserDetails> USER_DETAILS_CASTER = CastTool.caster(UserDetails.class);

    @Around(value = "perobobbot.rest.controller.aspect.RestPointArchitecture.allRestCall()")
    public Object setTokenIdentifier(ProceedingJoinPoint joinPoint) throws Throwable {
        this.initializeOAuthContextWithAuthenticatedUserLogin();
        try {
            return joinPoint.proceed();
        } finally {
            OAuthContextHolder.remove();
        }
    }

    private void initializeOAuthContextWithAuthenticatedUserLogin() {
        retrieveUserDetails()
                .map(UserDetails::getUsername)
                .map(LoginTokenIdentifier::new)
                .ifPresent(identifier -> OAuthContextHolder.getContext().setTokenIdentifier(identifier));
    }

    private @NonNull Optional<UserDetails> retrieveUserDetails() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                       .map(Authentication::getPrincipal)
                       .flatMap(USER_DETAILS_CASTER);
    }
}
