package perobobbot.twitch.client.webclient.spring;

import lombok.NonNull;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import perobobbot.lang.CastTool;
import perobobbot.lang.Caster;
import perobobbot.lang.Platform;
import perobobbot.oauth.tools.OAuthTokenHelper;

/**
 * Setup the OAuthContext with correct Token if it exists.
 * After the aspect has been applied, the {@link perobobbot.oauth.OAuthContext}
 * contains the {@link perobobbot.oauth.CallRequirements} and the User/Client token
 * associated with this <code>CallRequirements</code> and the <code>tokenIdentifier</code>
 * as well.
 *
 * If no token could be found, none will be set in the context.
 */
@Aspect
@Component
@Order(1)
public class TwitchOAuthTokenInitializer {

    private final static Caster<MethodSignature> METHOD_SIGNATURE_CASTER = CastTool.caster(MethodSignature.class);

    private final @NonNull OAuthTokenHelper oAuthTokenHelper;

    public TwitchOAuthTokenInitializer(@NonNull OAuthTokenHelper.Factory factory) {
        this.oAuthTokenHelper = factory.create(Platform.TWITCH, new TwitchOAuthAnnotationProvider().createCallRequirementFactory());
    }

    @Before(value = "perobobbot.twitch.client.webclient.spring.TwitchApiArchitectures.allCallsToTwitchApi()")
    public void initializeTwitchOauthToken(JoinPoint joinPoint) {
        METHOD_SIGNATURE_CASTER.cast(joinPoint.getSignature())
                               .map(MethodSignature::getMethod)
                               .ifPresent(oAuthTokenHelper::initializeOAuthContext);
    }

}
