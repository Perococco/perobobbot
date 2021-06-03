package perobobbot.twitch.client.webclient.spring;

import lombok.NonNull;
import perobobbot.oauth.OauthAnnotationProvider;
import perobobbot.oauth.RequiredScope;
import perobobbot.oauth.RequiredToken;
import perobobbot.twitch.client.api.TwitchService;

import java.lang.reflect.Method;
import java.util.Optional;

public class TwitchOAuthAnnotationProvider implements OauthAnnotationProvider {

    @Override
    public @NonNull Optional<RequiredScope> findRequiredScope(@NonNull Method method) {
        return Optional.ofNullable(retrieveMethodOnTwitchService(method).getAnnotation(RequiredScope.class));
    }

    @Override
    public @NonNull Optional<RequiredToken> findRequiredToken(@NonNull Method method) {
        return Optional.ofNullable(retrieveMethodOnTwitchService(method).getAnnotation(RequiredToken.class));
    }

    private @NonNull Method retrieveMethodOnTwitchService(@NonNull Method method) {
        try {
            return TwitchService.class.getMethod(method.getName(),method.getParameterTypes());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
