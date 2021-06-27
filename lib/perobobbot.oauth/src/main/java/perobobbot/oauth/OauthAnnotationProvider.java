package perobobbot.oauth;

import lombok.NonNull;

import java.lang.reflect.Method;
import java.util.Optional;

public interface OauthAnnotationProvider {

    @NonNull Optional<RequiredScope> findRequiredScope(@NonNull Method method);

    @NonNull Optional<RequiredToken> findRequiredToken(@NonNull Method method);


    default @NonNull ScopeRequirementExtractor createScopeRequirementExtractor() {
        return new ScopeRequirementExtractor(this);
    }
}
