package perobobbot.oauth._private;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.Scope;
import perobobbot.oauth.CallRequirements;
import perobobbot.oauth.OauthAnnotationProvider;

import java.lang.reflect.Method;
import java.util.Optional;

@RequiredArgsConstructor
public class CRFWithAnnotationProvider implements CallRequirements.Factory {

    private final @NonNull OauthAnnotationProvider annotationProvider;

    @Override
    public @NonNull Optional<CallRequirements> create(@NonNull Method method) {

        final var requiredToken = annotationProvider.findRequiredToken(method).orElse(null);
        if (requiredToken == null) {
            return Optional.empty();
        }
        final var requiredScope = annotationProvider.findRequiredScope(method).orElse(null);

        return Optional.ofNullable(CallRequirements.builder().tokenType(requiredToken.value())
                                                   .scope(requiredScope == null ? null : Scope.basic(
                                                           requiredScope.value()))
                                                   .optional(requiredScope == null || requiredScope.optional())
                                                   .build());
    }

}
