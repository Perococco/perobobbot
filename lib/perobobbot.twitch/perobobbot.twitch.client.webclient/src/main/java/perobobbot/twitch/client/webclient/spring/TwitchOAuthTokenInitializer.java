package perobobbot.twitch.client.webclient.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import perobobbot.lang.CastTool;
import perobobbot.lang.Caster;

@Aspect
@RequiredArgsConstructor
public class TwitchOAuthTokenInitializer {

    private final static Caster<MethodSignature> METHOD_SIGNATURE_CASTER = CastTool.caster(MethodSignature.class);

    private final @NonNull OAuthTokenInitializer oAuthTokenInitializer;

    @Before("execution(* perobobbot.twitch.client.webclient.WebClientAppTwitchService.*(..))")
    public void initializeTwitchOauthToken(JoinPoint joinPoint) {
        METHOD_SIGNATURE_CASTER.cast(joinPoint.getSignature())
                               .ifPresent(oAuthTokenInitializer::initialize);
    }

}
