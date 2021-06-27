package perobobbot.oauth;

import lombok.*;
import perobobbot.lang.Scope;
import perobobbot.lang.TokenType;

import java.lang.reflect.Method;
import java.util.Optional;

@Value
@Builder
public class ScopeRequirements {

    public interface Factory {
        @NonNull Optional<ScopeRequirements> createFromMethod(@NonNull Method method);
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
