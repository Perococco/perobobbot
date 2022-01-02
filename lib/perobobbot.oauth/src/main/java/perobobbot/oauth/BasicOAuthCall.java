package perobobbot.oauth;

import lombok.NonNull;
import reactor.util.annotation.Nullable;

public interface BasicOAuthCall<T> extends OAuthCall {

    @Nullable
    T call(@NonNull ApiToken apiToken) throws Throwable;

    @Override
    default <R> @NonNull R accept(@NonNull Visitor<R> visitor) throws Throwable {
        return visitor.visit(this);
    }
}
