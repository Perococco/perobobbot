package perobobbot.twitch.client.webclient.spring;

import lombok.*;
import org.aspectj.lang.reflect.MethodSignature;
import perobobbot.lang.Scope;
import perobobbot.lang.TokenType;
import perobobbot.oauth.RequiredScope;
import perobobbot.oauth.RequiredToken;

import java.util.Optional;

@Value
@Builder
public class CallRequirements {

    public static @NonNull Optional<CallRequirements> createFromSignature(@NonNull MethodSignature methodSignature) {
        {
            final var requiredToken = methodSignature.getMethod().getAnnotation(RequiredToken.class);
            if (requiredToken == null) {
                return Optional.empty();
            }
            final var requiredScope = methodSignature.getMethod().getAnnotation(RequiredScope.class);
            return Optional.ofNullable(builder().tokenType(requiredToken.value())
                                                .scope(requiredScope == null ? null : Scope.basic(requiredScope.value()))
                                                .optional(requiredScope == null || requiredScope.optional())
                                                .build());
        }

    }


    @NonNull TokenType tokenType;
    @Getter(AccessLevel.NONE)
    Scope scope;
    boolean optional;

    public @NonNull Optional<Scope> getScope() {
        return Optional.ofNullable(scope);
    }

    public boolean hasOptionalOrNoScope() {
        return scope == null || optional;
    }
}
