package perobobbot.oauth;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.Scope;
import perobobbot.oauth.ScopeRequirements;
import perobobbot.oauth.OauthAnnotationProvider;

import java.lang.reflect.Method;
import java.util.Optional;

@RequiredArgsConstructor
public class ScopeRequirementExtractor {

    private final @NonNull OauthAnnotationProvider annotationProvider;

    public @NonNull Optional<ScopeRequirements> extract(@NonNull Method method) {

        final var requiredToken = annotationProvider.findRequiredToken(method).orElse(null);
        if (requiredToken == null) {
            return Optional.empty();
        }
        final var requiredScope = annotationProvider.findRequiredScope(method).orElse(null);

        return Optional.ofNullable(ScopeRequirements.builder().tokenType(requiredToken.value())
                                                    .scope(requiredScope == null ? null : Scope.basic(
                                                           requiredScope.value()))
                                                    .optional(requiredScope == null || requiredScope.optional())
                                                    .build());
    }

}
