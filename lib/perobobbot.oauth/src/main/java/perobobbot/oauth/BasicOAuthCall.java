package perobobbot.oauth;

import lombok.NonNull;

public interface BasicOAuthCall<T> extends OAuthCall {

    @NonNull T call(@NonNull ApiToken apiToken) throws Throwable;

    @Override
    default <R> @NonNull R accept(@NonNull Visitor<R> visitor) throws Throwable {
        return visitor.visit(this);
    }
}
