package perobobbot.oauth;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import java.lang.reflect.Method;
import java.util.Optional;

@Value
public class ProxyMethod {

    @NonNull Method methodWithoutToken;
    @NonNull Method methodWithToken;
    @NonNull OAuthRequirement oAuthRequirement;
    @Getter(AccessLevel.NONE)
    Integer tokenPosition;

    public ProxyMethod(@NonNull Method methodWithoutToken,
                       @NonNull Method methodWithToken,
                       @NonNull OAuthRequirement oAuthRequirement,
                       Integer tokenPosition) {
        this.methodWithoutToken = methodWithoutToken;
        this.methodWithToken = methodWithToken;
        this.oAuthRequirement = oAuthRequirement;
        this.tokenPosition = tokenPosition;
        if (tokenPosition != null && oAuthRequirement.hasNoRequirement()) {
            throw new IllegalArgumentException("A token is required but no oauth information is provided on method "+methodWithoutToken);
        }
    }

    public @NonNull Optional<Integer> getTokenPosition() {
        return Optional.ofNullable(tokenPosition);
    }

}
